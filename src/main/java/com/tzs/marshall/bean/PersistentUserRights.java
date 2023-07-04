package com.tzs.marshall.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersistentUserRights {
    private Long userId;
    private Long roleId;
    private String roleName;
    private String typeId;
    private String typeName;
    private Long permissionId;
    private String permissionName;
    private Long associateId;
    private Long bridgeId;
    private Long subsId;
    private Long addressEducationId;
    private Long umappingId;
    private String is_deleted;
    private String is_enable;
    private boolean isDeleted;
    private boolean isEnable;

    public PersistentUserRights(String type_name, String role_name) {
        this.typeName = type_name;
        this.roleName = role_name;
    }

    public PersistentUserRights(Long roleId, String role_name, String typeId, String type_name) {
        this.roleId = roleId;
        this.roleName = role_name;
        this.typeId = typeId;
        this.typeName = type_name;
    }
}
