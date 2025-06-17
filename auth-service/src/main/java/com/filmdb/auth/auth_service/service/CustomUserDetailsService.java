package com.filmdb.auth.auth_service.service;

import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.Username;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUser = userRepository.findByUsername(Username.of(username));
//        User user = optionalUser.get();
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return new CustomUserDetails(user);
        return userRepository.findByUsername(Username.of(username))
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
