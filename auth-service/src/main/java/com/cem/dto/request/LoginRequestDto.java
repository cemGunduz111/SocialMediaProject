package com.cem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    @NotNull(message = "Kullanıcı adı girilmesi zorunludur.")
    @Size(min = 3, max = 16)
    String username;
    @NotNull(message = "Şifre girilmesi zorunludur.")
    @Size(min = 8, max = 32)
    String password;
}
