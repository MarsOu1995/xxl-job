package com.xxl.job.admin.beetl.utils;

import org.beetl.sql.core.query.interfacer.StrongValue;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

/**
 * 自定义条件判断
 *
 * @author Mars
 * @date 2019/10/23
 */
public class CustomQuery {
    private static ExpressionParser EL_PAESER = new SpelExpressionParser();

    public static StrongValue conditionNumber(long value, CustomCondition condition, long conditionValue) {
        return new StrongValue() {
            @Override
            public boolean isEffective() {
                switch (condition) {
                    case GT:
                        return value > conditionValue;
                    case GTE:
                        return value >= conditionValue;
                    case LT:
                        return value < conditionValue;
                    case LTE:
                        return value <= conditionValue;
                    case EQ:
                        return value == conditionValue;
                    case NEQ:
                        return value != conditionValue;
                    default:
                        return false;
                }
            }

            @Override
            public Object getValue() {
                return value;
            }
        };
    }

    public static StrongValue filterLikeEmpty(String value) {
        return new StrongValue() {
            @Override
            public boolean isEffective() {
                return !StringUtils.isEmpty(value);
            }

            @Override
            public Object getValue() {
                return "%"+value+"%";
            }
        };
    }

    public static StrongValue elCondition(Object value, String el, Object... param) {
        EvaluationContext context = new StandardEvaluationContext();
        if (param == null || param.length == 0) {
            context.setVariable("param", value);

        }else {
            context.setVariable("param", param);
        }
        return new StrongValue() {
            @Override
            public boolean isEffective() {
                if (value == null) {
                    return false;
                }
                return EL_PAESER.parseExpression(el).getValue(context,Boolean.class);
            }

            @Override
            public Object getValue() {
                return value;
            }
        };
    }

}
