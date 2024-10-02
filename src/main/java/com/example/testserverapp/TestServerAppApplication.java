package com.example.testserverapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestServerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestServerAppApplication.class, args);
        // Menampilkan pesan ketika server berjalan
        System.out.println("Server Test Server App is running");
    }
}
