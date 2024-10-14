package com.example.testserverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private String to;             
    private String subject;        
    private String body;           
    private String attachment;     
    private String recipientName;  
    private String title;          
    private String milestone1;     
    private String milestone2;     
    private String milestone3;     
    private String phase1Date;     
    private String phase2Date;     
    private String phase3Date;     
    private String actionItem1;    
    private String actionItem2;    
    private String actionItem3;    
    private String senderName;     
}
