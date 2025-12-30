//MANAGER

//signup ka code

package com.vbs.demo.controller;

import com.vbs.demo.dto.DisplayDto;
import com.vbs.demo.dto.LoginDto;
import com.vbs.demo.dto.UpdateDto;
import com.vbs.demo.models.History;
import com.vbs.demo.models.Transaction;
import com.vbs.demo.models.User;
import com.vbs.demo.repositories.HistoryRepo;
import com.vbs.demo.repositories.TransactionRepo;
import com.vbs.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
        //restcontroller batayega manager hai

@CrossOrigin(origins ="*")
        //alag alag port sharing allowed nhi hota, sab data share nhi kar sakte, allow karne ke liye @cross origin/ to connect data bet frontend and backend

public class UserController {

    @Autowired
        //majdur(interface) ka object nhi bana sakte but banana padega isiliye autowired

    UserRepo userRepo;
            //majdur ka obj
            //manager ko samjha post aaya hai register ke upar-means majdur ko bolna hai save kar-needs majdur ka access- therefore majdur ka obj banao (1st is classname 2nd is obj name)
            @Autowired
            HistoryRepo historyRepo;
            @Autowired
            TransactionRepo transactionRepo;
    @PostMapping("/register")
             //postmapping se clear hua ki this is post, therefore manager activate hua and end point dekhna padega, yaha /register hai which is sighup ka end point so sirf vhi activate hoga baki kn, forntend me register likha hai therefore match hoke activate hoga



    //signup func is this: register
    public String register(@RequestBody User user) {
              //ye bana function-'register', String coz last me string return karna hai
              //data aane wala hai from request ka body-@RequestBody, isko save karna hai user ke class ka obj (4 log ka kholi)    User- classname   user-obj name

        userRepo.save(user);
                 //userRepo(majdur) ko bolenge save kar and usko pass karenge obj

        return "Signup Successful";


    }

    //login ka code:

    @PostMapping("/login")
    public String login(@RequestBody LoginDto u) {
        User user = userRepo.findByUsername(u.getUsername());

        if (user == null) {
            return "User not found";
        }

        if (!u.getPassword().equals(user.getPassword())) {
            return "Password incorrect";
        }

        if (!u.getRole().equals(user.getRole())) {
            return "Role incorrect";
        }
        return String.valueOf(user.getId()); //id return kiya

    }
   @GetMapping("/get-details/{id}") //get mapping hai yaha par
   public DisplayDto display(@PathVariable int id){ //diplay is the function
        User user= userRepo.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        DisplayDto displayDto= new DisplayDto();
        displayDto.setBalance(user.getBalance());
        displayDto.setUsername(user.getUsername());
        return displayDto;
   }


   @PostMapping("/update")
    public String update(@RequestBody UpdateDto obj){
        User user= userRepo.findById(obj.getId()).orElseThrow(()->new RuntimeException("Not found"));

        if (obj.getKey().equalsIgnoreCase("name"))
       {
           if (user.getName().equalsIgnoreCase(obj.getValue())) return "Cannot be same";
           user.setName(obj.getValue());
       }

       else if (obj.getKey().equalsIgnoreCase("password"))
       {
           if (user.getPassword().equalsIgnoreCase(obj.getValue())) return "Cannot be same";
           user.setPassword(obj.getValue());
       }

        else  if(obj.getKey().equalsIgnoreCase("Email")) {
            if (user.getEmail().equalsIgnoreCase(obj.getValue())) return "Cannot be same";
            User user2 = userRepo.findByEmail(obj.getValue());
            if (user2 != null) return "Email already exists";
            user.setEmail(obj.getValue());
        }
       else {
           return "Invalid key";
       }
       userRepo.save(user);
       return "Update done successfully";
    }
    @PostMapping("/add")
    public String add(@RequestBody User user){


        userRepo.save(user);


        return "Successfully added";
    }

            //add is path variable, adminId pass kiya to know konsa admin is adding
    @PostMapping("/add/{adminId}")
    public String add(@RequestBody User user,@PathVariable int adminId){

            History h1=new History();
        h1.setDescription("Admin" +adminId+ "Creates User "+ user.getUsername());
        userRepo.save(user);

        if(user.getBalance()>0) {
            User user2 = userRepo.findByUsername(user.getUsername());
            Transaction t = new Transaction();
            t.setAmount(user.getBalance());
            t.setCurrBalance(user.getBalance());
            t.setDescription("Rs " + user.getBalance() + " Deposit successful");
            t.setUserId(user2.getId());

            transactionRepo.save(t);
            historyRepo.save(h1);

        }
        return "Successfully added";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam String sortBy, @RequestParam String order) {
        Sort sort;
        if (order.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        }

        else{
            sort= Sort.by(sortBy).ascending();
        }
        return userRepo.findAllByRole("customer",sort);
}


@GetMapping("/users/{keyword}")
public List<User> getUser(@PathVariable String keyword){
        return userRepo.findByUsernameContainingIgnoreCaseAndRole(keyword,"customer");
    }
    @DeleteMapping("delete-user/{userId}/admin/{adminId}")
    public String delete(@PathVariable int userId,@PathVariable int adminId){
        User user= userRepo.findById(userId).orElseThrow(()->new RuntimeException("Not found"));
        if(user.getBalance()>0){
            return "Balance should be zero";
        }
        History h1=new History();
        h1.setDescription("Admin "+adminId+"Deleted User "+user.getUsername());
        historyRepo.save(h1);
        userRepo.delete(user);
        return "User Deleted Successfully";
    }

    }
// code exited with 1 error- kill port coz ek port pe 2 kaam chal rhe- search on google