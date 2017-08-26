package sample;

public class TileIndex {
    private int i;
    private int j;
    /**
     * Create a new TileIndex.
     * @param i Represents the Row Number.
     * @param j Represents the Column Number.
     */
    public TileIndex(int i, int j) {
        this.i = i;
        this.j = j;
    }
    /**
     * @return Row Number.
     */
    public int getRow() {
        return i;
    }
    /**
     * @return Column Number.
     */
    public int getColumn() {
        return j;
    }
    /**
     * @param i Integer to set the Row Number.
     */
    public void setRow(int i) {
        this.i = i;
    }
    /**
     * @param j Integer to set the Row Number.
     */
    public void setColumn(int j) {
        this.j = j;
    }
}
