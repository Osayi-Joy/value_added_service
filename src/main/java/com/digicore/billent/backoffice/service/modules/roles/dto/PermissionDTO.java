package com.digicore.billent.backoffice.service.modules.roles.dto;

import com.digicore.billent.data.lib.modules.common.authentication.enums.PermissionType;
import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionDTO implements Serializable {
    private String name;
    private String description;
    private PermissionType permissionType;
}
