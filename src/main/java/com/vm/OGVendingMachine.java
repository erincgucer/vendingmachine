package com.vm;

import com.vm.domain.Coin;
import com.vm.domain.Item;
import com.vm.exception.ItemNotFoundException;
import com.vm.exception.NotEnoughBalanceException;
import com.vm.exception.NotEnoughChangeException;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by egucer on 27-Mar-19.
 */
public class OGVendingMachine implements VendingMachine {

    private Map<Item, Integer> itemInventory = new HashMap<Item, Integer>();
    private Map<Coin, Integer> coinInventory = new HashMap<Coin, Integer>();
    private int currentBalance;

    public void initialize() {
        for (Coin c : Coin.values()) {
            coinInventory.put(c, 2);
        }
        for (Item i : Item.values()) {
            itemInventory.put(i, 10);
        }
    }

    public Item selectAndGetItem(Item item) {
        if (itemInventory.get(item) != null && itemInventory.get(item) > 0) {
            if (currentBalance >= item.getFiyat()) {
                if (hasEnoughChange(currentBalance - item.getFiyat())) {
                    itemInventory.put(item, itemInventory.get(item) - 1);
                    return item;
                }

                throw new NotEnoughChangeException();
            }

            throw new NotEnoughBalanceException();
        }

        throw new ItemNotFoundException();
    }

    public boolean hasEnoughChange(int changeTotal) {

        Map<Coin, Integer> coinInventoryBefore = new HashMap<>();
        try {
            coinInventory.forEach(coinInventoryBefore::putIfAbsent);
            getChange(changeTotal);
        } catch (NotEnoughChangeException ex) {
            return false;
        } finally {
            coinInventory.clear();
            coinInventoryBefore.forEach(coinInventory::putIfAbsent);
        }

        return true;
    }

    public void insertCoin(Coin coin) {
        currentBalance += coin.getDeger();
    }

    public Map<Coin, Integer> getChange(int changeTotal) {
        Map<Coin, Integer> coinMap = new HashMap<Coin, Integer>();

        List<Coin> maxCoinList = new ArrayList<>();

        while (changeTotal > 0) {
            Stream<Coin> coinStream = coinInventory.keySet().stream();

            if (maxCoinList.size() > 0) {
                coinStream = coinStream.filter(c -> !maxCoinList.contains(c));
            }

            Coin newMaxCoin;
            try {
                newMaxCoin = coinStream.max((p1, p2) -> p1.compareTo(p2)).get();
            } catch (NoSuchElementException ex) {
                throw new NotEnoughChangeException();
            }

            if (changeTotal >= newMaxCoin.getDeger() && coinInventory.get(newMaxCoin) > 0) {
                coinMap.put(newMaxCoin, coinMap.get(newMaxCoin) == null ? 1 : coinMap.get(newMaxCoin) + 1);
                changeTotal = changeTotal - newMaxCoin.getDeger();
                coinInventory.put(newMaxCoin, coinInventory.get(newMaxCoin) - 1);
            } else {
                maxCoinList.add(newMaxCoin);
            }
        }

        return coinMap;
    }

    public Map<Item, Integer> getItemInventory() {
        return itemInventory;
    }

    public Map<Coin, Integer> getCoinInventory() {
        return coinInventory;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

}
