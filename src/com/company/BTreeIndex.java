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

		// Construct binary Search Tree
		int size = termDict.size();
		int rootIndex = size/2;
		root = termDict.get(rootIndex);
		termList = new BinaryTree();

		// Print nodes with containing ID
//		for(int i = 0 ; i < termDict.size() ; i++) System.out.println(termDict.get(i).term + " " + termDict.get(i).docLists );

		// Constructing the binary search tree
		for(BTNode node : termDict) termList.add(root, node);

//		System.out.println("\n\n----------------Print out the nodes in order----------------");
//		termList.printInOrder(root);
//		System.out.println("\n");

	}

	
	/**
	 * Single keyword search
	 * @param query the query string
	 * @return docLists that contain the term
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
	 * @return docLists that contain all the query terms
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

		ArrayList<BTNode> nodes = termList.wildCardSearch(root, wildcard);
		if(nodes == null) return null;
		HashSet<Integer> set = new HashSet<>();
		for(BTNode node : nodes) set.addAll(node.docLists);
		return new ArrayList<>(set);
	}
	
	
	private ArrayList<Integer> merge(ArrayList<Integer> l1, ArrayList<Integer> l2)
	{
		ArrayList<Integer> mergedList = new ArrayList<>();
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
		System.out.println("----------------Single query search----------------");
		singleQuery = "before";
		System.out.println("single term search: " + singleQuery);
		result = bTree.search(singleQuery);
		System.out.println("docId : " + result + "\n\n");

		singleQuery = "text";
		System.out.println("single term search: " + singleQuery);
		result = bTree.search(singleQuery);
		System.out.println("docId : " + result + "\n\n");




		ArrayList<Integer> resultConjunctive;
		System.out.println("\n\n----------------Conjunctive query search----------------");
		String[] conjunctive = {"text" ,"mining"};
		System.out.println("conjunctive term search: " + conjunctive[0] + "  " +  conjunctive[1]);
		resultConjunctive = bTree.search(conjunctive);
		System.out.println("docId : " + resultConjunctive + "\n\n");


		String[] Conjunctive2 = {"big" ,"warehousing"};
		System.out.println("conjunctive term search: " + Conjunctive2[0] + "  " + Conjunctive2[1]);
		resultConjunctive = bTree.search(Conjunctive2);
		System.out.println("docId : " + resultConjunctive + "\n\n");


		String[] Conjunctive3 = {"big" ,"warehousing" , "data"};
		System.out.println("conjunctive term search: " + Conjunctive3[0] + "  " + Conjunctive3[1] + "  " + Conjunctive3[2] );
		resultConjunctive = bTree.search(Conjunctive3);
		System.out.println("docId : " + resultConjunctive + "\n\n");


		String[] Conjunctive4 = {"nlp", "before" ,"text" ,"mining"};
		System.out.println("conjunctive term search: " + Conjunctive4[0] + "  " + Conjunctive4[1] + "  " + Conjunctive4[2] + "  " + Conjunctive4[3]);
		resultConjunctive = bTree.search(Conjunctive4);
		System.out.println("docId : " + resultConjunctive + "\n\n");




		String query;
		ArrayList<Integer> wildcardSearchResult;
		System.out.println("\n\n----------------Wildcard search----------------");

		query = "nlp";
		wildcardSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query + "*");
		System.out.println("docId : " + wildcardSearchResult + "\n\n");

		query = "be";
		wildcardSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query + "*");
		System.out.println("docId : " + wildcardSearchResult + "\n\n");

		query = "war";
		wildcardSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query + "*");
		System.out.println("docId : " + wildcardSearchResult + "\n\n");

		query = "t";
		wildcardSearchResult = bTree.wildCardSearch(query);
		System.out.println("wildcard search: " + query + "*");
		System.out.println("docId : " + wildcardSearchResult + "\n\n");
	}
}