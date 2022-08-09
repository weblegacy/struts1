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


package org.apache.struts.tiles;


/**
 * Root class for all Tiles-exceptions.
 */
public class TilesException extends Exception
{
  private static final long serialVersionUID = -2512402467536260269L;


  /**
    * Constructor.
    */
  public TilesException()
    {
    super();
  }

  /**
    * Constructor.
    * @param message The error or warning message.
    */
  public TilesException(String message)
    {
    super(message);
  }


  /**
    * Create a new <code>TilesException</code> wrapping an existing exception.
    *
    * @param e The root cause exception
    */
  public TilesException(Exception e)
  {
    super(e);
  }


  /**
    * Create a new <code>TilesException</code> from an existing exception.
    *
    * @param message The detail message.
    * @param e The root cause exception
    */
  public TilesException(String message, Exception e)
  {
    super(message, e);
  }


  /**
    * Return the embedded exception, if any.
    *
    * @return The root cause exception, or <code>null</code> if there is none.
    */
  public Exception getException ()
  {
    return (Exception) getCause();
  }

}
