package cz.matejprerovsky.bakalarigui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Request {
    public String request(URL target, String method, String data, String token) {
        /* clear output */
        String output = "";
        HttpURLConnection conn;
        conn = null;

        /** -----Http request-------------------------------------------------- */
        try {
            conn = (HttpURLConnection) target.openConnection();
        } catch (IOException ignored) { }

        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "UTF-8");

        /** Sets request method (POST or GET) */
        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) { Main.errorMessage();}

        conn.setUseCaches(false);
        if (token != null) conn.setRequestProperty("Authorization", "Bearer " + token);

        /** Outputs data if needed */
        if (data != null)
        {
            conn.setRequestProperty("Content-Length", "" + data.length());
            try (OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream())) { out.write(data); }
            catch (IllegalArgumentException illegalArgumentException){
                Main.wrongAddressOrNoInternetConnectionMessage();
            }
            catch (IOException ignored){}
        }

        /** Reads input stream */
        try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String currentLine;
            while ((currentLine = in.readLine()) != null)
                output += currentLine;


        }
        catch (IOException e){
            Main.wrongLoginMessage();
            System.out.println(output);
        }

        return output;
    }
}
