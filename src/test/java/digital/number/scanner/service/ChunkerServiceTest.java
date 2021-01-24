package digital.number.scanner.service;

import digital.number.scanner.exception.InvalidChunkException;
import digital.number.scanner.model.Chunk;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ChunkerServiceTest {

	private final ChunkerService chunkerService = new ChunkerServiceImpl(9, 3);
	private static final List<String> DEFAULT_LINES = List.of(
			"    _  _  _  _     _  _    ",
			"|_||_ |_ | ||_   ||_|| ||_|",
			"  ||_| _||_| _|  ||_||_|  |"
	);
		
	@Test
	public void shouldCreateAChunk() {
		// Given
		List<String> lines = DEFAULT_LINES;

				// When
		Chunk chunk = chunkerService.buildChunk(lines);
		
		// Then
		assertEquals(27, chunk.getLength());
		assertEquals(DEFAULT_LINES.get(0), chunk.getRow(0));
		assertEquals(DEFAULT_LINES.get(1), chunk.getRow(1));
		assertEquals(DEFAULT_LINES.get(2), chunk.getRow(2));
	}

	@Test
	public void shouldCreateAChunkEvenWithAdditionalWhitespacesAtTheEnd() {
		// Given
		List<String> lines = List.of(
				"    _  _  _  _     _  _         ",
				"|_||_ |_ | ||_   ||_|| ||_|    ",
				"  ||_| _||_| _|  ||_||_|  | "
		);

		// When
		Chunk chunk = chunkerService.buildChunk(lines);

		// Then
		assertEquals(27, chunk.getLength());
		assertEquals(DEFAULT_LINES.get(0), chunk.getRow(0));
		assertEquals(DEFAULT_LINES.get(1), chunk.getRow(1));
		assertEquals(DEFAULT_LINES.get(2), chunk.getRow(2));
	}

	@Test
	public void shouldCreateAChunkIfTrailingWhitespaceMissing() {
		// Given
		List<String> lines = List.of(
				"    _  _  _  _     _  _",
				"|_||_ |_ | ||_   ||_|| ||_|",
				"  ||_| _||_| _|  ||_||_|  |"
		);

		// When
		Chunk chunk = chunkerService.buildChunk(lines);

		// Then
		assertEquals(27, chunk.getLength());
		assertEquals(DEFAULT_LINES.get(0), chunk.getRow(0));
		assertEquals(DEFAULT_LINES.get(1), chunk.getRow(1));
		assertEquals(DEFAULT_LINES.get(2), chunk.getRow(2));
	}

	@Test
	public void shouldFailToCreateAChunkIfHeightTooSmall() {
		// Given
		List<String> lines = List.of(
				"    _  _  _  _     _  _    ",
				"|_||_ |_ | ||_   ||_|| ||_|"
		);

		// When
		InvalidChunkException ex = assertThrows(InvalidChunkException.class, () -> chunkerService.buildChunk(lines));

		// Then
		assertEquals("Invalid chunk - Height is different than 3", ex.getMessage());
	}

	@Test
	public void shouldFailToCreateAChunkIfRowsHaveDifferentWidth() {
		// Given
		List<String> lines = List.of(
				"    _  _  _  _     _  _     _ ",
				"|_||_ |_ | ||_   ||_|| ||_|",
				"  ||_| _||_| _|  ||_||_|  ||_ "
		);

		// When
		InvalidChunkException ex = assertThrows(InvalidChunkException.class, () -> chunkerService.buildChunk(lines));

		// Then
		assertEquals("Invalid chunk - Rows have different sizes", ex.getMessage());
	}
	
	@Test
	public void shouldFailToCreateAChunkIfWidthIsIncorrect() {
		// Given
		List<String> lines = List.of(
				"    _  _  _  _     _  _     _",
				"|_||_ |_ | ||_   ||_|| ||_| _",
				"  ||_| _||_| _|  ||_||_|  ||_"
		);

		// When
		InvalidChunkException ex = assertThrows(InvalidChunkException.class, () -> chunkerService.buildChunk(lines));

		// Then
		assertEquals("Invalid chunk - Chunk width is different than 27", ex.getMessage());
	}
}
