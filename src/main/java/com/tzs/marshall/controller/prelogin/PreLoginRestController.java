package com.tzs.marshall.controller.prelogin;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.constants.RequestTypeDictionary;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.mailsender.EmailBean;
import com.tzs.marshall.mailsender.EmailService;
import com.tzs.marshall.service.UserPreLoginService;
import com.tzs.marshall.service.UserRegistrationService;
import com.tzs.marshall.token.ConfirmationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.tzs.marshall.constants.Constants.*;
import static com.tzs.marshall.constants.MessageConstants.*;


@RestController
@RequestMapping("/init")
public class PreLoginRestController {

    @Autowired
    private UserPreLoginService userPreLoginService;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(PreLoginRestController.class);


    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public PersistentUserDetails userRegistration(@RequestBody PersistentUserDetails userDetails, HttpServletRequest request) {
        log.info("Registering new user with details as: " + userDetails);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        String url = String.valueOf(request.getRequestURL().replace(request.getRequestURL().toString().indexOf(String.valueOf(request.getServerPort())) + (String.valueOf(request.getServerPort()).length()), request.getRequestURL().toString().length(), ""));
        userDetails.setRoleName(USER);
        userDetails.setTypeName(Constants.TYPE_REGISTERED);
        return userRegistrationService.registerUser(userDetails, url);
    }

