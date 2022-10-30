package com.cem.repository.entity;

import com.cem.repository.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbluser")
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authid;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String photo;
    private String address;
    private String about;
    @Builder.Default
    private Long created = System.currentTimeMillis();
    private Long updated;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDING;

}
