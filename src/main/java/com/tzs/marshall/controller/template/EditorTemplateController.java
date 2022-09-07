package com.tzs.marshall.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/editor")
public class EditorTemplateController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String dashboard() {
        return "editor/editorhome";
    }

    @RequestMapping(value = "/findauthor", method = RequestMethod.GET)
    public String findAuthor() {
        return "editor/findAuthor";
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String authorInfo() {
        return "editor/authorDetail";
    }

    @RequestMapping(value = "/findfile", method = RequestMethod.GET)
    public String findFile() {
        return "editor/findFile";
    }

    @RequestMapping(value = "/editfile", method = RequestMethod.GET)
    public String editFile() {
        return "editor/editFile";
    }

    @RequestMapping(value = "/content/fileupload", method = RequestMethod.GET)
    public String createReport() {
        return "editor/createReport";
    }

    @RequestMapping(value = "/editreport", method = RequestMethod.GET)
    public String editReport() {
        return "editor/editReport";
    }

    @RequestMapping(value = "/findorder", method = RequestMethod.GET)
    public String findOrder() {
        return "editor/findOrder";
    }

    @RequestMapping(value = "/editorder", method = RequestMethod.GET)
    public String editOrder() {
        return "editor/editOrder";
    }

}
