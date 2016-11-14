package functionality;

import java.awt.*;

/**
 * Created by yaros on 31.10.2016.
 */
public class Combinations {

    public Color combineColors(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color((int)(r*0.3+r1*0.7), (int)(g*0.3+g1*0.7),(int)(b*0.3+b1*0.7));
    }

    public Color combineColorsMax(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color(Math.min(255, r+r1), Math.min(255, g+g1), Math.min(255, b+b1));
    }

    // Image(x,y) = ( Image1(x,y) + Image2(x,y) ) % 256
    public Color combineColorsCycle(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color((r+r1)%256, (g+g1)%256, (b+b1)%256);
    }

    // Image(x,y) = ( Image1(x,y) + (1-W1)Image2(x,y) )
    public Color combineColorsSaturation(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color(Math.min(255,(int)(r+(1-0.4)*r1)), Math.min(255,(int)(g+(1-0.4)*g1)),Math.min(255,(int)(b+(1-0.4)*b1)));
    }

    public Color combineColorsMultiply(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color(Math.min(255,(r+r1)), Math.min(255,(g+g1)),Math.min(255,(b+b1)));
    }

    // Image(x,y) = ( W1*Image1(x,y) - (1-W1)Image2(x,y) )
    public Color combineColorsMinus(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color(Math.max(0,(int)(r*0.3-r1*0.7)), Math.max(0,(int)(g*0.3-g1*0.7)),Math.max(0,(int)(b*0.3-b1*0.7)));
    }

    // Image(x,y) = max( 0, Image1(x,y) - Image2(x,y) )
    public Color combineColorsMinusMax(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color(Math.max(0, r-r1), Math.max(0, g-g1), Math.max(0, b-b1));
    }

    //Image(x,y) = ( Image1(x,y) - Image2(x,y) + 256 ) % 256
    public Color combineColorsMinusCycle(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();
        return new Color((r-r1+256)%256, (g-g1+256)%256, (b-b1+256)%256);
    }

    //Image(x,y) = Image1(x,y) * (Image2(x,y)/255)
    public Color combineColorsRealMultiply(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed()/255;
        int g1 = second.getGreen()/255;
        int b1 = second.getBlue()/255;

            return new Color(r*r1,g*g1,b*b1);
    }

    public Color combineColorsSubstruct(Color first, Color second){
        int r = first.getRed();
        int g = first.getGreen();
        int b = first.getBlue();
        int r1 = second.getRed();
        int g1 = second.getGreen();
        int b1 = second.getBlue();

        if(r1!=0) r=r/r1;
        if(g1!=0) g=g/g1;
        if(b1!=0) b =b/b1;

        return new Color(r,g,b);

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

    public void combineImagesSaturation(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsSaturation(c1, c2));
                }
            }
        }
    }

    public void combineImagesMultiply(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsMultiply(c1, c2));
                }
            }
        }
    }

    public void combineImagesMinus(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsMinus(c1, c2));
                }
            }
        }
    }

    public void combineImagesMinusMax(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsMinusMax(c1, c2));
                }
            }
        }
    }

    public void combineImagesMinusCycle(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsMinusCycle(c1, c2));
                }
            }
        }
    }

    public void combineImagesRealMultiply(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsRealMultiply(c1, c2));
                }
            }
        }
    }

    public void combineImagesSubstruct(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    Color c1 = picture.getColor(i, k);
                    Color c2 = picture2.getColor(i,k);
                    picture.setColor(i,k,combineColorsSubstruct(c1, c2));
                }
            }
        }
    }

}
