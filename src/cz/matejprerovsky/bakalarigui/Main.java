package cz.matejprerovsky.bakalarigui;

import javax.swing.*;

/**
 * @author Matěj Přerovský
 */
public class Main  {

    final private static String wrongLoginText = "Vadné přihlašovací údaje.";
    final private static String errorText = "Error";

    private static LoginWindow l;

    public static void main(String[] args) {
        l = new LoginWindow();
        l.loginWind();
    }

    public static void wrongLoginMessage(){ l.throwMessage(wrongLoginText, wrongLoginText, JOptionPane.WARNING_MESSAGE); }
    public static void wrongAddressOrNoInternetConnectionMessage(){
        l.throwMessage("Buď máte špatně URL adresu, nebo nejste připojeni k internetu", "Buď máte špatně URL adresu, nebo nejste připojeni k internetu", JOptionPane.ERROR_MESSAGE);
    }
    public static void errorMessage(){ l.throwMessage(errorText, errorText, JOptionPane.ERROR_MESSAGE); }


}