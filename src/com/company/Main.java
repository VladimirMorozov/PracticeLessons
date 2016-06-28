package com.company;

import com.company.Services.InventoryClass;
import com.company.Models.Film;
/*
Общее: 
1) не ООП подход
2) не ведется учет того, кто взял фильм. Один клиент всего будет ? :)
   даже если не брать в учет здравый смысл, в условии четко сказано
   •	Keep track of the customers “bonus” points 
   требование не выполнено.
3) много плохо называнных переменных, методов. по имени надо сразу чтобы было понятно для чего оно.
4) вот хочу я чтобы у меня 2 диска с бетменом было. что делать? удаляются по имени например. а имя одно будет.
5) Неправильно считает цены.

Программу даже не запускал если что.


*/
public class Main {

    public static void main(String[] args) {
        InventoryClass.deSerializeFilms();
        //как-то не ясно. вроде должен создать инвентори и дальше ничего не произойдет.
        InventoryClass.createInventory();
    }
}
