package sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PuzzlePanel {
    public static int DIMENSION;
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
    public PuzzlePanel(int dimension){
        DIMENSION = dimension;
        init();
        createPuzzle();
    }
    /**
     * Create a new Puzzle Game.
     * @param dimension Puzzle Dimension.
     * @param file Image File to form the puzzle.
     * @throws IOException `If File cannot be opened.
     */
    public PuzzlePanel(int dimension, File file) throws IOException{
        DIMENSION = dimension;
        init();
        createPuzzle(file);
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
    /**
     * Create Puzzle Tiles each with an image.
     * @param file Image File to be divided into smaller chunks.
     * @throws IOException ~If File cannot be opened.
     */
    private void createPuzzle(File file) throws IOException{
        ArrayList<Tile> myTiles = new ArrayList<>();
        ImageDivider splitter = new ImageDivider(file,DIMENSION);
        ArrayList<BufferedImage> images = splitter.Images();
        for(int i=0;i<(DIMENSION*DIMENSION)-1;i++){
            myTiles.add(new Tile(i, images.get(i)));
        }
        winningTile = new Tile((DIMENSION*DIMENSION)-1, images.get((DIMENSION*DIMENSION)-1),DIMENSION-1, DIMENSION-1);
        Random rand = new Random();
        int position;
        for(int i=0;i<DIMENSION-1;i++){
            for(int j=0;j<DIMENSION;j++){
                position=rand.nextInt(myTiles.size());
                tiles[i][j]=myTiles.remove(position);
                tiles[i][j].setIndex(new TileIndex(i, j));
            }
        }
        for(int j=0;j<DIMENSION-1;j++){
            position=rand.nextInt(myTiles.size());
            tiles[DIMENSION-1][j]=myTiles.remove(position);
            tiles[DIMENSION-1][j].setIndex(new TileIndex(DIMENSION-1, j));
        }
        tiles[DIMENSION-1][DIMENSION-1]=new Tile(-1,DIMENSION-1, DIMENSION-1);
        spaceIndex = new TileIndex(DIMENSION-1, DIMENSION-1);
    }
}
