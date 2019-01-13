package edu.cofc.csci230;

/**
 * 
 * Open hashing data structure
 * 
 * @author CSCI 230: Data Structures and Algorithms Fall 2017
 *
 */
public class OpenHashing extends HashTable { 
	
	/* private instance variables */
	private ArrayList<String>[] hash_table;
	
	/**
	 * Constructor
	 */
    public OpenHashing( int hash_function ) {
    	
    	this.hash_function = hash_function;
    	
    } // end constructor
    
    /**
     * Initialize the hash table
     * 
     * The number of elements in the hash table (m) is equal 97.
     * 
     */
    public void initialize() {
    	
    	hash_table = (ArrayList<String>[]) new ArrayList[ HashTable.M ];
    	
    	for ( int i=0; i<hash_table.length; i++ ) {
    		
    		hash_table[i] = new ArrayList<String>();
    		
    	}
    	
    } // end initialize() method
    
    
    /**
     * Search for key in the hash table
     * 
     * Exceptions: If the key does not exist in the hash table, the throw
     * 		       a HashTableKeyException
     * 
     * return: The number of list probes needed to find the key in the hash 
     * 		   table, e.g. 1 if the key was the first element in the list, n 
     * 	       if the key was the very last element in the list (where n is 
     *         the number of elements in the list).
     * 
     * @param key
     * @return
     */
	public int search( String key ) throws HashTableKeyException {
		
		int probes = 0;
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		int hashVal=calcHash(key);
		if(hash_table[hashVal].size()==0)
			throw new HashTableKeyException("element not in list");
		else {
			int i = 0;
			boolean found = false;
			while (i < hash_table[hashVal].size() && found != true) {
				if (hash_table[hashVal].get(i).equals(key)) {
					//probes++;
					found = true;
				}
				else {
					i++;
					probes=hashVal;
				}
			}
			if (i == hash_table[hashVal].size()) {
				throw new HashTableKeyException("element not in list. number of probes to find "+key+ ": "+probes);
			}
		}
		
		
		return probes;
	    
		
	} // end search() method
	
	/**
	 * Insert key into hash table
	 * 
	 * Exceptions: Duplicate keys are not allowed, e.g., if "dog" is already exists
	 * 			   in the hash table, then another "dog" key cannot be inserted. In 
	 * 			   this instance, throw a HashTableKeyException.
	 * 
	 * @param key
	 */
	public void insert( String key ) throws HashTableKeyException {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		int hashVal=calcHash(key);
		//System.out.println("hashVal "+hashVal);
		//System.out.println("hash place "+ hash_table[hashVal]);

		if(hash_table[hashVal].size()==0)
			hash_table[hashVal].add(key);
		else {
			/*for (int i = 0; i < hash_table[hashVal].size(); i++) {
				//System.out.println("in for loop");
				if (hash_table[hashVal].get(i).compareTo(key)==0)
					throw new HashTableKeyException("element already in the list");
			}*/
			int i=0;
			while(i<hash_table[hashVal].size())
			{
				//System.out.println(hash_table[hashVal].get(i));
				if(key.equals(hash_table[hashVal].get(i)))
					throw new HashTableKeyException("element already in the list");
				i++;
			}
			hash_table[hashVal].add(key);
		}

		
		
	} // end insert() method
	
	/**
	 * Delete a key from the hash table
	 * 
	 * Exceptions: if they key does not exist in the hash table, then throw
	 * 			   a HashTableKeyException
	 * 
	 * return: The number of probes needed to find the key in the hash table,
     *         e.g. 1 if the key was the first element in the list, n if it 
     *         was the very last element in the list, where n is the size 
     *         of the list.
	 * 
	 * @param key
	 * @return
	 */
	public int delete ( String key ) throws HashTableKeyException {
		
		int probes = 0;
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		int hashVal=calcHash(key);
		if(hash_table[hashVal].size()==0)
			throw new HashTableKeyException("element not in list");
		else {
			int i = 0;
			boolean found = false;
			while (i < hash_table[hashVal].size() && !found) {
				if (key.equals(hash_table[hashVal].get(i))) {
					//probes++;
					hash_table[hashVal].set(i,null);
					found = true;
				}
				else {
					i++;
					probes =hashVal;
				}
			}
			if (i == hash_table[hashVal].size()) {
				//System.out.println("i=list size for delete");
				throw new HashTableKeyException("element not in list. the number of probes was "+probes);
			}
		}
		
	      
		return probes;
		
	} // end delete() method
	
	/**
	 * See page 271 in supplemental course textbook (chapter is provided as PDF 
	 * on OAKS in content section).
	 * 
	 * Also, refer to your lecture notes.
	 * 
	 * @return
	 */
	public double loadFactor() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		//a=n/m   n = num of elements in list m=size

		double n=0;
		/*for(int i=0;i<hash_table.length;i++)
			n+=hash_table[i].size();*/
		for(int i=0;i<hash_table.length;i++){
			for(int j=0;j<hash_table[i].size();j++) {
				if (hash_table[i].get(j)!=null){
					n++;
					//System.out.println("i "+i);
					//System.out.println(hash_table[i].get(j));
				}
			}
		}
		//System.out.println("n = "+n);

