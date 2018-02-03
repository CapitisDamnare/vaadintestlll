package tapsi.com.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogHandler {

    private static Date startDate;
    private static Date lastDate;
    private static List<String> list = new ArrayList<>();
    private static List<String> date;

    public static void setData(String data) {
        String temp = data;
        while (temp.contains("\n")) {
            String row = temp.substring(0,temp.indexOf("\n"));
            temp = temp.replace(row + "\n", "");
            list.add(row);
        }
        saveStartDate();
        saveEndDate();
    }

    private static void saveStartDate() {
        String tempDate = list.get(1);
        String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String startDate = tempDate.substring(0,tempDate.indexOf(thisYear) + 4);
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        try {
            LogHandler.startDate = format.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("LogHandler.startDate: " + LogHandler.startDate);
    }

    private static void saveEndDate() {
        String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        for (int iter = list.size()-1; iter >=0; iter--) {
            if (list.get(iter).contains("CET " + thisYear)) {
                String lastDate = list.get(iter).substring(0,list.get(iter).indexOf(thisYear) + 4);
                DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
                try {
                    LogHandler.lastDate = format.parse(lastDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("LogHandler.saveEndDate: " + LogHandler.lastDate);
                return;
            }
        }
    }
}
