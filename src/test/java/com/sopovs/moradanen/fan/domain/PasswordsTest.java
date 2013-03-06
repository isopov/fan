package com.sopovs.moradanen.fan.domain;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordsTest {

    @Test
    public void test(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String rawPassword = "FooBar#228";
        String hash1 = encoder.encode(rawPassword);
        String hash2 = encoder.encode(rawPassword);
        Assert.assertTrue(!hash1.equals(hash2));
        Assert.assertTrue(encoder.matches(rawPassword,hash1));
        Assert.assertTrue(encoder.matches(rawPassword,hash2));
   }


}
