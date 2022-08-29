package com.tzs.marshall.controller.prelogin;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.NewsLetterEmailSubs;
import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.constants.RequestTypeDictionary;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.mailsender.EmailBean;
import com.tzs.marshall.mailsender.EmailService;
import com.tzs.marshall.service.AuthorPreLoginService;
import com.tzs.marshall.service.AuthorRegistrationService;
import com.tzs.marshall.token.ConfirmationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.tzs.marshall.constants.Constants.*;


/*
PreLoginRestController APIs
1. Login
2. Registration
3. Forgot Password
4. Reset Password
5. OTP-Verification
*/

@RestController
@RequestMapping("/init")
public class PreLoginRestController {

    @Autowired
    private AuthorPreLoginService authorPreLoginService;
    @Autowired
    private AuthorRegistrationService authorRegistrationService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(PreLoginRestController.class);


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public PersistentUserDetails userRegistration(@RequestBody PersistentUserDetails authorDetails, HttpServletRequest request) {
        log.info("Registering new user with details as: " + authorDetails);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        String url = String.valueOf(request.getRequestURL().replace(request.getRequestURL().toString().indexOf(String.valueOf(request.getServerPort())) + (String.valueOf(request.getServerPort()).length()), request.getRequestURL().toString().length(), ""));
        authorDetails.setRoleName(Constants.ROLE_USER);
        authorDetails.setTypeName(Constants.TYPE_REGISTERED);
        return authorRegistrationService.registerNewUser(authorDetails, url);
    }

    @RequestMapping(value = "/enable-account")
    public ModelAndView enableAccount(@RequestParam Map<String, String> allRequestParams, HttpSession session) {
        log.info("Confirming Token...");
        String token = allRequestParams.get("token");
        String reqType = allRequestParams.get("reqType");
        log.info("Token: " + token + " & reqType: " + reqType);
        ModelAndView modelAndView = new ModelAndView();
        try {
            String flag = authorRegistrationService.enableAccountTokenHandler(token, reqType);
            if (flag.equalsIgnoreCase("success")) {
                session.setAttribute("successMessage", MessageConstants.ACCOUNT_VERIFIED);
                modelAndView.setViewName("redirect:/login");
            }
        } catch (Exception e) {
            session.setAttribute("errorMessage", e.getMessage());
            modelAndView.setViewName("redirect:/init/resend-token?token=" + token + "&reqType=" + reqType);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/resend-token", method = RequestMethod.POST)
    public ModelAndView resendValidationToken(@RequestParam Map<String, String> allRequestParams, HttpServletRequest request, HttpSession session) {
        log.info("Resending Token...");
        String token = allRequestParams.get("token");
        String reqType = allRequestParams.get("reqType");
        log.info("Token: " + token + " & reqType: " + reqType);
        ModelAndView modelAndView = new ModelAndView();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        String url = String.valueOf(request.getRequestURL().replace(request.getRequestURL().toString().indexOf(String.valueOf(request.getServerPort())) + (String.valueOf(request.getServerPort()).length()), request.getRequestURL().toString().length(), ""));
        try {
            token = confirmationTokenService.resendTokenHandler(token, reqType, url);
            session.setAttribute("successMessage", MessageConstants.TOKEN_SENT);
            modelAndView.setViewName("redirect:/login");
        } catch (Exception e) {
            log.error(e.getMessage());
            session.setAttribute("errorMessage", e.getMessage());
            modelAndView.setViewName("redirect:/init/resend-token?token=" + token + "&reqType=" + reqType);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    String forgotPassword(@RequestBody NewsLetterEmailSubs newsLetterEmailSubs,
                          HttpServletRequest request, HttpSession session) throws ResponseStatusException {
        log.info("fetching user email...");
        String token;
        try {
            PersistentUserDetails PersistentUserDetails = authorPreLoginService.handleFetchedValidUser(newsLetterEmailSubs.getEmail());
            log.info("user found!");
            log.info("Email: " + PersistentUserDetails.getEmail());
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            token = confirmationTokenService.tokenHandler(newsLetterEmailSubs.getEmail(), RequestTypeDictionary.PASSWORD.getReqType(), url);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return token;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    String resetPassword(@RequestParam Map<String, String> allRequestParams,
                         @RequestBody PersistentUserDetails PersistentUserDetails, HttpSession session) {
        log.info("Confirming Token...");
        String token = allRequestParams.get("token");
        String reqType = allRequestParams.get("reqType");
        String password = PersistentUserDetails.getPassword();
        log.info("Token: " + token + " & reqType: " + reqType);
        String flag;
        try {
            flag = authorPreLoginService.resetPasswordHandler(token, reqType, password);
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
        return DBProperties.properties.getProperty(property.toUpperCase());
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
}
