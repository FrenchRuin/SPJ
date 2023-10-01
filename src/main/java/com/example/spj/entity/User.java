package com.example.spj.entity;

import com.example.spj.dto.UserDto;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "spj_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String password;

    private String username;

    private boolean enabled;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "userId"))
    private Set<UserAuthority> authorities;

    @OneToMany(mappedBy = "user")
    private List<Board> boards;

    @Column(updatable = false)
    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    /*
     * UserDto to UserEntity Converter
     * */
    public static User userDtoToEntity(UserDto userDto){
        return User.builder()
                .username(userDto.getUsername())
                .enabled(userDto.isEnabled())
                .password(userDto.getPassword())
                .updatedTime(userDto.getUpdatedTime())
                .createdTime(userDto.getCreatedTime())
                .userId(userDto.getUserId())
                .build();
    }
}
