package com.tzs.marshall.author.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminTemplateController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String dashboard() {
        return "admin/adminhome";
    }

    @RequestMapping(value = "/findauthor", method = RequestMethod.GET)
    public String findAuthor() {
        return "admin/findAuthor";
    }

    @RequestMapping(value = "/authorinfo", method = RequestMethod.GET)
    public String authorInfo() {
        return "admin/authorDetail";
    }

    @RequestMapping(value = "/findfile", method = RequestMethod.GET)
    public String findFile() {
        return "admin/findFile";
    }

    @RequestMapping(value = "/editfile", method = RequestMethod.GET)
    public String editFile() {
        return "admin/editFile";
    }

    @RequestMapping(value = "/content/fileupload", method = RequestMethod.GET)
    public String createReport() {
        return "admin/createReport";
    }

    @RequestMapping(value = "/editreport", method = RequestMethod.GET)
    public String editReport() {
        return "admin/editReport";
    }

    @RequestMapping(value = "/findorder", method = RequestMethod.GET)
    public String findOrder() {
        return "admin/findOrder";
    }

    @RequestMapping(value = "/editorder", method = RequestMethod.GET)
    public String editOrder() {
        return "admin/editOrder";
    }

    @RequestMapping(value = "/updateqrcode", method = RequestMethod.GET)
    public String qrCode() {
        return "admin/updateQrCode";
    }

}
