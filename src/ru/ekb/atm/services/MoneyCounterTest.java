package ru.ekb.atm.services;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MoneyCounterTest {


    @Test
    public void countMoney() {
        int cash = 5430;
        Map<Integer, Integer> billsAdvanced = new LinkedHashMap<>();

        billsAdvanced.put(5000, 10);
        billsAdvanced.put(2000, 10);
        billsAdvanced.put(1000, 10);
        billsAdvanced.put(500, 10);
        billsAdvanced.put(200, 10);
        billsAdvanced.put(100, 10);
        billsAdvanced.put(50, 10);
        billsAdvanced.put(10, 10);

        Map<Integer, Integer> expected = MoneyCounter.countMoney(cash, billsAdvanced);

        Map<Integer, Integer> actual = new LinkedHashMap<>();
        actual.put(5000, 1);
        actual.put(2000, 0);
        actual.put(1000, 0);
        actual.put(500, 0);
        actual.put(200, 2);
        actual.put(100, 0);
        actual.put(50, 0);
        actual.put(10, 3);

        //Запускаем тест
        Assert.assertEquals(expected, actual);
    }
}