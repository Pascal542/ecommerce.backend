package com.pascal.ecommerce.backend.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String password;
}
