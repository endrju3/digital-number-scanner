package digital.number.scanner.service;

import digital.number.scanner.model.Chunk;
import digital.number.scanner.model.Symbol;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.logging.LogManager;

public class ScannerServiceIntegrationTest extends BaseScannerServiceIntegrationTest {

    private static final int NUMBER_OF_DIGITS = 9;
    private static final int SYMBOL_WIDTH = 3;
    private static final String VALUES = "0123456789";
    private static final List<String> DIGITS = List.of(
            " _     _  _     _  _  _  _  _ ",
            "| |  | _| _||_||_ |_   ||_||_|",
            "|_|  ||_  _|  | _||_|  ||_| _|"
    );    
    
    private final ScannerService scannerService = new ScannerServiceImpl(NUMBER_OF_DIGITS, SYMBOL_WIDTH, DIGITS, VALUES);

    public ScannerServiceIntegrationTest() throws IOException {
        try(InputStream configFile = this.getClass().getResourceAsStream("/app.properties")) {
            LogManager.getLogManager().readConfiguration(configFile);
        }
    }

    @Override
    protected List<String> performScanning(String inputFilePath) {
        return scannerService.scan(inputFilePath);
    }

    // Generate some random numbers
    public static void main(String[] args) {
        Chunk digitsChunk = new Chunk(VALUES.length() * 3, DIGITS);
        int numberOfDigits = 9;
        ChunkProcessorService chunkProcessorService = new ChunkProcessorServiceImpl(3);
        List<Symbol> symbols = chunkProcessorService.fetchSymbolFlux(digitsChunk).collectList().block();
        for (int i=0; i < 20; i++) {
            int number = new Random().nextInt((int) Math.pow(10, 10));
            String numberAsString = String.format("%1$" + numberOfDigits + "s", number).replace(" ", "0");
            List<StringBuilder> result = List.of(new StringBuilder(), new StringBuilder(), new StringBuilder());
            for(int j=0; j < numberOfDigits; j++) {
                int digit = numberAsString.charAt(j);
                int index = VALUES.indexOf(digit);
                result.get(0).append(symbols.get(index).getCode(0));
                result.get(1).append(symbols.get(index).getCode(1));
                result.get(2).append(symbols.get(index).getCode(2));
            }
            System.out.println(result.get(0).toString());
            System.out.println(result.get(1).toString());
            System.out.println(result.get(2).toString());
            System.out.println();
        }
    }
}