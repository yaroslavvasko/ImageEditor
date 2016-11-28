package functionality;

import java.awt.*;

/**
 * Created by Yaroslav Vasko on 28.11.2016.
 */
public class YCbCr {

    private int Y;
    private int Cb;
    private int Cr;

    public YCbCr(int y, int cb, int cr){
        setY(y);
        setCb(cb);
        setCr(cr);
    }

    public YCbCr(Color c){
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();

        int Y = Math.min(255,(int)(0.299*r+0.587*g+0.114*b));
        int Cb= Math.min(255,(int)(128-0.169*r-0.331*g+0.500*b));
        int Cr = Math.min(255,(int)(128+0.500*r-0.419*g-0.081*b));

        setY(Y);
        setCb(Cb);
        setCr(Cr);

    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getCb() {
        return Cb;
    }

    public void setCb(int cb) {
        Cb = cb;
    }

    public int getCr() {
        return Cr;
    }

    public void setCr(int cr) {
        Cr = cr;
    }

    public Color getRGB(){
        int r = Math.min(255,(Y + (Cb - 128) + (Cr - 128)));
        int g = Math.min(255,(int)(Y - (0.344*(Cb - 128)) - (0.714*(Cr - 128))));
        int b = Math.min(255, (int)(Y + (1.773*(Cb - 128))));
        return new Color(r,g,b);
    }

    public void brighten(int a){
        this.Y = Math.min(256,this.Y += a);
    }

}
