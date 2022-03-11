/*
 * $Id: $
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

import junit.framework.TestCase;

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

public abstract class BaseTestUserDatabase extends TestCase {

    protected UserDatabase userDatabase;
    private int userCt = 10;
    private int subscriptionCt = 20;

    protected void setUp() throws Exception {
        super.setUp();
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


    protected void tearDown() throws Exception {
        super.tearDown();
        userDatabase.close();
    }

    public void testCase01() throws Exception{
        User user = userDatabase.findUser("user5");
        assertTrue("Check username", "user5".equals(user.getUsername()));
        assertTrue("Check fromAddress", "fromAddress5".equals(user.getFromAddress()));
        assertTrue("Check fullName", "fullName5".equals(user.getFullName()));
        assertTrue("Check password", "password5".equals(user.getPassword()));
        assertNull("Check replyToAddress", user.getReplyToAddress());

    }
    public void testCase02() throws Exception{
        User user = userDatabase.findUser("bogusName");
        assertNull(user);
    }

    public void testCase03() throws Exception{
        User[] users = userDatabase.findUsers();
        assertTrue("Check users", users.length == userCt);
    }

    public void testCase04() throws Exception{
        String newUserName = "newUser04";
        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser2 = userDatabase.findUser(newUserName);
        assertTrue("Check username", newUserName.equals(newUser2.getUsername()));
        assertTrue("Check fromAddress", "fromAddress1".equals(newUser2.getFromAddress()));
        assertTrue("Check replyToAddress", "replyToAddress1".equals(newUser2.getReplyToAddress()));
        assertTrue("Check password", "pass1".equals(newUser2.getPassword()));
        assertNull("Check fullName", newUser2.getFullName());
    }

    public void testCase04a() throws Exception{
        String newUserName = "newUser04a";
        int subs = 5;

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser2 = userDatabase.findUser(newUserName);
        assertTrue("Check username", newUserName.equals(newUser2.getUsername()));
        assertTrue("Check fromAddress", "fromAddress1".equals(newUser2.getFromAddress()));
        assertTrue("Check replyToAddress", "replyToAddress1".equals(newUser2.getReplyToAddress()));
        assertTrue("Check password", "pass1".equals(newUser2.getPassword()));
        assertNull("Check fullName", newUser2.getFullName());

        generateUsers(3, subs, "04a");

        User newUser3 = userDatabase.findUser("04auser1");
        Subscription[] subscriptions = newUser3.getSubscriptions();
        assertTrue ("Testing subscriptions length", subscriptions.length == subs);
        newUser3.removeSubscription(subscriptions[0]);

        // TODO this is a problem
//        assertTrue ("Testing subscriptions length", subscriptions.length < subs);

    }

    public void testCase05() throws Exception{
        String newUserName = "anotherNewUser05";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser5 = userDatabase.findUser("user5");
        assertTrue("Check username", "user5".equals(newUser5.getUsername()));
        assertTrue("Check fromAddress", "fromAddress5".equals(newUser5.getFromAddress()));
        assertTrue("Check fullName", "fullName5".equals(newUser5.getFullName()));
        assertTrue("Check password", "password5".equals(newUser5.getPassword()));
        assertNull("Check replyToAddress", newUser5.getReplyToAddress());

    }

    public void testCase05a() throws Exception{
        String newUserName = "anotherNewUser05a";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User newUser5a = userDatabase.findUser("user5");
        assertTrue("Check username", "user5".equals(newUser5a.getUsername()));
        assertTrue("Check fromAddress", "fromAddress5".equals(newUser5a.getFromAddress()));
        assertTrue("Check fullName", "fullName5".equals(newUser5a.getFullName()));
        assertTrue("Check password", "password5".equals(newUser5a.getPassword()));
        assertNull("Check replyToAddress", newUser5a.getReplyToAddress());

        Subscription[] subscriptions = newUser5a.getSubscriptions();
        assertTrue ("Testing subscriptions length", subscriptions.length == subscriptionCt);

    }

    public void testCase06() throws Exception{
        String newUserName = "anotherNewUser06";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User user6 = userDatabase.findUser("user6");
        assertTrue("Check username", "user6".equals(user6.getUsername()));
        assertTrue("Check fromAddress", "fromAddress6".equals(user6.getFromAddress()));
        assertTrue("Check fullName", "fullName6".equals(user6.getFullName()));
        assertTrue("Check password", "password6".equals(user6.getPassword()));
        assertNull("Check replyToAddress", user6.getReplyToAddress());

    }

    public void testCase07() throws Exception{
        String newUserName = "anotherNewUser07";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User user7 = userDatabase.findUser("user7");
        assertTrue("Check username", "user7".equals(user7.getUsername()));
        assertTrue("Check fromAddress", "fromAddress7".equals(user7.getFromAddress()));
        assertTrue("Check fullName", "fullName7".equals(user7.getFullName()));
        assertTrue("Check password", "password7".equals(user7.getPassword()));
        assertNull("Check replyToAddress", user7.getReplyToAddress());

        User[] users = userDatabase.findUsers();
        assertTrue("Check users", users.length == userCt + 1);

    }

    public void testCase08() throws Exception{
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
      assertTrue("Check username", newUserName.equals(newUser2.getUsername()));
      assertTrue("Check fromAddress", "fromAddress1".equals(newUser2.getFromAddress()));
      assertTrue("Check replyToAddress", "replyToAddress1".equals(newUser2.getReplyToAddress()));
      assertTrue("Check password", "pass1".equals(newUser2.getPassword()));
      assertNull("Check fullName", newUser2.getFullName());

      generateUsers(3, subs, "08");

      User newUser3 = userDatabase.findUser("08user1");
      Subscription[] subscriptions = newUser3.getSubscriptions();
      assertTrue ("Testing subscriptions length", subscriptions.length == subs);

  //            userDatabase.save();
  //            userDatabase.close();
  //            userDatabase.open();

      User newUser4 = userDatabase.findUser("08user1");
      Subscription[] subscriptions2 = newUser4.getSubscriptions();
      assertTrue ("Testing subscriptions length", subscriptions2.length == subs);

    }

    public void testCase09() throws Exception{

            // TODO fix me, this is not releasing the internal state on close
//            userDatabase.save();
//            userDatabase.close();
//            userDatabase.open();

            User[] users = userDatabase.findUsers();
            assertTrue("Testing users count", users.length == userCt);

    }

    public void testCase010() throws Exception{

            // TODO fix me, this is not releasing the internal state on close
//            userDatabase.save();
//            userDatabase.close();
//            userDatabase.open();

            User user = userDatabase.findUser("bogus user");
            assertNull("Find non-existing user", user);

    }

    public void testCase011() throws Exception{

        String newUserName = "newUser11";

        User newUser = userDatabase.createUser(newUserName);
        newUser.setPassword("pass1");
        newUser.setFromAddress("fromAddress1");
        newUser.setReplyToAddress("replyToAddress1");

        User user = userDatabase.findUser(newUserName);
        assertNotNull("Find non-existing user", user);

        userDatabase.removeUser(user);
        User user2 = userDatabase.findUser(newUserName);
        assertNull("Find non-existing user", user2);

    }


    protected abstract UserDatabase getNewUserDatabase();
    protected abstract User getNewUser(UserDatabase db, String userName);
    protected abstract Subscription getNewSubscription(User user, String host);

}
