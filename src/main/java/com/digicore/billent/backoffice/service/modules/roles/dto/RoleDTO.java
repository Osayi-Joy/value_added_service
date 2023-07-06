package com.digicore.billent.backoffice.service.modules.roles.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class RoleDTO implements Serializable {
    private String name;
    private String description;
    private boolean active = true;
    private Set<PermissionDTO> permissions = new HashSet<>();
}
