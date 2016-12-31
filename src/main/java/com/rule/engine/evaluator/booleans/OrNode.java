package com.rule.engine.evaluator.booleans;

/**
 * signifies + operator
 */

public class OrNode extends ExpressionNode {

	@Override
	public boolean evaluate(Object... params) {
		return (Boolean) params[0] || (Boolean) params[1];
	}
}
