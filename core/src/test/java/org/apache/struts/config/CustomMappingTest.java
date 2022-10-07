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
package org.apache.struts.config;

import org.apache.struts.action.ActionMapping;

/**
 * Custom action mapping used by {@link TestModuleConfig}
 */
public class CustomMappingTest extends ActionMapping {
    private static final long serialVersionUID = -6767297240958404965L;

    private boolean pub = false;

    public void setPublic(boolean val) {
        this.pub = val;
    }

    public boolean getPublic() {
        return pub;
    }
}
