package functionality;

import java.awt.*;

/**
 * Created by yaros on 31.10.2016.
 */
public class Combinations {

    public Color combineColors(Color first, Color second){
        int r = first.getRed();
        int g = first.getRed();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getGreen();
        return new Color((int)(r*0.3+r1*0.7), (int)(g*0.3+g1*0.7),(int)(b*0.3+b1*0.7));
    }

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

    public void combineImages(Picture picture, Picture picture2){

        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColors(c1, c2));
                }
            }
        }
    }

    public void combineImagesMax(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsMax(c1, c2));
                }
            }
        }
    }

    public void combineImagesCycle(Picture picture, Picture picture2){

        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsCycle(c1, c2));
                }
            }
        }
    }
}
