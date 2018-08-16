package main.Services.Impl;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class UserInfoServiceTest {

    UserInfoService userInfoService = new UserInfoService();

    @Test
    public void getUserExtendedById() throws IOException {
        assertNotNull(userInfoService.getUserExtendedById(0));
    }
}