//MAJDUR

package com.vbs.demo.repositories;

import com.vbs.demo.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
        //batana padega ye repository hai

public interface UserRepo extends JpaRepository<User, Integer> {
         //JpaRepository ke pass sql queries hai, isko 2 chiz chahiye to make change in database- User is table ka naam and integer is PRIMARY KEY ka data type

    User findByUsername(String username);

    User findByEmail(String value);

    List<User> findAllByRole(String customer, Sort sort);

    List<User> findByUsernameContainingIgnoreCaseAndRole(String keyword, String customer);
}

    //ye majdur(interface) ko sql ka acces dena pdega therefore extends JpaRepo- usko 2 chiz denge i.e user,integer

