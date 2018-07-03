package pavanasahithi.mymarvel;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

public class SuperWidget extends AppWidgetProvider {
    public static String text="";
    final static String MYPREF = "MyPref";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Cursor cursor = null;
        SharedPreferences sharedPreferences;
        Intent intent = new Intent(context, MainActivity.class);
        sharedPreferences = context.getSharedPreferences(MYPREF, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.super_widget);
        if (!sharedPreferences.getBoolean(context.getResources().getString(R.string.isLogged), false)) {
            views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.WidgetLogOff));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } else {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
            cursor = context.getContentResolver().query(ContractClass.TableEntry.CONTENT_URI,
                   null, null, null, null);
           if (cursor.getCount() == 0) {
               views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.noFavAdded));
               appWidgetManager.updateAppWidget(appWidgetId, views);
           } else {
               cursor.moveToFirst();
               do{
                   text = text+"\n"+cursor.getString(1);
               }while (cursor.moveToNext());
                cursor.close();
                views.setTextViewText(R.id.appwidget_text, text);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

