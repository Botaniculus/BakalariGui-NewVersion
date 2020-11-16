package cz.matejprerovsky.bakalarigui;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static jdk.nashorn.internal.objects.NativeString.trim;

public class Timetable {
    private adjustDate date=new adjustDate();

    private String accessToken;
    private URL targetURL;
    private String[] baseSubjectAbbrev;
    private String got;
    private int year, month, day;
    final private String[] dayOfWeek = new String[]{"Po", "Út", "Stř", "Čt", "Pá"};//new String[]{"Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek"};

    public Timetable(String baseURL, String token, int year, int month, int day){
        this.accessToken=token;
        try {
            targetURL = new URL(baseURL + "/api/3/timetable/actual?date=" + year + "-" + month + "-" + day);
        } catch (MalformedURLException e) { }

        got = new Request().request(targetURL, "GET", null, accessToken);

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

    public String[][] getTimetable() {
        String[][] arr = new String[5][13];

        System.out.println(got);
        JSONObject obj = new JSONObject(got);

        //-----Rooms-----------------------------------------
        JSONArray rooms = obj.getJSONArray("Rooms");
        String[] roomIds = new String[rooms.length()];
        String[] roomAbbrevs = new String[rooms.length()];
        for (int b = 0; b < rooms.length(); b++) {
            JSONObject room = rooms.getJSONObject(b);
            roomIds[b] = room.get("Id").toString();
            roomAbbrevs[b] = room.get("Abbrev").toString();
        }

        //------------------------

        //-----Subjects----------------------------------------
        JSONArray subjects = obj.getJSONArray("Subjects");
        baseSubjectAbbrev = new String[subjects.length() + 1];
        int[] baseSubjectId = new int[subjects.length() + 1];
        baseSubjectId[0] = 0; baseSubjectAbbrev[0] = "";
        for (int a = 0; a < subjects.length(); a++) {
            JSONObject sub = subjects.getJSONObject(a);
            baseSubjectAbbrev[a + 1] = sub.getString("Abbrev");
            baseSubjectId[a + 1] = Integer.parseInt(trim(sub.get("Id").toString()));
        }
        //-------------------

        //-----Days---------------------------------------------------------------------------
        JSONArray days = obj.getJSONArray("Days");

        for (int i = 0; i < (days.length()); i++) {
            JSONObject den = days.getJSONObject(i);

            //-----Day of week and Date-----------------------------
            String dateString = den.getString("Date");
            String dayOfWeekString = dayOfWeek[(den.getInt("DayOfWeek") - 1)];
            arr[i][0] = (dayOfWeekString + " " + date.getDate(dateString));
            //----------------------------------

            //-----Lessons--------------------------------------------------
            JSONArray atoms = den.getJSONArray("Atoms");
            for (int j = 0; j < atoms.length(); j++) {
                JSONObject lesson = atoms.getJSONObject(j);

                int hourId = lesson.getInt("HourId");

                //-----Get room-------------------------
                String roomId = lesson.get("RoomId").toString();
                int indexOfRoom = 0;
                for (int c = 0; c < roomIds.length; c++) {
                    if (roomId.equals(roomIds[c]))
                        indexOfRoom = c;
                }
                String roomAbbrev = roomAbbrevs[indexOfRoom];
                //-------------------------

                //-----Get changes in timetable-----
                JSONObject changeIs;
                String changeDescription = "";
                String change = lesson.get("Change").toString();
                if (!change.equals("null")) {
                    changeIs = lesson.getJSONObject("Change");
                    changeDescription = changeIs.get("Description").toString();
                }
                //-----------------------------------------------

                //-----Get subject id and find its abbreviation-----
                String subjectAbbrev = "";

                String subjectIdString = trim(lesson.get("SubjectId"));
                int subjectId=0;
                try{
                    subjectId = Integer.parseInt(subjectIdString);
                } catch (NumberFormatException e){

                }

                int indexOfSubject = 0;
                for (int k = 0; k < baseSubjectId.length; k++) {
                    if (subjectId == baseSubjectId[k]) {
                        indexOfSubject = k;
                    }
                    subjectAbbrev = baseSubjectAbbrev[indexOfSubject];

                }
                //-----------------------------------------------
                //---Get theme of lesson---
                //String theme = lesson.get("Theme").toString();
                //---Print result---
                String result =/*"\n"+" " + (hourId-2) + ": " + */subjectAbbrev/* + " " +(hourTimes[hourId-2])*/;
                //if(!theme.equals(""))
                //result+=" | " + theme;

                result += " " + roomAbbrev;
                //---If there is some change in timetable, print it---
                if (!changeDescription.equals(""))
                    result += " (" + changeDescription + ")";

                arr[i][hourId - 2 + 1] = result;
            }
        }

        return arr;
    }

}
