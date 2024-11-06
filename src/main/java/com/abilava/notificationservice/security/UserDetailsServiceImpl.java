package com.abilava.notificationservice.security;

import com.abilava.notificationservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        var user = userService.findByUsernameRequired(username);
        return User.builder()
                   .username(user.getUsername())
                   .password(user.getPassword())
                   .build();
    }

}
