package com.tzs.marshall.author.controller.postlogin;

import com.tzs.marshall.author.bean.PersistentAuthorDetails;
import com.tzs.marshall.author.bean.PersistentAuthorRights;
import com.tzs.marshall.author.constants.Constants;
import com.tzs.marshall.author.filesystem.FileBean;
import com.tzs.marshall.author.filesystem.FileService;
import com.tzs.marshall.author.service.AuthorPostLoginService;
import com.tzs.marshall.author.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthorPostLoginService authorPostLoginService;
    @Autowired
    private FileService fileService;

    //Author's Rights
    @RequestMapping(value = "/authors/rights", method = RequestMethod.GET)
    public List<PersistentAuthorDetails> getAuthorsRights() {
        return adminService.getAuthorsRights();
    }

    @RequestMapping(value = "/author/rights", method = RequestMethod.GET)
    public PersistentAuthorDetails getAuthorRights(@RequestParam("authorId") String userId) {
        return adminService.getAuthorRightsById(Long.parseLong(userId));
    }

    @RequestMapping(value = "/author/rights", method = RequestMethod.PUT)
    public PersistentAuthorDetails updateAuthorRights(@RequestBody PersistentAuthorRights authorRights,
                                                      @AuthenticationPrincipal PersistentAuthorDetails adminDetails) {
        adminService.checkAuthorizedAdmin(adminDetails.getUserId());
        if ("TRUE".equalsIgnoreCase(authorRights.getIs_enable()))
            authorRights.setEnable(Constants.isEnable);
        else authorRights.setEnable(!Constants.isEnable);
        if ("TRUE".equalsIgnoreCase(authorRights.getIs_deleted()))
            authorRights.setDeleted(!Constants.isDeleted);
        else authorRights.setDeleted(Constants.isDeleted);

        return adminService.updateAuthorRights(authorRights);
    }

    //Authors' Profile
    //complete
    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public List<PersistentAuthorDetails> getAllAuthors() {
        return adminService.getAllCompleteProfileAuthors();
    }

    //Single Author Profile Details
    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public PersistentAuthorDetails getAuthorDetails(@RequestParam("authorId") String authorId) {
        return adminService.getAuthorDetailsById(Long.parseLong(authorId));
    }

    //Incomplete
    @RequestMapping(value = "/incomplete/authors", method = RequestMethod.GET)
    public List<PersistentAuthorDetails> getAuthors() {
        return adminService.getAllIncompleteProfileAuthors();
    }

    //Authors' Files
    @RequestMapping(value = "/authors/files", method = RequestMethod.GET)
    public List<FileBean> getAllFiles() {
        return fileService.fetchAllFiles();
    }

    @RequestMapping(value = "/author/files", method = RequestMethod.GET)
    public List<FileBean> getAllFilesByAuthor(@RequestParam("authorId") String authorId) {
        return fileService.findFilesHandler(Long.parseLong(authorId), Constants.ROLE_AUTHOR);
    }

    @RequestMapping(value = "/author/file", method = RequestMethod.GET)
    public FileBean getAuthorFile(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findAuthorFileById(Long.parseLong(allRequestParams.get("authorId")), Long.parseLong(allRequestParams.get("fileId")));
    }

    //Admin-Report for Authors
    @RequestMapping(value = "/author/file/reports", method = RequestMethod.GET)
    public List<FileBean> getEditorReportsForFile(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findReportsForAuthorFile(Long.parseLong(allRequestParams.get("authorId")), Long.parseLong(allRequestParams.get("fileId")));
    }

    @RequestMapping(value = "/author/file/report", method = RequestMethod.GET)
    public FileBean getEditorReportForFile(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findReportForAuthorFile(Long.parseLong(allRequestParams.get("authorId")),
                Long.parseLong(allRequestParams.get("fileId")), Long.parseLong(allRequestParams.get("reportId")));
    }

    @RequestMapping(value = "/author/reports", method = RequestMethod.GET)
    public List<FileBean> getEditorReportsForAuthor(@RequestParam Map<String, String> allRequestParams) {
        return fileService.findAllReportsForAuthor(Long.parseLong(allRequestParams.get("authorId")));
    }
}
