package main.Services.Impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashServiceTest {
    String pass = "test";
    String hash = null;

    @Test
    public void getSaltedHash() throws Exception {
        hash=HashService.getSaltedHash(pass);
        assertTrue(pass!=hash);
    }

    @Test
    public void check() throws Exception {
        hash=HashService.getSaltedHash(pass);
        assertTrue(HashService.check(pass,hash));
    }
}