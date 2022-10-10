/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts.apps.mailreader.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TODO Complete use case tests for:
 * - [01 ] findUser(existing)                               [done]
 * - [02 ] findUser(bogus)                                  [done]
 * - [03 ] findUsers()                                      [done]
 * - [04 ] createUser1, find(new one 1)                     [done]
 * - [04a] createUser1, find(new one 1) find subscr         [done]
 * - [05 ] createUser2, find(existing)                      [done]
 * - [05a] createUser2, find(existing)  find subscr         [done]
 * - [06 ] createUser3, findUsers(all)                      [done]
 * - [06a] createUser4 (duplicate)                          [done]
 * - [07 ] findUsers(all)                                   [done]
 * - [08 ] create, save, close, open find(new one)          [done]
 * - [09 ] create, save, close, open findUsers(all)         [done]
 * - [10 ] find(bogus)                                      [done]
 * - [11 ] create, remove, find(valid), find(invalid)       [done]
 *
 * Registrations
 *
 */

public abstract class BaseTestUserDatabase {

    protected UserDatabase userDatabase;
    private int userCt = 10;
    private int subscriptionCt = 20;

    @BeforeEach
    protected void setUp() throws Exception {
            userDatabase = getNewUserDatabase();
      generateUsers(userCt, subscriptionCt, "");
    }


    /**
     *
     */
    private void generateUsers(int users, int subs, String prefix) {
        for (int i = 0; i < users; i++) {
          User user = getNewUser(userDatabase, prefix + "user" + i);
          user.setFromAddress(prefix + "fromAddress" + i);
          user.setFullName(prefix + "fullName" + i);
          user.setPassword(prefix + "password" + i);

          for (int j = 0; j < subs; j++) {
              Subscription subscription = getNewSubscription(user, prefix + "host" + j);
              subscription.setAutoConnect(j % 1 == 0);
              subscription.setUsername(prefix + "subscriptionUserName" + j);
              subscription.setPassword(prefix + "subscriptionPassword" + j);
              subscription.setType(prefix + "type" + j);
          }
        }
    }


    @AfterEach
    protected void tearDown() throws Exception {
        userDatabase.close();
    }

    @Test
    public void testCase01() throws ExpiredPasswordException {
        User user = userDatabase.findUser("user5");
        assertEquals("user5", user.getUsername(), "Check username");
        assertEquals("fromAddress5", user.getFromAddress(), "Check fromAddress");
        assertEquals("fullName5", user.getFullName(), "Check fullName");
        assertEquals("password5", user.getPassword(), "Check password");
        assertNull(user.getReplyToAddress(), "Check replyToAddress");

    }
    @Test
    public void testCase02() throws ExpiredPasswordException {
        User user = userDatabase.findUser("bogusName");
        assertNull(user);
    }

    @Test
    public void testCase03() {
        User[] users = userDatabase.findUsers();
        assertEquals(users.length, userCt, "Check users");
    }

    @Test
    public void testCase04() throws ExpiredPasswordException {
        String newUserName = "newUser04";
        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser2 = userDatabase.findUser(newUserName);
        assertEquals(newUserName, newUser2.getUsername(), "Check username");
        assertEquals("fromAddress1", newUser2.getFromAddress(), "Check fromAddress");
        assertEquals("replyToAddress1", newUser2.getReplyToAddress(), "Check replyToAddress");
        assertEquals("pass1", newUser2.getPassword(), "Check password");
        assertNull(newUser2.getFullName(), "Check fullName");
    }

    @Test
    public void testCase04a() throws ExpiredPasswordException {
        String newUserName = "newUser04a";
        int subs = 5;

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser2 = userDatabase.findUser(newUserName);
        assertEquals(newUserName, newUser2.getUsername(), "Check username");
        assertEquals("fromAddress1", newUser2.getFromAddress(), "Check fromAddress");
        assertEquals("replyToAddress1", newUser2.getReplyToAddress(), "Check replyToAddress");
        assertEquals("pass1", newUser2.getPassword(), "Check password");
        assertNull(newUser2.getFullName(), "Check fullName");

        generateUsers(3, subs, "04a");

        User newUser3 = userDatabase.findUser("04auser1");
        Subscription[] subscriptions = newUser3.getSubscriptions();
        assertEquals(subs, subscriptions.length, "Testing subscriptions length");
        newUser3.removeSubscription(subscriptions[0]);

        // TODO this is a problem
//        assertTrue(subscriptions.length < subs, "Testing subscriptions length");

    }

    @Test
    public void testCase05() throws ExpiredPasswordException {
        String newUserName = "anotherNewUser05";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser5 = userDatabase.findUser("user5");
        assertEquals("user5", newUser5.getUsername(), "Check username");
        assertEquals("fromAddress5", newUser5.getFromAddress(), "Check fromAddress");
        assertEquals("fullName5", newUser5.getFullName(), "Check fullName");
        assertEquals("password5", newUser5.getPassword(), "Check password");
        assertNull(newUser5.getReplyToAddress(), "Check replyToAddress");

    }

