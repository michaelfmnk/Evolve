package com.dreamteam.api.services;

import com.dreamteam.api.entities.User;
import com.dreamteam.api.repositories.UsersRepository;
import com.dreamteam.api.security.JwtUserFactory;
import com.dreamteam.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final MessagesService messagesService;

    public User findValidUserById(Integer userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("user.not.found")));
    }

    public User findValidUserByEmail(String email) {
        return usersRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("user.not.found")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return JwtUserFactory.create(findValidUserByEmail(username));
    }
}
