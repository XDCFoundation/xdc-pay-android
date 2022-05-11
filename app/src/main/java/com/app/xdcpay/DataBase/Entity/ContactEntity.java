package com.app.xdcpay.DataBase.Entity;


import static com.app.xdcpay.Utils.DatabaseConstants.CONTACT_FIELD_1;
import static com.app.xdcpay.Utils.DatabaseConstants.CONTACT_FIELD_2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.xdcpay.Utils.DatabaseConstants;

@Entity(tableName = DatabaseConstants.CONTACT_TABLE_NAME)
public class ContactEntity {
    @PrimaryKey(autoGenerate = true)
    public int contactId;

    @ColumnInfo(name = CONTACT_FIELD_1)
    public String contactName;

    @ColumnInfo(name = CONTACT_FIELD_2)
    public String contactAddress;

}
