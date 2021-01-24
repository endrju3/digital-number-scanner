package digital.number.scanner.service;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static org.junit.Assert.assertEquals;

public class OutputCollectorTest  {

	private final OutputCollector outputCollector = new OutputCollector();
	
	@Test
	public void shouldCollectTheOutput() {
		// Given
		Flux<Character> numberFlux = Flux.just('1', '3');
		
		// When
		String output = numberFlux.collect(outputCollector).block();

		// Then
		assertEquals("13", output);
	}

	@Test
	public void shouldCollectTheOutputAndHandleInvalidNumbers() {
		// Given
		Flux<Character> numberFlux = Flux.just('1', '?', '8');

		// When
		String output = numberFlux.collect(outputCollector).block();

		// Then
		assertEquals("1?8ILL", output);
	}
}
