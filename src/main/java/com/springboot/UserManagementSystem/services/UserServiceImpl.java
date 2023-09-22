package com.springboot.UserManagementSystem.services;

import com.springboot.UserManagementSystem.Exceptions.ApiResponse;
import com.springboot.UserManagementSystem.dao.AddressRepository;
import com.springboot.UserManagementSystem.dao.UserRepository;
import com.springboot.UserManagementSystem.models.Address;
import com.springboot.UserManagementSystem.models.User;
//import com.springboot.UserManagementSystem.utils.CheckValidation;
import com.springboot.UserManagementSystem.utils.EncryptPwd;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private static String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    //    @Autowired
//    private CheckValidation validation;
    public void createUser(User user, MultipartFile file, String cnfPassword, HttpSession session) throws Exception {
        List<Address> addresses = user.getAddresses();
        String message = validData(user, cnfPassword);
//        List<String> errors = new ArrayList<>();
        if (addresses == null) {
            throw new Exception("Please add at least one address!!");
//            session.setAttribute("message", new ApiResponse("", null, "alert-danger"));
        } else if (!message.equals("valid")) {
            session.setAttribute("message", new ApiResponse(message, null, "alert-danger"));
        } else {
            try {
                for (Address address : addresses) {
                    address.setUser(user);
                }
                user.setAddresses(addresses);
                byte[] data = file.getBytes();
                user.setImage(data);
                user.setPassword(encoder.encode(user.getPassword()));
                this.userRepository.save(user);
                session.setAttribute("message", new ApiResponse("User Registered Successfully", null, "alert-success"));
            } catch (Exception e) {
                session.setAttribute("message", new ApiResponse(e.getLocalizedMessage(), null, "alert-danger"));
            }
        }
    }


    public boolean userExist(String email) {
        return this.userRepository.userExist(email);
    }


    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return this.userRepository.getById(id);
    }

    @Override
    public void updateUserDetails(User user, int id, MultipartFile file, String[] addressid, HttpSession session) throws Exception {
        List<Address> addressList = new ArrayList<>();
        List<Address> newAddresses = user.getAddresses();
        if (newAddresses != null) {
            for (Address address : newAddresses) {
                address.setUser(user);
                addressList.add(address);
            }
        }

        if (addressid != null) {

            List<Address> addresses = this.addressRepository.getAddressByUserId(user.getId());
            int index = 0;
            int oldAddressid[] = new int[addresses.size()];
            int addressIdLength = 0;
            if (addressid != null) {
                addressIdLength = addressid.length;
            }
            int count = 0;
            for (Address address : addresses) {
                oldAddressid[index] = address.getAid();
                if (count < addressIdLength && addressid[count].length() != 0) {
                    int addrssid = Integer.parseInt(addressid[count]);
                    if (oldAddressid[index] == addrssid) {
                        count++;
                    } else {
                        if (addressList.size() != 0) {
                            this.addressRepository.deleteAddress(address.getAid());
                            log.info("address deleted");
                        } else {
                            throw new Exception("Please add at least one address!");
                        }

                        //user Address deleted
                    }
                } else {
                    if (addressList.size() != 0) {
                        this.addressRepository.deleteAddress(address.getAid());
                        log.info("address deleted");
                    } else {
                        throw new Exception("Please add at least one address!");
                    }
                }
                index++;
            }
        }

            if (addressList.size() == 0) {
                session.setAttribute("message", new ApiResponse("Please add at least one address!", null, "alert-danger"));
                throw new Exception("Please add at least one address!");
            } else {

                try {
                    User oldUser = this.userRepository.getById(id);
                    user.setEmail(oldUser.getEmail());
                    user.setRole(oldUser.getRole());
                    user.setAddresses(addressList);
                    user.setPassword(oldUser.getPassword());
                    user.setSecurityQuestion(oldUser.getSecurityQuestion());
                    user.setSecurityAnswer(oldUser.getSecurityAnswer());
                    if (file.getBytes().length == 0) {
                        user.setImage(oldUser.getImage());
                    } else {
                        user.setImage(file.getBytes());
                    }
                    this.userRepository.save(user);
                    session.setAttribute("message", new ApiResponse("User Updated Successfully", null, "alert-success"));
                } catch (Exception e) {
                    session.setAttribute("message", new ApiResponse(e.getLocalizedMessage(), null, "alert-danger"));
                }
            }

    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.getUserByEmail(email);
    }

    @Override
    public void deleteUser(User user, HttpSession session) {
        this.userRepository.delete(user);
        session.setAttribute("message", new ApiResponse("User Deleted Successfully", null, "alert-success"));
    }

    @Override
    public void updatePassword(String email, String password, HttpSession session) {
        session.setAttribute("message", new ApiResponse("Password changed Successfully", null, "alert-success"));
        this.userRepository.updatePassword(password, email);
    }


    public boolean validatepwd(String pwd) {
        if (Pattern.matches(regex, pwd)) {
            log.debug("Password Validation Pass");
            return false;
        } else {
            log.debug("Password Validation Fails");
            return true;
        }
    }

    public String validData(User user, String repwd) {
        String message = "";
        if (userExist(user.getEmail())) {
            message = "*Email already exist";
        } else if (validatepwd(user.getPassword())) {
            message = "*Please Choose Strong Password.";
        } else if (!user.getPassword().equals(repwd)) {
            message = "*Confirm password Should be same as Password.";
        } else {
            message = "valid";
        }
        return message;
    }
}
