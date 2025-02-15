package ru.ekb.atm.models;

public class Card {
    private int sum;

    public Card(int sum) {
        this.sum = sum;
    }

    public int getNumber() {
        return 12345678;
    }

    public int getCRC() {
        return 159;
    }

    public int getPIN() {
        return 1234;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String toString() {
        return "Сумма на карте №" + getNumber() + " составляет: " + sum + "\n";
    }
}
