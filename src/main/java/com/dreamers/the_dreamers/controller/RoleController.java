package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Role;
import com.dreamers.the_dreamers.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Role API", description = "API for managing user roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Get all roles",
            description = "Returns a list of all defined user roles.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Role.class)))
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Operation(summary = "Get role by ID",
            description = "Returns a single role based on its ID.")
    @ApiResponse(responseCode = "200", description = "Role found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "404", description = "Role not found")
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(
            @Parameter(description = "ID of the role to retrieve")
            @PathVariable String id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get role by name",
            description = "Returns a single role based on its name.")
    @ApiResponse(responseCode = "200", description = "Role found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "404", description = "Role not found")
    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(
            @Parameter(description = "Name of the role", example = "MEMBER")
            @PathVariable String name) {
        return roleService.getRoleByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new role",
            description = "Creates a new role.")
    @ApiResponse(responseCode = "200", description = "Role created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Role.class)))
    @PostMapping
    public ResponseEntity<Role> createRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new role", required = true)
            @RequestBody Role role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @Operation(summary = "Update a role",
            description = "Updates an existing role based on its ID.")
    @ApiResponse(responseCode = "200", description = "Role updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "404", description = "Role not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(
            @Parameter(description = "ID of the role to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated role details", required = true)
            @RequestBody Role roleDetails) {
        try {
            Role updatedRole = roleService.updateRole(id, roleDetails);
            return ResponseEntity.ok(updatedRole);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a role",
            description = "Deletes a role based on its ID.")
    @ApiResponse(responseCode = "204", description = "Role deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "ID of the role to delete")
            @PathVariable String id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}