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
		//TO BE COMPLETED

		if(node != null) {
			if (iNode.term.compareTo(node.left.term) > 0) {
				BTNode left = node.left;
				if(left == null) node.left = iNode;
				else add(left, iNode);
			} else if (iNode.term.compareTo(node.left.term) < 0) {
				BTNode right = node.right;
				if(right == null) node.right = iNode;
				else add(right, iNode);
			}
		}
	}
	
	/**
	 * Search a term in a subtree
	 * @param n root node of a subtree
	 * @param key a query term
	 * @return tree nodes with term that match the query term or null if no match
	 */
	public BTNode search(BTNode n, String key)
	{
		//TO BE COMPLETED
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
	public ArrayList<BTNode> wildCardSearch(BTNode n, String key)
	{
		//TO BE COMPLETED
		return null;
	}
	
	/**
	 * Print the inverted index based on the increasing order of the terms in a subtree
	 * @param node the root node of the subtree
	 */
	public void printInOrder(BTNode node)
	{
		
		//TO BE COMPLETED
	}
}

