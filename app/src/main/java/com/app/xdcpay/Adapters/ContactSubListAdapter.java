package com.app.xdcpay.Adapters;

import static com.app.xdcpay.Utils.Constants.CONTACT_ID;
import static com.app.xdcpay.Utils.Constants.CONTACT_NAME;
import static com.app.xdcpay.Utils.Constants.CONTACT_WALLET;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Activities.Contacts.ContactDetailsActivity;
import com.app.xdcpay.Activities.Contacts.ContactsActivity;
import com.app.xdcpay.DataBase.Entity.ContactEntity;
import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.List;

public class ContactSubListAdapter extends RecyclerView.Adapter<ContactSubListAdapter.NetworkViewHolder> {
    private Context context;
    private ContactEntity ContactEntity;
    int adapterPosition;
    List<ContactEntity> list;

//    NetworkListInterface networkCallback;

    public ContactSubListAdapter(Context context, List<ContactEntity> list, ContactEntity ContactEntity, int pos) {
        this.context = context;
        this.ContactEntity = ContactEntity;
        this.adapterPosition = pos;
        this.list = list;
//        this.networkCallback = networkCallback;
    }

    @NonNull
    @Override
    public NetworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.contact_sublist, parent, false);
        return new NetworkViewHolder(v, adapterPosition);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkViewHolder holder, int position) {
//        ContactEntity model = ContactEntity.get(adapterPosition);
        holder.tvName.setText(ContactEntity.getContactName());
        holder.linear_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ContactDetailsActivity.class);
                i.putExtra(CONTACT_NAME, ContactEntity.getContactName());
                i.putExtra(CONTACT_WALLET, ContactEntity.getContactAddress());
                i.putExtra(CONTACT_ID, ContactEntity.getContactId());
                context.startActivity(i);
                ((ContactsActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NetworkViewHolder extends RecyclerView.ViewHolder {
        private TextViewMedium tvName;
        private LinearLayout linear_contact;
        Integer adapterPosition;

        public NetworkViewHolder(@NonNull View itemView, int pos) {
            super(itemView);
            this.adapterPosition = pos;
            tvName = itemView.findViewById(R.id.tvName);
            linear_contact = itemView.findViewById(R.id.linear_contact);
        }
    }
}