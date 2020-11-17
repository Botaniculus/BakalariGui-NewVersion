package cz.matejprerovsky.bakalarigui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Matěj Přerovský
 */
public class Window extends JFrame {
    private String[] dataArray;

    private JButton loginBtn;
    private JTextField urlField, username;
    private JPasswordField password;
    private JCheckBox saveCheckBox;

    public JTextField getUrlField() { return urlField; }
    public JTextField getUsername() { return username; }
    public JPasswordField getPassword() { return password; }
    public JButton getLoginBtn() { return loginBtn; }

    public JCheckBox getSaveCheckBox() { return saveCheckBox; }

    Window(String additionalTitle) {
        this.setTitle("Bakaláři" + " – " + additionalTitle);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("baky.png"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    //-----CSV-------------------------------------
    /*public void loadCsv(){
        dataArray = new String[2];
        try (BufferedReader br = new BufferedReader(new FileReader("addressAndUsername.txt"))) {
            String s;
            while ((s = br.readLine()) != null) {
                dataArray = s.split(";");
            }
        }
        catch(IOException e){
            dataArray[0]="https://";
            dataArray[1]="username";
        }
    }///
    public void saveCsv(String url, String username){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("addressAndUsername.txt"));
            String[] values = {url, username};
            String line = String.join(";", values);
            bw.append(line);
            bw.append("\n");
            bw.flush();
        }
        catch (IOException e){
            this.throwMessage("Nepodařilo se uložit přihlašovací jméno a URL.", "Chyba při ukládání", JOptionPane.WARNING_MESSAGE);
        }
    }
    //--------------------------------------
    public void login() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        loadCsv();
        urlField = new JTextField(dataArray[0], 30);
        g.gridx=0; g.gridy=0; g.gridwidth=3; g.fill = GridBagConstraints.HORIZONTAL;
        pane.add(urlField, g);
        username = new JTextField(dataArray[1], 12);
        g.gridx=0; g.gridy=1; g.gridwidth=1;
        pane.add(username, g);
        password = new JPasswordField("Password", 12);
        g.gridx=1; g.gridy=1; g.gridwidth=1;
        pane.add(password, g);
        loginBtn = new JButton("Login");
        g.gridx=2; g.gridy=1; g.gridwidth=1;
        pane.add(loginBtn, g);
        loginBtn.addActionListener(new Main());
        saveCheckBox = new JCheckBox("Uložit jméno a url", false);
        g.gridx=0;
        g.gridy=2;
        g.gridwidth=1;
        pane.add(saveCheckBox, g);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /*
    public void userInfo(Bakal bakal){
        JTabbedPane tabbedPane = new JTabbedPane();

        String[] hoursString=bakal.hours(date()[0], date()[1], date()[2]);
        JTable timetableTable = new JTable(bakal.getTimetable(date()[0], date()[1], date()[2]), hoursString);
        timetableTable.setCellSelectionEnabled(false);
        //timetableTable.setEnabled(false);
        timetableTable.setSelectionBackground(Color.BLUE);
        timetableTable.setRowHeight(50);
        JTableUtilities.setCellsAlignment(timetableTable, SwingConstants.CENTER);
        timetableTable.setFont(new Font("Serif", Font.BOLD, 11));
        JScrollPane scrollTable = new JScrollPane (timetableTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.addTab("Rozvrh hodin", scrollTable);

        String marksString = bakal.getMarks();
        JTextArea textArea = new JTextArea(marksString);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.addTab("Známky", scroll);

        add(tabbedPane);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void throwMessage(String title, String error, int type){
        JOptionPane.showMessageDialog(this, error, title, type);
    }

    public int[] date(){
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
    }*/

}
