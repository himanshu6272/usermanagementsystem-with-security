package com.springboot.UserManagementSystem.services;

import com.springboot.UserManagementSystem.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {
    public void createUser(User user, MultipartFile file, String cnfPassword, HttpSession session) throws Exception;
    public boolean userExist(String email);

    List<User> getAllUsers();

    User getUserById(int id);

    void updateUserDetails(User user, int id, MultipartFile file, String[] addressid, HttpSession session) throws Exception;

    User getUserByEmail(String email);

    void deleteUser(User user, HttpSession session);

    void updatePassword(String email, String password, HttpSession session);
}
