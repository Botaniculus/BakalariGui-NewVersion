package cz.matejprerovsky.bakalarigui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoWindow extends JFrame{
    final private String loginTitle = "Info";
    final private Timetable timetable;

    public InfoWindow(String token, String baseURL){
        this.setTitle("Bakaláři" + " – " + loginTitle);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("baky.png"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        timetable = new Timetable(baseURL, token, date()[2], date()[1], date()[0]);
    }
    public void userInfo(){
        JTabbedPane tabbedPane = new JTabbedPane();

        String[] hoursString=timetable.hours();
        JTable timetableTable = new JTable(timetable.getTimetable(), hoursString);
        timetableTable.setCellSelectionEnabled(false);
        //timetableTable.setEnabled(false);
        timetableTable.setSelectionBackground(Color.BLUE);
        timetableTable.setRowHeight(50);
        JTableUtilities.setCellsAlignment(timetableTable, SwingConstants.CENTER);
        timetableTable.setFont(new Font("Serif", Font.BOLD, 11));
        JScrollPane scrollTable = new JScrollPane (timetableTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.addTab("Rozvrh hodin", scrollTable);
/*
        String marksString = bakal.getMarks();
        JTextArea textArea = new JTextArea(marksString);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.addTab("Známky", scroll);
*/
        add(tabbedPane);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private int[] date(){
        int[] output = new int[3];
        Date date = new Date();
        SimpleDateFormat formatter;
        //day
        formatter = new SimpleDateFormat("dd");
        output[0] = Integer.parseInt(formatter.format(date));

        //month
        formatter = new SimpleDateFormat("MM");
        output[1] = Integer.parseInt(formatter.format(date));

        //year
        formatter = new SimpleDateFormat("yyyy");
        output[2] = Integer.parseInt(formatter.format(date));

        return output;
    }
}
