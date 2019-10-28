package com.example.readit.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readit.R;
import com.example.readit.Room.AppDatabase;
import com.example.readit.Room.FavouritesViewModel;
import com.example.readit.model.BookData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static String nameOfBook="";
    public static String img="";


    @BindView(R.id.main_title)
    Toolbar toolbar;
    @BindView(R.id.main_poster)
    ImageView mainPoster;
    @BindView(R.id.previewBtn)
    Button previewBtn;
    @BindView(R.id.favouritesBtn)
    Button favouritesBtn;
    @BindView(R.id.author)
    TextView authorTV;
    @BindView(R.id.description)
    TextView descriptionTV;
    @BindView(R.id.publisher)
    TextView publisherTV;
    @BindView(R.id.publishedDate)
    TextView publishedDateTV;


    BookData bookData;
    FavouritesViewModel favouritesViewModel;
    AppDatabase appDatabase;
    private int favFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        favouritesViewModel= ViewModelProviders.of(this).get(FavouritesViewModel.class);
        appDatabase= AppDatabase.getInstance(this);

        final String id=getIntent().getStringExtra("id");

        final String thumbnail=getIntent().getStringExtra("thumbnail");
        final String title=getIntent().getStringExtra("title");
        String author=getIntent().getStringExtra("author");
        String description=getIntent().getStringExtra("description");
        String publisher=getIntent().getStringExtra("publisher");
        String publishedDate=getIntent().getStringExtra("publishedDate");

        final String previewLink = getIntent().getStringExtra("previewLink");


        bookData=new BookData( id ,  title,  author,  publisher,  publishedDate,  description,  previewLink,  thumbnail);


        Picasso.with(DetailActivity.this).load(thumbnail).error(R.drawable.ic_launcher_background).into(mainPoster);
        toolbar.setTitle(title);
        authorTV.setText(author);
        descriptionTV.setText(description);
        publisherTV.setText(publisher);
        publishedDateTV.setText(publishedDate);

        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(previewLink));
                startActivity(intent);
            }
        });
        
        
        //add to favourites : 


        favouritesViewModel.getBooksListLiveData().observe(DetailActivity.this, new Observer<List<BookData>>() {
            @Override
            public void onChanged(List<BookData> books) {
                for (int i=0;i<books.size();i++){
                    if (id.equals(books.get(i).getId())) {
                        favouritesBtn.setBackgroundColor(getResources().getColor(R.color.white));
                        favouritesBtn.setText(R.string.added_to_favourites);
                        favFlag = 1;
                        break;
                    }
                }

            }
        });


        favouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favFlag==0){
                    new AddToFavouriteAsyncTask().execute();
                    favouritesBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    favouritesBtn.setText(R.string.added_to_favourites);

                    favFlag=1;

                    nameOfBook=title;
                    img=thumbnail;

                }else{
                    favouritesViewModel.deleteBook(bookData);
                    favouritesBtn.setBackgroundResource(R.color.red);
                    favouritesBtn.setText(R.string.Add_to_favourits);

                    favFlag=0;

                    Toast.makeText(getApplicationContext(), R.string.book_deleted, Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    public class AddToFavouriteAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.bookDao().insertBook(bookData);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), R.string.book_added, Toast.LENGTH_SHORT).show();
        }
    }
}
