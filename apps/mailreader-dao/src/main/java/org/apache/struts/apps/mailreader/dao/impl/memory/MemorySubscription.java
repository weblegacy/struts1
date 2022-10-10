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


package org.apache.struts.apps.mailreader.dao.impl.memory;


import org.apache.struts.apps.mailreader.dao.Subscription;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.apps.mailreader.dao.impl.AbstractSubscription;


/**
 * <p>Concrete implementation of {@link Subscription} for an in-memory
 * database backed by an XML data file.</p>
 *
 * @author Craig R. McClanahan
 * @version $Rev$ $Date$
 * @since Struts 1.1
 */
public final class MemorySubscription extends AbstractSubscription{


    // ----------------------------------------------------------- Constructors


    /**
     * <p>Construct a new Subscription associated with the specified
     * {@link User}.
     *
     * @param user The user with which we are associated
     * @param host The mail host for this subscription
     */
    public MemorySubscription(User user, String host) {
        super(user, host);
    }

}
