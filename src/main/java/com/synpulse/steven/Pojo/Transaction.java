package com.synpulse.steven.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String username;
    private String unique_id;
    private Integer amount;
    private String currency;
    private String iban;
    private String date;
    private String description;
}
