package sample;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ImageDivider {
    /**
     * Variables Declaration.
     */
    private File file;
    private int dimension;
    private ArrayList<BufferedImage> images;
    /**
     * Create a new ImageDivider.
     * @param image Image File to be divided into smaller images forming the puzzle tiles.
     * @param dimension Integer represent puzzle Dimension.
     * @throws IOException ~if Image File cannot be opened or not exist.
     */
    public ImageDivider(File image, int dimension) throws IOException{
        this.file = image;
        this.dimension = dimension;
        images = new ArrayList<>();
        split();
    }
    /**
     * Divide the Image File into smaller images.
     * @throws FileNotFoundException ~if Image File is not found.
     * @throws IOException  ~if Image File cannot be opened.
     */
    private void split() throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis); //reading the image file
        int chunks = dimension*dimension;

        int chunkWidth = image.getWidth() / dimension; // determines the chunk width and height
        int chunkHeight = image.getHeight() / dimension;
        int count = 0;
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                //Initialize the image array with image chunks
                BufferedImage temp = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // draws the image chunk
                Graphics2D gr = temp.createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
                images.add(temp);
            }
        }

    }
    /**
     * @return ArrayList Containing smaller chunks of the Image.
     */
    public ArrayList<BufferedImage> Images(){
        return images;
    }
}
