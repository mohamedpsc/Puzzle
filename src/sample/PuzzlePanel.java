package sample;

import java.util.ArrayList;
import java.util.Random;

public class PuzzlePanel {
    public static short DIMENSION;
    private Tile[][] tiles;
    private Tile winningTile;
    private TileIndex spaceIndex;
    private boolean finished;
    /**
     * Create a new Puzzle Game.
     */
    private PuzzlePanel(){
    }
    /**
     * Create a new Puzzle Game.
     * @param dimension Puzzle Dimension.
     */
    public PuzzlePanel(short dimension){
        DIMENSION = dimension;
        init();
        createPuzzle();
    }
    /**
     * Initialize Variables.
     */
    private void init(){
        tiles = new Tile[DIMENSION][DIMENSION];
        finished = false;
    }
    /**
     * Create Puzzle Tiles each with a number.
     */
    private void createPuzzle(){
        ArrayList<Tile> myTiles = new ArrayList<>();
        for(int i=1;i<DIMENSION*DIMENSION;i++){
            myTiles.add(new Tile(i));
        }
        Random rand = new Random();
        int position;
        for(int i=0;i<DIMENSION-1;i++){
            for(int j=0;j<DIMENSION;j++){
                position=rand.nextInt(myTiles.size());
                tiles[i][j]=myTiles.remove(position);
                tiles[i][j].setIndex(new TileIndex(i, j));
            }
        }
        winningTile = new Tile(DIMENSION*DIMENSION,DIMENSION-1, DIMENSION-1);
        for(int j=0;j<DIMENSION-1;j++){
            position=rand.nextInt(myTiles.size());
            tiles[DIMENSION-1][j]=myTiles.remove(position);
            tiles[DIMENSION-1][j].setIndex(new TileIndex(DIMENSION-1, j));
        }
        tiles[DIMENSION-1][DIMENSION-1]=new Tile(-1,DIMENSION-1, DIMENSION-1);
        spaceIndex = new TileIndex(DIMENSION-1, DIMENSION-1);
    }
}
