package com.springboot.UserManagementSystem.configs;

import com.springboot.UserManagementSystem.dao.UserRepository;
import com.springboot.UserManagementSystem.models.User;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    HttpSession session;

    private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);

        User user = this.userRepository.getUserByEmail(username);
        if (user==null){
            System.out.println("User not found with this email!!");
            throw new UsernameNotFoundException("User not found with this email!!");
        }else {
            session.setAttribute("email", username);
            return new CustomUserDetails(user);
        }
    }
}
