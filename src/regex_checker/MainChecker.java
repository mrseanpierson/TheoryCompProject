package regex_checker;

import regex_checker.KeyPair;
import regex_checker.State;
import java.util.*;
import java.io.*;

/*
	Class: MainChecker
	@author: Sean Pierson, Ryson Asuncion
	runs the given string through an NFA that is read in from a file
*/

	public class MainChecker {


		public static void main(String[] args) {
		 	// The name of the file to open.
			String file = "NFAtoParse.txt";
			String finalString = null;
			InputStream stream = MainChecker.class.getResourceAsStream(file); //get an InputStream for the file from the same directory as the class
			Reader streamReader = new InputStreamReader(stream); //now take that InputStream and turn it into an InputStreamReader
			List<Character> dataList = new ArrayList<Character>(); //make an ArrayList of Charaters to store all the stuff we read 
			
			try{ //try reading!
				int data = streamReader.read(); //intitiate the read on the first char in the file
				while(data != -1){ //loop until we arent reading a character anymore
					Character theChar = (char) data; //for each character we find, cast it to a Character so we can store it in our ArrayList
					dataList.add(theChar); //add that character to the ArrayList
					data = streamReader.read(); //read the next character
				}
				StringBuilder sb = new StringBuilder(dataList.size()); //make a StringBuilder Object to have the same size as our finished ArrayList
				for (Character c : dataList){ //loop through all the Characters in the ArrayList
					sb.append(Character.toChars(c)); //add the char version of the Character to the end of the string that we are building
				}
				finalString = sb.toString(); //make a string out of the StringBuilder
				
				//System.out.println(finalString);
			}
			catch(IOException e){ //necessary for any 'try' statement
				System.out.println(e); // just print the error to let the person know that there was one
			}
			System.out.println("NFAacceptW: "+ NFAacceptW(parseNFA(finalString), "aba")); //this is equivalent to the line below 
			//System.out.println("NFAacceptW: "+ NFAacceptW(parseNFA("(q0, [{a, q1}, {a, q2}], false, true), (q1, [{b, q3}], false, false), (q2, [{a, q2}], false, false), (q3, [{a, q3}, {b, q3}], true, false)"), "ab"));
		}

		/** isValidAlphabet 
	 * 
	 * checks to see if the alphabet used is the same as what is specified ( ∑={a.b}* )
	 * 
	 * @param inputString
	 * @return inputArray
	 */
		public static boolean isValidAlphabet(String inputString) {

		char inputArray[] = new char[inputString.length()]; // To split the input string into a char array

		char indexedChar; // The currently examined char
		for (int i = 0; i < inputString.length(); i++) {
			
			indexedChar = inputString.charAt(i);
			
			if ((indexedChar == 'a') || (indexedChar == 'b') || (indexedChar == 'e')) { 
				inputArray[i] = indexedChar; // Put valid char into array
			} else{
				return false; // We haven't read an 'a' or 'b' or 'e' (for epsilon)
			}

		}

		return true; // return the char array
		
	}//isValidAlphabet

	public static State[] parseNFA(String nfaString){
		
		// Holds the extraced node strings fron the NFA string
		List<String> isolatedNodeList = new ArrayList<String>();
		
		// Keeps track of the beginning of the current 
		// node we are trying to extract
		int begIdx = 0; 
		
		// Loop through each character of the VERY LONG NFA STRING
		// Isolate nodes (states in the NFA)
		for (int i=0; i<nfaString.length(); i++){
			if (nfaString.charAt(i) == '('){
				begIdx = i;
			}
			if (nfaString.charAt(i) == ')'){
				isolatedNodeList.add(nfaString.substring(begIdx, i+1));
					// System.out.println("added \"" + nfaString.substring(begIdx, i+1) + "\" to the list");
			}
		}
		
		
		List<State> isolatedStateList = new ArrayList<State>();
		
		// values contained in a State object
		String stateName = null;
		KeyPair[] reachableStates = null;
		boolean isAccept = false;
		boolean isStart = false;
		
		int keyPairStart = 0;
		int keyPairEnd = 0;
		
		for(String node : isolatedNodeList)
		{   
			
			for (int i=0; i<node.length(); i++){
				if (node.charAt(i) == '['){
					stateName = node.substring(1, i-2); // extract the name
					keyPairStart = i;
					// System.out.println("Statename is: " + stateName);
				}
				else if (node.charAt(i) == ']'){
					reachableStates = parseKeyPairs(node.substring(keyPairStart+1, i));
					// System.out.println(node.substring(keyPairStart+1, i));
					keyPairEnd = i;		
					if (node.substring(keyPairEnd+3, node.indexOf(',', i+2)).equals("true")){
						isAccept = true;
						// System.out.println("isAccept is: " + isAccept);
					}
					else{
						isAccept = false;
						// System.out.println("isAccept is: " + isAccept);
					}
					
					if (node.substring(node.indexOf(',', i+2)+2, node.length()-1).equals("true")){
						isStart = true;
						// System.out.println("isStart is: " + isStart);
					}
					else{
						isStart = false;
						// System.out.println("isStart is: " + isStart);
					} 
				}
			}// forloop
			
			// Add the State object to a list of state objects
			isolatedStateList.add(new State(stateName, reachableStates, isAccept, isStart));
			
	    }// for node in isolatedNodeList

	    State[] isolatedStateArray = isolatedStateList.toArray(new State[isolatedStateList.size()]);
	    isolatedStateList.toArray(isolatedStateArray);

		// return the array of states
	    return isolatedStateArray;

	}//parseNFA
	

	public static KeyPair[] parseKeyPairs(String keyPairList) {
		
		int keyPairStart = 0;
		String reachableState = null;
		char reachableBy;
		
		List<KeyPair> isolatedKeyPairList = new ArrayList<KeyPair>();
		
		for (int i=0; i<keyPairList.length(); i++){
			if (keyPairList.charAt(i) == '{'){
				keyPairStart = i;
			}
			if (keyPairList.charAt(i) == '}'){
				reachableBy = keyPairList.charAt(keyPairStart+1);
				reachableState = keyPairList.substring(keyPairStart+4, i);
				// System.out.println(reachableBy + reachableState);
				isolatedKeyPairList.add(new KeyPair(reachableState, reachableBy));
			}
		}
		
		KeyPair[] isolatedKeyPairArray = isolatedKeyPairList.toArray(new KeyPair[isolatedKeyPairList.size()]);
		isolatedKeyPairList.toArray(isolatedKeyPairArray);
		
		// return the array of states
		return isolatedKeyPairArray;
		
		
	}// parseKeyPairs


	public static boolean NFAacceptW(State[] states, String inputString){

		List<State> whereIAmCopy = new ArrayList<State>();
		State startState = null;
		List<State> whereIAm = new ArrayList<State>();
		
		for(State x : states){
			System.out.println("states contains: " + x.stateName);
		}
		System.out.println("==================");
		for(State s : states){ //loop through all the states that were given to us

			if(s.isStart && startState == null){ //take the first start state we come across as the one to start from
				startState = s;
				whereIAm.add(startState); //now we have the start state at the front of the list of states that we will check against our input string
				
			}	
			else if(s.isStart && startState != null){ //if we find another start state, we are done cuz you cant do that
				System.exit(-1);//oh dang things got messed up! get out of here!
			}
		}//end for-loop to find start state

		for(int i = 0; i < inputString.length(); i++){ //loop to see where the currently read letter of our string can take us
			char c = inputString.charAt(i); //iterate through each of the letters
			whereIAmCopy = new ArrayList<State>(whereIAm);		
			whereIAm.clear();
			for(State s : whereIAmCopy){ //for everystate where we currently are
				
				KeyPair[] reachableFromHere = s.reachableStates; // get all the places that are reachable from here so that we can go to them

				if(s.isAccept && i == inputString.length()){ //if we are in an accept state and we are at the end of the string accept
					return true;
				}

				for(int k = 0; k < reachableFromHere.length; k++){ //loop through all the reachable places
					if(reachableFromHere[k].reachableBy == c || reachableFromHere[k].reachableBy == 'e'){
						System.out.println("reachableFromHere[" + k + "].reachableState = " + reachableFromHere[k].reachableState);
						System.out.println("reachableFromHere[" + k + "].reachableBy = " + reachableFromHere[k].reachableBy);
						for(State s1 : states){
							System.out.println("s1.stateName = " + s1.stateName);
							if(s1.stateName.equals(reachableFromHere[k].reachableState)){
								if(s1.isAccept){ //if we are in an accept state and we are at the end of the string accept
									return true;
								}
								whereIAm.add(s1);
							}
						}						
					}
				}//end secondary inner for loop
			}//end primary inner loop
		}//end outer for loop
		
		return false;
	}

}


