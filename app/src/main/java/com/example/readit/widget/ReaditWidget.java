package com.example.readit.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.readit.R;
import com.example.readit.UI.DetailActivity;
import com.example.readit.UI.FavouritesActivity;
import com.squareup.picasso.Picasso;

/**
 * Implementation of App Widget functionality.
 */
public class ReaditWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, FavouritesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.readit_widget);
        views.setTextViewText(R.id.widgetTextView, DetailActivity.nameOfBook);
        Log.d("IIIIIIIIIII",DetailActivity.img);

        if(DetailActivity.img!="")
            Picasso.with(context).load(DetailActivity.img).error(R.drawable.ic_launcher_background).into(views,R.id.widgetImageView,new int[] { appWidgetId });

        views.setOnClickPendingIntent(R.id.widgetTextView, pendingIntent);
        views.setOnClickPendingIntent(R.id.widgetImageView, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

