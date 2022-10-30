package com.cem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    //@NotNull(message = "Kullanıcı adı girilmesi zorunludur.")
    @NotBlank
    @Size(min = 3, max = 16, message = "Kullanıcı adı en az 3, en fazla 16 karakter olmalıdır.")
    private String username;
    // @NotNull(message = "Şifre girilmesi zorunludur")
    @NotBlank
    @Size(min = 8, max = 32, message = "Şifre en az 8, en fazla 32 karakter olabilir.")
    private String password;
    @Email(message = "Geçerli bir email adresi giriniz")
    @NotBlank
    private String email;
    private String admincode;
}
