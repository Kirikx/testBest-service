package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.admin.RoleDto;
import ru.testbest.service.RoleService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@RestController
public class RoleRestController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleDto> getRoles() {
        log.info("Get all roles");
        return roleService.getRoles();
    }

    @GetMapping("/{id}")
    public RoleDto getRole(@PathVariable("id") String id) {
        log.info("Get role by id {}", id);
        return roleService.getRoleById(UUID.fromString(id));
    }
}
