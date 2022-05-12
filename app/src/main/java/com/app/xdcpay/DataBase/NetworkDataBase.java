package com.app.xdcpay.DataBase;

import static com.app.xdcpay.Utils.DatabaseConstants.DATABASE_NAME;
import static com.app.xdcpay.Utils.DatabaseConstants.DATABASE_VERSION;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.app.xdcpay.DataBase.DAO.AddNetworkDAO;
import com.app.xdcpay.DataBase.DAO.Add_ImportAccountDao;
import com.app.xdcpay.DataBase.DAO.TransactionsDao;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.Entity.TransactionsEntity;

@Database(entities = {NetworkEntity.class, AccountEntity.class, TransactionsEntity.class}, version = DATABASE_VERSION)
public abstract class NetworkDataBase extends RoomDatabase {

    // below line is to create instance for our database class.
    private static NetworkDataBase instance;

    // below line is to create abstract variable for dao.
    public abstract AddNetworkDAO getDatabaseDao();

    public abstract Add_ImportAccountDao getAccountDao();

    public abstract TransactionsDao getTransactionsDao();

    public static synchronized NetworkDataBase getInstance(Context context) {
        if (instance == null) {
            // if the instance is null we are creating a new instance
            instance = Room.databaseBuilder(context, NetworkDataBase.class, DATABASE_NAME).allowMainThreadQueries().build();
            //                    .fallbackToDestructiveMigration()
            //                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(NetworkDataBase instance) {
//            AddNetworkDAO dao = instance.getDatabaseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
