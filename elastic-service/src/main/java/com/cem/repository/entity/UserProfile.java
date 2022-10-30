package com.cem.repository.entity;

import com.cem.repository.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "user_profile")
public class UserProfile implements Serializable {
    @Id
    String uuid;
    Long userid;
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
    @Builder.Default
    private Status status = Status.PENDING;

}
