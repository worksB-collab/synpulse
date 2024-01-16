package com.example.demo.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.demo.Util.convertJsonToObject;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserDao userDao;

    public CustomUserDetails getUserById(final String userId) {
        return userDao.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user not exist"));
    }

    public CustomUserDetails getUserByUsername(final String username) {
        return userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not exist"));
    }


    public void saveUser(final String userJson) {
        userDao.save(convertJsonToObject(userJson));
    }

}
