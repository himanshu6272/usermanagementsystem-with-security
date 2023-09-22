package com.springboot.UserManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Entity
@Table(name = "user_address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aid;
    @NotEmpty(message = "*Street is required")
    private String street;
    @NotEmpty(message = "*City is required")
    private String city;
    @NotEmpty(message = "*State is required")
    private String state;
    @NotEmpty(message = "*Zip is required")
    @Pattern(regexp = "^[0-9]{6,7}$", message = "*Please enter valid zipcode")
    private String zip;
    @NotEmpty(message = "*Country is required")
    private String country;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
