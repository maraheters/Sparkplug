package com.sparkplug.auth.application.security.service;

import com.sparkplug.auth.application.repository.UsersRepository;
import com.sparkplug.auth.domain.vo.Username;
import com.sparkplug.auth.application.security.user.SparkplugUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SparkplugUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public SparkplugUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userOptional = usersRepository.findByUsername(new Username(username));
        if (userOptional.isPresent()) {
            return new SparkplugUserDetails(userOptional.get());
        }

        throw new UsernameNotFoundException("User with value '" + username + "' not found.");
    }
}
