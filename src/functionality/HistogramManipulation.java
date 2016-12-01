package functionality;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Yaroslav Vasko on 28.11.2016.
 */
public class HistogramManipulation {

    private int[] lutRed;
    private int[] lutGreen;
    private int[] lutBlue;
    private double[] disRed;
    private double[] disGreen;
    private double[] disBlue;

    public void createLUTStretching(Picture p){
        lutRed = new int[256];
        lutGreen = new int[256];
        lutBlue = new int[256];
        int redMin = 255;
        int greenMin = 255;
        int blueMin = 255;
        int redMax = 0;
        int greenMax = 0;
        int blueMax = 0;

        for (int x = 0; x < p.getWidth(); x++){
            for (int y = 0; y < p.getHeight(); y++){
                if(p.getColor(x,y).getRed() > redMax) redMax = p.getColor(x,y).getRed();
                if(p.getColor(x,y).getGreen() > greenMax) greenMax = p.getColor(x,y).getGreen();
                if(p.getColor(x,y).getBlue() > blueMax) blueMax = p.getColor(x,y).getBlue();
                if(p.getColor(x,y).getRed() < redMin) redMin = p.getColor(x,y).getRed();
                if(p.getColor(x,y).getGreen() < greenMin) greenMin = p.getColor(x,y).getGreen();
                if(p.getColor(x,y).getBlue() < blueMin) blueMin = p.getColor(x,y).getBlue();
            }
        }

        for(int i = 0; i < 256; i++){
            lutRed[i] = (255/(redMax - redMin))*(i-redMin);
            lutGreen[i] = (255/(greenMax - greenMin))*(i-greenMin);
            lutBlue[i] = (255/(blueMax - redMin))*(i-blueMin);

        }

    }


    public void createAlignmentLUT(Picture p){
        lutRed = new int[256];
        lutGreen = new int[256];
        lutBlue = new int[256];
        double sumr = 0, sumg = 0, sumb = 0;
        double scale_factor = (double) (255.0 / (p.getWidth() * p.getHeight()));
        int[] stat = p.getColorStatistics();

        for(int i=0; i<256; i++) {
            sumr += stat[i];
            lutRed[i] = Math.min(255, (int)(sumr * scale_factor));

            sumg += stat[256+i];
            lutGreen[i] = Math.min(255, (int)(sumg * scale_factor));

            sumb += stat[512+i];
            lutBlue[i] = Math.min(255, (int)(sumb * scale_factor));
        }
    }

    public void histogramAlignment(Picture picture){
        createAlignmentLUT(picture);
        for (int x = 0; x < picture.getWidth(); x++) {
            for (int y = 0; y < picture.getHeight(); y++) {
                picture.setColor(x, y, new Color(
                        lutRed[picture.getColor(x,y).getRed()],
                        lutGreen[picture.getColor(x,y).getGreen()],
                        lutBlue[picture.getColor(x,y).getBlue()]));
            }
        }
    }

    public void histogramStretching(Picture picture){
        createLUTStretching(picture);
        for (int x = 0; x < picture.getWidth(); x++) {
            for (int y = 0; y < picture.getHeight(); y++) {
                picture.setColor(x, y, new Color(
                        lutRed[picture.getColor(x,y).getRed()],
                        lutGreen[picture.getColor(x,y).getGreen()],
                        lutBlue[picture.getColor(x,y).getBlue()]));
            }
        }
    }

}
