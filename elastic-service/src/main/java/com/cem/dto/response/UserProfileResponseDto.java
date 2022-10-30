package com.cem.dto.response;

import com.cem.repository.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDto {
    private Long id;
    private Long authid;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String photo;
    private String address;
    private String about;
    private Long created;
    private Long updated;
    private Status status;
}
