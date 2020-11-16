package cz.matejprerovsky.bakalarigui;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Login {
    private String baseURL, refreshToken, accessToken;
    private URL targetURL;
    public Login(String baseURL) {
        this.baseURL=baseURL;
    }
    public String login(String username, String password, boolean refresh){
        String data;
        if (!refresh)
            data = "client_id=ANDR&grant_type=password&username=" + username + "&password=" + password;
        else
            data = "client_id=ANDR&grant_type=refresh_token&refresh_token=" + refreshToken;

        try { targetURL = new URL(baseURL + "/api/login");
        } catch (MalformedURLException ignored) { }

        String got = new Request().request(targetURL, "POST", data, null);

        JSONObject obj = new JSONObject(got);
        accessToken = obj.getString("access_token");
        refreshToken = obj.getString("refresh_token");

        return accessToken;
    }
}
