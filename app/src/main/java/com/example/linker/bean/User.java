package com.example.linker.bean;

import java.io.Serializable;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/11/10 15:33
 * <br/>
 *
 * @since
 */
public class User implements Serializable {
    private int id;
    private String name;
    private String telephone;
    private String qq;
    private String email;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id, String name, String telephone, String qq, String email) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.qq = qq;
        this.email = email;
    }
}