    @RequestMapping(value = "/driver/register", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ProfileDetails driverRegistration(@RequestParam Map<String, String> allRequestParams, @ModelAttribute ProfileDetails profileDetails){
        String name = allRequestParams.get("name");
        profileDetails.setFirstName(name.substring(0, name.indexOf(" ")));
        profileDetails.setLastName(name.substring(name.indexOf(" ")+1));
        profileDetails.setPassword(allRequestParams.get("password"));
        profileDetails.setMobile(allRequestParams.get("mobile"));
        return userRegistrationService.registerDriver(profileDetails);

    }

    @RequestMapping(value = "/otp-verify", method = RequestMethod.POST)
    public ResponseEntity<String> postOTPVerification(@RequestBody Map<String, String> allRequestParams,
                                                HttpSession session) {
        log.info("Confirming Token...");
        String reqType = allRequestParams.get("reqType");
        String token = allRequestParams.get("token") != null ? allRequestParams.get("token") : allRequestParams.get("otp");
        log.info("Token: " + token + " & reqType: " + reqType);
        return verifyOTP(session, reqType, token);
    }

    @RequestMapping(value = "/otp-verification", method = RequestMethod.GET)
    public ResponseEntity<String> getOTPVerification(@RequestParam Map<String, String> allRequestParams,
                                                HttpSession session) {
        log.info("Confirming Token for admin...");
        String reqType = allRequestParams.get("reqType");
        String token = allRequestParams.get("token") != null ? allRequestParams.get("token") : allRequestParams.get("otp");
        log.info("Token: " + token + " & reqType: " + reqType);
        return verifyOTP(session, reqType, token);
    }

    private ResponseEntity<String> verifyOTP(HttpSession session, String reqType, String token) {
        ResponseEntity<String> body = new ResponseEntity<>("redirect:/login", HttpStatus.OK);
        String message;
        try {
            String confirmedToken;
            if ("enableAccount".equalsIgnoreCase(reqType)) {
                confirmedToken = userRegistrationService.enableAccountTokenHandler(token, reqType);
                message = ACCOUNT_VERIFIED;
            } else {
                confirmedToken =confirmationTokenService.confirmToken(token, reqType).getToken();
                message = TOKEN_VERIFIED;
            }
            if (confirmedToken.equalsIgnoreCase("success")) {
                session.setAttribute("successMessage", message);
                body = ResponseEntity
                        .status(HttpStatus.OK)
                        .body(confirmedToken);
            }
        } catch (Exception e) {
            session.setAttribute("errorMessage", e.getMessage());
            body = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return body;
    }

    @RequestMapping(value = "/resend-token", method = RequestMethod.POST)
    public ResponseEntity<String> resendValidationToken(@RequestBody Map<String, String> allRequestParams, HttpServletRequest request, HttpSession session) {
        log.info("Resending Token...");
        String token = allRequestParams.get("token") != null ? allRequestParams.get("token") : allRequestParams.get("otp");
        String reqType = allRequestParams.get("reqType");
        log.info("Token: " + token + " & reqType: " + reqType);
        ResponseEntity<String> body = new ResponseEntity<String>("redirect:/login", HttpStatus.OK);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        String url = String.valueOf(request.getRequestURL().replace(request.getRequestURL().toString().indexOf(String.valueOf(request.getServerPort())) + (String.valueOf(request.getServerPort()).length()), request.getRequestURL().toString().length(), ""));
        try {
            token = confirmationTokenService.resendTokenHandler(token, reqType, url);
            session.setAttribute("successMessage", MessageConstants.TOKEN_SENT);
            body = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            session.setAttribute("errorMessage", e.getMessage());
            body = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(e.getMessage());
        }
        return body;
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    String forgotPassword(@RequestBody NewsLetterEmailSubs newsLetterEmailSubs,
                          HttpServletRequest request, HttpSession session) throws ResponseStatusException {
        log.info("fetching user email...");
        String token;
        try {
            PersistentUserDetails userDetails = userPreLoginService.handleFetchedValidUser(newsLetterEmailSubs.getEmail());
            log.info("user found!");
            log.info("Email: " + userDetails.getEmail());
            String contact = userDetails.getEmail() != null ? userDetails.getEmail() : newsLetterEmailSubs.getEmail();
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            token = confirmationTokenService.tokenHandler(contact, RequestTypeDictionary.PASSWORD.getReqType(), userDetails.getRoleName(), url);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return token;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    String resetPassword(@RequestBody Map<String, String> allRequestParams) {
        log.info("Confirming Token...");
        String token = allRequestParams.get("token") != null ? allRequestParams.get("token") : allRequestParams.get("otp");
        String reqType = allRequestParams.get("reqType");
        String password = allRequestParams.get("password");
        log.info("Token: " + token + " & reqType: " + reqType);
        String flag;
        try {
            flag = userPreLoginService.resetPasswordHandler(token, reqType, password);
            if (flag.equalsIgnoreCase("success")) {
                return flag;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(e.getMessage());
        }
        return flag;
    }

    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public Object getProperties(@RequestParam("property") String property) {
        return DBProperties.splitString(DBProperties.properties.getProperty(property.toUpperCase()));
    }

    @RequestMapping(value = "/contact-us", method = RequestMethod.POST)
    public ModelAndView sendContactUsMail(@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute EmailBean emailBean,
                                          HttpSession session) {
        String errorMessage = null;
        String successMessage = null;
        try {
            if (file != null)
                emailBean.setAttachment(file);
            if (emailBean.getFrom() == null || emailBean.getSubject() == null || emailBean.getMessage() == null)
                throw new ApiException(MessageConstants.REQUIRED_PARAMS_MISSING);
            String subject = "[Contact-Us][FROM: " + emailBean.getFrom() + "] " + emailBean.getSubject();
            String message = "Issue: " + emailBean.getIssue() + "\nQuestion: " + emailBean.getQuestion() + "\n Message: \n" + emailBean.getSubject();
            emailBean.setFrom(DBProperties.properties.getProperty(CONTACT_US_EMAIL));
            emailBean.setTo(DBProperties.properties.getProperty(ADMIN_EMAIL));
            emailBean.setSubject(subject);
            emailBean.setMessage(message);
            emailService.send(emailBean);
            successMessage = MessageConstants.EMAIL_SENT;
        } catch (ApiException exception) {
            errorMessage = MessageConstants.EMAIL_FAILED;
        }
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("successMessage", successMessage);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/init/contact-us");
        return modelAndView;
    }

    @RequestMapping(value = "/csrf", method = RequestMethod.GET)
    public @ResponseBody String getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        return token.getToken();
    }
}
