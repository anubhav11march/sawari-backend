package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;
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

    private String aadharNumber;
    private String rickshawNumber;
    private String paytmNumber;

    private MultipartFile rickshawFrontPhoto;
    private String rickshawFrontPhotoName;
    private String rickshawFrontPhotoPath;
    private Long rickshawFrontPhotoSize;

    private MultipartFile rickshawBackPhoto;
    private String rickshawBackPhotoName;
    private String rickshawBackPhotoPath;
    private Long rickshawBackPhotoSize;

    private MultipartFile rickshawSidePhoto;
    private String rickshawSidePhotoName;
    private String rickshawSidePhotoPath;
    private Long rickshawSidePhotoSize;

    public ProfileDetails() {
    }

    public ProfileDetails(PersistentUserDetails userDetails, String paytmNumber, String rickshawNumber, MultipartFile rickshawFrontPhoto, MultipartFile rickshawBackPhoto, MultipartFile rickshawSidePhoto) {
        super(userDetails);
        this.paytmNumber = paytmNumber;
        this.rickshawNumber = rickshawNumber;
        this.rickshawFrontPhoto = rickshawFrontPhoto;
        this.rickshawBackPhoto = rickshawBackPhoto;
        this.rickshawSidePhoto = rickshawSidePhoto;
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

    public MultipartFile getRickshawFrontPhoto() {
        return rickshawFrontPhoto;
    }

    public void setRickshawFrontPhoto(MultipartFile rickshawFrontPhoto) {
        this.rickshawFrontPhoto = rickshawFrontPhoto;
    }

    public String getRickshawFrontPhotoName() {
        return rickshawFrontPhotoName;
    }

    public void setRickshawFrontPhotoName(String rickshawFrontPhotoName) {
        this.rickshawFrontPhotoName = rickshawFrontPhotoName;
    }

    public String getRickshawFrontPhotoPath() {
        return rickshawFrontPhotoPath;
    }

    public void setRickshawFrontPhotoPath(String rickshawFrontPhotoPath) {
        this.rickshawFrontPhotoPath = rickshawFrontPhotoPath;
    }

    public Long getRickshawFrontPhotoSize() {
        return rickshawFrontPhotoSize;
    }

    public void setRickshawFrontPhotoSize(Long rickshawFrontPhotoSize) {
        this.rickshawFrontPhotoSize = rickshawFrontPhotoSize;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getRickshawNumber() {
        return rickshawNumber;
    }

    public void setRickshawNumber(String rickshawNumber) {
        this.rickshawNumber = rickshawNumber;
    }

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public void setPaytmNumber(String paytmNumber) {
        this.paytmNumber = paytmNumber;
    }

    public MultipartFile getRickshawBackPhoto() {
        return rickshawBackPhoto;
    }

    public void setRickshawBackPhoto(MultipartFile rickshawBackPhoto) {
        this.rickshawBackPhoto = rickshawBackPhoto;
    }

    public String getRickshawBackPhotoName() {
        return rickshawBackPhotoName;
    }

    public void setRickshawBackPhotoName(String rickshawBackPhotoName) {
        this.rickshawBackPhotoName = rickshawBackPhotoName;
    }

    public String getRickshawBackPhotoPath() {
        return rickshawBackPhotoPath;
    }

    public void setRickshawBackPhotoPath(String rickshawBackPhotoPath) {
        this.rickshawBackPhotoPath = rickshawBackPhotoPath;
    }

    public Long getRickshawBackPhotoSize() {
        return rickshawBackPhotoSize;
    }

    public void setRickshawBackPhotoSize(Long rickshawBackPhotoSize) {
        this.rickshawBackPhotoSize = rickshawBackPhotoSize;
    }

    public MultipartFile getRickshawSidePhoto() {
        return rickshawSidePhoto;
    }

    public void setRickshawSidePhoto(MultipartFile rickshawSidePhoto) {
        this.rickshawSidePhoto = rickshawSidePhoto;
    }

    public String getRickshawSidePhotoName() {
        return rickshawSidePhotoName;
    }

    public void setRickshawSidePhotoName(String rickshawSidePhotoName) {
        this.rickshawSidePhotoName = rickshawSidePhotoName;
    }

    public String getRickshawSidePhotoPath() {
        return rickshawSidePhotoPath;
    }

    public void setRickshawSidePhotoPath(String rickshawSidePhotoPath) {
        this.rickshawSidePhotoPath = rickshawSidePhotoPath;
    }

    public Long getRickshawSidePhotoSize() {
        return rickshawSidePhotoSize;
    }

    public void setRickshawSidePhotoSize(Long rickshawSidePhotoSize) {
        this.rickshawSidePhotoSize = rickshawSidePhotoSize;
    }
}
