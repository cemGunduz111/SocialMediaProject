package com.cem.repository.entity;

import com.cem.repository.enums.Roles;
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
@Table(name = "tblauth")
public class Auth {
    /**
     * login metodu yazalım ve controller ve servicede
     * login request dto
     * birde response dto oluşturalım
     * bu oluşturduğumuz dtoları da metodumuzda kullanalım
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Roles roles = Roles.USER;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDING;
    private String activatedCode;

}
