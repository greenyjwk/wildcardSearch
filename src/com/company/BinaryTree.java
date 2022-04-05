package com.company;

import java.util.*;

/**
 * 
 * @author Ji Woong Kim
 * a node in a binary search tree
 */
class BTNode{
	BTNode left, right;
	String term;
	ArrayList<Integer> docLists;
	
	/**
	 * Create a tree node using a term and a document list
	 * @param term the term in the node
	 * @param docList the ids of the documents that contain the term
	 */
	public BTNode(String term, ArrayList<Integer> docList) {
		this.term = term;
		this.docLists = docList;
	}
}

/**
 * 
 * Binary search tree structure to store the term dictionary
 */
public class BinaryTree {

	/**
	 * insert a node to a subtree 
	 * @param node root node of a subtree
	 * @param iNode the node to be inserted into the subtree
	 */
	public void add(BTNode node, BTNode iNode){
		if(node != null) {
			if ((iNode.term).compareTo(node.term) < 0) {
				if(node.left == null) node.left = iNode;
				else add(node.left, iNode);
			} else if ((iNode.term).compareTo(node.term) > 0) {
				if(node.right == null) node.right = iNode;
				else add(node.right, iNode);
			}
		}
	}
	
	/**
	 * Search a term in a subtree
	 * @param n root node of a subtree
	 * @param key a query term
	 * @return tree nodes with term that match the query term or null if no match
	 */
	public BTNode search(BTNode n, String key) {
		if(n == null) return null;
		else if (n.term.equals(key)) return n;
		else if(key.compareTo(n.term) < 0) return search( n.left, key);
		else if(key.compareTo(n.term) > 0) return search( n.right, key);
		return null;
	}
	
	/**
	 * Implement a wildcard search in a subtree
	 * @param n the root node of a subtree
	 * @param key a wild card term, e.g., ho (terms like home will be returned)
	 * @return tree nodes that match the wild card
	 */
	public ArrayList<BTNode> wildCardSearch(BTNode n, String key) {
		ArrayList<BTNode> result = new ArrayList<>();
		BTNode node = searchWildcardNode(n, key);

		if(node == null) return null;

		Queue<BTNode> queue = new Queue();
		queue.enqueue(node);

		BTNode popedNode;

		// traverse the subtree
		// put all the nodes that match
		while(!queue.isEmpty()){
			popedNode = queue.dequeue();
			String nodeSubstring = (popedNode.term).substring(0, key.length());
			if(nodeSubstring.equals(key)) result.add(popedNode);
			if(popedNode.left != null) queue.enqueue(popedNode.left);
			if(popedNode.right != null) queue.enqueue(popedNode.right);
		}
		return result;
	}


	/**
	 * search the root of the subtree that contains the wildcard search term
	 * @param n the root node of a subtree
	 * @param key  wildcard query, e.g., ho (so that home can be located)
	 * @return node that contains the wildcard search term
	 */
	public BTNode searchWildcardNode(BTNode n, String key) {

		if(n == null) return null;
		int wildcardLength = key.length();
		if( n.term.length() < wildcardLength ) return null;

		String nodeTerm = n.term.substring(0, wildcardLength);
		if (nodeTerm.equals(key)) return n;
		else if(key.compareTo(nodeTerm) < 0) return searchWildcardNode( n.left, key);
		else if(key.compareTo(nodeTerm) > 0) return searchWildcardNode( n.right, key);

		return null;
	}


	/**
	 * Print the inverted index based on the increasing order of the terms in a subtree
	 * @param node the root node of the subtree
	 */
	public void printInOrder(BTNode node) {
		if (node == null)
			return;
		/* first recur on left child */
		printInOrder(node.left);

		/* then print the data of node */
		System.out.print(node.term + " ");

		/* now recur on right child */
		printInOrder(node.right);
	}
}