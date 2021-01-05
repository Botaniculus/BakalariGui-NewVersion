package cz.matejprerovsky.bakalarigui;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static jdk.nashorn.internal.objects.NativeString.trim;

public class Timetable {
    private final AdjustDate date=new AdjustDate();
    private URL targetURL;
    private final String got;
    final private String[] dayOfWeek = new String[]{"Po", "Út", "Stř", "Čt", "Pá"};//new String[]{"Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek"};

    public Timetable(String baseURL, String token, int year, int month, int day){
        try {
            targetURL = new URL(baseURL + "/api/3/timetable/actual?date=" + year + "-" + month + "-" + day);
        } catch (MalformedURLException ignored) { }

        got = new Request().request(targetURL, "GET", null, token);

    }

    public String[] hours() {
        System.out.println(got);
        JSONObject obj= new JSONObject(got);
        //-----Hours--------------------------------------
        JSONArray hours = obj.getJSONArray("Hours");
        String[] hourTimes = new String[hours.length()];
        for (int i = 0; i < hours.length(); i++) {
            JSONObject hour = hours.getJSONObject(i);
            String BeginTime = hour.getString("BeginTime");
            //String EndTime = hour.getString("EndTime");
            hourTimes[i] = " (" + BeginTime + ")"/*" - " + EndTime+")"*/;
        }
        String[] hoursAndTimes = new String[hourTimes.length+1];
        for(int i=0; i<hourTimes.length;i++){
            hoursAndTimes[i+1] = i + hourTimes[i];
        }
        hoursAndTimes[0]="";
        return hoursAndTimes;
    }

    private HashMap<String, String> getRooms(JSONObject obj){
        //-----Rooms-----------------------------------------
        JSONArray rooms = obj.getJSONArray("Rooms");
        HashMap<String, String> roomsMap = new HashMap<>();
        for (int b = 0; b < rooms.length(); b++) {
            JSONObject roomObject = rooms.getJSONObject(b);
            roomsMap.put(roomObject.get("Id").toString(), roomObject.get("Abbrev").toString());
        }
        return roomsMap;
    }

    private HashMap<String, String> getSubjects(JSONObject obj){
        JSONArray subjectsArray = obj.getJSONArray("Subjects");
        HashMap<String, String > subjectsMap = new HashMap<>();

        for (int a = 0; a < subjectsArray.length(); a++) {
            JSONObject subjectObject = subjectsArray.getJSONObject(a);
            subjectsMap.put(trim(subjectObject.get("Id").toString()), subjectObject.getString("Abbrev"));
        }
        return subjectsMap;
    }

    public String[][] getTimetable() {
        String[][] arr = new String[5][13];
        JSONObject obj = new JSONObject(got);

        //-----Days---------------------------------------------------------------------------
        JSONArray days = obj.getJSONArray("Days");

        for (int i = 0; i < (days.length()); i++) {
            JSONObject den = days.getJSONObject(i);

            //-----Day of week and Date-----------------------------
            String dateString = den.getString("Date");
            String dayOfWeekString = dayOfWeek[(den.getInt("DayOfWeek") - 1)];
            arr[i][0] = (dayOfWeekString + " " + date.getDate(dateString));
            //----------------------------------


            //-----Lessons-------------------------------------------------
            JSONArray atoms = den.getJSONArray("Atoms");
            for (int j = 0; j < atoms.length(); j++) {
                JSONObject lesson = atoms.getJSONObject(j);

                int hourId = lesson.getInt("HourId");

                String result;

                //-----Get changes in timetable-----
                JSONObject changeIs;
                String change = lesson.get("Change").toString();
                String roomAbbrev;

                if (!change.equals("null")) {
                    changeIs = lesson.getJSONObject("Change");
                    result = changeIs.get("Description").toString();

                }
                else{
                    roomAbbrev = (getRooms(obj)).get(lesson.get("RoomId").toString());
                    String subjectAbbrev = getSubjects(obj).get(trim(lesson.get("SubjectId")));

                    result =subjectAbbrev+" "+roomAbbrev;
                }

                //String theme = lesson.get("Theme").toString();
                arr[i][hourId -1] = result;
            }
        }
        return arr;
    }

}
