package tapsi.com.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogHandler {

    private static Date startDate;
    private static Date lastDate;
    private static Date now;
    private static String upTime;
    private static List<String> list = new ArrayList<>();
    private static List<String> errorMessages = new ArrayList<>();

    public synchronized static Date getStartDate() {
        return startDate;
    }

    public synchronized static String getUpTime() {
        return upTime;
    }

    public synchronized static String getErrorMessages() {
        return String.valueOf(errorMessages.size());
    }

    public synchronized static String getLast20Logs() {
        String value = "";
        for (int iterator = list.size()-1; iterator >= list.size()-20; iterator--) {
            value += list.get(iterator) + "\n";
        }
        return value;
    }

    private synchronized static void clearData() {
        list.clear();
        errorMessages.clear();
    }

    public synchronized static void setData(String data) {
        clearData();
        String temp = data;
        while (!temp.isEmpty()) {
            String row = temp.substring(0, temp.indexOf("\n"));
            if (!row.isEmpty()) {
                temp = temp.replace(row + "\n", "");
                list.add(row);
            }
            else
                temp = temp.substring(1, temp.length());
        }
        now = new Date();
        saveStartDate();
        saveEndDate();
        calcUpTime();
        countErrors();
    }

    private synchronized static void saveStartDate() {
        String tempDate = list.get(1);
        String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String startDate = tempDate.substring(0, tempDate.indexOf(thisYear) + 4);
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        try {
            LogHandler.startDate = format.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private synchronized static void saveEndDate() {
        String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        for (int iterator = list.size() - 1; iterator >= 0; iterator--) {
            if (list.get(iterator).contains("CET " + thisYear)) {
                String lastDate = list.get(iterator).substring(0, list.get(iterator).indexOf(thisYear) + 4);
                DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
                try {
                    LogHandler.lastDate = format.parse(lastDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private synchronized static void calcUpTime() {
        long totalSeconds = (now.getTime() - startDate.getTime()) / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        upTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private synchronized static void countErrors() {
        for (int iterator = 0; iterator <= list.size() - 1; iterator++) {
            String message = list.get(iterator);
            if (message.contains("Exception!")) {
                String errorMessage = message + "\n";
                errorMessage += list.get(iterator + 1) + "\n";
                for (int innerIterator = iterator + 2; innerIterator <= list.size() - 1; innerIterator++) {
                    if (list.get(innerIterator).contains("\tat")) {
                        errorMessage += list.get(innerIterator) + "\n";
                    } else
                        break;
                }
                errorMessages.add(errorMessage);
            }
        }
    }
}
