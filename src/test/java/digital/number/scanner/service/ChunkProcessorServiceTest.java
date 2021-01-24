package digital.number.scanner.service;

import digital.number.scanner.model.Chunk;
import digital.number.scanner.model.Symbol;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChunkProcessorServiceTest {
	
	private final ChunkProcessorService chunkProcessorService = new ChunkProcessorServiceImpl(3);

	@Test
	public void shouldMatchSymbol() {
		// Given
		Chunk digits = new Chunk(2 * 3, List.of(
				" _    ",
				"| |  |",
				"|_|  |"
		));

		// When
		List<Symbol> symbols = chunkProcessorService.fetchSymbolFlux(digits).collectList().block();

		// Then
		assertEquals(2, symbols.size());
		assertEquals(0, symbols.get(0).getOffset());
		assertEquals(" _ ", symbols.get(0).getCode(0));
		assertEquals("| |", symbols.get(0).getCode(1));
		assertEquals("|_|", symbols.get(0).getCode(2));

		assertEquals(1, symbols.get(1).getOffset());
		assertEquals("   ", symbols.get(1).getCode(0));
		assertEquals("  |", symbols.get(1).getCode(1));
		assertEquals("  |", symbols.get(1).getCode(2));
	}

	@Test
	public void shouldMatchSymbolByDifferentWidth() {
		// Given
		Chunk digits = new Chunk(2 * 3, List.of(
				" _    ",
				"| |  |",
				"|_|  |"
		));

		// When
		List<Symbol> symbols = new ChunkProcessorServiceImpl(2).fetchSymbolFlux(digits).collectList().block();

		// Then
		assertEquals(3, symbols.size());
		assertEquals(0, symbols.get(0).getOffset());
		assertEquals(" _", symbols.get(0).getCode(0));
		assertEquals("| ", symbols.get(0).getCode(1));
		assertEquals("|_", symbols.get(0).getCode(2));

		assertEquals(1, symbols.get(1).getOffset());
		assertEquals("  ", symbols.get(1).getCode(0));
		assertEquals("| ", symbols.get(1).getCode(1));
		assertEquals("| ", symbols.get(1).getCode(2));

		assertEquals(2, symbols.get(2).getOffset());
		assertEquals("  ", symbols.get(2).getCode(0));
		assertEquals(" |", symbols.get(2).getCode(1));
		assertEquals(" |", symbols.get(2).getCode(2));
	}
}
