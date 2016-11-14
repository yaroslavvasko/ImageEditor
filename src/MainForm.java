import functionality.*;
import history.HistoryItem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by yaros on 23.10.2016.
 */
public class MainForm extends JFrame{
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private ImageForm image;
    private HistoryForm hForm;

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
        JMenu menuTransformations = new JMenu("Transformation");
        JMenu menuCombines = new JMenu("Combining");
        JMenu menuDifference = new JMenu("Difference");

        menuBar.add(menuFile);
        menuBar.add(menuFilters);
        menuBar.add(menuTransformations);
        menuBar.add(menuCombines);
        menuBar.add(menuDifference);

        JMenuItem menuItemNew = new JMenuItem(" New  ");
        menuFile.add(menuItemNew);

        hForm = new HistoryForm();
        hForm.setLocation(900,0);
        hForm.undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                image.setPicture(hForm.history.get(hForm.history.size()-2).getPicture());
                image.repaint();
            }
        });
        hForm.redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                image.setPicture(hForm.history.get(hForm.history.size()-1).getPicture());
                image.repaint();
            }
        });
        hForm.list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JList list = (JList)mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = list.locationToIndex(mouseEvent.getPoint());
                    image.setPicture(hForm.history.get(index).getPicture());
                    image.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

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
                    image.setLocation(200,0);
                    hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Picture Opened"));
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


        // ------------------------ F I L T E R I N G --------------------------------------------------------------

        JMenuItem menuItemGrayScale = new JMenuItem(" Gray scale");
        menuItemGrayScale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    Filters filters = new Filters();
                    filters.filterGray(image.picture);
                    hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Gray scale filtering"));
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemGrayScale);

        JMenuItem menuItemRedFilter = new JMenuItem(" Red filter");
        menuItemRedFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    Filters filters = new Filters();
                    filters.filterRed(image.picture);
                    hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Red filtering"));
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemRedFilter);

        JMenuItem menuItemGreenFilter = new JMenuItem(" Green filter");
        menuItemGreenFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    Filters filters = new Filters();
                    filters.filterGreen(image.picture);
                    hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Green filtering"));
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemGreenFilter);

        JMenuItem menuItemBlueFilter = new JMenuItem(" Blue filter");
        menuItemBlueFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    Filters filters = new Filters();
                    filters.filterBlue(image.picture);
                    hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Blue filtering"));
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemBlueFilter);

        JMenuItem menuItemNegativeFilter = new JMenuItem(" Negative filter");
        menuItemNegativeFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    Filters filters = new Filters();
                    filters.filterNegative(image.picture);
                    hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Negative filtering"));
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemNegativeFilter);

        JMenuItem menuItemSepiaFilter = new JMenuItem(" Sepia filter");
        menuItemSepiaFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    Filters filters = new Filters();
                    filters.filterSepia(image.picture);
                    hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Sepia filtering"));
                    image.repaint();
                }
            }
        });
        menuFilters.add(menuItemSepiaFilter);

        JMenuItem menuItemLUTFilter = new JMenuItem(" LUT filter");
        menuItemLUTFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    String[] values = {"1", "2", "3", "4", "5", "8", "16"};

                    Object selected = JOptionPane.showInputDialog(null, "What is LUT level", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
                    if ( selected!= null ){
                        String selectedString = selected.toString();

                        Filters filters = new Filters();
                        filters.filterGrayLUT(image.picture, Integer.parseInt(selectedString));
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Image shifted"));
                        image.repaint();
                    }else{
                        System.out.println("User cancelled");
                    }
                }
            }
        });
        menuFilters.add(menuItemLUTFilter);

        JMenuItem menuItemPersonalFilter = new JMenuItem(" Personal filter");
        menuItemPersonalFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){

                    JSlider sliderRed = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
                    sliderRed.setMajorTickSpacing(50);
                    sliderRed.setPaintTicks(true);
                    sliderRed.setPaintLabels(true);

                    JSlider sliderGreen = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
                    sliderGreen.setMajorTickSpacing(50);
                    sliderGreen.setPaintTicks(true);
                    sliderGreen.setPaintLabels(true);

                    JSlider sliderBlue = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
                    sliderBlue.setMajorTickSpacing(50);
                    sliderBlue.setPaintTicks(true);
                    sliderBlue.setPaintLabels(true);

                    JPanel myPanel = new JPanel();
                    myPanel.add(new JLabel("Red color"));
                    myPanel.add(sliderRed, "Red color");
                    myPanel.add(new JLabel("Green color"));
                    myPanel.add(sliderGreen, "Green color");
                    myPanel.add(new JLabel("Blue color"));
                    myPanel.add(sliderBlue, "Blue color");
                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        Filters filters = new Filters();
                        filters.filterPersonal(image.picture, sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue());
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Personal filtering"));
                        image.repaint();
                    }

                    ;
                }
            }
        });
        menuFilters.add(menuItemPersonalFilter);

        // ------------------------ T R A N S F O R M A T I O N -----------------------------------------------------

        JMenuItem menuItemRotate = new JMenuItem(" Rotate");
        menuItemRotate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    String[] values = {"30", "45", "60", "90", "120", "180"};

                    Object selected = JOptionPane.showInputDialog(null, "What is the angle of rotation", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
                    if ( selected != null ){
                        String selectedString = selected.toString();
                        Transformation transform = new Transformation();
                        transform.rotation(image.picture, Integer.parseInt(selectedString));
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Rotation"));
                        image.repaint();
                    }else{
                        System.out.println("User cancelled");
                    }
                }
            }
        });
        menuTransformations.add(menuItemRotate);

        JMenuItem menuItemShifting = new JMenuItem("Shift image");
        menuItemShifting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(image.isVisible()){
                    String[] values = {"10", "20", "30", "40", "50", "60", "70", "80", "90"};

                    Object selected = JOptionPane.showInputDialog(null, "What is the angle of rotation", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
                    if ( selected!= null ){
                        String selectedString = selected.toString();
                        Transformation transform = new Transformation();
                        transform.imageShifting(image.picture, Integer.parseInt(selectedString));
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Image shifted"));
                        image.repaint();
                    }else{
                        System.out.println("User cancelled");
                    }
                }
            }
        });
        menuTransformations.add(menuItemShifting);

        // ------------------------ C O M B I N A T I O N S ---------------------------------------------------------

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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImages(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture"));
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesMax(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined via max with " + chooser.getFile() + " picture"));
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesCycle(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined via Cycle with " + chooser.getFile() + " picture"));
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

        JMenuItem menuItemCombineImagesSaturation = new JMenuItem(" Combine Images with Saturation ");
        menuItemCombineImagesSaturation.addActionListener(new ActionListener() {
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesSaturation(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture with Saturation"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesSaturation);

        JMenuItem menuItemCombineImagesMultiply = new JMenuItem(" Combine Images Multiplying ");
        menuItemCombineImagesMultiply.addActionListener(new ActionListener() {
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesMultiply(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture Multiplying"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesMultiply);

        JMenuItem menuItemCombineImagesMinus = new JMenuItem(" Combine Images Minus  ");
        menuItemCombineImagesMinus.addActionListener(new ActionListener() {
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesMinus(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture Minus"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesMinus);

        JMenuItem menuItemCombineImagesMinusMax = new JMenuItem(" Combine Images Minus with Max  ");
        menuItemCombineImagesMinusMax.addActionListener(new ActionListener() {
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesMinusMax(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture Minus with Max"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesMinusMax);

        JMenuItem menuItemCombineImagesMinusCycle = new JMenuItem(" Combine Images Minus Cycle  ");
        menuItemCombineImagesMinusCycle.addActionListener(new ActionListener() {
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesMinusCycle(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture Minus Cycle"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesMinusCycle);


        JMenuItem menuItemCombineImagesRealMultiply = new JMenuItem(" Combine Images via Multiply   ");
        menuItemCombineImagesRealMultiply.addActionListener(new ActionListener() {
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesRealMultiply(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture Multyplying R"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesRealMultiply);

        JMenuItem menuItemCombineImagesSubstruct = new JMenuItem(" Combine Images via Substruct ");
        menuItemCombineImagesSubstruct.addActionListener(new ActionListener() {
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
                        Combinations combinations = new Combinations();
                        Picture picture2 = new Picture(chooser.getDirectory()+chooser.getFile());
                        combinations.combineImagesSubstruct(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Combined with " + chooser.getFile() + " picture Substructing"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuCombines.add(menuItemCombineImagesSubstruct);

        // ------------------------ D I F F E R E N C E ---------------------------------------------------------
        JMenuItem menuItemFindDifference = new JMenuItem(" Find differences  ");
        menuItemFindDifference.addActionListener(new ActionListener() {
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
                        Differences difference = new Differences();
                        difference.findDifference(image.picture, picture2);
                        hForm.addHistoryItem(new HistoryItem(new Picture(image.picture), "Finding differences with " + chooser.getFile() + " picture"));
                        image.repaint();
                    }
                }
                else
                {
                    System.out.println("No start image");
                }
            }
        });
        menuDifference.add(menuItemFindDifference);




        return menuBar;
    }


    public Picture getImage(){
        if (image != null)
            return image.picture;
        else
            return null;
    }

}
