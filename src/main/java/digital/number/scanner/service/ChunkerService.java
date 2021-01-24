package digital.number.scanner.service;

import digital.number.scanner.model.Chunk;

import java.util.List;

public interface ChunkerService {
	Chunk buildChunk(List<String> lines);
}
