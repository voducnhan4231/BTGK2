package btgk2;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeCalculator {
    public static String calculateAndEncryptAge(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(dateOfBirth, formatter);
        LocalDate currentDate = LocalDate.now();


        int age = Period.between(dob, currentDate).getYears();


        return String.valueOf(age);
    }
}