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

import org.antlr.v4.runtime.RuleContext;
import org.apache.commons.validator.util.ValidatorUtils;

/**
 * Visitor to process the evalution.
 *
 * @author Graff Stefan
 *
 * @since Struts 1.4.1
 */
public class ValidWhenEvaluator extends ValidWhenBaseVisitor<ValidWhenResult<?>> {
    private final Object form;
    private final String value;
    private final int index;

    public ValidWhenEvaluator(Object form, String value, int index) {
        super();
        this.form = form;
        this.value = value;
        this.index = index;
    }

    @Override
    public ValidWhenResult<?> visitDecimal(ValidWhenParser.DecimalContext ctx) {
        String s = ctx.getText();
//        System.out.println("Decimal: " + s);
        return new ValidWhenResultBigDecimal(s);
    }

    @Override
    public ValidWhenResult<?> visitInteger(ValidWhenParser.IntegerContext ctx) {
        String s = ctx.getText();
//        System.out.println("Integer: " + s);
        return new ValidWhenResultInteger(s);
    }

    @Override
    public ValidWhenResult<?> visitString(ValidWhenParser.StringContext ctx) {
        final String s = ctx.getText();
//        System.out.println("String: " + s);
        return new ValidWhenResultString(s.substring(1, s.length() - 1));
    }

    @Override
    public ValidWhenResult<?> visitField1(ValidWhenParser.Field1Context ctx) {
        return visitField(ctx.id1.getText() + "[" + index + "]" + ctx.id2.getText());
    }

    @Override
    public ValidWhenResult<?> visitField2(ValidWhenParser.Field2Context ctx) {
        return visitField(ctx.id1.getText() + "[" + ctx.idx.getText() + "]" + ctx.id2.getText());
    }

    @Override
    public ValidWhenResult<?> visitField3(ValidWhenParser.Field3Context ctx) {
        return visitField(ctx.id1.getText() + "[" + ctx.idx.getText() + "]");
    }

    @Override
    public ValidWhenResult<?> visitField4(ValidWhenParser.Field4Context ctx) {
        return visitField(ctx.id1.getText() + "[" + index + "]");
    }

    @Override
    public ValidWhenResult<?> visitField5(ValidWhenParser.Field5Context ctx) {
        return visitField(ctx.id1.getText());
    }

    private ValidWhenResult<?> visitField(String property) {
//        System.out.println("Field: " + property);
        return new ValidWhenResultString(ValidatorUtils.getValueAsString(form, property));
    }

    @Override
    public ValidWhenResult<?> visitLiteralNull(ValidWhenParser.LiteralNullContext ctx) {
        super.visitLiteralNull(ctx);
//        System.out.println("NULL");
        return new ValidWhenResultString(null);
    }

    @Override
    public ValidWhenResult<?> visitLiteralThis(ValidWhenParser.LiteralThisContext ctx) {
        super.visitLiteralThis(ctx);
//        System.out.println("*this*");
        return new ValidWhenResultString(value);
    }

    @Override
    public ValidWhenResult<?> visitJoinedExpression(ValidWhenParser.JoinedExpressionContext ctx) {
        boolean b1 = extracted(ctx.e1);
        boolean b2 = extracted(ctx.e2);

//        System.out.println(b1 + " " + ctx.j.getText() + " " + b2);

        boolean ret = false;
        switch (ctx.j.getType()) {
            case ValidWhenParser.ANDSIGN:
                ret = b1 && b2;
                break;
            case ValidWhenParser.ORSIGN:
                ret = b1 || b2;
                break;
        }

        return new ValidWhenResultBoolean(ret);
    }

    private boolean extracted(RuleContext ctx) {
        ValidWhenResult<?> result = ctx.accept(this);
        return result == null ? false : result.toBoolean();
    }

    @Override
    public ValidWhenResult<?> visitComparisonExpression(ValidWhenParser.ComparisonExpressionContext ctx) {
        ValidWhenResult<?> v1 = ctx.v1.accept(this);
        ValidWhenResult<?> v2 = ctx.v2.accept(this);

//        System.out.println(v1 + " " + ctx.c.start.getText() + " " + v2);
        ValidWhenComparison comp = ValidWhenComparison.getComp(ctx.c.start.getType());

        return new ValidWhenResultBoolean(comp == null ? false : comp.compare(v1, v2));
    }

    @Override
    public ValidWhenResult<?> visitExprComp(ValidWhenParser.ExprCompContext ctx) {
        return ctx.comparisonExpression().accept(this);
    }

    @Override
    public ValidWhenResult<?> visitExprJoin(ValidWhenParser.ExprJoinContext ctx) {
        return ctx.joinedExpression().accept(this);
    }

    @Override
    public ValidWhenResult<?> visitExpression(ValidWhenParser.ExpressionContext ctx) {
        return ctx.expr().accept(this);
    }
}