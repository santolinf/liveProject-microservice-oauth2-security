package com.liveproject.oauth2.authorization.server.service;

import com.liveproject.oauth2.authorization.server.model.ApplicationUser;
import com.liveproject.oauth2.authorization.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(ApplicationUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
