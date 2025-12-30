package com.vbs.demo.controller;

import com.vbs.demo.dto.TransactionDto;
import com.vbs.demo.dto.TransferDto;
import com.vbs.demo.models.Transaction;
import com.vbs.demo.models.User;
import com.vbs.demo.repositories.TransactionRepo;
import com.vbs.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@CrossOrigin(origins = "*")

public class TransactionController {
    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    UserRepo userRepo;


    //deposit code
    @PostMapping("/deposit")
    public String deposit(@RequestBody TransactionDto obj) {
        User user = userRepo.findById(obj.getId()).orElseThrow(() -> new RuntimeException("wrong id"));
        double newBalance = user.getBalance() + obj.getAmount();
        user.setBalance(newBalance);
        userRepo.save(user);

        Transaction t = new Transaction();
        t.setAmount(obj.getAmount());
        t.setCurrBalance(newBalance);
        t.setDescription("Rs " + obj.getAmount() + " deposit successful");
        t.setUserId(obj.getId());
        transactionRepo.save(t);
        return "Deposit successful";
    }


    //withdraw code
    @PostMapping("/withdraw")
    public String withdraw(@RequestBody TransactionDto obj) {
        User user = userRepo.findById(obj.getId()).orElseThrow(() -> new RuntimeException("wrong id"));
        double newBalance = user.getBalance() - obj.getAmount();
        if (newBalance < 0) {
            return "insufficient balance";
        }
        user.setBalance(newBalance);
        userRepo.save(user);

        Transaction t = new Transaction();
        t.setAmount(obj.getAmount());
        t.setCurrBalance(newBalance);
        t.setDescription("Rs " + obj.getAmount() + " withdraw successful");
        t.setUserId(obj.getId());
        transactionRepo.save(t);
        return "Withdrawal successful";
    }


    //transfer code
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferDto obj) {
        User sender = userRepo.findById(obj.getId()).orElseThrow(() -> new RuntimeException("Not found"));
        User rec = userRepo.findByUsername(obj.getUsername());
        if (rec == null) {
            return "Receiver not found";
        }
        if (sender.getId() == rec.getId()) {
            return "Self Transaction not allowed";
        }
        if (obj.getAmount() < 1) {
            return "Invalid amount";
        }

        double sbalance = sender.getBalance() - obj.getAmount();
        if (sbalance < 0) {
            return "Unsufficient balance";
        }
        double rbalance = rec.getBalance() + obj.getAmount();

        sender.setBalance(sbalance);
        rec.setBalance(rbalance);
        userRepo.save(sender);
        userRepo.save(rec);

        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();

        t1.setAmount(obj.getAmount());
        t1.setCurrBalance(sbalance);
        t1.setDescription("Rs " + obj.getAmount() + " Send to user " + obj.getUsername());
        t1.setUserId(sender.getId());

        t2.setAmount(obj.getAmount());
        t2.setCurrBalance(rbalance);
        t2.setDescription("Rs " + obj.getAmount() + " received from " + sender.getUsername());
        t2.setUserId(rec.getId());

        transactionRepo.save(t1);
        transactionRepo.save(t2);
        return "Transfer done successfully";
    }


    //passbook code:
    @GetMapping("/passbook/{id}")
    public List<Transaction> getPassbook(@PathVariable int id)
    {
        return transactionRepo.findAllByUserId(id);
    }
}

