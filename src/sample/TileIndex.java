package sample;

public class TileIndex {
    private short i;
    private short j;
    /**
     * Create a new TileIndex.
     * @param i Represents the Row Number.
     * @param j Represents the Column Number.
     */
    public TileIndex(short i, short j) {
        this.i = i;
        this.j = j;
    }
    /**
     * @return Row Number.
     */
    public short getRow() {
        return i;
    }
    /**
     * @return Column Number.
     */
    public short getColumn() {
        return j;
    }
    /**
     * @param i Integer to set the Row Number.
     */
    public void setRow(short i) {
        this.i = i;
    }
    /**
     * @param j Integer to set the Row Number.
     */
    public void setColumn(short j) {
        this.j = j;
    }
}
