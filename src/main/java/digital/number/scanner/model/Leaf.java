package digital.number.scanner.model;

public class Leaf extends Node {

	private char digit;

	public Leaf(char digit) {
		this.digit = digit;
	}

	public char getDigit() {
		return digit;
	}
}
