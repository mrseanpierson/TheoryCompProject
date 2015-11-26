package regex_checker;

import java.util.Scanner;

public class MainChecker {

	public static void main(String[] args) {
		
		Scanner userInput = new Scanner(System.in);

		System.out.println("Enter REGEX");
		//userInput.next();
		String regex = userInput.nextLine(); // Get user's REGEX input
		char[] regexArr = isValidREGEX(regex); // Check if valid
		if (regexArr == null) {
			System.exit(-1); // if not valid then terminate abnormally
		} 
		else{
			System.out.println("Enter string to check");
			//userInput.next();
			String input = userInput.nextLine();
			char[] inputArr = isValidAlphabet(input); // Get user's input string to check
			if (inputArr == null) {
				System.exit(-1);
			}
			else{
				if (isOfLanguage(regexArr, inputArr)){
					System.out.println("Language: "+regex+" accepts the string: "+input);
					System.exit(0); // if input is of language then terminate normally
				}
				else{
					System.out.println("Language: "+regex+" DOES NOT accept the string: "+input);
					System.exit(0); // if input is of language then terminate normally
				}
			}
			
		}

	}

	/** isValidREGEX 
	 * 
	 * Checks to see if the inputed REGEX is valid ( ∑={a,b}* and operators |, *, concat )
	 * 
	 * @param inputREGEX
	 * @return
	 */
	public static char[] isValidREGEX(String inputREGEX) {

		char inputArray[] = new char[inputREGEX.length()]; // To split the REGEX into a char array
 		char indexedChar; // The current character that we are examining
		int parenBalance = 0; // used to check if we have PAIRS of ENCLOSING
								// parentheses

		for (int i = 0; i < inputREGEX.length(); i++) {

			indexedChar = inputREGEX.charAt(i);

			if (indexedChar == '(') {
				parenBalance += 1; // Found an open paran
				inputArray[i] = indexedChar; // Place in char array
			} 
			else if (indexedChar == ')') {
				parenBalance -= 1; // Found a close paran
				if (parenBalance < 0) {
					return null; // There are either too many close parans, or
									// parans are out of order
				}
				else{
					inputArray[i] = indexedChar;
				}
			} 
			else if ((indexedChar == 'a') || (indexedChar == 'b')      // Ensures that we stick to concat, union,
					|| (indexedChar == '|') || (indexedChar == '*')) { // and star with only a's and b's
				inputArray[i] = indexedChar;
			}
			else
			{
				return null; // we did not read on of the following: a, b, (, ), |, *
			}
		}// end of for-loop

		if (parenBalance > 0) {
			return null; // There are too many open parans
		}
		else if (parenBalance == 0){ // Every open paran has been closed somewhere down the string
			return inputArray; // return the char array
		}
		else{
			return null; // Should never be reached
		}
	}//isValidREGEX

	
	/** isValidAlphabet 
	 * 
	 * checks to see if the alphabet used is the same as what is specified ( ∑={a.b}* )
	 * 
	 * @param inputString
	 * @return inputArray
	 */
	public static char[] isValidAlphabet(String inputString) {

		char inputArray[] = new char[inputString.length()]; // To split the input string into a char array

		char indexedChar; // The currently examined char
		for (int i = 0; i < inputString.length(); i++) {
			
			indexedChar = inputString.charAt(i);
			
			if ((indexedChar == 'a') || (indexedChar == 'b')) { 
				inputArray[i] = indexedChar; // Put valid char into array
			} else{
				return null; // We haven't read an 'a' or 'b'
			}

		}

		return inputArray; // return the char array
		
	}//isValidAlphabet
	
	
	/** isOfLanguage
	 * 
	 * @param regexArr
	 * @param inputArr
	 * @return boolean 
	 */
	public static boolean isOfLanguage(char[] regexArr, char[]inputArr){

		boolean isStarred = false;
		boolean isUnioned = false;
		List<char> firstRuleToCheck = new List<char>();
		outerloop:
		if(regexArr[0] == '('){ //if there is a rule that is like (abb)* we will get the abb out
			//
			for(int i = 1, i<regexArr.length, i++){
				char c = regexArr[i];

				if(c == ')'){
					if(regexArr[i+1] == '*'){
						isStarred == true;
					}
					if(regexArr[i+1] == '|'){
						isUnioned = true;
					}
					break outerloop;
				}
				else if(c == 'a' || c == 'b'){
					firstRuleToCheck.add(c);
				}
			}
		}
		//stuff after outerloop happens here
		String firstChunkToCheck = inputArr
		
		return false;
	}//isOfLanguage
	

}
