package com.backen.multicommerce.security.service.imp;

import com.backen.multicommerce.security.entity.PrimaryUser;
import com.backen.multicommerce.security.entity.User;
import com.backen.multicommerce.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByNick(username).get();
        return PrimaryUser.build(user);
    }
}
