package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.data.lib.modules.common.authorization.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.authorization.model.Role;
import com.digicore.billent.data.lib.modules.common.authorization.service.RoleService;
import com.digicore.registhentication.common.dto.response.PaginatedResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackOfficeRoleService {

  private final RoleService<RoleDTO, Role> roleService;

  public PaginatedResponseDTO<RoleDTO> getAllRoles(int pageNumber, int pageSize) {
    return roleService.retrieveAllRoles(pageNumber, pageSize);
  }
}
