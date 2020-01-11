package com.nf.yy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author smile
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userInfoService.findByUserId(userId);
    }

}
