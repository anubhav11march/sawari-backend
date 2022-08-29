package com.tzs.marshall.controller.postlogin;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.filesystem.FileBean;
import com.tzs.marshall.filesystem.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/author/content", "/admin/content", "/editor/content"})
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @ModelAttribute FileBean fileBean,
                                   @AuthenticationPrincipal PersistentUserDetails authorDetails,
                                   HttpSession session) {
        String errorMessage = null;
        String successMessage = null;
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (file == null)
                throw new ApiException(MessageConstants.FILE_EMPTY);
            fileBean.setFile(file);
            fileBean.setFileStatus("Uploaded");
            fileBean.setFileUserId(authorDetails.getUserId());
            if (Constants.ROLE_USER.equalsIgnoreCase(authorDetails.getRoleName())) {
                if (fileBean.getRequestServeDate().before(Timestamp.valueOf(LocalDateTime.now().plusDays(5L))))
                    throw new ApiException(MessageConstants.REQUEST_SERVE_DATE_ERR);
            }
            fileBean.setUploadBy(authorDetails.getRoleName());
            fileBean.setUploadDate(Timestamp.valueOf(LocalDateTime.now()));
            fileBean.setModifyDate(Timestamp.valueOf(LocalDateTime.now()));
            if (fileBean.getRequestServeDate() == null)
                fileBean.setRequestServeDate(Date.valueOf(LocalDate.now()));
            fileService.uploadFileHandler(fileBean);
            successMessage = String.format(MessageConstants.FILE_UPLOADED, file.getOriginalFilename());
            session.setAttribute("successMessage", successMessage);
            modelAndView.setViewName("redirect:/" + authorDetails.getRoleName().toLowerCase() + "/content/find-file");
        } catch (ApiException exception) {
            errorMessage = exception.getMessage();
            session.setAttribute("errorMessage", errorMessage);
            modelAndView.setViewName("redirect:/" + authorDetails.getRoleName().toLowerCase() + "/content/fileupload");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public FileBean updateFile(@RequestParam(value = "file", required = false) MultipartFile file,
                               @RequestBody FileBean fileBean,
                               @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (file != null)
            fileBean.setFile(file);
        if (Constants.ROLE_USER.equalsIgnoreCase(authorDetails.getRoleName())) {
            if (fileBean.getRequestServeDate().before(Timestamp.valueOf(LocalDateTime.now().plusDays(5l))))
                throw new ApiException(MessageConstants.REQUEST_SERVE_DATE_ERR);
        }
        fileBean.setFileUserId(authorDetails.getUserId());
        fileBean.setUploadBy(authorDetails.getRoleName());
        fileBean.setModifyDate(Timestamp.valueOf(LocalDateTime.now()));
        if (fileBean.getRequestServeDate() == null)
            fileBean.setRequestServeDate(Date.valueOf(LocalDate.now()));
        fileBean = fileService.updateFileHandler(fileBean);
        return fileBean;
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public FileBean getFileById(@RequestParam Map<String, String> allRequestParams,
                                @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        return fileService.findFileByIdHandler(authorDetails.getUserId(), Long.parseLong(allRequestParams.get("fileId")), authorDetails.getRoleName());
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public List<FileBean> getAllFileByUserId(@AuthenticationPrincipal PersistentUserDetails authorDetails) {
        return fileService.findFilesHandler(authorDetails.getUserId(), authorDetails.getRoleName());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int deleteFileById(@RequestParam Map<String, String> allRequestParams, @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        return fileService.deleteFileHandler(authorDetails.getUserId(), Long.parseLong(allRequestParams.get("fileId")), authorDetails.getRoleName());
    }

    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    public FileBean updateFileStatus(@RequestBody FileBean fileBean,
                                     @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        if (Constants.ADMINS.contains(authorDetails.getRoleName())) {
            fileBean.setUploadBy(authorDetails.getRoleName());
            fileBean = fileService.updateFileStatusHandler(fileBean);
        } else {
            throw new ApiException(MessageConstants.NOT_AUTHORIZED_FILE_STATUS);
        }
        return fileBean;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@RequestParam Map<String, String> allRequestParams,
                                                 @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        String uploadBy = Constants.ROLE_USER;
        if (Constants.ADMINS.contains(authorDetails.getRoleName()) && allRequestParams.get("uploadBy") != null)
            uploadBy = allRequestParams.get("uploadBy");
        String isReport = "N";
        long reportId = 0;
        if ("Y".equalsIgnoreCase(allRequestParams.get("isReport"))) {
            isReport = allRequestParams.get("isReport");
            reportId = Long.parseLong(allRequestParams.get("reportId"));
        }
        FileBean fileBean = fileService.downloadFileHandler(authorDetails.getUserId(), Long.parseLong(allRequestParams.get("fileId")), reportId, uploadBy, isReport);
        if (fileBean != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileBean.getFileFormat()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileBean.getFileName() + "\"")
                    .body(fileBean.getResource());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public List<FileBean> getFileReports(@RequestParam("fileId") String fileId, @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        return fileService.findReportsForAuthorFile(authorDetails.getUserId(), Long.parseLong(fileId));
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public FileBean getFileReport(@RequestParam Map<String, String> allRequestParams, @AuthenticationPrincipal PersistentUserDetails authorDetails) {
        return fileService.findReportForAuthorFile(authorDetails.getUserId(), Long.parseLong(allRequestParams.get("fileId")), Long.parseLong(allRequestParams.get("reportId")));
    }
}
