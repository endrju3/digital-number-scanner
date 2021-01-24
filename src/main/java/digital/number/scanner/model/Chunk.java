package digital.number.scanner.model;

import java.util.List;

import static digital.number.scanner.model.Symbol.SYMBOL_HEIGHT;

public class Chunk {
	
	private String[] row = new String[SYMBOL_HEIGHT];

	public Chunk(int chunkWidth, List<String> rows) {
		if (rows.size() != SYMBOL_HEIGHT) throw new RuntimeException("Invalid chunk - height is different than " + SYMBOL_HEIGHT);
		if (rows.get(0).length() != rows.get(1).length() || rows.get(0).length() != rows.get(2).length()) throw new RuntimeException("Invalid chunk - rows have different sizes");
		if (rows.get(0).length() != chunkWidth) throw new RuntimeException("Invalid chunk - chunk width is different than " + chunkWidth);
		this.row[0] = rows.get(0);
		this.row[1] = rows.get(1);
		this.row[2] = rows.get(2);
	}
	
	public String getRow(int index) {
		return this.row[index];
	}
	
	public int getLength() {
		return this.row[0].length();
	}
}
