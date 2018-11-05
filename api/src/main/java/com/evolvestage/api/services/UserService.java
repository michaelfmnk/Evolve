package com.evolvestage.api.services;

import com.evolvestage.api.dtos.UserBriefDto;
import com.evolvestage.api.dtos.UserDto;
import com.evolvestage.api.entities.User;
import com.evolvestage.api.repositories.UsersRepository;
import com.evolvestage.api.security.JwtUserFactory;
import com.evolvestage.api.utils.MessagesService;
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
    private final ConverterService converterService;

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

    public UserDto getUserInfo(Integer userId) {
        return converterService.toDto(findValidUserById(userId));
    }

    public UserBriefDto getUserBriefInfo(Integer userId) {
        return converterService.toBriefDto(findValidUserById(userId));
    }
}
