package digital.number.scanner.service;

import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.BaseStream;

public class ScannerServiceImpl implements ScannerService {
	
	public static final String WHITESPACE_REGEX = "^\\s*$";
	public static final Logger LOG = Logger.getLogger(ScannerServiceImpl.class.getName());

	private final ChunkerService chunkerService;
	private final ChunkProcessorService chunkProcessorService;
	private final MatcherService matcherService;
	private final OutputCollector outputCollector;

	public ScannerServiceImpl(int numberOfDigits, int symbolWidth, List<String> digits, String values) {
		this.chunkerService = new ChunkerServiceImpl(numberOfDigits, symbolWidth);
		this.chunkProcessorService = new ChunkProcessorServiceImpl(symbolWidth);
		this.matcherService = new MatcherServiceImpl(symbolWidth, digits, values);
		this.outputCollector = new OutputCollector();
	}

	@Override
	public List<String> scan(final String inputFilePath) {
		return fromPath(inputFilePath)
				.bufferWhile(s -> !s.matches(WHITESPACE_REGEX))
				.map(chunkerService::buildChunk)
				.flatMap(chunk -> chunkProcessorService.fetchSymbolFlux(chunk).map(matcherService::match).collect(outputCollector))
				.onErrorContinue((throwable, o) -> LOG.warning(throwable.getMessage()))
				.collectList()
				.block();
	}

	private static Flux<String> fromPath(String path) {
		return Flux.using(() -> Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8).lines(),
				Flux::fromStream,
				BaseStream::close
		);
	}
}
