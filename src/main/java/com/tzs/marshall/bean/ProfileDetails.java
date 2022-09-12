package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@AllArgsConstructor
public class ProfileDetails extends PersistentUserDetails {

    private MultipartFile profilePhoto;
    private String profilePhotoName;
    private String profilePhotoPath;
    private Long profilePhotoSize;

    private MultipartFile aadharBackPhoto;
    private String aadharBackPhotoName;
    private String aadharBackPhotoPath;
    private Long aadharBackPhotoSize;

    private MultipartFile aadharFrontPhoto;
    private String aadharFrontPhotoName;
    private String aadharFrontPhotoPath;
    private Long aadharFrontPhotoSize;

    private MultipartFile rickshawPhoto;
    private String rickshawPhotoName;
    private String rickshawPhotoPath;
    private Long rickshawPhotoSize;

    public ProfileDetails() {
    }

    public MultipartFile getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(MultipartFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoName() {
        return profilePhotoName;
    }

    public void setProfilePhotoName(String profilePhotoName) {
        this.profilePhotoName = profilePhotoName;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public Long getProfilePhotoSize() {
        return profilePhotoSize;
    }

    public void setProfilePhotoSize(Long profilePhotoSize) {
        this.profilePhotoSize = profilePhotoSize;
    }

    public MultipartFile getAadharBackPhoto() {
        return aadharBackPhoto;
    }

    public void setAadharBackPhoto(MultipartFile aadharBackPhoto) {
        this.aadharBackPhoto = aadharBackPhoto;
    }

    public String getAadharBackPhotoName() {
        return aadharBackPhotoName;
    }

    public void setAadharBackPhotoName(String aadharBackPhotoName) {
        this.aadharBackPhotoName = aadharBackPhotoName;
    }

    public String getAadharBackPhotoPath() {
        return aadharBackPhotoPath;
    }

    public void setAadharBackPhotoPath(String aadharBackPhotoPath) {
        this.aadharBackPhotoPath = aadharBackPhotoPath;
    }

    public Long getAadharBackPhotoSize() {
        return aadharBackPhotoSize;
    }

    public void setAadharBackPhotoSize(Long aadharBackPhotoSize) {
        this.aadharBackPhotoSize = aadharBackPhotoSize;
    }

    public MultipartFile getAadharFrontPhoto() {
        return aadharFrontPhoto;
    }

    public void setAadharFrontPhoto(MultipartFile aadharFrontPhoto) {
        this.aadharFrontPhoto = aadharFrontPhoto;
    }

    public String getAadharFrontPhotoName() {
        return aadharFrontPhotoName;
    }

    public void setAadharFrontPhotoName(String aadharFrontPhotoName) {
        this.aadharFrontPhotoName = aadharFrontPhotoName;
    }

    public String getAadharFrontPhotoPath() {
        return aadharFrontPhotoPath;
    }

    public void setAadharFrontPhotoPath(String aadharFrontPhotoPath) {
        this.aadharFrontPhotoPath = aadharFrontPhotoPath;
    }

    public Long getAadharFrontPhotoSize() {
        return aadharFrontPhotoSize;
    }

    public void setAadharFrontPhotoSize(Long aadharFrontPhotoSize) {
        this.aadharFrontPhotoSize = aadharFrontPhotoSize;
    }

    public MultipartFile getRickshawPhoto() {
        return rickshawPhoto;
    }

    public void setRickshawPhoto(MultipartFile rickshawPhoto) {
        this.rickshawPhoto = rickshawPhoto;
    }

    public String getRickshawPhotoName() {
        return rickshawPhotoName;
    }

    public void setRickshawPhotoName(String rickshawPhotoName) {
        this.rickshawPhotoName = rickshawPhotoName;
    }

    public String getRickshawPhotoPath() {
        return rickshawPhotoPath;
    }

    public void setRickshawPhotoPath(String rickshawPhotoPath) {
        this.rickshawPhotoPath = rickshawPhotoPath;
    }

    public Long getRickshawPhotoSize() {
        return rickshawPhotoSize;
    }

    public void setRickshawPhotoSize(Long rickshawPhotoSize) {
        this.rickshawPhotoSize = rickshawPhotoSize;
    }
}
