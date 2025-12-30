//'package' is collection of class and interface, to keep classes together
//'controller' package is MANAGER
//'dto' package is that extra jholi jo manager ko office me 1 se zyada and 6 se kam data store karne ke liye lagega
// jska jiska table banana hai vo sab classes ko 'models' nam ke package me rakhenge
// 'repositories' is MAJD


package com.vbs.demo.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
        //yahi class ka table banana hai ye baat batana padega therefore iss class user par kuch marking karna padega so we use entity
        //entity laal aaya toh m pe jake reload all maven projects

@Data
        //partial purpose-automatic constructor, complete purpose-automatic getter setter banta hai jo ki lombok library karta hai

@AllArgsConstructor
        //the only purpose-automatic constructor banta hai

@NoArgsConstructor
        //ye chiz ye sab automatic banta hai--constructor
public class User {
        @Id
        //id will get automatically PRIMARY KEY--id hum user se nhi maang rhe
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        //har bar unique key assign hoga har user ko, i++ hoga
        int id;

        @Column(nullable = false, unique = true)
        String username;
        @Column(nullable = false) //nullable false=null values reject kar dega, unique=true sab username unique hona chahiye
        String password;
        @Column(nullable = false, unique = true)
        String email;
        @Column(nullable = false)
        String name;
        @Column(nullable = false)
        String role;
        @Column(nullable = false)
        double balance;
        //ye sab class bana liya ab isi class ka table(object) banana hai
}

//obj.getid- get use karte hai coz private variable hai..sidha access nhi mil sakta