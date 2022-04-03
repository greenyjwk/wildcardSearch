package com.company;

import java.util.*;


public class BTreeIndex {
	String[] myDocs;
	BinaryTree termList;
	BTNode root;
	ArrayList<BTNode> termDict;
	ArrayList<String> termCheck;
	/**
	 * Construct binary search tree to store the term dictionary 
	 * @param docs List of input strings
	 * 
	 */
	public BTreeIndex(String[] docs) {
		//TO BE COMPLETED

		termDict = new ArrayList<>();
		termCheck = new ArrayList<>();
		myDocs = docs;

		for(int i = 0 ;i < docs.length; i++){
			String[] tokens = docs[i].split(" ");
			for(String token: tokens){
				if(!termCheck.contains(token)){
					ArrayList<Integer> docList = new ArrayList<>();
					docList.add(i);
					BTNode node = new BTNode(token , docList);
					termDict.add(node);
					termCheck.add(token);
				}else{
					int idx = termCheck.indexOf(token);
					BTNode node = termDict.get(idx);
					if(!node.docLists.contains(i)) {
						node.docLists.add(i);
						termDict.set( idx, node);
					}
				}
			}
		}

		// Develop Constructing binary Search Tree
		int size = termDict.size();
		int rootIndex = size/2;
		BTNode rootParam = termDict.get(rootIndex);
		root = rootParam;
		termList = new BinaryTree();

		// Print nodes with containing ID
		for(int i = 0 ; i < termDict.size() ; i++) System.out.println(termDict.get(i).term + " " + termDict.get(i).docLists );

		// Constructing the binary search tree
		for(BTNode node : termDict) termList.add(root, node);

		System.out.println("\n\n----------------Print out the nodes in order----------------");
		termList.printInOrder(root);
		System.out.println("\n");
	}

	
	/**
	 * Single keyword search
	 * @param query the query string
	 * @return doclists that contain the term
	 */
	public ArrayList<Integer> search(String query)
	{
			BTNode node = termList.search(root, query);
			if(node==null)
				return null;
			return node.docLists;
	}

	/**
	 * conjunctive query search
	 * @param query the set of query terms
	 * @return doclists that contain all the query terms
	 */
	public ArrayList<Integer> search(String[] query)
	{
		ArrayList<Integer> result = search(query[0]);
		int termId = 1;
		while(termId<query.length) {
			ArrayList<Integer> result1 = search(query[termId]);
			result = merge(result,result1);
			termId++;
		}
		return result;
	}
	
	/**
	 * 
	 * @param wildcard the wildcard query, e.g., ho (so that home can be located)
	 * @return a list of ids of documents that contain terms matching the wild card
	 */
	public ArrayList<Integer> wildCardSearch(String wildcard) {
		//TO BE COMPLETED

		ArrayList<BTNode> nodes = termList.wildCardSearch(root, wildcard);

		if(nodes == null) return null;

		HashSet<Integer> set = new HashSet<>();

		for(BTNode node : nodes) set.addAll(node.docLists);

		ArrayList<Integer> result = new ArrayList<>(set);
		return result;
	}
	
	
	private ArrayList<Integer> merge(ArrayList<Integer> l1, ArrayList<Integer> l2)
	{
		ArrayList<Integer> mergedList = new ArrayList<Integer>();
		int id1 = 0, id2=0;
		while(id1<l1.size()&&id2<l2.size()){
			if(l1.get(id1).intValue()==l2.get(id2).intValue()){
				mergedList.add(l1.get(id1));
				id1++;
				id2++;
			}
			else if(l1.get(id1)<l2.get(id2))
				id1++;
			else
				id2++;
		}
		return mergedList;
	}
	
	
	/**
	 * Test cases
	 * @param args commandline input
	 */
	public static void main(String[] args)
	{
		String[] docs = {"text warehousing over big data",
                       "dimensional data warehouse over big data",
                       "nlp before text mining",
                       "nlp before text classification"};

		BTreeIndex bTree = new BTreeIndex(docs);

		String singleQuery;
		ArrayList<Integer> result;
		System.out.println("\n\n----------------Single query search----------------");
		singleQuery = "before";
		System.out.println("single term search: " + singleQuery);
		result = bTree.search(singleQuery);
		System.out.println("docId : " + result + "\n\n");

		ArrayList<Integer> resultConjuct;
		System.out.println("\n\n----------------Conjust query search----------------");
		String[] conjuct = {"text" ,"mining"};
		System.out.println("conjuct term search: " + conjuct);
		resultConjuct = bTree.search(conjuct);
		System.out.println("docId : " + resultConjuct + "\n\n");



		String query;
		ArrayList<Integer> wildcarSearchResult;
		System.out.println("\n\n----------------Wild card search----------------");

		query = "nlp";
		wildcarSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query);
		System.out.println("docId : " + wildcarSearchResult + "\n\n");

		query = "be";
		wildcarSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query);
		System.out.println("docId : " + wildcarSearchResult + "\n\n");

		query = "war";
		wildcarSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query);
		System.out.println("docId : " + wildcarSearchResult + "\n\n");

		query = "t";
		wildcarSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query);
		System.out.println("docId : " + wildcarSearchResult + "\n\n");
	}
}