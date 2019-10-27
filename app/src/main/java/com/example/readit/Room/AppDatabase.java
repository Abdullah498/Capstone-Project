package com.example.readit.Room;

import android.content.Context;

import com.example.readit.model.BookData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BookData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "booksDB";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if (sInstance==null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract BookDao bookDao();
}
