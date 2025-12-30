package com.vbs.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column (nullable = false)
    String description;
    @Column(nullable = false, updatable = false)
            @CreationTimestamp //automatic updates date and time, better than prepersisit
                                //  @PrePersist
                                 //  protected void onCreate() {
                                    //   this.date = LocalDateTime.now();
    LocalDateTime date;

}
