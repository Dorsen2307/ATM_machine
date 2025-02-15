package ru.ekb.atm;

import ru.ekb.atm.models.ATMMachine;
import ru.ekb.atm.models.Piople;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static ru.ekb.atm.menu.FirstMenu.checkedPIN;

public class Main {
    public static Map<Integer, Integer> bills = new LinkedHashMap<>();

    public static void main(String[] args) {
        ATMMachine atm = new ATMMachine();
        Piople piople = new Piople();
        Scanner sc = new Scanner(System.in);

        checkedPIN(atm, piople, sc);
    }


}