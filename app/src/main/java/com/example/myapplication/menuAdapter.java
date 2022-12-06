package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class menuAdapter extends RecyclerView.Adapter<menuAdapter.menuViewHolder> {
    LayoutInflater inflater;
    Context _context;
    List<menu> menuList;
    private ItemClickListener mItemListener;

    public menuAdapter(Context _context, List<menu> menuList, ItemClickListener mItemListener) {
        this._context = _context;
        this.menuList = menuList;
        this.inflater = LayoutInflater.from(this._context);
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public menuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_menu, parent, false);
        return new menuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull menuViewHolder holder, int position) {
        menu Menu = menuList.get(position);
        holder.nama.setText(Menu.getNama());
        holder.stok.setText("Stok : " + String.valueOf(Menu.getStok()));
        holder.harga.setText("Harga : Rp " + String.valueOf(Menu.getHarga()) + ",00");
        holder.admin.setText(Menu.getUsername());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(menuList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public interface ItemClickListener{
        void onItemClick(menu Menu);
    }

    class menuViewHolder extends RecyclerView.ViewHolder {

        TextView nama;
        TextView harga;
        TextView stok;
        TextView admin;

        public menuViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            harga = itemView.findViewById(R.id.tvHarga);
            stok = itemView.findViewById(R.id.tvStok);
            admin = itemView.findViewById(R.id.tvAdmin);
        }
    }
}
