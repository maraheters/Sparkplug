package com.sparkplug.auth.security.service;

import com.sparkplug.auth.domain.repository.AdminsRepository;
import com.sparkplug.auth.domain.repository.ClientsRepository;
import com.sparkplug.auth.domain.vo.Username;
import com.sparkplug.auth.security.user.SparkplugUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SparkplugUserDetailsService implements UserDetailsService {

    private final ClientsRepository clientsRepository;
    private final AdminsRepository adminsRepository;

    @Autowired
    public SparkplugUserDetailsService(ClientsRepository clientsRepository, AdminsRepository adminsRepository) {
        this.clientsRepository = clientsRepository;
        this.adminsRepository = adminsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var clientOptional = clientsRepository.findByUsername(new Username(username));
        if (clientOptional.isPresent()) {
            return new SparkplugUserDetails(clientOptional.get());
        }

        var adminOptional = adminsRepository.findByUsername(new Username(username));
        if (adminOptional.isPresent()) {
            return new SparkplugUserDetails(adminOptional.get());
        }

        throw new UsernameNotFoundException("User with value '" + username + "' not found.");
    }
}
