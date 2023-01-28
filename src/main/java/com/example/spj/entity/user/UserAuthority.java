package com.example.spj.entity.user;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "spj_authority")
@IdClass(UserAuthority.class)
public class UserAuthority implements GrantedAuthority {

    @Id
    private Long userId;

    private String authority;

}
