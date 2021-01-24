package digital.number.scanner.service;

import digital.number.scanner.model.Chunk;
import digital.number.scanner.model.Symbol;
import reactor.core.publisher.Flux;

public class ChunkProcessorServiceImpl implements ChunkProcessorService {
	
	private int width;
	
	public ChunkProcessorServiceImpl(int width) {
		this.width = width;
	}

	@Override
	public Flux<Symbol> fetchSymbolFlux(Chunk chunk) {
		return Flux.range(0, chunk.getLength() / width).map(i -> new Symbol(this.width, chunk, i));
	}
}
