package edu.cofc.csci230;

/**
 * 
 * Closed hashing data structure using linear probing.
 * 
 * @author CSCI 230: Data Structures and Algorithms Fall 2017
 *
 */
public class ClosedHashing extends HashTable {

	/* private instance variables */
	private String[] hash_table;

	/**
	 * Constructor
	 */
    public ClosedHashing( int hash_function ) {

    	this.hash_function = hash_function;

    } // end constructor

    /**
     * Initialize the hash table
     *
     * The number of elements in the hash table is equal to 2 x the number of words
     * in the word list.
     *
     */
    public void initialize() {

    	hash_table = new String[ 2*words.size() ];

    	for ( int i=0; i<words.size(); i++ ) {

    		hash_table[i] = null;

    	}

    } // end initialize() method



    /**
     * Search for key in the hash table.
     *
     * In this implementation, a lazy character "^" (at the beginning of the
	 * string value) is used to indicate a collision has occurred. The number
	 * of lazy characters indicate the number of collisions,e.g. "^^" would
	 * indicate two collisions have occurred.
     *
     * Exceptions: If the key does not exist in the hash table, then throw
     *             a HashTableKeyException
     *
     * return: The number of linear probes needed to find the key in the
     * 		   hash table, e.g. 1 if no probing, n if probed n times to
     *         find an open location.
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

			int hashVal = calcHash(key);
			String find = hash_table[hashVal];
			if (find.contains("^")) {
				String replace = "";
				for (int i = 0; i < find.length(); i++) {
					char carrot = '^';
					char placeChar = find.charAt(i);
					if (carrot != (placeChar))
						replace += find.charAt(i);
				}
				find = replace;
			}
			if (find.equals(key))
				probes = hashVal;
			else {
				int start = hashVal;
				boolean found = false;
				String place = hash_table[start];
				if (place.contains("^")) {
					String replace = "";
					for (int i = 0; i < place.length(); i++) {
						char carrot = '^';
						char placeChar = place.charAt(i);
						if (carrot != (placeChar))
							replace += place.charAt(i);
					}
					place = replace;
				}
				//System.out.println("place = "+place);
				while (hash_table[start] != null && !key.equals(place)) {
					start = (start + 1) % hash_table.length;
					place = hash_table[start];
					if (place == null)
						throw new HashTableKeyException("key does not exisit. probes to find it is " + probes);
					if (place.contains("^")) {
						String replace = "";
						for (int i = 0; i < place.length(); i++) {
							char carrot = '^';
							char placeChar = place.charAt(i);
							if (carrot != (placeChar))
								replace += place.charAt(i);
						}
						place = replace;
					}
					probes = start;
					found = true;

				}
				if (!found)
					throw new HashTableKeyException("key does not exist. probes to find it is " + probes);


			}

			return probes;
	} // end search() method

	/**
	 * Insert key into hash table
	 *
	 * In this implementation, a lazy character "^" (at the beginning of the
	 * string value) is used to indicate a collision has occurred. The number
	 * of lazy characters indicate the number of collisions,e.g. "^^" would
	 * indicate two collisions have occurred.
	 *
	 * Exceptions: Duplicate key values are not allowed e.g., if "dog" is
	 * 		       already exists in the hash table, then another
	 * 			   "dog" key cannot be inserted. In this instance, throw a
	 * 			   HashTableKeyException.
	 *
	 * @param key
	 */
	public void insert( String key ) throws HashTableKeyException {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */

		int hashVal=calcHash(key);
		//System.out.println("hashVal " + hashVal);
		if(hash_table[hashVal]==null) {
			hash_table[hashVal] = key;
			//System.out.println("null string was replaced");
		}
		else if(hash_table[hashVal]!=null){
			int start=hashVal;
			String place=hash_table[start];
			//System.out.println("before loop");
			while(place!=null) {
				String redo = ("^" + hash_table[start]);
				hash_table[start] = redo;

				//System.out.println("in while loop");
				//System.out.println("place "+place);
				//System.out.println("key "+key);
				if(key.equals(place))
					throw new HashTableKeyException("element already exists in the hash table");
				start=(start+1)%hash_table.length;
				place=hash_table[start];

			}

				hash_table[start]=key;
				//System.out.println("it has been inserted");

		}
		
		
	} // end insert() method
	
