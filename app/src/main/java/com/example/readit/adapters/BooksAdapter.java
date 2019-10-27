package com.example.readit.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.readit.model.BookData;
import com.example.readit.UI.DetailActivity;
import com.example.readit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private static final String TAG = BooksAdapter.class.getSimpleName();
    ArrayList<BookData> books = new ArrayList<>();
    private Context mcontext;


    public BooksAdapter(Context context, ArrayList<BookData> books) {
        mcontext=context;
        this.books=books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.book_poster,viewGroup,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {

        final BookData bookData=books.get(position);
        Log.d("onBindViewHolder",bookData.getThumbnail());
        Picasso.with(mcontext).load(bookData.getThumbnail()).into(holder.poster);
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, DetailActivity.class);
                intent.putExtra("id",bookData.getId());
                intent.putExtra("thumbnail",bookData.getThumbnail());
                intent.putExtra("title",bookData.getTitle());
                intent.putExtra("description",bookData.getDescription());
                intent.putExtra("publishedDate",bookData.getPublishedDate());
                intent.putExtra("author",bookData.getauthor());
                intent.putExtra("publisher", bookData.getPublisher());
                intent.putExtra("previewLink", bookData.getPreviewLink());
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.d("number of books",Integer.toString(books.size()));
        return this.books.size();
    }

    public void setBooks(List<BookData> books) {
        this.books= (ArrayList<BookData>) books;
        notifyDataSetChanged();
    }


    class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public BookViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_book_poster);
        }
    }
}
