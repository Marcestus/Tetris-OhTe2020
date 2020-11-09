package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    //luodun kassapäätteen rahamäärä ja myytyjen lounaiden määrä on oikea (rahaa 1000, lounaita myyty 0)
    //testit:
    @Test
    public void luodunKassapaatteenRahamaaraOnOikea() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void luodunKassapaatteenMyytyjenEdullistenLounaidenMaaraOnOikea() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void luodunKassapaatteenMyytyjenMaukkaidenLounaidenMaaraOnOikea() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    //jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja vaihtorahan suuruus on oikea
    //testit:
    @Test
    public void maksuRiittavaEdulliseenLounaaseen_RahamaaraKasvaaEdullisellaLounaalla() {
        kassa.syoEdullisesti(440);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    @Test
    public void maksuRiittavaMaukkaaseenLounaaseen_RahamaaraKasvaaMaukkaallaLounaalla() {
        kassa.syoMaukkaasti(600);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    @Test
    public void maksuRiittavaEdulliseenLounaaseen_VaihtorahanSuuruusOnOikea() {
        assertEquals(200, kassa.syoEdullisesti(440));
    }
    @Test
    public void maksuRiittavaMaukkaaseenLounaaseen_VaihtorahanSuuruusOnOikea() {
        assertEquals(200, kassa.syoMaukkaasti(600));
    }
    
    //jos maksu on riittävä: myytyjen lounaiden määrä kasvaa
    //testit:
    @Test
    public void maksuRiittavaEdulliseenLounaaseen_MyytyjenEdullistenLounaidenMaaraKasvaa() {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void maksuRiittavaMaukkaaseenLounaaseen_MyytyjenMaukkaidenLounaidenMaaraKasvaa() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    //jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta
    //testit:
    @Test
    public void maksuEiOleRiittavaEdulliseenLounaaseen_KassassaOlevaRahamaaraEiMuutu() {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void maksuEiOleRiittavaMaukkaaseenLounaaseen_KassassaOlevaRahamaaraEiMuutu() {
        kassa.syoMaukkaasti(350);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void maksuEiOleRiittavaEdulliseenLounaaseen_KaikkiRahatPalautetaanVaihtorahana() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    @Test
    public void maksuEiOleRiittavaMaukkaseenLounaaseen_KaikkiRahatPalautetaanVaihtorahana() {
        assertEquals(350, kassa.syoMaukkaasti(350));
    }
    @Test
    public void maksuEiOleRiittavaEdulliseenLounaaseen_MyytyjenEdullistenLounaidenMaaraEiMuutu() {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void maksuEiOleRiittavaMaukkaaseenLounaaseen_MyytyjenMaukkaidenLounaidenMaaraEiMuutu() {
        kassa.syoMaukkaasti(350);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    //jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true
    //testit:
    @Test
    public void kortillaTarpeeksiRahaaEdulliseenLounaaseen_EdullisenLounaanSummaVeloitetaanKortilta() {
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    @Test
    public void kortillaTarpeeksiRahaaMaukkaaseenLounaaseen_MaukkaanLounaanSummaVeloitetaanKortilta() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    @Test
    public void kortillaTarpeeksiRahaaEdulliseenLounaaseen_PalautetaanTrue() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
    }
    @Test
    public void kortillaTarpeeksiRahaaMaukkaaseenLounaaseen_PalautetaanTrue() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
    }
    
    //jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa
    //testit:
    @Test
    public void kortillaTarpeeksiRahaaEdulliseenLounaaseen_MyytyjenEdullistenLounaidenMaaraKasvaa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void kortillaTarpeeksiRahaaMaukkaaseenLounaaseen_MyytyjenMaukkaidenLounaidenMaaraKasvaa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    //jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen lounaiden määrä muuttumaton ja palautetaan false
    //testit:
    @Test
    public void kortillaEiOleTarpeeksiRahaaEdulliseenLounaaseen_KortinRahamaaraEiMuutu() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(40, kortti.saldo());
    }
    @Test
    public void kortillaEiOleTarpeeksiRahaaMaukkaaseenLounaaseen_KortinRahamaaraEiMuutu() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(200, kortti.saldo());
    }
    @Test
    public void kortillaEiOleTarpeeksiRahaaEdulliseenLounaaseen_MyytyjenEdullistenLounaidenMaaraEiMuutu() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(4, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void kortillaEiOleTarpeeksiRahaaMaukkaaseenLounaaseen_MyytyjenMaukkaidenLounaidenMaaraEiMuutu() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void kortillaEiOleTarpeeksiRahaaEdulliseenLounaaseen_PalautetaanFalse() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(false, kassa.syoEdullisesti(kortti));
    }
    @Test
    public void kortillaEiOleTarpeeksiRahaaMaukkaaseenLounaaseen_PalautetaanFalse() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(false, kassa.syoMaukkaasti(kortti));
    }
    
    //kassassa oleva rahamäärä ei muutu kortilla ostettaessa
    //testit:
    @Test
    public void kassanRahamaaraEiMuutuKunKortillaOstetaanEdullinenLounas() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void kassanRahamaaraEiMuutuKunKortillaOstetaanMaukasLounas() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    //kortille rahaa ladattaessa kortin saldo muuttuu ja kassassa oleva rahamäärä kasvaa ladatulla summalla
    //testit:
    @Test
    public void kunKortilleLadataanSaldoaKortinSaldoMuuttuuLadatullaSummalla() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(1500, kortti.saldo());
    }
    @Test
    public void kunKortilleLadataanSaldoaKassanRahamaaraKasvaaLadatullaSummalla() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(100500, kassa.kassassaRahaa());
    }
    
    //lisätestit: kortille rahaa ladattaessa jos ladattava summa on negatiivinen, ei tehdä mitään
    @Test
    public void kunKortilleLadataanSaldoaJaLadattavaSummaOnNegatiivinenKortinSaldoEiMuutu() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(1000, kortti.saldo());
    }
    @Test
    public void kunKortilleLadataanSaldoaJaLadattavaSummaOnNegatiivinenKassanRahamaaraEiMuutu() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
