package digital.number.scanner.service;

import digital.number.scanner.model.Chunk;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

public class ChunkerServiceImpl implements ChunkerService {

	private final int chunkWidth;

	public ChunkerServiceImpl(int numberOfDigits, int symbolWidth) {		
		this.chunkWidth = numberOfDigits * symbolWidth;
	}

	@Override
	public Chunk buildChunk(List<String> lines) {
		List<String> formattedRows = Flux.range(0, lines.size())
				.map(i -> String.format("%1$-" + this.chunkWidth + "s", lines.get(i).stripTrailing()))
				.collect(Collectors.toList())
				.block();
		return new Chunk(this.chunkWidth, formattedRows);
	}

}
