package com.example.mywalletapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mywalletapp1.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding addBinding;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(addBinding.getRoot());

        dataBaseInit();


    }


    public void dataBaseInit(){
        dataBaseHelper = new DataBaseHelper(AddActivity.this);

    }
    public void kaydetButonClick(View view){
        try {
            dataBaseHelper.addWallletInBase(
                    addBinding.isimAddActivity.getText().toString(),
                    addBinding.soyisimAddActivity.getText().toString(),
                    String.valueOf(addBinding.kartNoAddActivity.getText().toString()));
            Toast.makeText(AddActivity.this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(AddActivity.this,"Kayıt başarılı olamadı",Toast.LENGTH_LONG).show();
            System.out.println(e.toString());
        }
    }

    public void deleteButonClick(View view){
        try {
            dataBaseHelper.deleteWalletInBase(
                    addBinding.isimAddActivity.getText().toString(),
                    addBinding.soyisimAddActivity.getText().toString());
        }catch (Exception e){
            Toast.makeText(AddActivity.this,"Silinemedi Lütfen Tekrar Deneyiniz",Toast.LENGTH_LONG).show();
        }

    }
}