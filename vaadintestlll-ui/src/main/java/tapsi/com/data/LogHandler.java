package tapsi.com.data;

import java.util.ArrayList;
import java.util.List;

public class LogHandler {

    private static String data;
    private static List<String> list = new ArrayList<>();
    private static List<String> date;

    public static void setData(String data) {
        String temp = data;
        while (temp.contains("\n") && temp.length() > 10) {
            String row = temp.substring(0,temp.indexOf("\n"));
            temp.replace(row + "\n", "");
            list.add(row);
            System.out.println("List size: " + list.size());
            System.out.println("Temp size: " + temp.length());
        }
    }
}
