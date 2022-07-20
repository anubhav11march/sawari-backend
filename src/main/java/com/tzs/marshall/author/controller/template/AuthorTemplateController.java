package com.tzs.marshall.author.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/author")
public class AuthorTemplateController {

    @RequestMapping(value = "/content/fileupload", method = RequestMethod.GET)
    public String fileUpload() {
        return "author/fileupload";
    }

    @RequestMapping(value = "/content/editfile", method = RequestMethod.GET)
    public String editFile() {
        return "author/editFile";
    }

    @RequestMapping(value = "/get-profile", method = RequestMethod.GET)
    public String getProfile() {
        return "author/profile";
    }

    @RequestMapping(value = "/content/find-file", method = RequestMethod.GET)
    public String getFindFile() {
        return "author/findFile";
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String getAllServices() {
        return "author/services";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order() {
        return "author/order";
    }

    @RequestMapping(value = "/reportinfo", method = RequestMethod.GET)
    public String reportInfo() {
        return "author/reportInfo";
    }

    @RequestMapping(value = "/findorder", method = RequestMethod.GET)
    public String findOrder() {
        return "author/findOrder";
    }

    @RequestMapping(value = "/orderdetail", method = RequestMethod.GET)
    public String orderDetail() {
        return "author/orderDetail";
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payment() {
        return "author/payment";
    }


}
