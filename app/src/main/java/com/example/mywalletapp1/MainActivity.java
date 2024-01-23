package com.example.mywalletapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.mywalletapp1.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerOnItemClickListener{

    ActivityMainBinding mainBinding;
    LinearLayoutManager layoutManager;
    CustomAdapter customAdapter;
    ArrayList<String> kartNoList;
    ArrayList<String> isimList;
    ArrayList<String> soyisimList;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBinding();initAdapter();
        try {
            acilisEkrani();
        } catch (Exception e) {
            System.out.println(e.toString());
        } //açılış ekranı


        try {
            storeDataInArrays();
        } catch (Exception e) {
            System.out.println(e.toString());
        } //store data
        initDataBaseInit();


    }

    public void acilisEkrani(){
        //Eğer Recycler View İçerisinde veri yoksa arka planı vector ile set edeceğiz.
        int itemSayisi = customAdapter.getItemCount();

        if (itemSayisi == 0){
            // burada set işlemi gerçekleşecek
            mainBinding.mainArkaPlan.setBackgroundResource(R.drawable.no_data_background);
        }
        else{
            mainBinding.mainArkaPlan.setBackgroundResource(R.drawable.recyler_constrain_bg);
            mainBinding.recyclerMain.setBackgroundResource(R.drawable.recyler_constrain_bg);
        }
    }
    public void addButonClick(View view){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }

    public void initBinding(){
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
    }
    public void initAdapter(){
        kartNoList = new ArrayList();
        isimList = new ArrayList();
        soyisimList = new ArrayList();

        customAdapter = new CustomAdapter(MainActivity.this,kartNoList,isimList,soyisimList,this);
        layoutManager = new LinearLayoutManager(MainActivity.this);

        mainBinding.recyclerMain.setLayoutManager(layoutManager);
        mainBinding.recyclerMain.setAdapter(customAdapter);

    }
    public void initDataBaseInit(){
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
    }
    public void storeDataInArrays(){
        kartNoList.clear();isimList.clear();soyisimList.clear();

        Cursor cursor = dataBaseHelper.readAllData();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "veri bulunmadı", Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                isimList.add(cursor.getString(0));
                soyisimList.add(cursor.getString(2));
                kartNoList.add(cursor.getString(1));
            }
            cursor.close();
        }
        if (cursor != null){
            cursor.close();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        storeDataInArrays();
        customAdapter.notifyDataSetChanged();
        acilisEkrani();
    }


    @Override
    public void onItemClick(String isim, String soyisim, String kartno) {
        Intent intent = new Intent(MainActivity.this,PersonDetails.class);
        intent.putExtra("isim",isim);
        intent.putExtra("soyisim",soyisim);
        intent.putExtra("kartno",kartno);

        if (isim == null){
            Toast.makeText(getApplicationContext(),"İsim boş",Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(intent);
        }

    }
}