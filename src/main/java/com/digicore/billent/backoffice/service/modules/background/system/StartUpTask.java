package com.digicore.billent.backoffice.service.modules.background.system;

import com.digicore.api.helper.exception.ZeusRuntimeException;
import com.digicore.billent.data.lib.config.properties.PropertyConfig;
import com.digicore.billent.data.lib.modules.common.auth.model.Permission;
import com.digicore.billent.data.lib.modules.common.auth.model.Role;
import com.digicore.billent.data.lib.modules.common.auth.repository.PermissionRepository;
import com.digicore.billent.data.lib.modules.common.auth.repository.RoleRepository;
import com.digicore.billent.data.lib.modules.common.background.BackGroundService;
import com.digicore.common.util.BeanUtilWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Jun-27(Tue)-2023
 */
@Component
@RequiredArgsConstructor
@Transactional
public class StartUpTask implements BackGroundService {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final PropertyConfig propertyConfig;

  @Override
  @Async
  @EventListener(ContextRefreshedEvent.class)
  public void runSystemStartUpTask() {
    readRoles();
  }

  private void readRoles() {
    ObjectMapper mapper = new ObjectMapper();

    propertyConfig
        .getSystemDefinedRoles()
        .forEach(roles -> saveRole(Paths.get(roles).toFile(), mapper));
  }

  private void saveRole(File file, ObjectMapper mapper) {

    try {
      Role role = mapper.readValue(file, Role.class);
      Role existingRole = roleRepository.findFirstByNameOrderByCreatedDate(role.getName());
      existingRole = updatePermissions(existingRole, role);
      roleRepository.save(existingRole);
    } catch (IOException e) {
      throw new ZeusRuntimeException(e.getMessage());
    }
  }

  private Role updatePermissions(Role existingRole, Role newRole) {
    Set<Permission> newAuthorities = newRole.getPermissions();
    if (existingRole != null) {
      Set<Permission> existingAuthorities = existingRole.getPermissions();
      Set<String> existingAuthoritiesPermissions =
          existingAuthorities.stream().map(Permission::getName).collect(Collectors.toSet());

      newAuthorities.forEach(
          authority -> {
            if (!existingAuthoritiesPermissions.contains(authority.getName())) {
              Optional<Permission> permission =
                  permissionRepository.findFirstByNameOrderByCreatedDate(authority.getName());
              if (permission.isPresent()) {
                existingAuthorities.add(permission.get());
              } else {
                existingAuthorities.add(authority);
              }
            }
          });
      existingRole.setPermissions(existingAuthorities);
      return existingRole;
    }

    Role role = new Role();
    role.setDescription(newRole.getDescription());
    role.setName(newRole.getName());
    role.setRoleScope(newRole.getRoleScope());
    roleRepository.save(role);

    newAuthorities.forEach(
        authority -> {
          Optional<Permission> permission =
              permissionRepository.findFirstByNameOrderByCreatedDate(authority.getName());
          permission.ifPresent(value -> BeanUtilWrapper.copyNonNullProperties(value, authority));
          newRole.getPermissions().add(authority);
        });
    role.getPermissions().addAll(newAuthorities);

    return role;
  }
}
