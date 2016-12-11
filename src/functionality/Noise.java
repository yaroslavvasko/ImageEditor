package functionality;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Yaroslav Vasko on 10.12.2016.
 */
public class Noise {

    public void addUniformNoise(Picture picture, int min, int max, int probability){
        Random random = new Random();
        int noise = 0;

        for(int x = 0; x < picture.getWidth(); x++){
            for(int y = 0; y < picture.getHeight(); y++){
                noise = random.nextInt((max - min) + 1) + min;
                Color c = picture.getColor(x, y);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                if(random.nextInt(100) < probability){
                    r = Math.max(0,Math.min(255, r + noise));
                    g = Math.max(0,Math.min(255, g + noise));
                    b = Math.max(0,Math.min(255, b + noise));
                    picture.setColor(x, y, new Color(r, g, b));
                }
            }
        }
    }

    public void addGaussianNoise(Picture picture, int average, int deviation, int probability){
        Random random = new Random();
        int noiseR, noiseG, noiseB;

        for(int x = 0; x < picture.getWidth(); x++){
            for(int y = 0; y < picture.getHeight(); y++){
                Color c = picture.getColor(x, y);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                if(random.nextInt(100) < probability){
                    /*noiseR = (int) ((1/deviation*Math.sqrt(2*Math.PI))*Math.exp(-Math.pow(random.nextInt(255)-average,2)/(2*Math.pow(deviation,2))));
                    noiseG = (int) ((1/deviation*Math.sqrt(2*Math.PI))*Math.exp(-Math.pow(random.nextInt(255)-average,2)/(2*Math.pow(deviation,2))));
                    noiseB = (int) ((1/deviation*Math.sqrt(2*Math.PI))*Math.exp(-Math.pow(random.nextInt(255)-average,2)/(2*Math.pow(deviation,2))));*/
                    int noise = (int) random.nextGaussian()*deviation + average;
                    r = (r+noise)%255; if(r<0) r = -r;
                    g = (g+noise)%255; if(g<0) g = -g;
                    b = (b+noise)%255; if(b<0) b = -b;
                    picture.setColor(x, y, new Color(r,g,b));
                }
            }
        }
    }

    public void addSaltPepper(Picture picture, int probability){
        Random random = new Random();
        for(int x = 0; x < picture.getWidth(); x++){
            for(int y = 0; y < picture.getHeight(); y++){
                Color c = picture.getColor(x, y);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                if(random.nextInt(100) < probability){
                    int noise = (int) random.nextGaussian()*100;
                    r = Math.max(0,Math.min(255, r+noise ));
                    g = Math.max(0,Math.min(255, g+noise ));
                    b = Math.max(0,Math.min(255, b+noise ));
                    picture.setColor(x, y, new Color(r,g,b));
                }
            }
        }
    }

    public void addAvgFilter(Picture picture){
        for(int x = 1; x < picture.getWidth()-1; x++) {
            for (int y = 1; y < picture.getHeight()-1; y++) {
                int r = 0,g = 0,b = 0;
                for(int i = -1; i < 2; i++){
                    for (int k = -1; k < 2; k++) {
                        r += picture.getColor(x + i, y + k).getRed();
                        g += picture.getColor(x + i, y + k).getGreen();
                        b += picture.getColor(x + i, y + k).getBlue();
                    }
                }
                picture.setColor(x, y, new Color(r/9,g/9,b/9));
            }
        }
    }

    public void addMedianFilter(Picture picture){
        for(int x = 1; x < picture.getWidth()-1; x++) {
            for (int y = 1; y < picture.getHeight()-1; y++) {
                int[] r = new int[9],g = new int[9],b = new int[9];
                int tmp = 0;
                for(int i = -1; i < 2; i++){
                    for (int k = -1; k < 2; k++) {
                        r[tmp] = picture.getColor(x + i, y + k).getRed();
                        g[tmp] = picture.getColor(x + i, y + k).getGreen();
                        b[tmp]= picture.getColor(x + i, y + k).getBlue();
                        tmp++;
                    }
                }
                Arrays.sort(r);
                Arrays.sort(g);
                Arrays.sort(b);
                picture.setColor(x, y, new Color(r[r.length/2],g[g.length/2],b[b.length/2]));
            }
        }
    }

}
