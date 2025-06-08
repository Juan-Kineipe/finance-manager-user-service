package com.kineipe.financemanageruserservice.service;

import com.kineipe.financemanageruserservice.domain.User;
import com.kineipe.financemanageruserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Logger log = Logger.getLogger(UserService.class.getName());

    public User findById(Long id) {
        log.info("Finding user by id: " + id);
        User user = userRepository.findById(id).orElseThrow();
        log.info("Found user: " + user);
        return user;
    }

    public List<User> findAll() {
        log.info("Finding all users");
        return userRepository.findAll();
    }

    public User update(User user) {
        log.info("Updating user: " + user);
        User entity = userRepository.findById(user.getId()).orElseThrow();
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        return userRepository.save(entity);
    }

    public void delete(Long id) {
        log.info("Deleting user by id: " + id);
        User entity = userRepository.findById(id).orElseThrow();
        userRepository.delete(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Finding user by username " + username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            log.info("Found user by username " + user);
            return user;
        } else {
            log.info("Username not found " + username);
            throw new UsernameNotFoundException("Username" + username + "not found");
        }
    }
}
