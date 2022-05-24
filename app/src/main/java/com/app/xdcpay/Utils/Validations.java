package com.app.xdcpay.Utils;

import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static boolean hasGasLimit(int gasLimit) {
        if (gasLimit <= 2100)
            return false;
        else if (gasLimit >= 415800000)
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
        int strengthMeter = 0;
        if (password.length() == 0)
            strengthMeter = 0;
        if (password.length() < 8)
            strengthMeter = 25;
        else {
            Matcher hasLetterLower = letter_lower.matcher(password);
            Matcher hasLetterUpper = letter_upper.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

//            Log.d("strengthPassword:", "" + hasLetterUpper.find() + " .. " + hasLetterLower.find() + " .. "
//                    + hasDigit.find() + " .. " + hasSpecial.find());
//            if (hasLetterUpper.find() && hasLetterLower.find() && !hasDigit.find() && !hasSpecial.find())
//                strengthMeter = 60;

            if (hasLetterUpper.find() && hasLetterLower.find() && hasDigit.find() && hasSpecial.find()) {
                strengthMeter = 100;
            } else {
                strengthMeter = 60;
            }

        }
        return strengthMeter;
    }

    public static boolean isValidUrl(String url) {

        boolean urlPattern = false;

        String[] https = {"https://", "http://"};
        for (int i = 0; i < 2; i++) {
            if (url.startsWith(https[i])) {
                Pattern p = Patterns.WEB_URL;
                urlPattern = Patterns.WEB_URL.matcher(url.toLowerCase()).matches();
            }
        }
        return urlPattern;

    }

    public static boolean isContainNo(String seedPhrase) {

        boolean correctSeedPhrase = false;

        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < 10; i++) {
            if (seedPhrase.contains(numbers[i])) {
                correctSeedPhrase = false;
            } else
                correctSeedPhrase = true;
        }
        return correctSeedPhrase;

    }

    public static boolean seedPhraseDigits(String seedPhrase) {
        boolean correctSeedPhrase = false;
        if (seedPhrase.contains(" ")) {
            List<String> seedPhraseList = new ArrayList<String>(Arrays.asList(seedPhrase.split(" ")));
            if (seedPhraseList.size() == 12)
                correctSeedPhrase = true;
            else
                correctSeedPhrase = false;

        } else
            correctSeedPhrase = false;


        return correctSeedPhrase;

    }
}
