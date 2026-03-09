package org.kateryna.habit_tracker.service;

import org.kateryna.habit_tracker.model.User;
import org.kateryna.habit_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            var userDetails = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userDetails.getEmail())
                    .password(userDetails.getPassword())
                    .build();
        }
        else{
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

    }
}
