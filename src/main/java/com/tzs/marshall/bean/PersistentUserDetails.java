package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class PersistentUserDetails implements UserDetails {

    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String userName;
    private String phone;
    private String mobile;
    private String email;
    private String alternateEmail;
    private String password;
    private boolean is_deleted;
    private boolean is_enable;
    private Date joinDate;

    private Long roleId;
    private String roleName;
    private String typeId;
    private String typeName;

    private Long addressEducationId;
    private String street;
    private String city;
    private String state;
    private String country;
    private int zipCode;
    private String department;
    private String institution;

    private Long permissionId;
    private Long associateId;
    private Long subsId;
    private Long bridgeId;
    private Long umappingId;
    private String permissionName;

    private Long profileId;
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


    public PersistentUserDetails() {
    }


    public PersistentUserDetails(PersistentUserDetails userDetails) {
        this.userId = userDetails.getUserId();
        this.firstName = userDetails.firstName;
        this.middleName = userDetails.middleName;
        this.lastName = userDetails.lastName;
        this.userName = userDetails.userName;
        this.phone = userDetails.phone;
        this.mobile = userDetails.mobile;
        this.email = userDetails.email;
        this.alternateEmail = userDetails.alternateEmail;
        this.password = userDetails.password;
        this.is_deleted = userDetails.is_deleted;
        this.is_enable = userDetails.is_enable;
        this.joinDate = userDetails.joinDate;
        this.roleId = userDetails.roleId;
        this.roleName = userDetails.roleName;
        this.typeId = userDetails.typeId;
        this.typeName = userDetails.typeName;
        this.addressEducationId = userDetails.addressEducationId;
        this.street = userDetails.street;
        this.city = userDetails.city;
        this.state = userDetails.state;
        this.country = userDetails.country;
        this.zipCode = userDetails.zipCode;
        this.department = userDetails.department;
        this.institution = userDetails.institution;
        this.permissionId = userDetails.permissionId;
        this.associateId = userDetails.associateId;
        this.subsId = userDetails.subsId;
        this.bridgeId = userDetails.bridgeId;
        this.umappingId = userDetails.umappingId;
        this.permissionName = userDetails.permissionName;
        this.profileId = userDetails.profileId;
        this.profilePhoto = userDetails.profilePhoto;
        this.profilePhotoName = userDetails.profilePhotoName;
        this.profilePhotoPath = userDetails.profilePhotoPath;
        this.profilePhotoSize = userDetails.profilePhotoSize;
        this.aadharBackPhoto = userDetails.aadharBackPhoto;
        this.aadharBackPhotoName = userDetails.aadharBackPhotoName;
        this.aadharBackPhotoPath = userDetails.aadharBackPhotoPath;
        this.aadharBackPhotoSize = userDetails.aadharBackPhotoSize;
        this.aadharFrontPhoto = userDetails.aadharFrontPhoto;
        this.aadharFrontPhotoName = userDetails.aadharFrontPhotoName;
        this.aadharFrontPhotoPath = userDetails.aadharFrontPhotoPath;
        this.aadharFrontPhotoSize = userDetails.aadharFrontPhotoSize;
        this.aadharNumber = userDetails.aadharNumber;
        this.rickshawNumber = userDetails.rickshawNumber;
        this.paytmNumber = userDetails.paytmNumber;
        this.rickshawFrontPhoto = userDetails.rickshawFrontPhoto;
        this.rickshawFrontPhotoName = userDetails.rickshawFrontPhotoName;
        this.rickshawFrontPhotoPath = userDetails.rickshawFrontPhotoPath;
        this.rickshawFrontPhotoSize = userDetails.rickshawFrontPhotoSize;
        this.rickshawBackPhoto = userDetails.rickshawBackPhoto;
        this.rickshawBackPhotoName = userDetails.rickshawBackPhotoName;
        this.rickshawBackPhotoPath = userDetails.rickshawBackPhotoPath;
        this.rickshawBackPhotoSize = userDetails.rickshawBackPhotoSize;
        this.rickshawSidePhoto = userDetails.rickshawSidePhoto;
        this.rickshawSidePhotoName = userDetails.rickshawSidePhotoName;
        this.rickshawSidePhotoPath = userDetails.rickshawSidePhotoPath;
        this.rickshawSidePhotoSize = userDetails.rickshawSidePhotoSize;

    }

    public PersistentUserDetails(Long userId, String email, String userName, String mobile, String paytmNumber) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.mobile = mobile;
        this.paytmNumber = paytmNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean isIs_enable() {
        return is_enable;
    }

    public void setIs_enable(boolean is_enable) {
        this.is_enable = is_enable;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getAddressEducationId() {
        return addressEducationId;
    }

    public void setAddressEducationId(Long addressEducationId) {
        this.addressEducationId = addressEducationId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getAssociateId() {
        return associateId;
    }

    public void setAssociateId(Long associateId) {
        this.associateId = associateId;
    }

    public Long getSubsId() {
        return subsId;
    }

    public void setSubsId(Long subsId) {
        this.subsId = subsId;
    }

    public Long getBridgeId() {
        return bridgeId;
    }

    public void setBridgeId(Long bridgeId) {
        this.bridgeId = bridgeId;
    }

    public Long getUmappingId() {
        return umappingId;
    }

    public void setUmappingId(Long umappingId) {
        this.umappingId = umappingId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
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

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getRickshawNumber() {
        return rickshawNumber;
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

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public void setPaytmNumber(String paytmNumber) {
        this.paytmNumber = paytmNumber;
    }

    public void setRickshawNumber(String rickshawNumber) {
        this.rickshawNumber = rickshawNumber;
    }

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
/*        return Stream.of("ROLE_" + getRoleName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/

        return Stream.of(getRoleName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

      /*  Constants.GRANTED_AUTHORITIES
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
          return Arrays.stream(getRoleName().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isIs_deleted();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isIs_enable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isIs_enable();
    }

    @Override
    public boolean isEnabled() {
        return isIs_enable();
    }
}
