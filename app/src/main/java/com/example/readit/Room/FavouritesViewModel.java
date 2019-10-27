package com.example.readit.Room;

import android.app.Application;
import android.os.AsyncTask;

import com.example.readit.model.BookData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavouritesViewModel extends AndroidViewModel {
    private final LiveData<List<BookData>> booksListLiveData;

    private AppDatabase appDatabase;

    public FavouritesViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance(this.getApplication());

        booksListLiveData = appDatabase.bookDao().getAllBooks();
    }

    public LiveData<List<BookData>> getBooksListLiveData() {
        return booksListLiveData;
    }

    public void deleteBook(BookData book){
        new DeleteItemAsyncTask(appDatabase).execute(book);
    }

    private static class DeleteItemAsyncTask extends AsyncTask<BookData, Void, Void> {

        private AppDatabase appDatabase;

        public DeleteItemAsyncTask(AppDatabase database) {
            this.appDatabase = database;
        }

        @Override
        protected Void doInBackground(BookData... voids) {
            appDatabase.bookDao().deleteBook(voids[0].getId());
            return null;
        }
    }

}
