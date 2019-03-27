package com.vm.domain;

/**
 * Created by egucer on 27-Mar-19.
 */
public enum Coin {

    KURUS25(25), KURUS50(50), LIRA1(100);

    private int deger;

    private Coin(int deger) {
        this.deger = deger;
    }

    public int getDeger() {
        return deger;
    }
}
