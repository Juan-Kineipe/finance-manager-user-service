package com.kineipe.financemanageruserservice.domain.dto;

import com.kineipe.financemanageruserservice.domain.Permission;

import java.util.List;

public record UserDTO(Long id, String username, List<Permission> permissions) {}