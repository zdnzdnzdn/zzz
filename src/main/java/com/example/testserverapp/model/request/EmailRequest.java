package com.example.testserverapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private String to;             // Email tujuan
    private String subject;        // Subjek email
    private String body;           // Isi email (untuk email plain text)
    private String attachment;     // Path lampiran (jika ada)

    // Variabel tambahan untuk email template HTML
    private String recipientName;  // Nama penerima
    private String title;          // Judul (misalnya, Monthly Report)
    private String milestone1;     // Milestone 1
    private String milestone2;     // Milestone 2
    private String milestone3;     // Milestone 3
    private String phase1Date;     // Tanggal untuk fase 1
    private String phase2Date;     // Tanggal untuk fase 2
    private String phase3Date;     // Tanggal untuk fase 3
    private String actionItem1;    // Action item 1
    private String actionItem2;    // Action item 2
    private String actionItem3;    // Action item 3
    private String senderName;     // Nama pengirim
}
