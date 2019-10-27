package com.example.readit.Room;

import com.example.readit.model.BookData;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    LiveData<List<BookData>> getAllBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(BookData movie);

    @Query("DELETE FROM books WHERE book_id = :bookId")
    void deleteBook(String bookId);

    @Query("DELETE FROM books")
    void deleteAllBooks();
}