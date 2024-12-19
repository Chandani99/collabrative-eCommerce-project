package com.api.ecommerce.util;


import com.api.ecommerce.model.User;
import com.api.ecommerce.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUtil {

    private final UserRepository userRepository;


    private static final String USER_ERROR = "User Not Found with username: ";

    public AppUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public String loggedInEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(USER_ERROR + authentication.getName()));
        return user.getEmail();
    }

    public Long loggedInUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(USER_ERROR  + authentication.getName()));
        return user.getUserId();
    }

    public User loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(USER_ERROR  + authentication.getName()));

    }




}
