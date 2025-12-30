package com.vbs.demo;

import com.vbs.demo.models.History;
import com.vbs.demo.models.User;
import com.vbs.demo.repositories.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootApplication
public class VbsApplication
{
	public static void main(String[] args) {
		SpringApplication.run(VbsApplication.class, args);
	}


	}

