package com.company.Services;

import java.io.*;
import java.util.ArrayList;

import com.company.Constants.Constant;
import com.company.Models.Film;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class InventoryClass implements Serializable {

    public static ArrayList<String> inventoryList = new ArrayList<>();
    public static ArrayList<Film>  listOfFilms = new ArrayList<>();


    private static  Calendar calendar = Calendar.getInstance();
    private static  Calendar calendarReturn = new GregorianCalendar();
    private static int bonusPoints = 0;
    private static  long total;
    private static long totalExtra;
    private static long difference;
    private static long days;


    public static void createInventory() {
        inventoryList.add("Add a film");
        inventoryList.add("Remove a film");
        inventoryList.add("Change the type of a film");
        inventoryList.add("List all films");
        inventoryList.add("List all films in store");
        inventoryList.add("Rent a film");
        inventoryList.add("Return films");
        inventoryList.add("Exit");

        boolean flag = false;
        while(!flag)
        {
            for (int i = 0; i < inventoryList.size(); i++) {
                System.out.println((i + 1) + " " + inventoryList.get(i));
                System.out.println("");
            }

            System.out.println("Plese enter numbers between 1 to 8");
            String keyboard = Utils.readLine();

            boolean bool = Utils.isNumber(keyboard);

            if(bool)
            {
                switchMenu(keyboard);
            }
            else
            {
                System.out.println("Only numbers are allowed");
                Utils.scan.nextLine();
                Utils.newLine();
            }

        }
    }

    public static void addFilm(){
        Utils.newLine();
        System.out.print("Enter film name: ");
        String filmName = Utils.readLine();
        boolean flagType = true;

        while(flagType == true) {
            System.out.print("\nEnter film type: ");
            String filmType = Utils.readLine();
            System.out.println();

            if (Utils.checkFilmType(filmType) == true)
            {
                Film film = new Film(filmName, filmType, false);
                listOfFilms.add(film);
                System.out.println("Film added");
                flagType = false;
                serializeFilms();
                System.out.println("Press key to continue");
                Utils.readLine();
            }
            else
            {
                System.out.println("Film type not supported");
            }
        }

    }

    public static  void removeFilm() {
        System.out.println("Enter film name");
        String keyboard = Utils.readLine();


        for(int i = 0; i < listOfFilms.size(); i++){
            if(listOfFilms.get(i).getFilmName().toLowerCase().trim().equals(keyboard.toLowerCase().trim())){
                System.out.print("You want to remove \n"+ listOfFilms.get(i) +" \nyes/no: ");
                keyboard = Utils.readLine();
                if(keyboard.toLowerCase().trim().equals("yes") || keyboard.toLowerCase().trim().equals("y")) {
                    listOfFilms.remove(i);
                    System.out.println("Film removed");
                    System.out.println("Press key to continue");
                    Utils.readLine();
                }
                else{
                    System.out.println("Film was not removed");
                }
            }

        }
    }

    public static  void changeType(){
        System.out.println("Enter film name");
        String keyboard = Utils.readLine();

        for(int i = 0; i < listOfFilms.size(); i++){
            if(listOfFilms.get(i).getFilmName().toLowerCase().trim().equals(keyboard.toLowerCase().trim())){
                System.out.println("Enter new film type: ");
                keyboard = Utils.readLine();
                if(Utils.checkFilmType(keyboard) == true){
                    listOfFilms.get(i).setFilmType(keyboard);
                    System.out.println("Film type has been changed");
                    System.out.println("Press key to continue");
                    Utils.readLine();
                }
            }
        }
    }

    public static void listAllStore() {
        for(Film film : listOfFilms){
            System.out.println(film);
        }
        System.out.println("Press key to continue");
        Utils.readLine();
    }

    public static void listAvaFilms(){
        for(Film film : listOfFilms){
            if(!film.getIsRented()){
                System.out.println(film);
            }
        }
        System.out.println("Press key to continue");
        Utils.readLine();
    }

    public static void rentFilm() {
        System.out.println("Enter film name you want to rent");
        String keyboard = Utils.readLine();

        for(int i = 0; i < listOfFilms.size(); i++){
            if(listOfFilms.get(i).getFilmName().toLowerCase().trim().equals(keyboard.toLowerCase().trim())){
                System.out.println("You want to rent this film? \n\n" + listOfFilms.get(i) +"\nyes/no");
                keyboard = Utils.readLine();
                if(checkAns(keyboard)) {
                    listOfFilms.get(i).setIsRented(true);
                    listOfFilms.get(i).setDayOfRent(calendar);

                    System.out.print("Enter rent days: ");
                    String rDays = Utils.readLine();

                    if(Utils.isNumber(rDays)){
                        listOfFilms.get(i).setDaysRent(Integer.parseInt(rDays));

                        System.out.println("Film rented: "+ listOfFilms.get(i).getDayOfRent().getTime() +" For "+rDays+ " Days");
                        bonusPoints = addBonus(i);

                        Utils.readLine();
                    }
                    else{
                        System.out.println("Enter only numbers numbers");
                        Utils.readLine();
                        System.out.println("Press key to continue");
                    }
                }
                else{
                    System.out.println("Film was not rented");
                    System.out.println("Press key to continue");
                    Utils.readLine();
                }
            }
        }
    }

    public static void rentedFilms(){
        for(Film film : listOfFilms){
            if(film.getIsRented()){
                System.out.println(film);
            }
        }

        Utils.readLine();
    }

    public static  void returnFilm() {

        String day = "";
        String month = "";
        String year = "";

        System.out.println("Rented films: \n");
        rentedFilms();
        System.out.println("\nYou want to return films?\n\nyes/no?");
        String returnKeyboard = Utils.readLine();

        if(checkDateOption(day,month,year)){
            if(checkAns(returnKeyboard)){
                for(Film film : listOfFilms){
                    if(film.getIsRented()){
                        reciept(film, days);
                        bonusPayment(film);
                    }
                }
                System.out.println("Total: "+ (total) +" EUR");
                System.out.println();

            }
            else if(returnKeyboard.toLowerCase().trim().equals("no") || returnKeyboard.toLowerCase().trim().equals("n")){
                System.out.println("\n\nEnter film name you want to return");
                String keyboard = Utils.readLine();

                for(Film film : listOfFilms){
                    if(film.getFilmName().toLowerCase().trim().equals(keyboard.toLowerCase().trim()) && film.getIsRented()){
                        reciept(film,days);
                        bonusPayment(film);
                    }
                }
                System.out.println("Total: "+ (total) +" EUR");
                System.out.println("Press key to continue");
                Utils.readLine();
                System.out.println("");
            }
            else{
                System.out.println("Wrong command, use yes/no");
            }
        } else {
            System.out.println("Date, Month and Year must be a numbers");
        }

    }

    private static boolean checkAns(String cA){
        if(cA.toLowerCase().trim().equals("yes") || cA.toLowerCase().trim().equals("y")){
            return true;
        }
        else{
            return false;
        }

    }

    private static long calculateTotal(long days, Film film){

            if(film.getFilmType().toLowerCase().trim().equals("new release")){
                return Constant.PREMIUM_PRICE * days;
            }
            if(film.getFilmType().toLowerCase().trim().equals("old film")){
                if(days > 5){
                    return Constant.BASIC_PRICE + ((days - 5) * Constant.BASIC_PRICE);
                }
                if(days == 1){
                    return Constant.BASIC_PRICE;
                }
                else {
                    return Constant.BASIC_PRICE;
                }
            }
            if(film.getFilmType().toLowerCase().trim().equals("regular film")){
                if(days > 3){
                    return Constant.BASIC_PRICE + ((days - 3) * Constant.BASIC_PRICE);
                }
                if(days == 1){
                    return Constant.BASIC_PRICE;
                }
                else {
                    return Constant.BASIC_PRICE;
                }
            }
        return 0;
    }

    private static int addBonus(int i){
        if(listOfFilms.get(i).getFilmType().toLowerCase().trim().equals("new release")){
            bonusPoints = bonusPoints + 2;
        }
        else{
            bonusPoints++;
        }
        return bonusPoints;
    }

    private static boolean checkDateOption(String day, String month, String year){
        System.out.print("Enter day of returning: ");
        day=Utils.readLine();
        System.out.print("\nEnter month of returning: ");
        month=Utils.readLine();
        System.out.print("\nEnter year of returning \n");
        year=Utils.readLine();
        if(Utils.isNumber(day) && Utils.isNumber(month) && Utils.isNumber(year)){
            calendarReturn = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
            return true;
        }else{
            return false;
        }
    }

    private static void switchMenu(String keyboard){
        if (Integer.parseInt(keyboard) < 1 || Integer.parseInt(keyboard) > 8)
        {
            System.out.println("Enter numbers between 1 to 7");
            Utils.scan.nextLine();
            Utils.newLine();
        }
        else
        {
            switch (keyboard)
            {
                case "1":
                    addFilm();
                    Utils.newLine();
                    break;
                case "2":
                    removeFilm();
                    Utils.newLine();
                    break;
                case "3":
                    changeType();
                    Utils.newLine();
                    break;
                case "4":
                    listAllStore();
                    Utils.newLine();
                    break;
                case "5":
                    listAvaFilms();
                    Utils.newLine();
                    break;
                case "6":
                    rentFilm();
                    Utils.newLine();
                    break;
                case "7":
                    returnFilm();
                    Utils.newLine();
                    break;
                case "8":
                    serializeFilms();
                    System.out.println("All data were saved");
                    System.exit(0);
                    break;
                default:
                    System.out.println("");
            }

        }
    }

    private static void reciept(Film film, long days){
        difference = calendarReturn.getTimeInMillis() - film.getDayOfRent().getTimeInMillis();
        days = difference / 72000000;

        System.out.println("Film rented: "+ film.getDayOfRent().getTime()+ ";  Film returned: "+ calendarReturn.getTime());
        System.out.println();
        System.out.println(film + " "+ film.getDaysRent() +" days "+ calculateTotal(film.getDaysRent(), film) +" EUR");
        total = total + calculateTotal(days,film);

        if(days>film.getDaysRent()){
            totalExtra = totalExtra + calculateTotal(days-film.getDaysRent(), film);
            System.out.println("Extra days "+ (days-film.getDaysRent()));
            System.out.println("Total extra: "+ totalExtra + " EUR");
        }
    }

    private static void bonusPayment(Film film){
        if(bonusPoints >= 25){
            System.out.println("Would you like to pay by bonus yes/no?");
            String keyboard = Utils.readLine();
            if(checkAns(keyboard)){
                days--;
                total = total + calculateTotal(days,film);
                reciept(film, days);


                System.out.println("Total: "+ (total) +" EUR");
                System.out.println("1 Day payed by "+bonusPoints);
            }
            else{

            }
        }
        else {
            System.out.println("You have "+bonusPoints+" bonus points (need 25 to pay for 1 day)");
        }

    }

    private static void serializeFilms(){

        try
        {
            ArrayList<Film> tmp = listOfFilms;
            FileOutputStream fileOut =
                    new FileOutputStream("listOfFilms.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(listOfFilms);
            out.close();
            fileOut.close();;
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void deSerializeFilms(){

        FileInputStream inputFileStream = null;
        try {
            inputFileStream = new FileInputStream("listOfFilms.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
            listOfFilms=(ArrayList<Film>)objectInputStream.readObject();
            objectInputStream.close();
            inputFileStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
