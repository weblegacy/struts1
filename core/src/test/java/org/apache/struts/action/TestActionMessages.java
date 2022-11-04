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
package org.apache.struts.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ActionMessages} class.
 *
 * @version $Rev$ $Date$
 */
public class TestActionMessages {
    protected ActionMessages aMsgs = null;
    protected ActionMessage msg1 = null;
    protected ActionMessage msg2 = null;
    protected ActionMessage msg3 = null;
    protected ActionMessage msg4 = null;
    protected ActionMessage msg5 = null;


    @BeforeEach
    public void setUp() {
        aMsgs = new ActionMessages();

        Object[] objs1 = new Object[] { "a", "b", "c", "d", "e" };
        Object[] objs2 = new Object[] { "f", "g", "h", "i", "j" };

        msg1 = new ActionMessage("aMessage", objs1);
        msg2 = new ActionMessage("anMessage", objs2);
        msg3 = new ActionMessage("msg3", "value1");
        msg4 = new ActionMessage("msg4", "value2");
        msg5 = new ActionMessage("msg5", "value3", "value4");
    }

    @AfterEach
    public void tearDown() {
        aMsgs = null;
    }

    @Test
    public void testEmpty() {
        assertTrue(aMsgs.isEmpty(), "aMsgs is not empty!");
    }

    @Test
    public void testNotEmpty() {
        aMsgs.add("myProp", msg1);
        assertFalse(aMsgs.isEmpty(), "aMsgs is empty!");
    }

    @Test
    public void testSizeWithOneProperty() {
        aMsgs.add("myProp", msg1);
        aMsgs.add("myProp", msg2);
        assertEquals(2, aMsgs.size("myProp"), "number of mesages is not 2");
    }

    @Test
    public void testSizeWithManyProperties() {
        aMsgs.add("myProp1", msg1);
        aMsgs.add("myProp2", msg2);
        aMsgs.add("myProp3", msg3);
        aMsgs.add("myProp3", msg4);
        aMsgs.add("myProp4", msg5);
        assertEquals(1, aMsgs.size("myProp1"),
            "number of messages for myProp1 is not 1");
        assertEquals(5, aMsgs.size(), "number of messages");
    }

    @Test
    public void testSizeAndEmptyAfterClear() {
        testSizeWithOneProperty();
        aMsgs.clear();
        testEmpty();
        assertEquals(0, aMsgs.size("myProp"), "number of meesages is not 0");
    }

    @Test
    public void testGetWithNoProperty() {
        Iterator<ActionMessage> it = aMsgs.get("myProp");

        assertFalse(it.hasNext(), "iterator is not empty!");
    }

    @Test
    public void testGetForAProperty() {
        testSizeWithOneProperty();

        Iterator<ActionMessage> it = aMsgs.get("myProp");

        assertTrue(it.hasNext(), "iterator is empty!");
    }

    /**
     * Tests adding an ActionMessages object to an ActionMessages object.
     */
    @Test
    public void testAddMessages() {
        ActionMessage msg1 = new ActionMessage("key");
        ActionMessage msg2 = new ActionMessage("key2");
        ActionMessage msg3 = new ActionMessage("key3");
        ActionMessages msgs = new ActionMessages();
        ActionMessages add = new ActionMessages();

        msgs.add("prop1", msg1);
        add.add("prop1", msg2);
        add.add("prop3", msg3);

        msgs.add(add);
        assertEquals(3, msgs.size());
        assertEquals(2, msgs.size("prop1"));

        // test message order
        Iterator<ActionMessage> props = msgs.get();
        int count = 1;

        while (props.hasNext()) {
            ActionMessage msg = props.next();

            if (count == 1) {
                assertEquals("key", msg.getKey());
            } else if (count == 2) {
                assertEquals("key2", msg.getKey());
            } else {
                assertEquals("key3", msg.getKey());
            }

            count++;
        }
    }
}
