package cz.matejprerovsky.bakalarigui;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import static jdk.nashorn.internal.objects.NativeString.trim;

public class Komens {
    private URL targetURL;
    private final String got;
    private AdjustDate adjustDate;

    public Komens(String baseURL, String token){
        try {
            targetURL = new URL(baseURL + "/api/3/komens/messages/noticeboard/");
        } catch (MalformedURLException e) { }
        got = new Request().request(targetURL, "POST", null, token);
        adjustDate = new AdjustDate();
    }

    public String getKomens() {
        //JSONObject obj = new JSONObject(got);
        String output = got;
        return output;
    }

}
