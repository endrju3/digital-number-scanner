package digital.number.scanner.service;

import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.BaseStream;

public class ScannerServiceImpl implements ScannerService {
	
	public static final String WHITESPACE_REGEX = "^\\s*$";

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

	public List<String> performScanning(final String inputFilePath) {
		return fromPath(inputFilePath)
				.bufferWhile(s -> !s.matches(WHITESPACE_REGEX))
				.map(chunkerService::buildChunk)
				.flatMap(chunk -> chunkProcessorService.fetchSymbolFlux(chunk).map(matcherService::match).collect(outputCollector))
				.doOnNext(System.out::println)
				.onErrorContinue((throwable, o) -> System.out.println(throwable.getMessage()))
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
