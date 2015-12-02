package theoryproj;

/*
	Class: KeyPair
	@author : Sean Pierson, Ryson Asuncion
	a simple way to store a key value pair since java is dumb and doesnt like to do it in a reasonable way
	an array of these is stored in a State Object

	@param r
		@type : String
		the state that is reachable

	@param c
		@type : char
		how 'r' can be reached
		@example KeyPair(q1,b) means q1 can be reached with a b
*/


public class KeyPair{

	private String reachableState;
	private char reachableBy;

	public KeyPair(String r, char c){
		this.reachableState = r;
		this.reachableBy = c;
	}
}