package cz.matejprerovsky.bakalarigui;

public class AdjustDate {
    public String getDate(String dateString) {
        String[] dateInt = dateString.split("-");
        dateInt[2] = dateInt[2].substring(0, 2);

        //remove leading zeros
        dateInt[2] = dateInt[2].replaceFirst("^0+(?!$)", "");
        dateInt[1] = dateInt[1].replaceFirst("^0+(?!$)", "");

        return dateInt[2] + ". " + dateInt[1];
    }
}
