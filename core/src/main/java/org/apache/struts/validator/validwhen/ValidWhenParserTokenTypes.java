// $ANTLR 2.7.7 (20060906): "ValidWhenParser.g" -> "ValidWhenParser.java"$

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

package org.apache.struts.validator.validwhen;

import java.math.BigDecimal;
import java.util.Stack;
import org.apache.commons.validator.util.ValidatorUtils;

public interface ValidWhenParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int DECIMAL_LITERAL = 4;
	int DEC_INT_LITERAL = 5;
	int HEX_INT_LITERAL = 6;
	int OCTAL_INT_LITERAL = 7;
	int STRING_LITERAL = 8;
	int IDENTIFIER = 9;
	int LBRACKET = 10;
	int RBRACKET = 11;
	int LITERAL_null = 12;
	int THIS = 13;
	int LPAREN = 14;
	int RPAREN = 15;
	int ANDSIGN = 16;
	int ORSIGN = 17;
	int EQUALSIGN = 18;
	int GREATERTHANSIGN = 19;
	int GREATEREQUALSIGN = 20;
	int LESSTHANSIGN = 21;
	int LESSEQUALSIGN = 22;
	int NOTEQUALSIGN = 23;
	int WS = 24;
}
