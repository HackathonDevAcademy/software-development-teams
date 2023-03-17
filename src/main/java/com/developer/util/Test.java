package com.developer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void test() {
        List<String> list = new ArrayList<>();
        String pass = "password";
        for (int i = 1; i < 15; i++) {
            list.add("\n" + i + "   " + passwordEncoder.encode(pass + i));
        }
        System.out.println(list);
    }
}
