package cz.matejprerovsky.bakalarigui;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import static jdk.nashorn.internal.objects.NativeString.length;
import static jdk.nashorn.internal.objects.NativeString.trim;

public class Marks {
    private URL targetURL;
    private final String got;
    private AdjustDate adjustDate;
    public Marks(String baseURL, String token){
        try {
            targetURL = new URL(baseURL + "/api/3/marks");
        } catch (MalformedURLException e) { }
        got = new Request().request(targetURL, "GET", null, token);
        adjustDate = new AdjustDate();
    }
    public String getMarks() {
        JSONObject obj = new JSONObject(got);
        JSONArray subjects = obj.getJSONArray("Subjects");
        String output = "";
        for (int i = 0; i < subjects.length(); i++)
        {
            JSONObject subject = subjects.getJSONObject(i);
            String averageText = trim(subject.get("AverageText").toString());

            //-----Get subject abbrevation----------
             JSONObject subjectInfo = subject.getJSONObject("Subject");
             String baseSubjectAbbrev = trim(subjectInfo.getString("Abbrev"));

             //-----Get marks-------------------------
             JSONArray marks = subject.getJSONArray("Marks");
             String marksString = "";
             for (int j = 0; j < marks.length(); j++) {
             JSONObject mark = marks.getJSONObject(j);
             String markText = mark.getString("MarkText");
             String markCaption = mark.get("Caption").toString();
             int weight = mark.getInt("Weight");
             String date = adjustDate.getDate(mark.get("EditDate").toString());
             marksString += "\t" + markText + " (";
             if (markCaption.length() != 0)
             marksString += markCaption + ", ";
             marksString += date + "), " + "Váha: " + weight + "\n";

             }
             String result = baseSubjectAbbrev + " (" + averageText + ")" + ":\n" + marksString;
             output += result;
        }
             return output;
    }
}