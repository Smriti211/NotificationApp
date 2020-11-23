package com.example.notification;

import android.util.Log;

public class User {
    public String name,email,rollno,role;
    public User(){

    }

    public User(String name, String email, String rollno,String role){
        this.name = name;
        this.email = email;
        this.rollno = rollno;
        this.role = role;
    }
}
