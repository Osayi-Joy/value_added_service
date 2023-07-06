package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.backoffice.service.modules.roles.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.PaginatedApiModel;
import com.digicore.billent.data.lib.modules.common.authentication.model.Role;
import com.digicore.billent.data.lib.modules.common.authentication.repository.RoleRepository;
import com.digicore.request.processor.annotations.MakerChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @MakerChecker(makerPermission = "view-roles", checkerPermission = "approve-view-roles")
    public Object getAllRoles(Object request, Object[] fileDTO) {
        log.info("Request to get all roles.");
        return getAllRoles((Pageable) request);
    }

    PaginatedApiModel<RoleDTO> getAllRoles(Pageable pageable) {
        Page<Role> rolePage = roleRepository.findAll(pageable);

        List<RoleDTO> roleDTOS = rolePage.getContent()
                .stream()
                .map(this::mapToRoleDto)
                .toList();

        return PaginatedApiModel.<RoleDTO>builder()
                .content(roleDTOS)
                .currentPage(rolePage.getNumber())
                .isFirstPage(rolePage.isFirst())
                .isLastPage(rolePage.isLast())
                .totalItems(rolePage.getTotalElements())
                .build();
    }

    private RoleDTO mapToRoleDto(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }
}
