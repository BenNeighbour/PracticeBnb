package com.benneighbour.practicebnb.authServer.service;

import com.benneighbour.practicebnb.authServer.dao.UserDao;
import com.benneighbour.practicebnb.authServer.model.user.User;
import com.benneighbour.practicebnb.authServer.model.user.securityUser.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(dao.findUserByUsername(name));
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or Password is incorrect"));

        UserDetails userDetails = new SecurityUser(optionalUser.get());
        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }

}