package com.cem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequestDto {
    @NotNull
    String token;
    private String username;
    private String name;
    @Email
    private String email;
    private String phone;
    private String photo;
    private String address;
    private String about;
}
