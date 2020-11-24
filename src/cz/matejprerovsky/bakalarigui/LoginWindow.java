package cz.matejprerovsky.bakalarigui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginWindow extends JFrame{
    private String token;
    private final String filename = "addressAndUsername.txt";

    private JTextField urlField, username;
    private JPasswordField password;
    private JCheckBox saveCheckBox;


    public LoginWindow(){
        String additionalTitle = "Přihlášení";
        this.setTitle("Bakaláři" + " – " + additionalTitle);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("baky.png"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void loginWind() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);

        String[] addressAndUsernameArray = loadCsv();
        urlField = new JTextField(addressAndUsernameArray[0], 30);
        g.gridx=0; g.gridy=0; g.gridwidth=3; g.fill = GridBagConstraints.HORIZONTAL;
        pane.add(urlField, g);
        username = new JTextField(addressAndUsernameArray[1], 12);
        g.gridx=0; g.gridy=1; g.gridwidth=1;
        pane.add(username, g);
        password = new JPasswordField("Password", 12);
        g.gridx=1; g.gridy=1; g.gridwidth=1;
        pane.add(password, g);
        JButton loginBtn = new JButton("Login");
        g.gridx=2; g.gridy=1; g.gridwidth=1;
        pane.add(loginBtn, g);
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                LoginToBakalari loginToBakalari = new LoginToBakalari(urlField.getText(), username.getText(), password.getPassword());
                loginToBakalari.login(null);
                if(saveCheckBox.isSelected())
                    saveCsv(urlField.getText(), username.getText());

                InfoWindow infoWindow = new InfoWindow(loginToBakalari.getAccessToken(), loginToBakalari.getRefreshToken(), loginToBakalari);

                disposeLoginWindow();
                infoWindow.userInfo();
            }
        });
        saveCheckBox = new JCheckBox("Uložit jméno a URL", false);
        g.gridx=0;
        g.gridy=2;
        g.gridwidth=1;
        pane.add(saveCheckBox, g);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void disposeLoginWindow(){ this.dispose(); }

    private String[] loadCsv(){
        String[] array = new String[2];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String s;
            while ((s = br.readLine()) != null) {
                array = s.split(";");
            }
        }
        catch(IOException e){
            array[0]="https://";
            array[1]="username";
        }
        return array;
    }

    private void saveCsv(String url, String username){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
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

    /**
     * Throws message dialogs
     * @param title
     * Title of dialog
     * @param error
     * Text of dialog
     * @param type
     * JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE, JOptionPane.INFORMATION_MESSAGE
     */
    public void throwMessage(String title, String error, int type){
        JOptionPane.showMessageDialog(this, error, title, type);
    }


}
