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
grammar ValidWhen;

decimal: DECIMAL_LITERAL ;

integer : DEC_INT_LITERAL   |
          HEX_INT_LITERAL   |
          OCTAL_INT_LITERAL ;

number : decimal | integer ;

string : STRING_LITERAL ;

identifier : IDENTIFIER ;

field :
    id1=identifier LBRACKET             RBRACKET id2=identifier # field1  |
    id1=identifier LBRACKET idx=integer RBRACKET id2=identifier # field2  |
    id1=identifier LBRACKET idx=integer RBRACKET                # field3  |
    id1=identifier LBRACKET             RBRACKET                # field4  |
    id1=identifier                                              # field5  ;

literal : number # literalNum  |
          string # literalStr  |
          NULL   # literalNull |
          THIS   # literalThis ;

value : field | literal ;

expression : expr EOF;

expr: LPAREN comparisonExpression RPAREN # exprComp |
      LPAREN joinedExpression     RPAREN # exprJoin ;

joinedExpression : e1=expr j=(ANDSIGN | ORSIGN) e2=expr ;

comparison :
    EQUALSIGN        |
    GREATERTHANSIGN  |
    GREATEREQUALSIGN |
    LESSTHANSIGN     |
    LESSEQUALSIGN    |
    NOTEQUALSIGN     ;

comparisonExpression :
    v1=value c=comparison v2=value ;

WS : [ \t\n\r]+ -> skip;

DECIMAL_LITERAL : MINUS DIGIT+ DOT DIGIT+ ;

DEC_INT_LITERAL : MINUS DIGIT_WITHOUT_ZERO DIGIT* ;

OCTAL_INT_LITERAL : ZERO OCTAL_DIGIT* ;

HEX_INT_LITERAL : ZERO X HEX_DIGIT+ ;

STRING_LITERAL : (QUOTE .+? QUOTE) | (DQUOTE .+? DQUOTE) ;

ANDSIGN : A N D ;

ORSIGN : O R ;

LBRACKET : '[' ;

RBRACKET : ']' ;

LPAREN : '(' ;

RPAREN : ')' ;

THIS : '*' T H I S '*' ;

NULL : N U L L ;

IDENTIFIER : ID_START (ID_START | DIGIT)+ ;

EQUALSIGN : '==' ;

NOTEQUALSIGN : '!=' ;

LESSTHANSIGN : '<';

GREATERTHANSIGN : '>';

LESSEQUALSIGN : '<=';

GREATEREQUALSIGN : '>=';

fragment A : [aA] ;
fragment D : [dD] ;
fragment H : [hH] ;
fragment I : [iI] ;
fragment L : [lL] ;
fragment N : [nN] ;
fragment O : [oO] ;
fragment R : [rR] ;
fragment S : [sS] ;
fragment T : [tT] ;
fragment U : [uU] ;
fragment X : [xX] ;

fragment LETTER : [a-zA-Z] ;
fragment DIGIT : [0-9] ;
fragment DIGIT_WITHOUT_ZERO : [1-9] ;
fragment OCTAL_DIGIT : [0-7] ;
fragment HEX_DIGIT : [0-9a-fA-F] ;

fragment ZERO : '0' ;
fragment DOT : '.' ;

fragment QUOTE : '\'';
fragment DQUOTE : '"';

fragment ID_START : LETTER | '.' | '_' ;

fragment MINUS : '-'? ;