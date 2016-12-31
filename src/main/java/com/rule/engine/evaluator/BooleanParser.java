
package com.rule.engine.evaluator;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.BracketPair;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;
import com.rule.engine.evaluator.booleans.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BooleanParser extends AbstractEvaluator<Node> {

    /** The logical AND operator.*/
    final static Operator AND = new Operator("&&", 2, Operator.Associativity.LEFT, 2);

    /** The logical OR operator.*/
    final static Operator OR = new Operator("||", 2, Operator.Associativity.LEFT, 1);

    private static final Parameters PARAMETERS;

    private Node lastExpression;

    static {
        // Create the evaluator's parameters
        PARAMETERS = new Parameters();
        // Add the supported operators
        PARAMETERS.add(AND);
        PARAMETERS.add(OR);
        // Add the parentheses
        PARAMETERS.addExpressionBracket(BracketPair.PARENTHESES);
    }

    public BooleanParser() {
        super(PARAMETERS);
    }

    @Override
    protected Node toValue(String literal, Object evaluationContext) {
        return new ValueNode(parseRule(literal));
    }

    @Override
    protected Iterator<String> tokenize(String expression) {
        return parseBooleanExpression(expression, new ArrayList<>()).iterator();
   }

    @Override
    protected Node evaluate(Operator operator, Iterator<Node> operands, Object evaluationContext) {
        String symbol = operator.getSymbol();
        ExpressionNode node = null;

        if (symbol.equals(AND.getSymbol())) {
            node = new AndNode();
        }

        if (symbol.equals(OR.getSymbol())) {
            node = new OrNode();
        }

        if (lastExpression == null) {
            Node next = operands.next();
            node.add(next);
            Node next1 = operands.next();
            node.add(next1);
            lastExpression = node;
        } else {
            Node nextElement = operands.next();
            if (nextElement.equals(lastExpression)) {
                nextElement = operands.next();
            }
            node.add(nextElement);
            node.add(lastExpression);
            lastExpression = null;
        }

        return node;
    }


    @Override
    public Node  evaluate(String expression, Object evaluationContext) {
        return super.evaluate(expression ,evaluationContext);
    }

    public static Node parse(String expression) {
        BooleanParser evaluator = new BooleanParser();
        return evaluator.evaluate(expression);
    }

    private static List<String> parseRule(String rule) {
   		List<String> ruleItems = new ArrayList<String>();

   		Matcher m = ExpressionRegex.SPLIT_ITEM_REGEX.matcher(rule);
   		int lastIndex = 0;
   		while (m.find()) {
   			String stringBetweenMatches = rule.substring(lastIndex, m.start());
   			ruleItems.add(stringBetweenMatches);
   			if (!m.group().trim().isEmpty()) {
   				ruleItems.add(m.group());
   			}
   			lastIndex = m.end();
   		}

        ExpressionUtils.fixUnbalancedBrackets(ruleItems, ExpressionUtils.BracketType.parentheses, null);
   		CollectionUtils.filter(ruleItems, ExpressionRegex.NON_EMPTY_STRING);

   		return ruleItems;
   	}

    private static List<String> parseBooleanExpression(String rule, List<String> ruleItems) {
        Matcher m = Pattern.compile(Arrays.toString(ExpressionRegex.AND_OR_OPERATORS)).matcher(rule);
        int lastIndex = 0;
        while (m.find()) {
            String stringBetweenMatches = rule.substring(lastIndex, m.start());
            ruleItems.add(stringBetweenMatches);
            if (!m.group().trim().isEmpty()) {
                String group = m.group();
                if (group.startsWith("(")) {
                    ruleItems.add("(");
                    parseBooleanExpression(group.replaceAll("^.|.$", ""), ruleItems);
                    ruleItems.add(")");
                } else {
                    if (group.equals("|") || group.equals("&"))  {
                        m.find();
                        ruleItems.add(group + group);
                    } else {
                        ruleItems.add(group);

                    }

                }
            }
            lastIndex = m.end();
        }

        if (lastIndex < rule.length()) {
            ruleItems.add(rule.substring(lastIndex));
        }

        ExpressionUtils.fixUnbalancedBrackets(ruleItems, ExpressionUtils.BracketType.parentheses, null);
        CollectionUtils.filter(ruleItems, ExpressionRegex.NON_EMPTY_STRING);

        return ruleItems;
    }
}
