package com.vm.domain;

/**
 * Created by egucer on 27-Mar-19.
 */
public enum Item {
    KOLA("Kola", 100), ETICIN("EtiCin", 25), POPKEK("PopKek", 50), NEGRO("Negro", 200);

    private String adi;
    private int fiyat;

    private Item(String adi, int fiyat) {
        this.adi = adi;
        this.fiyat = fiyat;
    }

    public String getAdi() {
        return adi;
    }

    public int getFiyat() {
        return fiyat;
    }
}
