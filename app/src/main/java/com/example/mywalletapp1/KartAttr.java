package com.example.mywalletapp1;

import java.util.ArrayList;

public class KartAttr {
    private String kartNo;
    private String isim;
    private String soyisim;

    public KartAttr(String kartNo, String isim, String soyisim) {
        this.kartNo = kartNo;
        this.isim = isim;
        this.soyisim = soyisim;
    }

    public String getKartNo() {
        return kartNo;
    }

    public void setKartNo(String kartNo) {
        this.kartNo = kartNo;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }
}
