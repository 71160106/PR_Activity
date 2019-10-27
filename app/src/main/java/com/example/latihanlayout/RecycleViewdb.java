package com.example.latihanlayout;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewdb extends RecyclerView.Adapter<RecycleViewdb.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public RecycleViewdb(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg , int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.list_data,vg,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int pos){
        if (!cursor.moveToPosition(pos)) {
            return;
        }
        String nim = cursor.getString(cursor.getColumnIndex(DataMahasiswa.mahasiswaEntry.COLUMN_NIM));
        String nama = cursor.getString(cursor.getColumnIndex(DataMahasiswa.mahasiswaEntry.COLUMN_NAMA));
        vh.nim.setText(nim);
        vh.nama.setText(nama);
    }
    @Override
    public int getItemCount(){
        return cursor.getCount();
    }
    public void swapCursor(Cursor newCur){
        if(cursor !=null){
            cursor.close();
        }
        cursor = newCur;
        if(newCur!=null){
            notifyDataSetChanged();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nim;
        public TextView nama;
        public ViewHolder(View view) {
            super(view);
            nim = (TextView) view.findViewById(R.id.col1);
            nama = (TextView) view.findViewById(R.id.col2);
        }
    }


}
