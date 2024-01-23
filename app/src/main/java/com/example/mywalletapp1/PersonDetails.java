package com.example.mywalletapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mywalletapp1.databinding.ActivityPersonDetailsBinding;

public class PersonDetails extends AppCompatActivity {
    private String isim,soyisim,kartno,yeniIsim,yeniSoyisim,yeniKartNo;
    private DataBaseHelper dataBaseHelper;
    private Intent intent;
    private ActivityPersonDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        binding = ActivityPersonDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sistemAyarlari();
        strinGleriAyarla();
        xmlKismiAyarlari();

    }

    public PersonDetails (){}

    private void xmlKismiAyarlari(){
        binding.isimPersonActivity.setText(isim);
        binding.soyisimPersonSurname.setText(soyisim);
        binding.personDetailsKartNo.setText(kartno);
    }

    private void sistemAyarlari(){
        intent = getIntent();
        dataBaseHelper = new DataBaseHelper(PersonDetails.this);

    }
    private void strinGleriAyarla(){
        isim = intent.getStringExtra("isim");
        soyisim = intent.getStringExtra("soyisim");
        kartno = intent.getStringExtra("kartno");

        binding.personDetailsKartNo.setText(kartno);
        binding.soyisimPersonSurname.setText(soyisim);
        binding.isimPersonActivity.setText(isim);


    }

    public void deleteClick(View view){
        dataBaseHelper.deleteWalletInBase(isim,soyisim,kartno);

    }


    public void updateClick(View view){
        yeniKartNo = binding.personDetailsKartNo.getText().toString();
        yeniIsim = binding.isimPersonActivity.getText().toString();
        yeniSoyisim = binding.soyisimPersonSurname.getText().toString();



        dataBaseHelper.updateWalletInBase(isim,soyisim,kartno,yeniIsim,yeniSoyisim,yeniKartNo);
        //mesajla("Başarıyla Gerçekleşti.");
    }


    private void mesajla(String mesaj){
        Toast.makeText(getApplicationContext(),mesaj,Toast.LENGTH_SHORT).show();
    }
}