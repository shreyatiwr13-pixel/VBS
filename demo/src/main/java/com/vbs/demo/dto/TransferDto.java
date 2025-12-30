package com.vbs.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransferDto {
    String username;
    int id;
    double amount;
    //id sender ka hai..user table update karna hai
    //sender ka table update hoga by id, receiver ka update hoga by username
}
//'post' me data request ke body me jata hai and 'GET' me data path variable me jata hai