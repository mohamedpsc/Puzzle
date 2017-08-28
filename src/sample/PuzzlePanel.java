package sample;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class PuzzlePanel extends JPanel {
    public static int DIMENSION;
    private Tile[][] tiles;
    private Tile winningTile;
    private TileIndex spaceIndex;
    private Stack<Directions> undo;
    private Stack<Directions> redo;
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
        undo = new Stack<>();
        redo = new Stack<>();
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
     * Draw all Panel Components.
     * @param g Graphics used to draw components.
     */
    @Override
    protected void paintComponent(Graphics g){
        for(Tile[] x: tiles){
            for(Tile y:x){
                y.draw((Graphics2D)g);
            }
        }
    }
    /**
     * Move the Suitable Tile to the Right.
     * @param undo boolean state, true if the call is from undo Operation.
     */
    public void moveRight(boolean undo){
        if(spaceIndex.getColumn()!=0){
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()-1].moveRight();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveLeft();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow(), spaceIndex.getColumn()-1);
            spaceIndex.setColumn(spaceIndex.getColumn()-1);
            repaint();
            if(undo){
                redo.push(Directions.Right);
            }
            else this.undo.push(Directions.Right);
        }
    }
    /**
     * Move the Suitable Tile to the Left.
     * @param undo boolean state, true if the call is from undo Operation.
     */
    public void moveLeft(boolean undo){
        if(spaceIndex.getColumn()!=DIMENSION-1){
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()+1].moveLeft();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveRight();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow(), spaceIndex.getColumn()+1);
            spaceIndex.setColumn(spaceIndex.getColumn()+1);
            repaint();
            if(undo){
                redo.push(Directions.Left);
            }
            else this.undo.push(Directions.Left);
        }
    }
    /**
     * Move the Suitable Tile Up.
     * @param undo boolean state, true if the call is from undo Operation.
     */
    public void moveUp(boolean undo){
        if(spaceIndex.getRow()!=DIMENSION-1){
            tiles[spaceIndex.getRow()+1][spaceIndex.getColumn()].moveUp();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveDown();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow()+1, spaceIndex.getColumn());
            spaceIndex.setRow(spaceIndex.getRow()+1);
            repaint();
            if(undo){
                redo.push(Directions.Up);
            }
            else this.undo.push(Directions.Up);
        }
    }
    /**
     * * Move the Suitable Tile down.
     * @param undo boolean state, true if the call is from undo Operation.
     */
    public void moveDown(boolean undo){
        if(spaceIndex.getRow()!=0){
            tiles[spaceIndex.getRow()-1][spaceIndex.getColumn()].moveDown();
            tiles[spaceIndex.getRow()][spaceIndex.getColumn()].moveUp();
            swap(spaceIndex.getRow(), spaceIndex.getColumn(), spaceIndex.getRow()-1, spaceIndex.getColumn());
            spaceIndex.setRow(spaceIndex.getRow()-1);
            repaint();
            if(undo){
                redo.push(Directions.Down);
            }
            else this.undo.push(Directions.Down);
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
                moveUp(false);
                return true;
            }
            else if(tile.getIndex().getRow()==spaceIndex.getRow()-1){
                moveDown(false);
                return true;
            }
        }else if(tile.getIndex().getRow()==spaceIndex.getRow()){
            if(tile.getIndex().getColumn()==spaceIndex.getColumn()+1){
                moveLeft(false);
                return true;
            }
            else if(tile.getIndex().getColumn()==spaceIndex.getColumn()-1){
                moveRight(false);
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
    /**
     * Check if the Puzzle is Solved.
     * @return ~true if the Puzzle is solved, ~false if the Puzzle is still not solved.
     */
    public boolean won(){
        int currentValue = 0;
        for(int i=0;i<DIMENSION-1;i++){
            for(int j=0;j<DIMENSION;j++){
                if(tiles[i][j].getValue()<currentValue){
                    return false;
                }
                currentValue = tiles[i][j].getValue();
            }
        }
        for(int j=0;j<DIMENSION-1;j++){
            if(tiles[DIMENSION-1][j].getValue()<currentValue){
                return false;
            }
            currentValue = tiles[DIMENSION-1][j].getValue();
        }
        tiles[DIMENSION-1][DIMENSION-1] = winningTile;
        winningTile.update();
        return finished=true;
    }

    /**
     * This is Function checks if the finish flag is set to true or not.
     * @return boolean, finish Flag.
     */
    public boolean isFinished(){
        return finished;
    }
    /**
     * Update All Tiles position.
     */
    public void updateTiles(){
        for(Tile[] x:tiles){
            for(Tile y:x){
                y.update();
            }
        }
    }
    /**
     * Undo Last Move.
     */
    public void undo(){
        if(undo.size()>0){
            Directions undoPeek = undo.pop();
            switch(undoPeek){
                case Left:
                    moveRight(true);
                    break;
                case Right:
                    moveLeft(true);
                    break;
                case Up:
                    moveDown(true);
                    break;
                case Down:
                    moveUp(true);
                    break;
            }
        }
    }
    /**
     * Redo Last Undo.
     */
    public void redo(){
        if(redo.size()>0){
            Directions temp = redo.pop();
            switch(temp){
                case Left:
                    moveRight(false);
                    break;
                case Right:
                    moveLeft(false);
                    break;
                case Up:
                    moveDown(false);
                    break;
                case Down:
                    moveUp(false);
                    break;
            }
        }
    }
}

enum Directions{
    Left,
    Right,
    Down,
    Up
}