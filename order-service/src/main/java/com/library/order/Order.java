package com.library.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;        
    private String readerId;      
    private String bookId;          
    private String bookTitle;       
    private String orderDate;       
    private String pickupDate;      
    private String status;         
}
