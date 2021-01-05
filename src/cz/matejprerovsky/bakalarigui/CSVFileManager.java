package cz.matejprerovsky.bakalarigui;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class CSVFileManager {

    private File file;
    public CSVFileManager(String filename){
        File homedir = new File(System.getProperty("user.home"));
        file = new File(homedir, filename);

    }

    /**
     * Loads a text file and creates array by splitting contents of file with ;
     * If it's not successful, it fills the array with values https:// and username
     * @return the array
     */
    public String[] loadCsv(){
        String[] array = new String[]{"https://", "username"};
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s = br.readLine();
            array = s.split(";");
        } catch(IOException ignored){}
        return array;
    }

    /**
     * Saves URL and username to a textfile, separated by ;
     * If not successful, it throws message
     *
     * @param url first value
     * @param username second value
     */
    public void saveCsv(String url, String username) throws IOException{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String[] values = {url, username};
            String line = String.join(";", values);
            bw.append(line);
            bw.append("\n");
            bw.flush();
    }
}
