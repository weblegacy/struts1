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

package examples.options;

/**
 * Represents a book
 *
 * @version $Rev$ $Date$
 */
public class BookBean {

    // ------------------------------------------------------ Instance Variables

    /**
     * International Standard Book Number (ISBN)
     */
    private String isbn;

    /**
     * Book Title
     */
    private String title;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructor for BookBean.
     * @param title the book title
     * @param isbn the ISBN
     */
    public BookBean(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    // ------------------------------------------------------ Property Accessors

    /**
     * Returns the ISBN.
     * @return the ISBN
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * Returns the book title
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }


}