		//System.out.println("n is "+n);
		double m=0;
		for(int k=0;k<hash_table.length;k++){
			if(hash_table[k].size()!=0)
				m++;
		}
		//System.out.println("size of hash table "+hash_table.length);
		//System.out.println("m = "+m);
		double a=n/m;
		return a;
				
	} // end loadFactor() method
	
	
	/**
	 * See equation (7.4) on page 271 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double successfulSearches() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */

		double s=1+(loadFactor()/2);
		
		return s;
				
	} // end successfulSearches() method
	
	/**
	 * See equation (7.4) on page 271 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double unsuccessfulSearches() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		double u=loadFactor();
		
		return u;
				
	} // end unsuccessfulSearches() method
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
        
		OpenHashing hashDS = new OpenHashing( HashTable.HASH_FUNC1 );
		System.out.printf("\n%s ----------------------------------\n", "Open Hashing: FUNC1" );
		
		if ( hashDS.loadWords() ) {
			
			hashDS.initialize();
			
			System.out.printf( "Number of words in list = %d\n", words.size() );

			try {
				//------------------------------------------------
				// * TODO:
				//* ------------------------------------------------
				//* 1) Insert each word into hash data structure
				ArrayList<String> check= new ArrayList<String>();

				for(int k=0;k<words.size();k++){
					boolean found=false;
					for(int j=0;j<check.size();j++)
					{
						if(check.get(j).equals(words.get(k)))
							found = true;
					}
					if(found==false) {
						//System.out.println("hash word "+words.get(k));
						hashDS.insert(words.get(k));
						check.add(words.get(k));
					}
				}


				//* 2) Calculate load factor value and print to
				//*    console (using System.printf or System.println)
				System.out.println("load factor "+hashDS.loadFactor());
				//* 3) Calculate successful searches value and print to
				//*    console (using System.printf or System.println)
				System.out.println("successful searches "+hashDS.successfulSearches());
				//* 4) Calculate unsuccessful searches value and print to
				//*    console (using System.printf or System.println)
				System.out.println("unsuccessful searches "+hashDS.unsuccessfulSearches());
				//* 5) For each word in words list, search in the hashDS,
				//*    and print mean probe value to console (using System.printf or System.println)
				/*for(int j=0;j<words.size();j++) {
					int probes = hashDS.search(words.get(j));
					System.out.println("Number of probes for " + words.get(j)+": "+probes);
				}*/
				//* 6) For a word that does not exist in hashDS, search in
				//*    the hashDS, print the probe value to console (using System.printf or System.println)
				//int knownUn = hashDS.search("y");
				//System.out.println("num of probes to find y "+knownUn);
				//* 7) For each word in words list, delete word in the hashDS,
				//*    and print mean probe value to console (using System.printf or System.println)
				/*for(int l=0;l<check.size();l++) {
					hashDS.delete(check.get(l));
					//System.out.println(check.get(l));
				}*/
				//* 8) For a word that does not exist in hashDS, delete in
				//    the hashDS, print the probe value to console (using System.printf or System.println)
				int find=hashDS.delete("y");
				System.out.println(find);
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		} else {
			
			System.out.println("Failed to load words from text file" );
		}
		
		// ------------------------------------------------
		// Repeat for second hash function
		
		hashDS = new OpenHashing( HashTable.HASH_FUNC2 );
		System.out.printf("\n%s ----------------------------------\n", "Open Hashing: FUNC2" );
		
		if ( hashDS.loadWords() ) {
			
			hashDS.initialize();
			
			System.out.printf( "Number of words in list = %d\n", words.size() );


			try {
				//------------------------------------------------
				// * TODO:
				//* ------------------------------------------------
				//* 1) Insert each word into hash data structure
				ArrayList<String> check= new ArrayList<String>();

				for(int k=0;k<words.size();k++){
					boolean found=false;
					for(int j=0;j<check.size();j++)
					{
						if(check.get(j).equals(words.get(k)))
							found = true;
					}
					if(found==false) {
						//System.out.println("hash word "+words.get(k));
						hashDS.insert(words.get(k));
						check.add(words.get(k));
					}
				}


				//* 2) Calculate load factor value and print to
				//*    console (using System.printf or System.println)
				System.out.println("load factor "+hashDS.loadFactor());
				//* 3) Calculate successful searches value and print to
				//*    console (using System.printf or System.println)
				System.out.println("successful searches "+hashDS.successfulSearches());
				//* 4) Calculate unsuccessful searches value and print to
				//*    console (using System.printf or System.println)
				System.out.println("unsuccessful searches "+hashDS.unsuccessfulSearches());
				//* 5) For each word in words list, search in the hashDS,
				//*    and print mean probe value to console (using System.printf or System.println)
				/*for(int j=0;j<words.size();j++) {
					int probes = hashDS.search(words.get(j));
					System.out.println("Number of probes for " + words.get(j)+": "+probes);
				}*/
				//* 6) For a word that does not exist in hashDS, search in
				//*    the hashDS, print the probe value to console (using System.printf or System.println)
				//int knownUn = hashDS.search("y");
				//System.out.println("num of probes to find y "+knownUn);
				//* 7) For each word in words list, delete word in the hashDS,
				//*    and print mean probe value to console (using System.printf or System.println)
				/*for(int l=0;l<check.size();l++) {
					hashDS.delete(check.get(l));
					//System.out.println(check.get(l));
				}*/
				//* 8) For a word that does not exist in hashDS, delete in
				//    the hashDS, print the probe value to console (using System.printf or System.println)
				//int find=hashDS.delete("y");
				//System.out.println(find);  //the hashDS, print the probe value to console (using System.printf or System.println)

			}
			catch(Exception e){
				System.out.println(e);
			}
			
		} else {
			
			System.out.println("Failed to load words from text file" );
		}
		
    } // end main() method
	
} // end OpenHashing class definition
