package com.tzs.marshall.author.controller.template;

import com.tzs.marshall.author.constants.Constants;
import com.tzs.marshall.author.constants.MessageConstants;
import com.tzs.marshall.author.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;



@Controller
public class PreLoginTemplateController {
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String getIndexPage(@RequestParam(value = "locale", required = false) String locale, HttpSession session) {
        if (locale != null) session.setAttribute("locale", locale);
        String page = getHome();
        if (page != null) return page;
        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String dashboard() {
        String page = getHome();
        if (page != null) return page;
        return "pages/login";
    }

    private String getHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ROLE_ADMIN))
                    || authentication.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ROLE_EDITOR))
                    || authentication.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ROLE_PRE_EDITOR)))
                return "redirect:/admin/home";
            else
                return "author/dashboard";
        }
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        if (error != null)
            session.setAttribute("errorMessage", MessageConstants.BAD_CREDENTIALS);
        return "pages/login";
    }

    @RequestMapping(value = "/init/reset-password", method = RequestMethod.GET)
    public String resetPassword(@RequestParam(value = "token", required = false) String token,
                                @RequestParam(value = "reqType", required = false) String reqType,
                                HttpSession session) {
        String page = "pages/resetpassword";
        if (token != null && reqType != null) {
            try {
                confirmationTokenService.confirmToken(token, reqType);
                session.setAttribute("successMessage", MessageConstants.TOKEN_VERIFIED);
            } catch (Exception e) {
                session.setAttribute("errorMessage", e.getMessage());
                page = "pages/login";
            }
        }
        return page;
    }

    @RequestMapping(value = "/init/resend-token", method = RequestMethod.GET)
    public String resetToken() {
        return "pages/resendToken";
    }

    @RequestMapping(value = "/init/forgot-password", method = RequestMethod.GET)
    public String forgotPassword() {
        return "pages/forgotpassword";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "pages/signup";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "pages/logout";
    }

    @RequestMapping(value = "/init/english-language-editing", method = RequestMethod.GET)
    public String getServicesPage1() {
        return "pages/english-language-editing";
    }

    @RequestMapping(value = "/init/scientific-editing", method = RequestMethod.GET)
    public String getServicesPage2() { return "pages/scientific-editing"; }

    @RequestMapping(value = "/init/academic-illustration", method = RequestMethod.GET)
    public String getServicesPage3() { return "pages/academic-illustration"; }

    @RequestMapping(value = "/init/conference-poster-creation", method = RequestMethod.GET)
    public String getServicesPage4() { return "pages/conference-poster-creation"; }

    @RequestMapping(value = "/init/figure-formatting", method = RequestMethod.GET)
    public String getServicesPage5() { return "pages/figure-formatting"; }

    @RequestMapping(value = "/init/graphical-abstract-service", method = RequestMethod.GET)
    public String getServicesPage6() { return "pages/graphical-abstract-service"; }

    @RequestMapping(value = "/init/journal-recommendation", method = RequestMethod.GET)
    public String getServicesPage7() { return "pages/journal-recommendation"; }

    @RequestMapping(value = "/init/research-news-stories", method = RequestMethod.GET)
    public String getServicesPage8() { return "pages/research-news-stories"; }

    @RequestMapping(value = "/init/contact-us", method = RequestMethod.GET)
    public String getContactUs() {
        return "pages/contactUs";
    }

    @RequestMapping(value = "/init/faq", method = RequestMethod.GET)
    public String getFaq() {
        return "pages/faq";
    }

    @RequestMapping(value = "/init/about-us", method = RequestMethod.GET)
    public String getAboutUs() {
        return "pages/aboutus";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {
        return "error/404";
    }


}
