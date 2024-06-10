package com.demo.mssql.javaspringbootconnectmssqlsample.loader;

import com.demo.mssql.javaspringbootconnectmssqlsample.model.User;
import com.demo.mssql.javaspringbootconnectmssqlsample.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        userRepository.deleteAll();
        userRepository.save(new User("Puck", 25, generateBinaryData()));
        userRepository.save(new User("Rang", 27, generateBinaryData()));
        userRepository.save(new User("Elen", 14, generateBinaryData()));
    }

    // Method to generate random binary data
    private byte[] generateBinaryData() {
        byte[] binaryData = new byte[10]; // Change the size according to your requirements
        new Random().nextBytes(binaryData);
        return binaryData;
    }
}
