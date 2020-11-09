package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(500);
        assertEquals(1500, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi() {
        kortti.otaRahaa(500);
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(1500);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void josRahatRiittivatPalautetaanTrue() {
        assertEquals(true, kortti.otaRahaa(500));
    }
    
    @Test
    public void josRahatEivatRiitaPalautetaanFalse() {
        assertEquals(false, kortti.otaRahaa(1500));
    }
    
    //oma lisätesti toString-metodille
    @Test
    public void toStringTulostaaOikeanSaldon() {
        kortti.otaRahaa(550);
        assertEquals("saldo: 4.50", kortti.toString());
    }
    
}
