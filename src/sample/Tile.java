package sample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    /**
     * Variables Declaration.
     */
    private static int TILEWIDTH = 100;
    private static int TILEHEIGHT = 100;
    private int value;
    private BufferedImage image;
    private TileIndex index;
    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    /**
     * Create a new instance of Tile with an image.
     * @param value Short represent it's correct order among other tiles. ranges from 0 to (PuzzleDimension^2)-1.
     * @param image Buffered image which represent a small portion of the whole image to be formed when the puzzle is solved.
     * @param i Int value represent it's current row index inside the 2d Tiles array.
     * @param j Int value represent it's current column index inside the 2d Tiles array.
     */
    public Tile(int value, BufferedImage image, int i, int j){
        this.value = value;
        this.image = image;
        this.index = new TileIndex(i, j);
        update();
    }
    /**
     * Create a new instance of Tile with an image.
     * @param value Int represent it's correct order among other tiles. ranges from 0 to (PuzzleDimension^2)-1.
     * @param image Buffered image which represent a small portion of the image to be sorted.
     */
    public Tile(int value, BufferedImage image){
        this.value = value;
        this.image = image;
    }
    /**
     * Create a new instance of Tile with a number.
     * @param value Int represent it's correct order among other tiles. ranges from 0 to (PuzzleDimension^2)-1.
     * @param i Int value represent it's current row index inside the 2d Tiles array.
     * @param j Int value represent it's current column index inside the 2d Tiles array.
     */
    public Tile(int value, int i, int j){
        this.value = value;
        this.index = new TileIndex(i, j);
        update();
    }
    /**
     * Create a new instance of Tile with a number.
     * @param value Int represent it's correct order among other tiles. ranges from 0 to (PuzzleDimension^2)-1.
     * @param index TileIndex the current row and column of the Tile inside the 2d Tiles array.
     */
    public Tile(int value, TileIndex index){
        this.value = value;
        this.index = index;
        update();
    }
    /**
     * * Create a new instance of Tile with a number.
     * @param value Int represent it's correct order among other tiles. ranges from 0 to (PuzzleDimension^2)-1.
     */
    public Tile(int value){
        this.value = value;
    }
    /**
     * @return value.
     */
    public int getValue() {
        return value;
    }
    /**
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }
    /**
     * @return index
     */
    public TileIndex getIndex() {
        return this.index;
    }
    /**
     * @param index
     */
    public void setIndex(TileIndex index) {
        this.index = index;
        update();
    }
    /**
     * Sets the Static Tile Width for all Tiles.
     * @param width value to be Stored to the Static Tile Width variable.
     */
    public static void setTileWidth(int width){
        TILEWIDTH = width;
    }
    /**
     * Sets the Static Tile Height for all Tiles.
     * @param height value to be Stored to the Static Tile Height variable.
     */
    public static void setTileHeight(int height){
        TILEHEIGHT = height;
    }
    /**
     * @return TileWidth.
     */
    public static int getTileWidth(){
        return TILEWIDTH;
    }
    /**
     * @return TileHeight.
     */
    public static int getTileHeight(){
        return TILEHEIGHT;
    }
    /**
     * Imaginary Move the Tile To the Left only by changing the Tile Index.
     */
    public void moveLeft(){
        this.index.setColumn((short) (this.index.getColumn()-1));
        update();
    }
    /**
     * Imaginary Move the Tile To the Right only by changing the Tile Index.
     */
    public void moveRight(){
        this.index.setColumn((short) (this.index.getColumn()+1));
        update();
    }
    /**
     * Imaginary Move the Tile Up only by changing the Tile Index.
     */
    public void moveUp(){
        this.index.setRow((short) (this.index.getRow()-1));
        update();
    }
    /**
     * Imaginary Move the Tile Down only by changing the Tile Index.
     */
    public void moveDown(){
        this.index.setRow((short) (this.index.getRow()+1));
        update();
    }
    /**
     * Update the Points of the Rectangle Representing the Tile. this function must be called whenever The Tile was moved or a change was made to either of TileWidth or TileHeight.
     */
    public void update(){
        int startX = (index.getColumn()% PuzzlePanel.DIMENSION)*TILEWIDTH;
        int startY = (index.getRow()% PuzzlePanel.DIMENSION)*TILEHEIGHT;
        this.p1 = new Point(startX, startY);
        this.p2 = new Point(startX+TILEWIDTH, startY);
        this.p3 = new Point(startX+TILEWIDTH, startY+TILEHEIGHT);
        this.p4 = new Point(startX, startY+TILEHEIGHT);
    }
    /**
     * Checks if a Point is inside the Tile.
     * @param x Point to be Checked if it is inside the Tile.
     * @return ~true if the Point is inside the tile, ~false if the point is outside the tile.
     */
    public boolean contains(Point x){
        if(x.x<p1.x || x.x>p2.x)
            return false;
        else if(x.y<p1.y || x.y>p3.y)
            return false;
        return true;
    }
    /**
     * @param g2 2D Graphics of the JPanel containing the tiles. it will be used so the Tile can draw itself inside the JPanel.
     */
    public void draw(Graphics2D g2){//, Color background, Color stroke){
        if(value==-1){
            g2.setColor(Color.WHITE);
            g2.fillRect(p1.x, p1.y, TILEWIDTH, TILEHEIGHT);
            g2.setColor(Color.black);
            g2.drawRect(p1.x, p1.y, TILEWIDTH, TILEHEIGHT);
        }else{
            if(image!=null){
                g2.drawImage(image,p1.x+index.getColumn()+2, p1.y, TILEWIDTH, TILEHEIGHT, null);
                g2.drawRect(p1.x, p1.y, TILEWIDTH, TILEHEIGHT);
            }else{
                g2.setColor(Color.GRAY);
                g2.fillRect(p1.x, p1.y, TILEWIDTH, TILEHEIGHT);
                g2.setColor(Color.BLACK);
                g2.drawRect(p1.x, p1.y, TILEWIDTH, TILEHEIGHT);
                int size = (int) (Math.min(TILEHEIGHT, TILEWIDTH)*0.2);
                g2.setFont(new Font("TimesRoman", Font.BOLD, size));
                g2.drawString(Integer.toString(value), p1.x+(TILEWIDTH/2)-(size/4), p1.y+(TILEHEIGHT/2));
            }
        }
    }
}
