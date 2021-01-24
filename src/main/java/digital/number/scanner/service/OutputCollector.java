package digital.number.scanner.service;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class OutputCollector implements Collector<Character, StringBuilder, String>{

	@Override
	public Supplier<StringBuilder> supplier() {
		return StringBuilder::new;
	}

	@Override
	public BiConsumer<StringBuilder, Character> accumulator() {
		return StringBuilder::append;
	}

	@Override
	public BinaryOperator<StringBuilder> combiner() {
		return StringBuilder::append;
	}

	@Override
	public Function<StringBuilder, String> finisher() {
		return (stringBuilder -> {
			try {
				Long.parseLong(stringBuilder.toString());
				return stringBuilder.toString();
			} catch (NumberFormatException e) {
				return stringBuilder.append("ILL").toString();
			}
		});
	}

	@Override
	public Set<Characteristics> characteristics() {
		return null;
	}
}
