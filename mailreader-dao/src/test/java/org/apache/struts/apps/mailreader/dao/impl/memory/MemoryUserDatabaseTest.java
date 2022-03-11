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

package org.apache.struts.apps.mailreader.dao.impl.memory;

import java.io.File;

import org.apache.struts.apps.mailreader.dao.BaseTestUserDatabase;
import org.apache.struts.apps.mailreader.dao.Subscription;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.apps.mailreader.dao.UserDatabase;



public class MemoryUserDatabaseTest extends BaseTestUserDatabase {

    protected String defaultPathName = "test-database.xml";
    private boolean deleteDatabaseFile = true;

    public boolean isDeleteDatabaseFile() {
        return deleteDatabaseFile;
    }

    public void setDeleteDatabaseFile(boolean deleteDatabaseFile) {
        this.deleteDatabaseFile = deleteDatabaseFile;
    }

    protected UserDatabase getNewUserDatabase() {
        // using default impl
        MemoryUserDatabase memoryUserDatabase = new MemoryUserDatabase();
        memoryUserDatabase.setPathname(defaultPathName);
        userDatabase = memoryUserDatabase;
        return memoryUserDatabase;

    }
    protected User getNewUser(UserDatabase db, String userName){
        // using default impl
        return db.createUser(userName);
    }
    protected Subscription getNewSubscription(User user, String host) {
        return user.createSubscription(host);
    }

    protected void setUp() throws Exception {
        super.setUp();
        // force write to disk
       userDatabase.close();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        if (isDeleteDatabaseFile()) {
            File file = new File(defaultPathName);
            file.delete();
        }
    }
}
