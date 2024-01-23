package com.example.mywalletapp1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mywalletapp1.databinding.ActivityPersonDetailsBinding;

public class DataBaseHelperr extends SQLiteOpenHelper {
    private Context context;

    final static private int DATABASE_VERSION = 1;
    final static private String DATABASE_NAME = "Wallet.db";
    final static private String TABLE_NAME = "Wallet";
    final static private String CARD_PERSON_NAME_KOLON_ISMI = "isim";
    final static private String CARD_NO_KOLON_ISMI = "kartNo";
    final static private String CARD_PERSON_SURNAME_KOLON_ISMI = "soyisim";


    public DataBaseHelperr(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_NAME +
                "(" +
                CARD_PERSON_NAME_KOLON_ISMI + " VARCHAR, " +
                CARD_NO_KOLON_ISMI + " VARCHAR, " +
                CARD_PERSON_SURNAME_KOLON_ISMI + " VARCHAR " + ");" ;

        db.execSQL(query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = " DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);

    }

    public void deleteWalletInBase(String isim, String soyisim, String kartno){
        boolean flag;
        flag = isPersonInDatabase(isim,soyisim,kartno);
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String whereClause = CARD_PERSON_NAME_KOLON_ISMI + " = ? AND " + CARD_PERSON_SURNAME_KOLON_ISMI + " = ? AND " + CARD_NO_KOLON_ISMI + " = ? " ;
            String[] whereArgs = {isim, soyisim,kartno};

            if (flag){db.delete(TABLE_NAME, whereClause, whereArgs);

                mesajla("Kişi Silindi");}
            else{
                mesajla(isim+" "+soyisim+" "+kartno);
                mesajla("Bu kişi zaten mevcut değil.");
            }

        } catch (SQLException e) {
            mesajla("Bu verilere göre biri bulunamadı, lütfen tekrar deneyiniz.");
            System.out.print(e.toString());
        } finally {
            db.close();
        }



    }

    public void deleteWalletInBase(String isim, String soyisim) {
        boolean flag;
        flag = isPersonInDatabase(isim,soyisim);
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String whereClause = CARD_PERSON_NAME_KOLON_ISMI + " = ? AND " + CARD_PERSON_SURNAME_KOLON_ISMI + " = ?";
            String[] whereArgs = {isim, soyisim};

            if (flag){db.delete(TABLE_NAME, whereClause, whereArgs);

                mesajla("Kişi Silindi");}
            else{
                mesajla("Bu kişi zaten mevcut değil.");
            }

        } catch (SQLException e) {
            mesajla("Bu verilere göre biri bulunamadı, lütfen tekrar deneyiniz.");
            System.out.print(e.toString());
        } finally {
            db.close();
        }


}

    public boolean isPersonInDatabase(String isim, String soyisim, String kartno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CARD_PERSON_NAME_KOLON_ISMI + " = ? AND " + CARD_PERSON_SURNAME_KOLON_ISMI + " = ? AND " + CARD_NO_KOLON_ISMI + " = ? " ;
            String[] selectionArgs = {isim, soyisim, kartno};
            cursor = db.rawQuery(query, selectionArgs);

            // Cursor'da kayıt varsa true, yoksa false döndür
            return cursor.getCount() > 0;
        } catch (SQLException e) {
            System.out.print(e.toString());
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean isPersonInDatabase(String isim, String soyisim) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CARD_PERSON_NAME_KOLON_ISMI + " = ? AND " + CARD_PERSON_SURNAME_KOLON_ISMI + " = ?";
            String[] selectionArgs = {isim, soyisim};
            cursor = db.rawQuery(query, selectionArgs);

            // Cursor'da kayıt varsa true, yoksa false döndür
            return cursor.getCount() > 0;
        } catch (SQLException e) {
            System.out.print(e.toString());
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }


    public void addWallletInBase(String isim, String soyisim, String kartNo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CARD_NO_KOLON_ISMI,kartNo);
        cv.put(CARD_PERSON_NAME_KOLON_ISMI,isim);
        cv.put(CARD_PERSON_SURNAME_KOLON_ISMI,soyisim);
        long result = db.insert(TABLE_NAME, null,cv);

        if (result == -1){
            mesajla("Başarısız Oldu.");
        }
        else{
            mesajla("Başarıyla Kaydedildi");
        }

    }

    public void updateWalletInBase(String isim, String soyisim, String kartNo, ActivityPersonDetailsBinding binding) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();


        try {
            ContentValues cv = new ContentValues();
            cv.put(CARD_NO_KOLON_ISMI, kartNo);
            cv.put(CARD_PERSON_NAME_KOLON_ISMI, isim);
            cv.put(CARD_PERSON_SURNAME_KOLON_ISMI, soyisim);

            String whereClause = CARD_PERSON_NAME_KOLON_ISMI + " = ? AND " + CARD_PERSON_SURNAME_KOLON_ISMI + " = ? AND " + CARD_NO_KOLON_ISMI + " = ?";
            //buradan kolonların ismimi mi yoksa dedeğerler mi güncellendi emin değilim

            String[] whereArgs = {isim, soyisim, kartNo};

            int affectedRows = db.update(TABLE_NAME, cv, whereClause, whereArgs);

            if (affectedRows > 0) {
                db.setTransactionSuccessful(); // İşlem başarılı olduysa onayla
                mesajla("Güncelleme başarılı oldu.");
                //burada recyclerview güncelleme işlemi gerçekleştirilecek
            } else {
                mesajla("Güncelleme işlemi başarısız oldu.");
                // Güncelleme başarısız olduysa gerekli işlemleri yapabilirsiniz.
            }
        } catch (Exception e) {
            e.printStackTrace(); // Hata durumunda hatayı logla
            System.out.println(e.toString());
        } finally {
            db.endTransaction(); // Transaksiyonu bitir
            db.close(); // Veritabanı bağlantısını kapat
        }
    }


    Cursor readAllData(){
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;

        if (database != null){
            cursor = database.rawQuery(query,null);
        }
        return cursor;

    }
    public void mesajla(String mesaj){
        Toast.makeText(context,mesaj,Toast.LENGTH_SHORT).show();
    }
}
