package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    public PersistentUserDetails() {
    }

    public PersistentUserDetails(Long userId, String email, String userName, String mobile) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.mobile = mobile;
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
