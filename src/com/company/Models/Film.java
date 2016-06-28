package com.company.Models;


import java.io.Serializable;
import java.util.Calendar;

public class Film implements Serializable
{
    private String filmName;

    private String filmType;

    private Boolean isRented;
    private Calendar dayOfRent;
    private long daysRent;

    //ничто не мешает создать несуществующий тип фильма
    public Film(String filmName, String filmType, boolean isRented) {
        this.filmName = filmName;
        this.filmType = filmType;
        this.isRented = isRented;
    }


    public void setFilmName(String filmName)
    {
        this.filmName = filmName;
    }

    public String getFilmName()
    {
        return  filmName;
    }

    public void setFilmType(String filmType)
    {
        this.filmType = filmType;
    }

    public String getFilmType()
    {
        return filmType;
    }

    public void setIsRented(Boolean isRented)
    {
        this.isRented = isRented;
    }

    public Boolean getIsRented()
    {
        return isRented;
    }

    public void setDayOfRent(Calendar daysOfRent) {this.dayOfRent = daysOfRent;}
    public Calendar getDayOfRent() {return dayOfRent;}

    public void setDaysRent(long daysRent) {this.daysRent = daysRent;}
    public long getDaysRent(){return daysRent;}

    @Override
    public String toString()
    {
        return String.format(" %s "+"("+"%s"+")", this.filmName, this.filmType);
    }

}
