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
    final private String loginTitle = "Přihlášení";
    private String[] dataArray;
    private String token;

    private JButton loginBtn;
    private JTextField urlField, username;
    private JPasswordField password;
    private JCheckBox saveCheckBox;


    public LoginWindow(){
        this.setTitle("Bakaláři" + " – " + loginTitle);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("baky.png"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void loginWind() {
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
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    token = loginBase(urlField.getText(), username.getText(), String.valueOf(password.getPassword()), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Main.token=token;
                Main.baseURL=urlField.getText();
                System.out.println(Main.token);
                if(saveCheckBox.isSelected())
                    saveCsv(urlField.getText(), username.getText());
                InfoWindow infoWindow = new InfoWindow(token, urlField.getText());
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

    private void loadCsv(){
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
    }
    /**
     * @param url
     * @param username
     */
    private void saveCsv(String url, String username){
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

    /**
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

    private String loginBase(String baseURL, String username, String password, String refreshToken) throws IOException {
        URL targetURL=null;
        String data;

        if (refreshToken.length()==0)
            data = "client_id=ANDR&grant_type=password&username=" + username + "&password=" + password;
        else
            data = "client_id=ANDR&grant_type=refresh_token&refresh_token=" + refreshToken;

        try { targetURL = new URL(baseURL + "/api/login");
        } catch (MalformedURLException ignored) { }

        String got = new Request().request(targetURL, "POST", data, null);

        JSONObject obj = new JSONObject(got);
        String accessToken = obj.getString("access_token");
        //String refreshToken = obj.getString("refresh_token");

        return accessToken;
    }

}
