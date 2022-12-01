package com.example.kursach3.services;

import com.example.kursach3.dao.UserDAO;
import com.example.kursach3.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDAO userDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User myUser= userDAO.getUserByLogin(login);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + login);
        }
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }

    public User findUserById(int id){
        return userDAO.getUserById(id);
    }

    public boolean saveUser(User user){
        User userFromDB = userDAO.getUserByEmail(user.getEmail().toLowerCase(Locale.ROOT));
        User userFromEmailDB = userDAO.getUserByEmail(user.getEmail().toLowerCase(Locale.ROOT));

        if(userFromDB != null || userFromEmailDB != null){
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.createUser(user);
        return true;
    }
}
