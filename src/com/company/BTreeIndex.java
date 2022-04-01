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

		// 1 Create BTNode array
		// 2 Need to sort the docs first

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


		ArrayList<BTNode> forRoot = termDict;
		// Sort termDict
		Collections.sort(forRoot, new Comparator<BTNode>() {
			@Override
			public int compare(BTNode term1, BTNode term2) {
				String s1 = term1.term;
				String s2 = term2.term;
				return s1.compareToIgnoreCase(s2);
			}
		});

		// Develop Constructing binary Search Tree
		int size = termDict.size();
		int rootIndex = size/2;
		BTNode rootTemp = forRoot.get(rootIndex);

		int idx2 = termDict.indexOf(rootTemp);
		BTNode root = termDict.get(idx2);
		BinaryTree tree = new BinaryTree();
		System.out.println( "Root Node: \n" + root);

		termDict.remove(root);

		// termDict without root
		for(BTNode temp : termDict) System.out.println(temp.term + "\n" + temp.docLists  + "\n\n");

		// Constructing the binary search tree
		for(BTNode node : termDict) tree.add(root, node);

		tree.printInOrder(root);

		BTNode temp = tree.search(root, "data");
		System.out.println(temp.docLists);
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

		return null;
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
		//TO BE COMPLETED with testcases

		BTreeIndex btree = new BTreeIndex(docs);


	}
}