package com.rule.engine.evaluator.booleans;


/**
 * defines the add method for a generic operator node
 **/

public abstract class ExpressionNode extends Node {

	private Node left;//node to the left
	private Node right;//node to the right

	/**
	 * adds a node to the tree at the current node
	 * @return true if the node can be added, false otherwise
	 */
	@Override
	public boolean add(Node node) {
		boolean retVal;
		if(left == null){//if there is no node on the left
			left= node;      //put the new node on the left
			retVal= true;
		}else if (!(retVal = left.add(node))) {//if there was a node on the
			//left try adding the new node
			// to the left
			if(right == null){//if that doesn't work check the right
				right= node;//if the right is open put the new node on the right
				retVal= true;
			}else{
				retVal= right.add(node);//last, try to add it to the right
			}
		}
		return retVal;
	}

	/**
	 * @return Returns the left.
	 */
	public Node getLeft() {
		return left;
	}

	/**
	 * @return Returns the right.
	 */
	public Node getRight() {
		return right;
	}

}
