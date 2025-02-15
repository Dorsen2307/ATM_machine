package ru.ekb.atm.menu;

import ru.ekb.atm.models.ATMMachine;
import ru.ekb.atm.models.Piople;

import java.util.Scanner;

import static ru.ekb.atm.Main.bills;

public class FirstMenu {
    public static void checkedPIN(ATMMachine atm, Piople piople, Scanner sc) {
        System.out.println("\u001b[35;1m>>>>> БАНКОМАТ <<<<<\u001b[0m");
        System.out.print("Карта вставлена!\nВведите PIN-код: ");
        if (sc.hasNextInt()) {
            int pin = sc.nextInt();
            if (pin == piople.card.getPIN()) {
                run(atm, piople, sc);
            } else {
                System.out.println("\u001b[31;1mPIN-код неверный. Работа с картой прервана.\u001b[0m");
            }
        } else {
            System.out.println("\u001b[31;1mPIN-код неверный. Работа с картой прервана.\u001b[0m");
        }
    }

    private static void run(ATMMachine atm, Piople piople, Scanner sc) {
        while (true) {
            getMenu();
            int item = getItemMenu(sc);
            switch (item) {
                case 1:
                    bills.clear();
                    atm.runGiveOutCash(piople, sc);
                    break;
                case 2:
                    bills.clear();
                    atm.runDepositCash(piople, sc);
                    break;
                case 3:
                    atm.getSumCard(piople);
                    break;
                case 4:
                    piople.getSumBills();
                    break;
                default:
                    sc.close();
                    return;
            }
        }
    }

    public static void getMenu() {
        System.out.println("\u001b[35;1m");
        System.out.println(">>>>> БАНКОМАТ <<<<<");
        System.out.println(
                "1: Снять наличные\n" +
                "2: Внести наличные\n" +
                "3: Посмотреть остаток по карте\n" +
                "0: Выход\n" +
                "-------------------------------\n" +
                "4: Заглянуть в кошелек\n" +
                "\u001b[0m"
        );
    }

    public static int getItemMenu(Scanner sc) {
        while (true) {
            System.out.print("Введите пункт меню: ");
            if (sc.hasNextInt()) {
                int item = sc.nextInt();
                if (item >= 0 && item <= 4) {
                    return item;
                } else {
                    System.out.println("\u001b[31;1mНет такого пункта меню!\u001b[0m");
                }
            } else {
                System.out.println("\u001b[31;1mВведите цифру пункта меню!\u001b[0m");
                sc.next();
            }
        }
    }
}
