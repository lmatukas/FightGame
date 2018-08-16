package main.Services.Impl;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RegisterServiceTest {

    RegisterService registerService = new RegisterService();

    @Test
    public void find() {
        assertNotNull(registerService.find(null,null));
    }

    @Test
    public void register() throws IOException {
        assertNotNull(registerService.register(null,null,null));
    }

    @Test
    public void addUserToCache() {
        assertNotNull(registerService.addUserToCache("l.lt"));
    }
}