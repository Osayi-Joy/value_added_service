package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.data.lib.modules.role.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleService {

    private final RoleService roleService;

    public PaginatedResponseDTO<RoleDTO> getAllRoles(int pageNumber, int pageSize) {
        return roleService.getAllRoles(pageNumber, pageSize);
    }
}