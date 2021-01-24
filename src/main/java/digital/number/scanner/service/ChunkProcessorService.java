package digital.number.scanner.service;

import digital.number.scanner.model.Chunk;
import digital.number.scanner.model.Symbol;
import reactor.core.publisher.Flux;

public interface ChunkProcessorService {
	Flux<Symbol> fetchSymbolFlux(Chunk chunk);
}
