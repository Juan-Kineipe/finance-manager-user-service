package com.kineipe.financemanageruserservice.domain.dto;

import com.kineipe.financemanageruserservice.domain.Permission;

import java.util.List;

public record RegisterDTO(String username, String firstName, String lastName, String password, List<Permission> permissions) {}
