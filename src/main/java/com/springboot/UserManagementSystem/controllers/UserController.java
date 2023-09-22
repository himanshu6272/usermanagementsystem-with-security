package com.springboot.UserManagementSystem.controllers;

import com.springboot.UserManagementSystem.Exceptions.ApiResponse;
import com.springboot.UserManagementSystem.models.Address;
import com.springboot.UserManagementSystem.models.User;
import com.springboot.UserManagementSystem.services.UserService;
import com.springboot.UserManagementSystem.utils.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult br, Model model, @RequestParam("file") MultipartFile file, @RequestParam("cnfPassword") String cnfPassword, HttpSession session) {
        log.info(br);
        try {
            if (br.hasErrors()) {
                throw new Exception("Please fill the all valid addresses field!");
            }
            this.userService.createUser(user, file, cnfPassword, session);
            return "redirect:/login";
        } catch (Exception e) {
            log.error(e);
            model.addAttribute("user", user);
            session.setAttribute("message", new ApiResponse(e.getLocalizedMessage(), null, "alert-danger"));
            return "register";
        }

    }

    @PostMapping("/editUser")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult br, @RequestParam("id") int id, @RequestParam("file") MultipartFile file, @RequestParam("addressid") String[] addressid, HttpSession session){
        try {
            if (br.hasErrors()) {
                log.info(br);
                throw new Exception("Please fill the all valid addresses field!");
            } else{
                this.userService.updateUserDetails(user, id, file, addressid, session);
            if (this.userService.getUserById(id).getRole().equals("ADMIN")) {
                return "redirect:/admin";
            } else {
                return "redirect:/viewPage";
            }
        }
        }catch (Exception e){
            session.setAttribute("message", new ApiResponse(e.getLocalizedMessage(), null, "alert-danger"));
            return "redirect:/editPage";
        }
//        if (request.getParameterValues("aid") == null){
//            return "redirect:/editPage";
//        }else


    }

    @GetMapping("/deleteUser")
    public String deleteUser(HttpSession session){
        int id = (int) session.getAttribute("userId");
        this.userService.deleteUser(this.userService.getUserById(id), session);
        return "redirect:/admin";
    }

}
