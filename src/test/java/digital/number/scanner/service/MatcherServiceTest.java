package digital.number.scanner.service;

import digital.number.scanner.model.Chunk;
import digital.number.scanner.model.Symbol;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MatcherServiceTest {

	private static final List<String> DIGITS = List.of(
			" _     _  _     _ ",
			"| |  | _| _||_||_ ",
			"|_|  ||_  _|  | _|"
	);
	private static final String VALUES = "012345";

	private final MatcherService matcherService = new MatcherServiceImpl(3, DIGITS, VALUES);

	@Test
	public void shouldMatchSymbol() {
		// Given
		Chunk five = new Chunk(1 * 3, List.of(
				" _ ",
				"|_ ",
				" _|"
		));
		Symbol symbol = new Symbol(3, five, 0);
		
		// When
		char ch = matcherService.match(symbol);
		
		// Then
		assertEquals('5', ch);
	}

	@Test
	public void shouldReturnQuestionMarkIfSymbolWasNotMatched() {
		// Given
		Chunk letterA = new Chunk(1 * 3, List.of(
				" _ ",
				"|_|",
				"| |"
		));
		Symbol symbol = new Symbol(3, letterA, 0);

		// When
		char ch = matcherService.match(symbol);

		// Then
		assertEquals('?', ch);
	}
}
