package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.DataBase.Entity.ContactEntity;
import com.app.xdcpay.Interface.NetworkListInterface;
import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<ContactEntity> ContactEntity = new ArrayList<>();
    ContactSubListAdapter contactSubListAdapter;
    List<String> name= new ArrayList<>();

    public ContactAdapter(Context context, List<ContactEntity> ContactEntity) {
        this.context = context;
        this.ContactEntity = ContactEntity;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.contact_list_row, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactEntity model = ContactEntity.get(position);
//        String name
        holder.tvFirstLetter.setText(model.getNameFirstLetter());
//        if (model.getNameFirstLetter())
        contactSubListAdapter = new ContactSubListAdapter(context,ContactEntity, model, position);
        holder.rvContact.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvContact.setAdapter(contactSubListAdapter);

    }

    @Override
    public int getItemCount() {
        return ContactEntity.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextViewMedium tvFirstLetter;
//        private LinearLayout id_linear_network;
        private RecyclerView rvContact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstLetter = itemView.findViewById(R.id.tvFirstLetter);
//            id_linear_network = itemView.findViewById(R.id.id_linear_network);
            rvContact = itemView.findViewById(R.id.rvContact);
        }
    }
}