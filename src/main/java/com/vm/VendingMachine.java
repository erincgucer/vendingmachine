package com.vm;

import com.vm.domain.Coin;
import com.vm.domain.Item;

import java.util.Map;

/**
 * Created by egucer on 27-Mar-19.
 */
public interface VendingMachine {

    void initialize();
    Item selectAndGetItem(Item item);
    void insertCoin(Coin coin);
    Map<Coin, Integer> getChange(int changeTotal);

}
