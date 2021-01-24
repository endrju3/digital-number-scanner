package digital.number.scanner.model;

import java.util.HashMap;
import java.util.Map;

public class InternalNode extends Node {

	private Map<String, Node> codeMap = new HashMap<>();

	public Map<String, Node> getCodeMap() {
		return codeMap;
	}
}
