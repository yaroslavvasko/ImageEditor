import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by yaros on 23.10.2016.
 */
public class MainForm extends JFrame{
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private ImageForm image;

    public MainForm(String title) {
        super(title);
        setContentPane(mainPanel);
        setJMenuBar(createMenu());
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JMenuBar createMenu()
    {
        menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuFilters = new JMenu("Filters");
        JMenu menuCombines = new JMenu("Combining");

        menuBar.add(menuFile);
        menuBar.add(menuFilters);
        menuBar.add(menuCombines);

        JMenuItem menuItemNew = new JMenuItem(" New  ");
        menuFile.add(menuItemNew);

        JMenuItem menuItemOpen = new JMenuItem(" Open  ");
        menuItemOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                FileDialog chooser = new FileDialog(new Frame(), "Choose image", FileDialog.LOAD);
                chooser.setDirectory("C:\\");
                chooser.setVisible(true);
                String filename = chooser.getFile();
                if (filename == null)
                    System.out.println("You cancelled the choice");
                else
                {
                    System.out.println("You chose " + chooser.getFile());
                    image = new ImageForm(chooser.getDirectory()+chooser.getFile());
                }
            }
        });
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuFile.add(menuItemOpen);

        JMenuItem menuItemSave = new JMenuItem(" Save   ");
        menuItemSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileDialog chooser = new FileDialog(new Frame(),
                        "Use a .png or .jpg extension", FileDialog.SAVE);
                chooser.setVisible(true);
                if (chooser.getFile() != null) {
                    if(image.isVisible())
                        image.picture.save(chooser.getDirectory() + File.separator + chooser.getFile());
                }
            }
        });
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuFile.add(menuItemSave);


        JMenuItem menuItemGrayScale = new JMenuItem(" Gray scale");
        menuItemGrayScale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    image.picture.filterGray();
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemGrayScale);

        JMenuItem menuItemRedFilter = new JMenuItem(" Red filter");
        menuItemRedFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    image.picture.filterRed();
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemRedFilter);

        JMenuItem menuItemGreenFilter = new JMenuItem(" Green filter");
        menuItemGreenFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    image.picture.filterGreen();
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemGreenFilter);

        JMenuItem menuItemBlueFilter = new JMenuItem(" Blue filter");
        menuItemBlueFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    image.picture.filterBlue();
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemBlueFilter);


        JMenuItem menuItemCombineImages = new JMenuItem(" Combine Images ");
        menuItemCombineImages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    FileDialog chooser = new FileDialog(new Frame(), "Choose image", FileDialog.LOAD);
                    chooser.setDirectory("C:\\");
                    chooser.setVisible(true);
                    String filename = chooser.getFile();
                    if (filename == null)
                        System.out.println("You cancelled the choice");
                    else
                    {
                        System.out.println("You chose " + chooser.getFile());
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        image.picture.combineImages(picture2);
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImages);

        JMenuItem menuItemCombineImagesMax = new JMenuItem(" Combine Images Max");
        menuItemCombineImagesMax.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    FileDialog chooser = new FileDialog(new Frame(), "Choose image", FileDialog.LOAD);
                    chooser.setDirectory("C:\\");
                    chooser.setVisible(true);
                    String filename = chooser.getFile();
                    if (filename == null)
                        System.out.println("You cancelled the choice");
                    else
                    {
                        System.out.println("You chose " + chooser.getFile());
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        image.picture.combineImagesMax(picture2);
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesMax);

        JMenuItem menuItemCombineImagesCycle = new JMenuItem(" Combine Images Cycle");
        menuItemCombineImagesCycle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    FileDialog chooser = new FileDialog(new Frame(), "Choose image", FileDialog.LOAD);
                    chooser.setDirectory("C:\\");
                    chooser.setVisible(true);
                    String filename = chooser.getFile();
                    if (filename == null)
                        System.out.println("You cancelled the choice");
                    else
                    {
                        System.out.println("You chose " + chooser.getFile());
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        image.picture.combineImagesCycle(picture2);
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesCycle);


        return menuBar;
    }


}
