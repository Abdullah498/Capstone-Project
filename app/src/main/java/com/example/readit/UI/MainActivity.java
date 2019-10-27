package com.example.readit.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.readit.Room.FavouritesViewModel;
import com.example.readit.model.BookData;
import com.example.readit.R;
import com.example.readit.adapters.BooksAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    final String API_KEY="https://www.googleapis.com/books/v1/volumes?q=harry";
    public ArrayList<BookData> books=new ArrayList<>();
    public static BooksAdapter booksAdapter;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.rv_books)
    RecyclerView recyclerView;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,numberOfColumns());

        recyclerView.setLayoutManager(gridLayoutManager);


        new BooksAsyncTask().execute(API_KEY);


        //setup AdMob

        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/
        MobileAds.initialize(this,"ca-app-pub-5639876944507815~7748021524");
                mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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

    class BooksAsyncTask extends AsyncTask<String,ArrayList<BookData>, ArrayList<BookData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<BookData> doInBackground(String... siteUrl) {
            String text;

            if (books.isEmpty()){
                try {

                    URL url = new URL(siteUrl[0]);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    text = stream2String(inputStream);

                    books = extractFromJson(text);

                    return books;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                return books;
            }
            return books;
        }

        @Override
        protected void onPostExecute(ArrayList<BookData> bookData) {


            progressBar.setVisibility(View.INVISIBLE);

            booksAdapter=new BooksAdapter(MainActivity.this,bookData);
            recyclerView.setAdapter(booksAdapter);
            booksAdapter.notifyDataSetChanged();
        }
    }
    public ArrayList<BookData> extractFromJson(String json){
        ArrayList<BookData> books = new ArrayList<>();
        try {

            JSONObject root = new JSONObject(json);
            JSONArray items = root.getJSONArray("items");

            Log.d("Length of items",Integer.toString(items.length()));
            for (int i=0; i<items.length(); i++){
                if(i==1)
                    continue;
                Log.d("@@@@@@@",Integer.toString(i));
                JSONObject currentBook = items.getJSONObject(i);

                String id = currentBook.getString("id");

                JSONObject volumeInfo =currentBook.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");

                String publisher = volumeInfo.getString("publisher");
                String publishedDate = volumeInfo.getString("publishedDate");
                String description = volumeInfo.getString("description");
                String previewLink = volumeInfo.getString("previewLink");

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String thumbnail = imageLinks.getString("smallThumbnail");

                JSONArray authors=volumeInfo.getJSONArray("authors");
                String author=authors.getString(0);
                Log.d("@@@@@@@",thumbnail);

                books.add(new BookData( id , title,  author,  publisher,  publishedDate,  description,  previewLink,  thumbnail));
            }

        } catch (JSONException e) {
            Log.d("88888888888","");
            e.printStackTrace();
        }

        return books;


    }
    public String stream2String(InputStream inputStream){

        String line;
        StringBuilder text = new StringBuilder();

        BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream));

        try{
            while((line = reader.readLine()) != null){
                text.append(line);
            }
        }catch (IOException e){}

        return text.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.favourite_books:
                Intent intent=new Intent(this,FavouritesActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return true;
    }
}
