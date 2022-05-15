package com.app.xdcpay.Activities.Contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Adapters.ContactAdapter;
import com.app.xdcpay.Adapters.NetworkListAdapter;
import com.app.xdcpay.DataBase.Entity.ContactEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactsActivity extends BaseActivity {
    private ImageView ivAdd;
    private TextViewMedium tvTitle, tvNoData;
    private ImageView back;
    private LinearLayout linear_contact;
    private RecyclerView recycler_Contacts;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    @Override
    public void getId() {
        ivAdd = findViewById(R.id.ivAdd);
        tvTitle = findViewById(R.id.tvTitle);
        back = findViewById(R.id.ivBack);
        tvNoData = findViewById(R.id.tvNoData);
        recycler_Contacts = findViewById(R.id.recycler_Contacts);
        linear_contact = findViewById(R.id.linear_contact);
        setData();

    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        recycler_Contacts.setOnClickListener(this);
        linear_contact.setOnClickListener(this);
    }

    @Override
    public void setData() {
        tvTitle.setText(getText(R.string.contacts));
        List<ContactEntity> entity = NetworkDataBase.getInstance(getApplicationContext()).getContactDao().getContactList();
        if (entity.size() > 0) {
            Collections.sort(entity, new Comparator<ContactEntity>() {

                @Override
                public int compare(ContactEntity lhs, ContactEntity rhs) {

                    char l = Character.toUpperCase(lhs.contactName.charAt(0));

                    if (l < 'A' || l > 'Z')

                        l += 'Z';

                    char r = Character.toUpperCase(rhs.contactName.charAt(0));

                    if (r < 'A' || r > 'Z')

                        r += 'Z';

                    String s1 = l + lhs.contactName.substring(1);

                    String s2 = r + rhs.contactName.substring(1);

                    return s1.compareTo(s2);
                }
            });
            contactAdapter = new ContactAdapter(ContactsActivity.this, entity);

            recycler_Contacts.setLayoutManager(new LinearLayoutManager(this));
            recycler_Contacts.setHasFixedSize(true);
            recycler_Contacts.setAdapter(contactAdapter);
            tvNoData.setVisibility(View.GONE);
            recycler_Contacts.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            recycler_Contacts.setVisibility(View.GONE);
        }

/*        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
//                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recycler_Contacts);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAdd:
                Intent intentContact = new Intent(ContactsActivity.this, AddContactActivity.class);
                startActivity(intentContact);
                finish();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
          /*  case R.id.linear_contact:
                Intent intent = new Intent(ContactsActivity.this, ContactDetailsActivity.class);
                startActivity(intent);
                finish();
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}