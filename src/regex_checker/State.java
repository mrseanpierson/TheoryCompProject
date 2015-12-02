package regex_checker;

import regex_checker.KeyPair;


/*
	Class: State
	@author : Sean Pierson, Ryson Asuncion
	Purpose: represent a state of an NFA
	@param name
		@type : String
		the name of the state i.e. q0, q1, ....

	@param reachableStateArray
		@type : KeyPair[]
		the array of pairs of what states can be reached from current state and how it can be reached
		@example: reachableStateArray = [(q1, a), (q4, b)] means q1 can be reached on an 'a' from here and q4 can be reached on a 'b'

	@param accept
		@type : boolean
		whether or not this state is an accepting state

	@param start
		@type : boolean
		whether or not this state is a start state

*/

public class State{

		String stateName;
		KeyPair[] reachableStates;
		boolean isAccept;
		boolean isStart;
		
	public State(String name, KeyPair[] reachableStateArray, boolean accept, boolean start){
		this.stateName = name;
		this.reachableStates = reachableStateArray;
		this.isAccept = accept;
		this.isStart = start;
	}


}