package sample;

import javafx.scene.layout.FlowPane;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PuzzlePanel extends JPanel {
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
    /**
     * Move the Suitable Tile to the Right.
     */
    public void moveRight(){
        if(spaceIndex.getColumn()!=0){
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()-1].moveRight();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveLeft();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow(), spaceIndex.getColumn()-1);
            spaceIndex.setColumn(spaceIndex.getColumn()-1);
            repaint();
        }
    }
    /**
     * Move the Suitable Tile to the Left.
     */
    public void moveLeft(){
        if(spaceIndex.getColumn()!=DIMENSION-1){
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()+1].moveLeft();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveRight();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow(), spaceIndex.getColumn()+1);
            spaceIndex.setColumn(spaceIndex.getColumn()+1);
            repaint();
        }
    }
    /**
     * Move the Suitable Tile Up.
     */
    public void moveUp(){
        if(spaceIndex.getRow()!=DIMENSION-1){
            tiles[spaceIndex.getRow()+1][spaceIndex.getColumn()].moveUp();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveDown();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow()+1, spaceIndex.getColumn());
            spaceIndex.setRow(spaceIndex.getRow()+1);
            repaint();
        }
    }
    /**
     * * Move the Suitable Tile down.
     */
    public void moveDown(){
        if(spaceIndex.getRow()!=0){
            tiles[spaceIndex.getRow()-1][spaceIndex.getColumn()].moveDown();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveUp();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow()-1, spaceIndex.getColumn());
            spaceIndex.setRow(spaceIndex.getRow()-1);
            repaint();
        }
    }
    /**
     * Move certain Tile in the allowed Direction.
     * @param tile Tile to be moved.
     * @return ~true if the Tile can be moved, ~false if the Tile cannot be moved.
     */
    private boolean move(Tile tile){
        if(tile.getIndex().getColumn()==spaceIndex.getColumn()){
            if(tile.getIndex().getRow()==spaceIndex.getRow()+1){
                moveUp();
                return true;
            }
            else if(tile.getIndex().getRow()==spaceIndex.getRow()-1){
                moveDown();
                return true;
            }
        }else if(tile.getIndex().getRow()==spaceIndex.getRow()){
            if(tile.getIndex().getColumn()==spaceIndex.getColumn()+1){
                moveLeft();
                return true;
            }
            else if(tile.getIndex().getColumn()==spaceIndex.getColumn()-1){
                moveRight();
                return true;
            }
        }
        return false;
    }
    /**
     * Move certain Tile in the allowed Direction.
     * @param index represent Index of the Tile to be Moved.
     * @return ~true if the Tile can be moved, ~false if the Tile cannot be moved.
     */
    public boolean move(TileIndex index){
        if(index.getRow()<DIMENSION && index.getColumn()<DIMENSION)
            return move(tiles[index.getRow()][index.getColumn()]);
        return false;
    }
    /**
     * Swap Two Tiles With each other inside the Tiles[][].
     * @param i Row Number of Tile 1.
     * @param j Column Number of Tile 1.
     * @param i1 Row Number of Tile 2.
     * @param j1 Column Number of Tile 2.
     */
    public void swap(int i, int j, int i1, int j1){
        Tile temp = tiles[i][j];
        tiles[i][j]=tiles[i1][j1];
        tiles[i1][j1] = temp;
    }
}
