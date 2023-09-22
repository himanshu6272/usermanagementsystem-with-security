package com.springboot.UserManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "*Firstname is required")
    @Pattern(regexp = "^[A-Z,a-z]{2,8}$", message = "*Please fill valid firstname")
    private String firstName;
    @NotEmpty(message = "*Lastname is required")
    @Pattern(regexp = "^[A-Z,a-z]{2,8}$", message = "*Please fill valid lastname")
    private String lastName;
    @NotEmpty(message = "*Mobile is required")
    @Pattern(regexp = "^[0-9]{1,11}$", message = "*Please enter valid mobile number")
    private String mobile;
    @NotEmpty(message = "*Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "*Please enter valid email")
    private String email;
    @NotNull(message = "*Please select the role")
    private String role;
    @NotEmpty(message = "*Please select the date")
    private String dob;
    @NotNull(message = "*Please select the gender")
    private String gender;
//    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "please enter valid password")
    @NotEmpty(message = "*Password is required")
    private String password;
    @NotNull(message = "*Please select the security question")
    private String securityQuestion;
    @NotEmpty(message = "*Please select the security answer")
    private String securityAnswer;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Valid
    @JsonManagedReference
    private List<Address> addresses;

}
