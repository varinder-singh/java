package com.baxter.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BaxterUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username){
    return new User("sandhu", "sandhu", new ArrayList());
    }
}
