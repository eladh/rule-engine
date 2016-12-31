package com.rule.engine.evaluator.booleans;


import java.util.List;

/**
 * defines the abstract methods for a value node
 */

public class ValueNode extends Node {

	private List<String> value;

	public ValueNode(List<String> value) {
		this.value = value;
	}

	/**
	 * adds a node to the tree at the current node
	 * @return false always
	 */
	public boolean add(Node node) {
		return false;
	}

	@Override
	public boolean evaluate(Object... params) {
		return params[0] != null;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(List<String> value){
		this.value= value;
	}


	/**
	 * @param value the value to get
	 */
	public List<String> getValue() {
		return this.value;
	}

}
