package cz.matejprerovsky.bakalarigui;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginToBakalari {
    private String baseURL, username, password, refreshToken, accessToken;

    public String getRefreshToken() { return refreshToken; }
    public String getAccessToken() { return accessToken; }
    public String getBaseURL() { return baseURL; }

    public LoginToBakalari(String baseURL, String username, char[] password){
        this.baseURL=baseURL;
        this.username=username;
        this.password=String.valueOf(password);
    }

    public void login(String caseRefreshToken) {
        URL targetURL=null;
        String data;

        if (caseRefreshToken==null)
            data = "client_id=ANDR&grant_type=password&username=" + username + "&password=" + password;
        else
            data = "client_id=ANDR&grant_type=refresh_token&refresh_token=" + caseRefreshToken;

        try { targetURL = new URL(baseURL + "/api/login");
        } catch (MalformedURLException ignored) { }

        String got = new Request().request(targetURL, "POST", data, null);

        JSONObject obj = new JSONObject(got);
        refreshToken = obj.getString("refresh_token");
        accessToken = obj.getString("access_token");

    }
}
