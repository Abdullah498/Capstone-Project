package com.example.readit.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.readit.R;
import com.example.readit.Room.FavouritesViewModel;
import com.example.readit.adapters.BooksAdapter;
import com.example.readit.model.BookData;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    @BindView(R.id.rv_favouriteBooks)
    RecyclerView recyclerView;

    private BooksAdapter booksAdapter;

    private FavouritesViewModel favouritesViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        ButterKnife.bind(this);

        booksAdapter=new BooksAdapter(this,new ArrayList<BookData>());
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,numberOfColumns());
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(booksAdapter);
        booksAdapter.notifyDataSetChanged();

        favouritesViewModel= ViewModelProviders.of(this).get(FavouritesViewModel.class);
        favouritesViewModel.getBooksListLiveData().observe(FavouritesActivity.this, new Observer<List<BookData>>() {
            @Override
            public void onChanged(List<BookData> books) {
                booksAdapter.setBooks(books);
            }
        });

    }

    //Here you can dynamically calculate the number of columns and the layout will adapt to the screen size and orientation
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
