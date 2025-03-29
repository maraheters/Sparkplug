package com.sparkplug.auth.domain.repository;

import com.sparkplug.auth.domain.entity.ClientAuthority;
import com.sparkplug.auth.domain.enums.ClientRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientAuthoritiesRepository extends JpaRepository<ClientAuthority, Long> {

    Optional<ClientAuthority> findByName(String name);
}
