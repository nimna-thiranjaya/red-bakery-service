package com.redbakery.redbakeryservice.model;

import com.redbakery.redbakeryservice.common.WellKnownStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId;

    private String FirstName;

    private String LastName;

    private String Email;

    private String Password;

    private Integer Status;

    private Boolean IsVerified;

    private Role Role;

    @CreationTimestamp
    private Date CreatedAt;

    @UpdateTimestamp
    private Date UpdatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.name()));
    }

    @Override
    public String getUsername() {
        return Email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Status == WellKnownStatus.ACTIVE.getValue();
    }
}
