package com.company;

import com.company.Services.InventoryClass;
import com.company.Models.Film;

public class Main {

    public static void main(String[] args) {
        InventoryClass.deSerializeFilms();
        InventoryClass.createInventory();


    }
}
