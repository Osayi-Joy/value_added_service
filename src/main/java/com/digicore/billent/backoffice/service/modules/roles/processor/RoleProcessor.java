package com.digicore.billent.backoffice.service.modules.roles.processor;


import com.digicore.billent.backoffice.service.modules.roles.services.RoleService;
import com.digicore.request.processor.annotations.RequestHandler;
import com.digicore.request.processor.annotations.RequestType;
import com.digicore.request.processor.enums.RequestHandlerType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestHandler(type = RequestHandlerType.ROLE_REQUESTS)
public class RoleProcessor {

    private final RoleService roleService;

    @RequestType(name = "getAllRoles")
    public Object getAllRoles(Object request) {
        return roleService.getAllRoles(request, null);
    }
}
