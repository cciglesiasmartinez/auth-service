package io.github.cciglesiasmartinez.auth_service.infrastructure.security;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(Username.of(username))
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public UserDetails loadUserByUserId(String userId) throws UserNotFoundException {
        User user = userRepository.findById(UserId.of(userId)).orElseThrow(UserNotFoundException::new);
        System.out.println(user.toString());
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.roles().forEach(
                role -> {
                    authorities.add(new SimpleGrantedAuthority(role.name()));
                });
        return userRepository.findById(UserId.of(userId))
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UserNotFoundException());
    }
}
