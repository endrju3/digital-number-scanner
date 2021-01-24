package digital.number.scanner.model;

import java.util.Arrays;

public class Symbol {

	public static final int SYMBOL_HEIGHT = 3;

	private String[] row = new String[SYMBOL_HEIGHT];
	private int offset;

	public Symbol(int symbolWidth, Chunk chunk, int offset) {
		this.row[0] = chunk.getRow(0).substring(offset * symbolWidth, (offset + 1) * symbolWidth);
		this.row[1] = chunk.getRow(1).substring(offset * symbolWidth, (offset + 1) * symbolWidth);
		this.row[2] = chunk.getRow(2).substring(offset * symbolWidth, (offset + 1) * symbolWidth);
		this.offset = offset;
	}
	
	public String getCode(int index) {
		return this.row[index];
	}

	public int getOffset() {
		return offset;
	}

	@Override
	public String toString() {
		return "Symbol{" +
				"row=" + Arrays.toString(row) +
				'}';
	}
}
