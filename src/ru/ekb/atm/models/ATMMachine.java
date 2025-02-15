package ru.ekb.atm.models;

import ru.ekb.atm.exceptions.absenceBanknotesException;
import ru.ekb.atm.exceptions.requestExceedsLimitException;
import ru.ekb.atm.services.MoneyCounter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/** Класс банкомата - машины по обналичиванию и зачислению денежных средств
 */
public class ATMMachine extends MoneyCounter {
    int amountCash;
    final int[] BANKNOTES = new int[]{5000, 2000, 1000, 500, 200, 100, 50, 10};
    Map<Integer, Integer> billAvailable = new LinkedHashMap<>();

    public ATMMachine() {
        for (int banknote : BANKNOTES) {
            billAvailable.put(banknote, 10);
        }

        amountCash = calculatingAmount();
    }

    /**
     * Выдача наличных.
     * <p>
     * Проверяется превышение запроса наличных над наличными в банкомате.
     * Вызывает {@code getNumberBills()} для определения номинала и количества купюр.
     * Вызывает метод обновления суммы наличных в банкомате {@code calculatingAmount()}
     * </p>
     * @param cash Требуемая сумма
     * @param piople Экземпляр класса {@link Piople}
     * @return {@code Map<Integer, Integer>} Возвращает коллекцию выдаваемых купюр и их количества
     * @throws requestExceedsLimitException Исключение при превышении запрашиваемой суммы наличных
     * @throws absenceBanknotesException Исключение при отсутствии необходимых банкнот
     */
    public Map<Integer, Integer> giveOutCash(int cash, Piople piople) throws requestExceedsLimitException, absenceBanknotesException {
        if (cash > amountCash) {
            throw new requestExceedsLimitException("\u001b[31;1m!!!!! Ваш запрос превышает сумму наличных в банкомате! !!!!!\u001b[0m");
        }
        if (cash > piople.card.getSum()) {
            throw new requestExceedsLimitException("\u001b[31;1m!!!!! Ваш запрос превышает сумму на Вашей карте! !!!!!\u001b[0m\nСумма на карте: " + piople.card.getSum() + "\n");
        }

        Map<Integer, Integer> billsOut = getNumberBills(cash);
        amountCash = calculatingAmount();

        return billsOut;
    }

    /**
     * Отображает коллекцию номиналов и количества соответствующих купюр.
     * @param cash Запрашиваемая сумма
     * @return {@code Map<Integer, Integer>} Возвращает коллекцию выдаваемых купюр и их количества
     * @throws absenceBanknotesException Исключение при отсутствии необходимых банкнот
     */
    private Map<Integer, Integer> getNumberBills(int cash) throws absenceBanknotesException {
        Map<Integer, Integer> bills = countMoney(cash, billAvailable);

        System.out.println("\nВыдача наличных одобрена!");

        for (Map.Entry<Integer, Integer> bill : bills.entrySet()) {
            billAvailable.put(bill.getKey(), billAvailable.get(bill.getKey()) - bill.getValue());
            if (bill.getValue() != 0) {
                System.out.println("Купюры по " + bill.getKey() + " - " + bill.getValue() + " шт.");
            }
        }

        return bills;
    }

    /**
     * Подсчет суммы наличных в банкомате.
     * @return {Integer} Возвращает сумму.
     */
    private int calculatingAmount() {
        int cash = 0;

        for (Map.Entry<Integer, Integer> entry : billAvailable.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            cash += key * value;
        }

        return cash;
    }

    /**
     * Перегрузка подсчета суммы наличных.
     * @param bills Любая коллекция типа {@code Map<Integer, Integer>}
     * @return {Integer} Возвращает сумму.
     */
    private int calculatingAmount(Map<Integer, Integer> bills) {
        int cash = 0;

        for (Map.Entry<Integer, Integer> entry : bills.entrySet()) {
            cash += entry.getKey() * entry.getValue();
        }

        return cash;
    }

    /**
     * Запуск процесса выдачи наличных.
     * @param piople Экземпляр человека {@link Piople}.
     * @param sc Экземпляр считывания ввода пользователя.
     */
    public void runGiveOutCash(Piople piople, Scanner sc) {
        int cash = inputSum(sc);

        try {
            piople.addingOrWithdrawingCash(giveOutCash(cash, piople), "+");
            addingOrWithdrawingToCard(cash, piople, "-");
        } catch (requestExceedsLimitException | absenceBanknotesException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Обработка типа операции по карте.
     * <p>
     *     Если операция была на снятие, то с суммы на карте отнимется запрашиваемая сумма.
     * </p>
     * @param cash Запрашиваемая сумма {@code Integer}.
     * @param piople Экземпляр человека {@link Piople}.
     * @param op Тип операции {@code String}.
     */
    public void addingOrWithdrawingToCard(int cash, Piople piople, String op) {
        if (op.equals("-")) {
            piople.card.setSum((piople.card.getSum() - cash));
        } else if (op.equals("+")) {
            piople.card.setSum((piople.card.getSum() + cash));
        }

        getSumCard(piople);
    }

    /**
     * Запрос суммы средств на карте клиента.
     * @param piople Экземпляр человека {@link Piople}
     */
    public void getSumCard(Piople piople) {
        System.out.println(piople.card);
    }

    /**
     * Зачисление средств на карту.
     * @param billsAdvanced Наличные экземпляра {@link Piople}, которые нужно обработать.
     * @param cash Запрашиваемая сумма {@code Integer}.
     * @return {@code Map<Integer, Integer>} Возвращает коллекцию зачисляемых купюр и их количества
     * @throws absenceBanknotesException Проброс исключения отсутствия необходимых банкнот.
     */
    public Map<Integer, Integer> depositingCash(Map<Integer, Integer> billsAdvanced, int cash) throws absenceBanknotesException {
        Map<Integer, Integer> bills = countMoney(cash, billsAdvanced);

        System.out.println("Зачисление денежных средств одобрено!");
        for (Map.Entry<Integer, Integer> bill : bills.entrySet()) {
            if (bill.getValue() != 0) {
                billAvailable.put(bill.getKey(), billAvailable.get(bill.getKey()) + bill.getValue());
                System.out.println("Зачислено " + bill.getKey() + " - " + bill.getValue() + " шт.");
            }
        }
        System.out.println("Зачислена сумма: " + calculatingAmount(bills));

        return bills;
    }

    /**
     * Запуск процесса зачисления наличных.
     * @param piople Экземпляр человека {@link Piople}.
     * @param sc Экземпляр считывания ввода пользователя.
     */
    public void runDepositCash(Piople piople, Scanner sc) {
        int cash = inputSum(sc);

        try {
            piople.addingOrWithdrawingCash(depositingCash(piople.cash, cash), "-");
            addingOrWithdrawingToCard(cash, piople, "+");
        } catch (requestExceedsLimitException | absenceBanknotesException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ввод суммы.
     * @param sc Экземпляр считывания ввода пользователя.
     * @return Возвращает введенную сумму.
     */
    private int inputSum(Scanner sc) {
        while (true) {
            System.out.print("Введите сумму: ");
            if (sc.hasNextInt()) {
                int input = sc.nextInt();
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("\u001b[31;1m!!!!! Сумма должна быть больше 0. Попробуйте еще раз. !!!!!\u001b[0m");
                }
            } else {
                System.out.println("\u001b[31;1m!!!!! Пожалуйста, введите число. !!!!!\u001b[0m");
                sc.next();
            }
        }
    }


}
