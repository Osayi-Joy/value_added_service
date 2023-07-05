package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.data.lib.modules.common.authentication.model.Role;
import com.digicore.billent.data.lib.modules.common.authentication.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void testGetAllRoles() {
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("Role 1");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("Role 2");

        List<Role> mockRoles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(mockRoles);

        List<Role> result = roleService.getAllRoles();

        assertEquals(mockRoles, result);
    }
}