    @Test
    public void testCase05a() throws ExpiredPasswordException {
        String newUserName = "anotherNewUser05a";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser5a = userDatabase.findUser("user5");
        assertEquals("user5", newUser5a.getUsername(), "Check username");
        assertEquals("fromAddress5", newUser5a.getFromAddress(), "Check fromAddress");
        assertEquals("fullName5", newUser5a.getFullName(), "Check fullName");
        assertEquals("password5", newUser5a.getPassword(), "Check password");
        assertNull(newUser5a.getReplyToAddress(), "Check replyToAddress");

        Subscription[] subscriptions = newUser5a.getSubscriptions();
        assertEquals(subscriptionCt, subscriptions.length, "Testing subscriptions length");

    }

    @Test
    public void testCase06() throws ExpiredPasswordException {
        String newUserName = "anotherNewUser06";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User user6 = userDatabase.findUser("user6");
        assertEquals("user6", user6.getUsername(), "Check username");
        assertEquals("fromAddress6", user6.getFromAddress(), "Check fromAddress");
        assertEquals("fullName6", user6.getFullName(), "Check fullName");
        assertEquals("password6", user6.getPassword(), "Check password");
        assertNull(user6.getReplyToAddress(), "Check replyToAddress");

    }

    @Test
    public void testCase07() throws ExpiredPasswordException {
        String newUserName = "anotherNewUser07";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User user7 = userDatabase.findUser("user7");
        assertEquals("user7", user7.getUsername(), "Check username");
        assertEquals("fromAddress7", user7.getFromAddress(), "Check fromAddress");
        assertEquals("fullName7", user7.getFullName(), "Check fullName");
        assertEquals("password7", user7.getPassword(), "Check password");
        assertNull(user7.getReplyToAddress(), "Check replyToAddress");

        User[] users = userDatabase.findUsers();
        assertEquals(userCt + 1, users.length, "Check users");

    }

    @Test
    public void testCase08() throws ExpiredPasswordException {
        String newUserName = "newUser08";
        int subs = 5;

      User newUser = userDatabase.createUser(newUserName);
      newUser.setPassword("pass1");
      newUser.setFromAddress("fromAddress1");
      newUser.setReplyToAddress("replyToAddress1");

      // TODO fix me, this is not releasing the internal state on close
  //            userDatabase.save();
  //            userDatabase.close();
  //            userDatabase.open();

      User newUser2 = userDatabase.findUser(newUserName);
      assertEquals(newUserName, newUser2.getUsername(), "Check username");
      assertEquals("fromAddress1", newUser2.getFromAddress(), "Check fromAddress");
      assertEquals("replyToAddress1", newUser2.getReplyToAddress(), "Check replyToAddress");
      assertEquals("pass1", newUser2.getPassword(), "Check password");
      assertNull(newUser2.getFullName(), "Check fullName");

      generateUsers(3, subs, "08");

      User newUser3 = userDatabase.findUser("08user1");
      Subscription[] subscriptions = newUser3.getSubscriptions();
      assertEquals(subs, subscriptions.length, "Testing subscriptions length");

  //            userDatabase.save();
  //            userDatabase.close();
  //            userDatabase.open();

      User newUser4 = userDatabase.findUser("08user1");
      Subscription[] subscriptions2 = newUser4.getSubscriptions();
      assertEquals(subs, subscriptions2.length, "Testing subscriptions length");

    }

    @Test
    public void testCase09() {

            // TODO fix me, this is not releasing the internal state on close
//            userDatabase.save();
//            userDatabase.close();
//            userDatabase.open();

            User[] users = userDatabase.findUsers();
            assertEquals(userCt, users.length, "Testing users count");

    }

    @Test
    public void testCase010() throws ExpiredPasswordException {

            // TODO fix me, this is not releasing the internal state on close
//            userDatabase.save();
//            userDatabase.close();
//            userDatabase.open();

            User user = userDatabase.findUser("bogus user");
            assertNull(user, "Find non-existing user");

    }

    @Test
    public void testCase011() throws ExpiredPasswordException {

        String newUserName = "newUser11";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User user = userDatabase.findUser(newUserName);
        assertNotNull(user, "Find non-existing user");

        userDatabase.removeUser(user);
        User user2 = userDatabase.findUser(newUserName);
        assertNull(user2, "Find non-existing user");

    }


    protected abstract UserDatabase getNewUserDatabase();
    protected abstract User getNewUser(UserDatabase db, String userName);
    protected abstract Subscription getNewSubscription(User user, String host);

}
