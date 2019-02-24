package by.intexsoft.scrzs.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * User entity class
 */
@Data
public class User implements Serializable {
    private Long userid;
    private String username;
    private String firstname;
    private String lastname;
    private int age;
    private String phonenumber;
}
