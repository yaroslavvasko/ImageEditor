import functionality.Picture;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by yaros on 23.10.2016.
 */
public class ImageForm extends JFrame{
    private JLabel imageLabel;
    public Picture picture;


    public ImageForm(String fileName) {
        super(fileName);

        picture = new Picture(fileName);
        setContentPane(picture.getImageLabel());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(fileName);
        setResizable(false);
        pack();
        setVisible(true);
    }


    public ImageForm(File file) {
        super(file.getName());

        picture = new Picture(file);
        setContentPane(picture.getImageLabel());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(file.getName());
        setResizable(false);
        pack();
        setVisible(true);
    }

    public ImageForm(Picture picture) {
        super(picture.getFilename());

        picture = new Picture(picture);
        setContentPane(picture.getImageLabel());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }


    public void setPicture(Picture p){

        for(int i = 0; i<picture.getWidth(); i++){
            for(int k = 0; k<picture.getHeight(); k++){
                picture.setColor(i, k, p.getColor(i,k));
            }
        }
    }

}
