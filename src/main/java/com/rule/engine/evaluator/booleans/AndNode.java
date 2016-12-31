package com.rule.engine.evaluator.booleans;

/**
 * signifies * operator
 */

public class AndNode extends ExpressionNode {

	@Override
	public boolean evaluate(Object... params) {
		return (Boolean) params[0] && (Boolean) params[1];
	}
}
