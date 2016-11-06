package functionality;

import java.awt.*;

/**
 * Created by Yaroslav Vasko on 06.11.2016.
 */
public class Transformation {

    public void rotation(Picture picture, int angle){
        double x0 = 0.5 * (picture.getWidth() - 1);
        double y0 = 0.5 * (picture.getHeight() - 1);

        Picture result = new Picture(picture.getWidth(), picture.getHeight());
        for (int x = 0; x < picture.getWidth(); x++) {
            for (int y = 0; y < picture.getHeight(); y++) {
                double a = x - x0;
                double b = y - y0;
                int xx = (int) (+a * Math.cos(Math.toRadians(-angle)) - b * Math.sin(Math.toRadians(-angle)) + x0);
                int yy = (int) (+a * Math.sin(Math.toRadians(-angle)) + b * Math.cos(Math.toRadians(-angle)) + y0);

                if (xx >= 0 && xx < picture.getWidth() && yy >= 0 && yy < picture.getHeight()) {
                    result.setColor(x, y, picture.getColor(xx, yy));
                }
            }
        }

        for (int x = 0; x < picture.getWidth(); x++) {
            for (int y = 0; y < picture.getHeight(); y++) {
                picture.setColor(x,y,result.getColor(x,y));
            }
        }
    }
}
