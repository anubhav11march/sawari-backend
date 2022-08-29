package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.filesystem.EditorFileService;
import com.tzs.marshall.filesystem.FileBean;
import com.tzs.marshall.service.editor.EditorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/editor")
public class EditorRestController {

    @Autowired
    private EditorService editorService;
    @Autowired
    private EditorFileService editorFileService;

    private static final Logger log = LoggerFactory.getLogger(EditorRestController.class);

    //Authors' Profile
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<PersistentUserDetails> getAllAuthorsAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails) {
        return editorService.getAllAuthorsAssignedToEditor(editorDetails.getUserId());
    }

    //Single Author Profile Details
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public PersistentUserDetails getAuthorDetailsAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails,
                                                                    @RequestParam("userId") String authorId) {
        return editorService.getAuthorDetailsByIdAssignedToEditor(editorDetails.getUserId(), Long.parseLong(authorId));
    }

    //Authors' Files
    @RequestMapping(value = "/users/files", method = RequestMethod.GET)
    public List<FileBean> getAllFilesAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails) {
        return editorFileService.fetchAllFilesAssignedToEditor(editorDetails.getUserId());
    }

    @RequestMapping(value = "/user/files", method = RequestMethod.GET)
    public List<FileBean> getAllFilesByAuthorAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails,
                                                              @RequestParam("userId") String authorId) {
        return editorFileService.findAuthorFilesAssignedToEditor(editorDetails.getUserId(),
                Long.parseLong(authorId));
    }

    @RequestMapping(value = "/user/file", method = RequestMethod.GET)
    public FileBean getAuthorFileAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails,
                                                  @RequestParam Map<String, String> allRequestParams) {
        return editorFileService.findAuthorFileByIdAssignedToEditor(editorDetails.getUserId(),
                Long.parseLong(allRequestParams.get("userId")), Long.parseLong(allRequestParams.get("fileId")));
    }

    //Editor-Report for Authors
    @RequestMapping(value = "/user/file/reports", method = RequestMethod.GET)
    public List<FileBean> getEditorReportsForFileAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails,
                                                                  @RequestParam Map<String, String> allRequestParams) {
        return editorFileService.findAllReportsForAuthorFileAssignedToEditor(editorDetails.getUserId(),
                Long.parseLong(allRequestParams.get("userId")), Long.parseLong(allRequestParams.get("fileId")));
    }

    @RequestMapping(value = "/user/file/report", method = RequestMethod.GET)
    public FileBean getEditorReportForFileAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails,
                                                           @RequestParam Map<String, String> allRequestParams) {
        return editorFileService.findReportForAuthorFileAssignedToEditor(editorDetails.getUserId(),
                Long.parseLong(allRequestParams.get("userId")),
                Long.parseLong(allRequestParams.get("fileId")), Long.parseLong(allRequestParams.get("reportId")));
    }

    @RequestMapping(value = "/user/reports", method = RequestMethod.GET)
    public List<FileBean> getEditorReportsForAuthorAssignedToEditor(@AuthenticationPrincipal PersistentUserDetails editorDetails,
                                                                    @RequestParam Map<String, String> allRequestParams) {
        return editorFileService.findAllReportsForAuthorAssignedToEditor(editorDetails.getUserId(), Long.parseLong(allRequestParams.get("userId")));
    }
}
