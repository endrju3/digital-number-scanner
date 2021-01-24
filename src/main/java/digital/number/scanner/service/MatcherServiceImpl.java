package digital.number.scanner.service;

import digital.number.scanner.model.*;

import java.util.List;
import java.util.Random;

import static digital.number.scanner.model.Symbol.SYMBOL_HEIGHT;

public class MatcherServiceImpl implements MatcherService {
	
	private final InternalNode root = new InternalNode();

	public MatcherServiceImpl(int symbolWidth, List<String> digits, String values) {
		Chunk digitsChunk = new Chunk(values.length() * symbolWidth, digits);
		ChunkProcessorService chunkProcessorService = new ChunkProcessorServiceImpl(symbolWidth);
		chunkProcessorService.fetchSymbolFlux(digitsChunk).doOnNext(s -> add(root, s, values.charAt(s.getOffset()))).collectList().block();
	}

	private void add(Node root, Symbol s, char digit) {
		Node currentNode = root;		
		for (int i = 0; i < SYMBOL_HEIGHT; i++) {
			String code = s.getCode(i);
			Node newNode = (i == SYMBOL_HEIGHT - 1) ? new Leaf(digit) : new InternalNode();
			currentNode = ((InternalNode) currentNode).getCodeMap().computeIfAbsent(code, c -> newNode);
		}
	}

	@Override
	public char match(Symbol s) {
		Node currentNode = root;
		for (int i = 0; i < SYMBOL_HEIGHT; i++) {
			String code = s.getCode(i);
			if (currentNode instanceof InternalNode)
				currentNode = ((InternalNode) currentNode).getCodeMap().getOrDefault(code, new Leaf('?'));
			else
				break;
		}
		return ((Leaf) currentNode).getDigit();
	}
}
