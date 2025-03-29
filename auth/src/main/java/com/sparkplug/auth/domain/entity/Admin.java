package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.enums.AdminRole;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.Username;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "admin")
@NoArgsConstructor(access = PROTECTED)
public class Admin extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "admin_authority_admin",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_authority_id"))
    private List<AdminAuthority> authorities;

    @Override
    public List<String> getAuthorities() {
        return authorities.stream()
                .map(AdminAuthority::toString)
                .toList();
    }

    @Builder(access = PRIVATE)
    private Admin(
            Long id, Username username, Email email, PhoneNumber phoneNumber, String passwordHash,
            List<AdminAuthority> authorities) {
        super(id, username, email, phoneNumber, passwordHash);
        this.authorities = authorities;
    }

    private Admin(List<AdminAuthority> authorities) {
        this.authorities = authorities;
    }

    public static Admin createWithEmail(
            Username username, Email email, String passwordHash, List<AdminAuthority> authorities) {

        return Admin.getBaseBuilder(username, passwordHash, authorities)
                .email(email)
                .build();
    }

    public static Admin createWithPhoneNumber(
            Username username, PhoneNumber phoneNumber, String passwordHash, List<AdminAuthority> authorities) {

        return Admin.getBaseBuilder(username, passwordHash, authorities)
                .phoneNumber(phoneNumber)
                .build();
    }

    public static Admin createWithPhoneNumberAndEmail(
            Username username, PhoneNumber phoneNumber, Email email, String passwordHash, List<AdminAuthority> authorities) {

        return Admin.getBaseBuilder(username, passwordHash, authorities)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }

    private static Admin.AdminBuilder getBaseBuilder(Username username, String passwordHash, List<AdminAuthority> authorities) {

        return Admin.builder()
                .username(username)
                .passwordHash(passwordHash)
                .authorities(authorities);
    }
}
