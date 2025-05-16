package com.ploggingbuddy.domain.member.entity;

import com.ploggingbuddy.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "member",
        indexes = {
                @Index(name = "idx_member_username", columnList = "username"),
        }
)
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    private String email;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private String detailAddress;
    private double latitude;
    private double longitude;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getName()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    //business
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateAddress(String detailAddress, double latitude, double longitude) {
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
