package functionality;

import java.awt.*;

/**
 * Created by Yaroslav Vasko on 12.11.2016.
 */
public class Differences {

    public void findDifference(Picture picture, Picture picture2){
        if(picture.getWidth()==picture2.getWidth() && picture.getHeight()==picture2.getHeight())
        {
            for(int i = 0; i<picture.getWidth(); i++){
                for(int k = 0; k<picture.getHeight(); k++) {
                    if(!compareColors(picture.getColor(i,k),picture2.getColor(i,k))){
                        picture.setColor(i,k, new Color(picture.getColor(i,k).getRed(), 0,0));
                    }
                }
            }
        }
    }

    private boolean compareColors(Color c, Color c1){
        if (c.getRed() == c1.getRed() && c.getGreen() == c.getGreen() && c.getBlue()==c1.getBlue())
            return true;
        else
            return false;
    }

}
