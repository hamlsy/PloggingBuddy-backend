package com.ploggingbuddy.domain.member.entity;

import com.ploggingbuddy.domain.auditing.entity.BaseTimeEntity;
import com.ploggingbuddy.global.vo.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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
public class Member extends BaseTimeEntity{

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

    @Embedded
    @Builder.Default
    private Address address = new Address();;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    //business
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

}
