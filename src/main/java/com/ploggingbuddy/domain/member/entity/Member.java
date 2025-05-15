package com.ploggingbuddy.domain.member.entity;

import com.ploggingbuddy.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

}
