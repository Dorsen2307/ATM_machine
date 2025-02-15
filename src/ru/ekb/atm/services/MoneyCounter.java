package ru.ekb.atm.services;

import ru.ekb.atm.Main;
import ru.ekb.atm.exceptions.absenceBanknotesException;

import java.util.Map;

public abstract class MoneyCounter {
    /**
     * Возвращает коллекцию номиналов и количества соответствующих купюр.
     * @param cash Требуемая сумма.
     * @param billsAdvanced Наличные.
     * @return {@code Map<Integer, Integer> Main.bills} Возвращает временную коллекцию выдаваемых купюр и их количества.
     * @throws absenceBanknotesException Исключение - отсутствие необходимых банкнот.
     */
    public static Map<Integer, Integer> countMoney(int cash, Map<Integer, Integer> billsAdvanced) throws absenceBanknotesException {
        for (Map.Entry<Integer, Integer> bill : billsAdvanced.entrySet()) {
            int count = cash / bill.getKey();
            if (count <= bill.getValue()) {
                cash -= bill.getKey() * count;
                Main.bills.put(bill.getKey(), count);
            } else {
                cash -= bill.getKey() * bill.getValue();
                Main.bills.put(bill.getKey(), bill.getValue());
            }
        }

        if (cash == 0) {
            return Main.bills;
        } else {
            throw new absenceBanknotesException("!!!!! Отсутствуют необходимые банкноты для обработки Вашего запроса. !!!!!\n");
        }
    }
}
