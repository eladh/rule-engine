package com.rule.engine.evaluator.booleans;

/**
 * a generic tree node
 */
public abstract class Node {

	/**
	 * adds a node to the tree from the current node
	 *
	 * @param n node to add
	 * @return true if it can be added, false otherwise
	 */
	public abstract boolean add(Node node);
	
	/**
	 * evaluates the node and performs its operation
	 * 
	 * @return a String that holds the result of the evaluation
	 */
	public abstract boolean evaluate(Object... params);
}