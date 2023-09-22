package com.springboot.UserManagementSystem.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    private static final Logger log = Logger.getLogger(MyAspect.class);
    @Before("execution(* com.springboot.UserManagementSystem.controllers.MainController.form(..))")
    public void openingRegistrationForm() {
        log.info("Opening Registration Form");
    }
    @Before("execution(* com.springboot.UserManagementSystem.controllers.MainController.loginPage(..))")
    public void openingLoginForm() {
        log.info("Opening Login page");
    }

    @Before("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.createUser(..))")
    public void beforeCreatingUser(){
        log.info("Creating User");
    }

    @AfterThrowing("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.createUser(..))")
    public void errorWhileCreatingUser(){
        log.error("Something went wrong while creating user");
    }
    @After("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.createUser(..))")
    public void afterCreatingUser(){
        log.info("User created successfully");
    }

    @Before("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.getUserById(..))")
    public void fetchingUserById(){
        log.info("Fetching user from database according to its userId");
    }

    @Before("execution(* com.springboot.UserManagementSystem.controllers.MainController.viewPage(..))")
    public void openingViewPage(){
        log.info("Opening view page");
    }

//    @After("execution(* com.springboot.UserManagementSystem.controllers.MainController.viewPage(..))")
//    public void afterOpeningViewPage(){
//        log.info("User logged in successfully");
//    }

    @AfterThrowing("execution(* com.springboot.UserManagementSystem.controllers.MainController.viewPage(..))")
    public void errorWhileLogin(){
        log.error("Something went wrong while login");
    }

    @Before("execution(* com.springboot.UserManagementSystem.controllers.MainController.editPage(..))")
    public void openingEditPage(){
        log.info("Opening edit page for edit user");
    }

    @Before("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.updateUserDetails(..))")
    public void beforeUpdatingUser(){
        log.info("Updating User details");
    }

    @AfterThrowing("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.updateUserDetails(..))")
    public void errorWhileUpdatingUser(){
        log.error("Something went wrong while updating user");
    }
    @After("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.updateUserDetails(..))")
    public void afterEditingUser(){
        log.info("User details updated successfully");
    }

    @Before("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.getAllUsers())")
    public void fetchingAllUsers(){
        log.info("Fetching all user from database");
    }

    @Before("execution(* com.springboot.UserManagementSystem.controllers.MainController.adminPage(..))")
    public void openingAdminPage(){
        log.info("Opening admin page");
    }

    @Before("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.deleteUser(..))")
    public void beforeDeletingUser(){
        log.info("Deleting User from database");
    }

    @AfterThrowing("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.deleteUser(..))")
    public void errorWhileDeletingUser(){
        log.error("Something went wrong while deleting user");
    }
    @After("execution(* com.springboot.UserManagementSystem.services.UserServiceImpl.deleteUser(..))")
    public void afterDeletingUser(){
        log.info("User deleted successfully");
    }

    @After("execution(* com.springboot.UserManagementSystem.controllers.MainController.logout(..))")
    public void afterLogout(){
        log.info("Logout Successfully ");
    }

}
