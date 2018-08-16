package main.Services.Impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginServiceTest {

    LoginService loginService = new LoginService();

    @Test
    public void find() {
        assertNotNull(loginService.find(null,"12345678"));
    }

    @Test
    public void validate() {
        assertNotNull(loginService.validate(null));
    }

    @Test
    public void findTokenCookie() {
        assertNotNull(loginService.findTokenCookie(null));
    }
}