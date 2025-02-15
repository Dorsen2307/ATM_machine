package ru.ekb.atm.models;

import ru.ekb.atm.services.MoneyCounter;

import java.util.LinkedHashMap;
import java.util.Map;

public class Piople extends MoneyCounter {
    final int[] BANKNOTES = new int[]{5000, 2000, 1000, 500, 200, 100, 50, 10};
    public Map<Integer, Integer> cash = new LinkedHashMap<>();
    public Card card = new Card(10000);

    public Piople() {
        for (int banknote : BANKNOTES) {
            cash.put(banknote, 10);
        }
    }

    public void addingOrWithdrawingCash(Map<Integer, Integer> bills, String op) {
        System.out.println("\nНаличных в кошельке:");

        for (Map.Entry<Integer, Integer> entry : bills.entrySet()) {
            if (op.equals("+")) {
                cash.put(entry.getKey(), cash.get(entry.getKey()) + entry.getValue());
            } else if (op.equals("-")) {
                cash.put(entry.getKey(), cash.get(entry.getKey()) - entry.getValue());
            }
            if (cash.get(entry.getKey()) != 0) {
                System.out.println(entry.getKey() + ": " + cash.get(entry.getKey()) + " шт.");
            }
        }

        getSumBills();
    }

    public void getSumBills() {
        int sum = 0;

        for (Map.Entry<Integer, Integer> entry : cash.entrySet()) {
            sum += entry.getValue() * entry.getKey();
        }

        System.out.println("\nСумма наличных средств: " + sum + "\n");
    }
}
