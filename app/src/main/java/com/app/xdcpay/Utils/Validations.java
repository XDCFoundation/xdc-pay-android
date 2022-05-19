package com.app.xdcpay.Utils;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    public static Pattern letter_lower = Pattern.compile("[a-z]");
    public static Pattern letter_upper = Pattern.compile("[A-Z]");
    public static Pattern digit = Pattern.compile("[0-9]");
    public static Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

    public static boolean hasText(String s) {
        if (s.equalsIgnoreCase(""))
            return false;
        else
            return true;
    }

    public static boolean hasText(EditText s) {
        if (s.getText().toString().trim().equalsIgnoreCase(""))
            return false;
        else
            return true;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {

        if (password.length() >= 8) {
            //Pattern eight = Pattern.compile (".{8}");


            Matcher hasLetterLower = letter_lower.matcher(password);
            Matcher hasLetterUpper = letter_upper.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetterLower.find() && hasDigit.find() && hasSpecial.find() && hasLetterUpper.find();

        } else
            return false;

    }

    public static int calculatePasswordStrength(String password) {
        if (password.length() == 0)
            return 0;
        if (password.length() < 8)
            return 25;
        else {
            Matcher hasLetterLower = letter_lower.matcher(password);
            Matcher hasLetterUpper = letter_upper.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            if (hasLetterUpper.find() && hasLetterLower.find() && !hasDigit.find() && !hasSpecial.find())
                return 60;

            if (hasLetterUpper.find() && hasLetterLower.find() && hasDigit.find() && hasSpecial.find())
                return 100;

        }
        return 25;
    }
}
