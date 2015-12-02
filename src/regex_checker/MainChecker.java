package theoryproj;

import theoryproj.KeyPair;
import theoryproj.State;


/*
	Class: MainChecker
	runs the given string through an NFA that is either specified in the command line args (bless your soul if you try)
	or read in from a file
*/

public class MainChecker {

	//variables and such
	
	private State startState;
	private List<State> whereIAm;
	private List<State> whereIAmCopy;
	private String inputString;
	public static void main(String[] args) {
		//initialize this stuff
		whereIAm = new List<State>();
		
		if(args.length == 2){ //if they were brave enough to enter a bunch of states then we have 2 arguments. 
			inputString = args[1]; //the first argument is the states they entered, the second is the string they want to check			
		}
		else{ //if they didnt enter the NFA states then just get the first argument because there is only 1
			inputString = args[0];
		}
			
	}


	public boolean NFAacceptW(State[] states){
		char[] inputCharArray = inputString.toCharArray();
		for(State s : states){ //loop through all the states that were given to us
			if(s.isStart && startState == null){ //take the first start state we come across as the one to start from
				startState = s;
				whereIAm.add(startState); //now we have the start state at the front of the list of states that we will check against our input string
			}
			else if(s.isStart && startState != null){ //if we find another start state, we are done cuz you cant do that
				System.exit(-1);//oh dang things got messed up! get out of here!
			}
		}//end for-loop to find start state

		for(char c : inputCharArray){ //loop to see where the currently read letter of our string can take us
			whereIAmCopy = whereIAm;
			whereIAm.clear();
			for(State s : whereIAmCopy){ //for everystate where we currently are
				
				if(s.isAccept){
					return true;
				}
				whereIAm = currentState.reachableStates;
				 //reset the list

				for(int i = 0; i < reachableFromHere; i++){
					if(reachableFromHere[i].reachableBy == c){
						whereIAm.add(reachableFromHere[i].reachableState);
					}
				}
			}
		}
	}

}


