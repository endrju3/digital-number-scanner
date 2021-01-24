package digital.number.scanner.service;

import java.util.List;

public interface ScannerService {
	List<String> performScanning(final String inputFilePath);
}
