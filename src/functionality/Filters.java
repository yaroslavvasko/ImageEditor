package functionality;

import java.awt.*;

/**
 * Created by yaros on 30.10.2016.
 */
public class Filters {

    public void filterGray(Picture picture){

        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                Color c = picture.getColor(i, k);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int grayLevel = (r + g + b) / 3;
                picture.setColor(i, k, new Color(grayLevel, grayLevel, grayLevel));
            }}
    }

    public void filterRed(Picture picture){
        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                int r = picture.getColor(i, k).getRed();
                picture.setColor(i, k, new Color(r, 0, 0));
            }
        }
    }

    public void filterGreen(Picture picture){
        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                int g = picture.getColor(i, k).getGreen();
                picture.setColor(i, k, new Color(0, g, 0));
            }
        }
    }

    public void filterBlue(Picture picture){
        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                int b =  picture.getColor(i, k).getBlue();
                picture.setColor(i, k, new Color(0, 0, b));
            }
        }
    }

    public void filterNegative(Picture picture){
        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                Color c = picture.getColor(i, k);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                picture.setColor(i, k, new Color(255-r, 255-g, 255-b));
            }
        }
    }


    public void filterSepia(Picture picture){
        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                Color c = picture.getColor(i, k);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                picture.setColor(i, k, new Color(
                        Math.min(255,(int)((r*0.393)+(g*0.769)+(b*0.189))),
                        Math.min(255,(int)((r*0.349)+(g*0.686)+(b*0.168))),
                        Math.min(255,(int)((r*0.272)+(g*0.534)+(b*0.131))))
                );
            }
        }
    }

    public void filterGrayLUT(Picture picture, int blevel) {
        int delta = 2 ^ 8 / 2 ^ blevel;
        int[] lut = new int[256];
        for(int i = 0; i < 256; i++){
            lut[i] = (int) Math.floor(Math.max((i - delta/2 -1)/delta,0))*delta + (delta/2 - 1);
        }

        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                Color c = picture.getColor(i, k);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                picture.setColor(i, k, new Color(lut[r], lut[g], lut[b]));
            }
        }
    }

}
