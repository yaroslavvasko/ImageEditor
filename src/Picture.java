import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.function.DoubleToIntFunction;

/**
 * Created by yaros on 23.10.2016.
 */
public class Picture {

    private BufferedImage image;
    private String filename;
    private boolean isOriginUpperLeft = true;
    private final int width, height;

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

    public void filterGray(){
        for(int i = 0; i<getWidth(); i++){
            for(int k = 0; k<getHeight(); k++){
                Color c = getColor(i, k);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int grayLevel = (r + g + b) / 3;
                setColor(i, k, new Color(grayLevel, grayLevel, grayLevel));
            }}
    }

    public void filterRed(){
        for(int i = 0; i<getWidth(); i++){
            for(int k = 0; k<getHeight(); k++){
                int r = getColor(i, k).getRed();
                setColor(i, k, new Color(r, 0, 0));
            }
        }
    };

    public void filterGreen(){
        for(int i = 0; i<getWidth(); i++){
            for(int k = 0; k<getHeight(); k++){
                int g = getColor(i, k).getGreen();
                setColor(i, k, new Color(0, g, 0));

            }
        }
    };


    public void filterBlue(){
        for(int i = 0; i<getWidth(); i++){
            for(int k = 0; k<getHeight(); k++){
                int b =  getColor(i, k).getBlue();
                setColor(i, k, new Color(0, 0, b));
            }
        }
    };

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

    public Color combineColors(Color first, Color second){
        int r = first.getRed();
        int g = first.getRed();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getGreen();
        return new Color((int)(r*0.3+r1*0.7), (int)(g*0.3+g1*0.7),(int)(b*0.3+b1*0.7));
    }


    // Image(x,y) = min( maks, Image1(x,y) + Image2(x,y) )

    public Color combineColorsMax(Color first, Color second){
        int r = first.getRed();
        int g = first.getRed();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getGreen();
        return new Color(Math.min(255, r+r1), Math.min(255, g+g1), Math.min(255, b+b1));
    }

    // Image(x,y) = ( Image1(x,y) + Image2(x,y) ) % 256
    public Color combineColorsCycle(Color first, Color second){
        int r = first.getRed();
        int g = first.getRed();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getGreen();
        return new Color((r+r1)%256, (g+g1)%256, (b+b1)%256);
        //return new Color(255 - (r+r1)%256, 255 - (g+g1)%256, 255 - (b+b1)%256);
    }

    // delta=2B/2newB

   // L′(x,y)=floor(max((L(x,y)−(delta2)−1)delta,0))*delta+(delta2−1)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void combineImages(Picture picture){

        if(getWidth()==picture.getWidth() && getHeight()==picture.getHeight())
        {
            for(int i = 0; i<getWidth(); i++){
                for(int k = 0; k<getHeight(); k++) {
                    Color c1 = getColor(i, k);
                    Color c2 = picture.getColor(i,k);
                    setColor(i,k,combineColors(c1, c2));
                }
            }
        }
    }

    public void combineImagesMax(Picture picture){

        if(getWidth()==picture.getWidth() && getHeight()==picture.getHeight())
        {
            for(int i = 0; i<getWidth(); i++){
                for(int k = 0; k<getHeight(); k++) {
                    Color c1 = getColor(i, k);
                    Color c2 = picture.getColor(i,k);
                    setColor(i,k,combineColorsMax(c1, c2));
                }
            }
        }
    }

    public void combineImagesCycle(Picture picture){

        if(getWidth()==picture.getWidth() && getHeight()==picture.getHeight())
        {
            for(int i = 0; i<getWidth(); i++){
                for(int k = 0; k<getHeight(); k++) {
                    Color c1 = getColor(i, k);
                    Color c2 = picture.getColor(i,k);
                    setColor(i,k,combineColorsCycle(c1, c2));
                }
            }
        }
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

    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because pictures are mutable");
    }
}
