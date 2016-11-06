import functionality.Picture;
import history.HistoryItem;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Yaroslav Vasko on 31.10.2016.
 */
public class HistoryForm extends JFrame{
    private JScrollPane scrollPane;
    public JList list;
    private JPanel historyPanel;
    public java.util.List<HistoryItem> history;
    public JButton undoButton;
    public JButton redoButton;
    private DefaultListModel listModel;

    public HistoryForm() {
        super("History");

        setSize(230, 300);
        setLayout(new FlowLayout());

        this.history  = new ArrayList<HistoryItem>();

        undoButton = new JButton("Undo", new ImageIcon("Icons/left-arrow.png"));
        redoButton = new JButton("Redo", new ImageIcon("Icons/right-arrow.png"));
        undoButton.setPreferredSize(new Dimension(100,20));
        redoButton.setPreferredSize(new Dimension(100,20));




        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setLayoutOrientation(JList.VERTICAL);
        scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(230, 220));
        add(scrollPane);
        add(undoButton);
        add(redoButton);
        setResizable(false);
        setVisible(true);
    }

    public void addHistoryItem(HistoryItem h){
        System.out.println(history.size());
        if(history.size() >= 10){
            history.remove(0);
            listModel.remove(0);
            history.add(h);
        }
        else {
            history.add(h);
            listModel.addElement(h.getTitle());
        }
    }

    public HistoryItem undoAction(){
        return history.get(0);
    }


    public void printHistory(){
        for (HistoryItem h: history){
            System.out.println(h.toString());
        }
    }
}
