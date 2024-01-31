package com.example.demo.util;

import com.example.demo.account.Account;
import com.example.demo.user.CustomUserDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.user.UserOm.newUser;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @Test
    void convertToJsonTest() {
        final CustomUserDetails user = newUser();
        final Account account = new Account(user);

        final String json = JsonUtil.convertToJson(account);

        assertTrue(json.contains(user.getUserId()));
        assertTrue(json.contains(user.getUsername()));
    }

    @Test
    void convertJsonToListTest() {
        final String listJson = "[{\"user\":{\"userId\":\"user1\"}}, {\"user\":{\"userId\":\"user2\"}}]";

        final List<Account> list = JsonUtil.convertJsonToList(listJson, Account.class);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("user1", list.get(0).getUser().getUserId());
        assertEquals("user2", list.get(1).getUser().getUserId());
    }
}
