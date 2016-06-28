package com.company.Services;

import java.util.Scanner;



public class Utils {


    public static Scanner scan = new Scanner(System.in);

    public static String readLine() {
        String keyboard = scan.nextLine();
        System.out.println(keyboard);
        return keyboard;
    }

    //вот вызову я откуда-то этот метод, внутрь не посмотрю и получу странное поведение.
    public static void newLine() {
        for(int i = 0; i < 5; i++)
        {
            System.out.println();
        }
    }

    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty())
        {
            return false;
        }
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isDigit(str.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }


    public static boolean checkFilmType(String filmType) {
        if(filmType.toLowerCase().trim().equals("regular film") || filmType.toLowerCase().trim().equals("new release") || filmType.toLowerCase().trim().equals("old film"))
        {
            return true;
        }
        else {
            return false;
        }

    }




}
