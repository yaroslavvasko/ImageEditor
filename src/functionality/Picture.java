package functionality;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by yaros on 23.10.2016.
 */
public class Picture {

    private BufferedImage image;
    private String filename;
    private boolean isOriginUpperLeft = true;
    private final int width, height;
    private int[] colorStatistics;
    private YCbCr[][] imageYCBCR;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isOriginUpperLeft() {
        return isOriginUpperLeft;
    }

    public void setOriginUpperLeft(boolean originUpperLeft) {
        isOriginUpperLeft = originUpperLeft;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }



    public Picture(int width, int height) {
        if (width  < 0) throw new IllegalArgumentException("width must be nonnegative");
        if (height < 0) throw new IllegalArgumentException("height must be nonnegative");
        this.width  = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
        filename = width + "-by-" + height;
    }

    public Picture(Picture picture) {
        width  = picture.getWidth();
        height = picture.getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        filename = picture.filename;
        for (int col = 0; col < getWidth(); col++)
            for (int row = 0; row < getHeight(); row++)
                image.setRGB(col, row, picture.getColor(col, row).getRGB());
    }

    public Picture(String filename) {
        this.filename = filename;
        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }

            // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) {
                    url = new URL(filename);
                }
                image = ImageIO.read(url);
            }

            if (image == null) {
                throw new IllegalArgumentException("Invalid image file: " + filename);
            }

            width  = image.getWidth(null);
            height = image.getHeight(null);
        }
        catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }
    }

    public Picture(File file) {
        try {
            image = ImageIO.read(file);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not open file: " + file);
        }
        if (image == null) {
            throw new RuntimeException("Invalid image file: " + file);
        }
        width  = image.getWidth(null);
        height = image.getHeight(null);
        filename = file.getName();
    }


    public Color getColor(int col, int row) {
        validateCol(col);
        validateRow(row);
        if (isOriginUpperLeft) return new Color(image.getRGB(col, row));
        else                   return new Color(image.getRGB(col, height - row - 1));
    }

    public void setColor(int col, int row, Color color) {
        validateCol(col);
        validateRow(row);
        if (color == null) throw new NullPointerException("can't set Color to null");
        if (isOriginUpperLeft) image.setRGB(col, row, color.getRGB());
        else                   image.setRGB(col, height - row - 1, color.getRGB());
    }


    private void validateRow(int row) {
        if (row < 0 || row >= getHeight())
            throw new IndexOutOfBoundsException("row must be between 0 and " + (getHeight() - 1) + ": " + row);
    }

    private void validateCol(int col) {
        if (col < 0 || col >= getWidth())
            throw new IndexOutOfBoundsException("col must be between 0 and " + (getWidth() - 1) + ": " + col);
    }

    public JLabel getImageLabel() {
        if (image == null) return null;         // no image available
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

    public void save(String name) {
        save(new File(name));
    }

    public void save(File file) {
        filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png")) {
            try {
                ImageIO.write(image, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Picture that = (Picture) other;
        if (this.getWidth()  != that.getWidth())  return false;
        if (this.getHeight() != that.getHeight()) return false;
        for (int col = 0; col < getWidth(); col++)
            for (int row = 0; row < getHeight(); row++)
                if (!this.getColor(col, row).equals(that.getColor(col, row))) return false;
        return true;
    }

    private void createColorsStatistics(){
        colorStatistics = new int[768];
        for(int k = 0; k < 256; k++){
            for(int x = 0; x<width; x++) {
                for (int y = 0; y < height; y++) {
                    if(this.getColor(x,y).getRed() == k) colorStatistics[k]+=1;
                    if(this.getColor(x,y).getGreen() == k) colorStatistics[256+k]+=1;
                    if(this.getColor(x,y).getBlue() == k) colorStatistics[512+k]+=1;
                }
            }
        }
    }




    public int[] getColorStatistics(){
        createColorsStatistics();
        return this.colorStatistics;
    }

    public void toYCBCR(){
        imageYCBCR = new YCbCr[width][height];
            for(int x = 0; x<width; x++) {
                for (int y = 0; y < height; y++) {
                    imageYCBCR[x][y] = new YCbCr(this.getColor(x,y));
                }
            }
    }

    public void luminanceYCbCr(int a){
        for(int x = 0; x<width; x++) {
            for (int y = 0; y < height; y++) {
                imageYCBCR[x][y].brighten(a);
                this.setColor(x,y, imageYCBCR[x][y].getRGB());
            }
        }
    }

    public void setColorStatistics(int[] statsctics){
        this.colorStatistics = statsctics;
    }


    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because pictures are mutable");
    }
}
