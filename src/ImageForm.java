import javax.swing.*;
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

}
