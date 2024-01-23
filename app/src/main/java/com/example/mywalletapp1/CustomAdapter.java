package com.example.mywalletapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> kartNoList;
    private ArrayList<String> isimList;
    private ArrayList<String> soyisimList;

    RecyclerOnItemClickListener recyclerOnItemClickListener;

    public CustomAdapter(Context context, ArrayList<String> kartNoList, ArrayList<String> isimList, ArrayList<String> soyisimList,RecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.context = context;
        this.kartNoList = kartNoList;
        this.isimList = isimList;
        this.soyisimList = soyisimList;
        this.recyclerOnItemClickListener = recyclerOnItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_activity,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.isimDetails.setText(isimList.get(position).toString());
        holder.soyisimDetails.setText(soyisimList.get(position).toString());
        holder.kartNoDetails.setText(kartNoList.get(position).toString());



    }

    @Override
    public int getItemCount() {
        return isimList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView isimDetails,soyisimDetails,kartNoDetails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            isimDetails = itemView.findViewById(R.id.isimDetails);
            soyisimDetails = itemView.findViewById(R.id.soyisimDetails);
            kartNoDetails = itemView.findViewById(R.id.kartNoDetails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (recyclerOnItemClickListener != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            String isim,soyisim,kartno;
                            isim = isimList.get(position);
                            soyisim = soyisimList.get(position);
                            kartno = kartNoList.get(position);
                            recyclerOnItemClickListener.onItemClick(isim,soyisim,kartno);
                        }
                    }



                }
            });

        }


    }
}
