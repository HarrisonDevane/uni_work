package dates;
import java.util.*;

public class Dates {
    String day;
    String month;
    String year;
    String strCopy;

    Boolean dayValid = true;
    Boolean monthValid = true;
    Boolean yearValid = true;
    Boolean leapYear = false;

    String[] monthsArr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    int[] monthsArrInt = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Dates (String dayIn, String monthIn, String yearIn){
        this.day = dayIn;
        this.month = monthIn;
        this.year = yearIn;
        this.strCopy = day + " " + month  + " " + year;

        checkYear();
        checkMonth();
        checkLeap();
        checkDay();
        output();
    }

    public void output(){
        String s = " - INVALID: ";

        if (dayValid == false){
            s += "Day, ";
        } 
        
        if (monthValid == false){
            s += "Month, ";
        }
        
        if (yearValid == false){
            s += "Year, ";
        }
        
        s += "out of range.";

        if (dayValid == false || monthValid == false || yearValid == false){
            System.out.println(strCopy + s);
        } else {
            System.out.println(day + " " + month + " " + year);
        }
    }

    public boolean isInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void checkLeap(){
        if (leapYear == true){
            monthsArrInt[1] += 1;
        }
    }

    public Boolean checkCase(){
        for (int i = 0; i < monthsArr.length; i++){
            String upper = monthsArr[i].toUpperCase();
            String lower = monthsArr[i].toLowerCase();

            if (month.equals(monthsArr[i]) || month.equals(upper) || month.equals(lower)){
                month = monthsArr[i];
                return true;
            } 
        }
        return false;
    }

    public void checkYear(){
        if (isInteger(year) == true && (year.length() == 2 || year.length() == 4)){
            int yearInt = Integer.parseInt(year);

            if (year.length() == 2){
                if (yearInt <= 49){
                    year = "20" + year;
                } else {
                    year = "19" + year;
                }
            } else if (year.length() == 4){
                if ((yearInt >= 3000) || (yearInt < 1753)){
                    yearValid = false;
                }
            }
            
            if (yearInt % 4 == 0){
                if (yearInt % 100 == 0){
                    if (yearInt % 400 == 0){
                        leapYear = true;
                    }
                } else {
                    leapYear = true;
                }
            }
            
        } else {
            yearValid = false;
        }
    }

    public void checkMonth(){
        if (isInteger(month)){
            int monthInt = Integer.parseInt(month);

            if (month.length() >= 3){
                monthValid = false;
            }

            if ((monthInt >= 1) && (monthInt <= 12)){
                month = monthsArr[monthInt -1];
            } else {
                monthValid = false;
            }
        } else if (checkCase() == false){
            monthValid = false;
        }
    }

    public void checkDay(){
        if (isInteger(day)){
            int dayInt = Integer.parseInt(day);
            int index = 0;

            if (day.length() >= 3){
                dayValid = false;
            }

            for (int i = 0; i < monthsArr.length; i++){
                if (month == monthsArr[i]){
                    index = i;
                }
            }

            if ((dayInt <= 0) || (dayInt > monthsArrInt[index])){
                dayValid = false;
            } else {
                day = String.valueOf(dayInt);
            }

            if ((dayValid == true) && (dayInt < 10)){
                day = "0" + day;
            }
        } else {
            dayValid = false;
        }
    }    
}