package com.rule.engine.evaluator.booleans;

/**
 * signifies ! operator
 */

public class  NotNode extends ExpressionNode {

	private Node left;

	@Override
	public boolean add(Node node) {
		if(left==null){
			left= node;
			return true;
		}
		return left.add(node);
	}

	@Override
	public boolean evaluate(Object... params) {
		return ! ((Boolean)params[0]);
	}


}
