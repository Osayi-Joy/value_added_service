package com.digicore.billent.backoffice.service.modules.roles.services;

import com.digicore.billent.backoffice.service.modules.roles.dto.RoleDTO;
import com.digicore.billent.data.lib.modules.common.PaginatedApiModel;
import com.digicore.billent.data.lib.modules.common.authentication.model.Role;
import com.digicore.billent.data.lib.modules.common.authentication.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        // Prepare test data
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("Role 1");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("Role 2");
        List<Role> roles = Arrays.asList(role1, role2);
        Page<Role> rolePage = new PageImpl<>(roles);

        // Mock the repository method
        when(roleRepository.findAll(pageable)).thenReturn(rolePage);

        // Invoke the service method
        PaginatedApiModel<RoleDTO> result = roleService.getAllRoles(pageable);

        // Verify the result
        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getCurrentPage());
        assertTrue(result.isFirstPage());
        assertTrue(result.isLastPage());
        assertEquals(2, result.getTotalItems());
    }
}