	/**
	 * Delete a key from the hash table. 
	 * 
	 * In this implementation, a lazy character "^" (at the beginning of the 
	 * string value) is used to indicate a collision has occurred. The number 
	 * of lazy characters indicate the number of collisions,e.g. "^^" would 
	 * indicate two collisions have occurred. Every successful deletion 
	 * should remove one "^" symbol. 
	 * 
	 * Exceptions: if they key does not exist in the hash table then throw
	 * 			   a HashTableKeyException
	 * 
	 * return: The number of probes needed to find the key in the hash table,
     *         e.g. 1 if the key was the first element in the list, n if it was 
     *         the very last element in the list, where n is the number of elements 
     *         in the list.
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
		int hash=calcHash(key);
		String place= hash_table[hash];
		if(place.contains("^")){
			String replace="";
			for(int i=0;i<place.length();i++) {
				char carrot='^';
				char placeChar=place.charAt(i);
				if (carrot!=(placeChar))
					replace += place.charAt(i);
			}
			place=replace;
		}
		while(place!=null&&!key.equals(place)){
			hash=(hash+1)%hash_table.length;
			//System.out.println("hash = "+hash);
			place=hash_table[hash];
			if(place==null) {
				probes = hash;
				throw new HashTableKeyException("element is not in the list. probes to find it was " + probes);
			}
			if(place.contains("^")){
				String replace="";
				for(int i=0;i<place.length();i++) {
					char carrot='^';
					char placeChar=place.charAt(i);
					if (carrot!=(placeChar))
						replace += place.charAt(i);
				}
				place=replace;
			}
			//System.out.println("hash table before one carrot delete "+ hash_table[hash]);
			if(hash_table[hash].contains("^")) {
				hash_table[hash] = hash_table[hash].substring(1);
				//System.out.println("hash after one carrot delete " + hash_table[hash]);
			}
			else if(place.equals(key)){
				hash_table[hash]=null;
				probes=hash;
				return hash;
			}
		}

		return hash;
		
	} // end delete() method
	
	/**
	 * See page 271 in supplemental course textbook (chapter is provided as PDF 
	 * on OAKS in content section).
	 * 
	 * Also, refer to your lecture notes. Note, for closed hashing, m is 
	 * the number of locations in the hash table.
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
		for(int i=0;i<hash_table.length;i++){
			if(hash_table[i]!=null)
				n++;
		}
		//System.out.println("n= "+n);
		//System.out.println("m = "+hash_table.length);
		double a=n/hash_table.length;
		return a;
				
	} // end loadFactor() method
	
	
	/**
	 * See equation (7.5) on page 273 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double successfulSearches() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		/*double half=(1/2);
		double a=loadFactor();
		double divide = 1/(1-a);
		double add = a+divide;
		double s= half*add;
		*/
		double a=loadFactor();
		double s = (.5) * (1+ (1 / (1-a)));
		
		return s;
				
	} // end successfulSearches() method
	
	/**
	 * See equation (7.5) on page 273 in supplemental course textbook (chapter 
	 * is provided as PDF on OAKS in content section).
	 * 
	 * @return
	 */
	public double unsuccessfulSearches() {
		
		/* ----------------------------------
		 * TODO: Put your solution here
		 * ----------------------------------
		 */
		double a = loadFactor();
		double sq= (1-a)*(1-a);
		//System.out.println("sq = "+sq);
		double div= 1/sq;
		//System.out.println("div = "+div);
		double add= 1+div;
		//System.out.println("add = "+add);
		double v= .5*add;
		//System.out.println("v = "+v);
		
		return v;
				
	} // end unsuccessfulSearches() method
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
        
		ClosedHashing hashDS = new ClosedHashing( HashTable.HASH_FUNC1 );
		System.out.printf("\n%s ----------------------------------\n", "Closed Hashing: FUNC1" );


		
		if ( hashDS.loadWords() ) {
			
			hashDS.initialize();
			
			System.out.printf( "Number of words in list = %d\n", words.size() );


			try {
				//------------------------------------------------
				// * TODO:
				//* ------------------------------------------------
				//* 1) Insert each word into hash data structure
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
				//System.out.println("right before find");
				//int find=hashDS.delete("y");
				//System.out.println(find);
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		} else {
			
			System.out.println("Failed to load words from text file" );
		}
		
		// ------------------------------------------------
		// Repeat for second hash function
		
		System.out.printf("\n%s ----------------------------------\n", "Closed Hashing: FUNC2" );
		hashDS = new ClosedHashing( HashTable.HASH_FUNC2 );
		
		if ( hashDS.loadWords() ) {
			
			hashDS.initialize();
			
			System.out.printf( "Number of words in list = %d\n", words.size() );

			try {
				//------------------------------------------------
				// * TODO:
				//* ------------------------------------------------
				//* 1) Insert each word into hash data structure
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
				//   and print mean probe value to console (using System.printf or System.println)
				for(int j=0;j<check.size();j++) {
					//System.out.println(words.get(j));
					int probes = hashDS.search(words.get(j));
					//System.out.println("j = "+j);
					System.out.println("Number of probes for " + words.get(j) + ": " + probes);
				}
				//* 6) For a word that does not exist in hashDS, search in
				//*    the hashDS, print the probe value to console (using System.printf or System.println)
				//int knownUn = hashDS.search("y");
				//System.out.println("num of probes to find y "+knownUn);
				//* 7) For each word in words list, delete word in the hashDS,
				//*    and print mean probe value to console (using System.printf or System.println)
				System.out.println("delete for every word");
				for(int l=0;l<check.size();l++) {
					int delete=hashDS.delete(check.get(l));
					System.out.println("To delete "+check.get(l)+". It took this many probes: "+delete);
				}
				//* 8) For a word that does not exist in hashDS, delete in
				//    the hashDS, print the probe value to console (using System.printf or System.println)
				System.out.println("right before find");
				int find=hashDS.delete("y");
				System.out.println(find);
			}
			catch(Exception e){
				System.out.println(e);
			}



	} else {
			
			System.out.println("Failed to load words from text file" );
		}
		
    } // end main() method
	
} // end ClosedHashing class definition