package com.app.xdcpay.DataBase.Entity;

import static com.app.xdcpay.Utils.DatabaseConstants.CONTACT_FIELD_1;
import static com.app.xdcpay.Utils.DatabaseConstants.CONTACT_FIELD_2;
import static com.app.xdcpay.Utils.DatabaseConstants.CONTACT_FIELD_3;
import static com.app.xdcpay.Utils.DatabaseConstants.CONTACT_TABLE_NAME;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.xdcpay.Utils.DatabaseConstants;

@Entity(tableName = CONTACT_TABLE_NAME)
public class ContactEntity {
    @PrimaryKey(autoGenerate = true)
    public int contactId;

    @ColumnInfo(name = CONTACT_FIELD_1)
    public String contactName;

    @ColumnInfo(name = CONTACT_FIELD_2)
    public String contactAddress;

    @ColumnInfo(name = CONTACT_FIELD_3)
    public String nameFirstLetter;

    public ContactEntity(String contactName, String contactAddress, String nameFirstLetter) {
//        this.contactId = contactId;
        this.contactName = contactName;
        this.contactAddress = contactAddress;
        this.nameFirstLetter = nameFirstLetter;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getNameFirstLetter() {
        return nameFirstLetter;
    }

    public void setNameFirstLetter(String nameFirstLetter) {
        this.nameFirstLetter = nameFirstLetter;
    }
}
