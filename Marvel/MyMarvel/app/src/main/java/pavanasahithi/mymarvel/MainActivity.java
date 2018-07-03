package pavanasahithi.mymarvel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    final static String POSITION = "POSITION";
    final static String STATE = "STATE";
    final static String MYPREF="MyPref";
    final static int fav = 1;
    final static int all = 2;
    final static int FAV_ID = 101;
    int posterWidth = 500;
    ProgressDialog progressDialog;
    GridLayoutManager gridLayoutManager;
    JSONConnection jsonConnection;
    RecyclerAdapter recyclerAdapter;
    @BindView(R.id.id_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.id_linear_layout)
    LinearLayout linearLayout;
    ArrayList<AllCharactersPojo> favArrayList;
    static  int position = 0, restore_id = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog=new ProgressDialog(this);
        AdView mAdView;
        mAdView = findViewById(R.id.banner);
        MobileAds.initialize(this, getString(R.string.add_initialize));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        if (isNetworkAvailable()) {
            if (savedInstanceState != null) {
                  restore_id=savedInstanceState.getInt(STATE);
                  position=savedInstanceState.getInt(POSITION);
                if (restore_id == all) {
                    restore_id = all;
                    JSONAsyncTask jsonAsyncTask = new JSONAsyncTask();
                    jsonAsyncTask.execute();
                } else if(restore_id==fav) {
                    restore_id = fav;
                    getSupportLoaderManager().restartLoader(FAV_ID, null, this);
                }

            } else {
                restore_id = all;
                JSONAsyncTask jsonAsyncTask = new JSONAsyncTask();
                jsonAsyncTask.execute();
            }
        } else {
            displayDialog();
        }
    }

    public void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.create();
        builder.setMessage(R.string.noInternet);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(MainActivity.this, R.string.redirecting_fav,
                        Toast.LENGTH_SHORT).show();
                restore_id = fav;
                getSupportLoaderManager().restartLoader(FAV_ID, null, MainActivity.this);
            }
        });
        builder.show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        position = gridLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(POSITION, position);
        outState.putInt(STATE, restore_id);
    }

    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.all_characters) {
            if (isNetworkAvailable()) {
                restore_id = all;
                JSONAsyncTask jsonAsyncTask = new JSONAsyncTask();
                jsonAsyncTask.execute();
            } else {
                displayDialog();
            }
        } else if (id == R.id.all_fav) {
            restore_id = fav;
            getSupportLoaderManager().restartLoader(FAV_ID, null, this);
        } else if (id == R.id.log_out) {
            Intent intent = new Intent(this, LoginActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences(MYPREF, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getResources().getString(R.string.isLogged), false);
            editor.apply();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public String isEmptyFind(String s) {
        if (s.equals(getString(R.string.nullString)))
            return getString(R.string.notKnown);
        return s;
    }

    public AllCharactersPojo loader(String s) {
        AllCharactersPojo charpojo1 = null;
        try {
            JSONObject jsonObject2 = new JSONObject(s);
            String id = jsonObject2.optString(getString(R.string.json_id), null);
            String name = jsonObject2.optString(getString(R.string.json_name), null);
            name = isEmptyFind(name);
            JSONObject powerStatsObject = jsonObject2.getJSONObject(getString(R.string.json_power));
            String intelligence = powerStatsObject.optString(getString(R.string.json_intelligence), null);
            intelligence = isEmptyFind(intelligence);
            String strength = powerStatsObject.optString(getString(R.string.strength), null);
            strength = isEmptyFind(strength);
            String speed = powerStatsObject.optString(getString(R.string.json_speed), null);
            speed = isEmptyFind(speed);
            String durability = powerStatsObject.optString(getString(R.string.json_durability), null);
            durability = isEmptyFind(durability);
            String power = powerStatsObject.optString(getString(R.string.json_power_data), null);
            power = isEmptyFind(power);
            String combat = powerStatsObject.optString(getString(R.string.json_combat), null);
            combat = isEmptyFind(combat);
            JSONObject biographyObject = jsonObject2.getJSONObject(getString(R.string.json_bio));
            String full_name = biographyObject.optString(getString(R.string.json_fullname), null);
            full_name = isEmptyFind(full_name);
            String alter_egos = biographyObject.optString(getString(R.string.json_alter), null);
            alter_egos = isEmptyFind(alter_egos);
            JSONArray aliasesArray = biographyObject.getJSONArray(getString(R.string.json_aliases));
            String alias = "";
            for (int j = 0; j < aliasesArray.length(); j++) {
                alias = alias + aliasesArray.optString(j, null) + ",";
            }
            alias = isEmptyFind(alias);
            String place_of_birth = biographyObject.optString(getString(R.string.json_birth), null);
            place_of_birth = isEmptyFind(place_of_birth);
            String first_appearance = biographyObject.optString(getString(R.string.json_first_app), null);
            first_appearance = isEmptyFind(first_appearance);
            String publisher = biographyObject.optString(getString(R.string.json_publisher), null);
            publisher = isEmptyFind(publisher);
            String alignment = biographyObject.optString(getString(R.string.json_alignment), null);
            alignment = isEmptyFind(alignment);
            JSONObject appearance = jsonObject2.getJSONObject(getString(R.string.json_appearance));
            String gender = appearance.optString(getString(R.string.json_gender), null);
            gender = isEmptyFind(gender);
            String race = appearance.optString(getString(R.string.json_race), null);
            race = isEmptyFind(race);
            JSONArray heightArray = appearance.getJSONArray(getString(R.string.json_height));
            String height = "";
            for (int j = 0; j < heightArray.length(); j++) {
                height = height + heightArray.optString(j, null) + ",";
            }
            height = isEmptyFind(height);
            JSONArray weightArray = appearance.getJSONArray(getString(R.string.json_weight));
            String weight = "";
            for (int j = 0; j < weightArray.length(); j++) {
                weight = weight + weightArray.optString(j, null) + ",";
            }
            weight = isEmptyFind(weight);
            String eye_color = appearance.optString(getString(R.string.json_eye), null);
            eye_color = isEmptyFind(eye_color);
            String hair_color = appearance.optString(getString(R.string.json_hair), null);
            hair_color = isEmptyFind(hair_color);
            JSONObject workObject = jsonObject2.getJSONObject(getString(R.string.json_work));
            String occupation = workObject.optString(getString(R.string.json_occupation), null);
            occupation = isEmptyFind(occupation);
            String base = workObject.optString(getString(R.string.json_base), null);
            base = isEmptyFind(base);
            JSONObject connectionsObject = jsonObject2.getJSONObject(getString(R.string.json_connections));
            String group_affiliation = connectionsObject.optString(getString(R.string.json_group), null);
            group_affiliation = isEmptyFind(group_affiliation);
            String relatives = connectionsObject.optString(getString(R.string.json_relatives), null);
            relatives = isEmptyFind(relatives);
            JSONObject imageobject = jsonObject2.getJSONObject(getString(R.string.json_image));
            String url_string = imageobject.optString(getString(R.string.json_url), null);
            charpojo1 = new AllCharactersPojo(id, name, intelligence,
                    strength, speed, durability, power, combat, full_name, alter_egos, alias, place_of_birth,
                    first_appearance, publisher, alignment, gender, race, height, weight, eye_color, hair_color, occupation,
                    base, group_affiliation, relatives, url_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return charpojo1;
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Nullable
            @Override
            public Object loadInBackground() {
                favArrayList = new ArrayList<>();
                ArrayList<AllCharactersPojo> tempList = new ArrayList<>();
                gridLayoutManager = new GridLayoutManager(MainActivity.this, calculateBestSpanCount(posterWidth));
                Cursor c = getContentResolver().query(ContractClass.TableEntry.CONTENT_URI,
                        null, null, null, null);
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    do {
                        AllCharactersPojo jsonPOJO = new AllCharactersPojo(c.getString(0), c.getString(1), c.getString(2),
                                c.getString(3), c.getString(4), c.getString(5), c.getString(6),
                                c.getString(7), c.getString(8), c.getString(9), c.getString(10), c.getString(11),
                                c.getString(12), c.getString(13), c.getString(14), c.getString(15),
                                c.getString(16), c.getString(17), c.getString(18), c.getString(19),
                                c.getString(19), c.getString(20), c.getString(21), c.getString(22),
                                c.getString(23), c.getString(24));
                        tempList.add(jsonPOJO);
                    } while (c.moveToNext());
                    c.close();
                    favArrayList = tempList;
                } else {
                    final Snackbar snackBar = Snackbar.make(linearLayout, getResources().getString
                            (R.string.noFavAdded), Snackbar.LENGTH_LONG);
                    snackBar.setAction(R.string.dismiss, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                }
                return null;
            }

        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        recyclerAdapter = new RecyclerAdapter(MainActivity.this, favArrayList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        gridLayoutManager.scrollToPosition(position);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (restore_id == fav) {
            restore_id = fav;
            getSupportLoaderManager().restartLoader(FAV_ID, null, this);

        } else if (restore_id == all) {
            if (isNetworkAvailable()) {
                restore_id = all;
                JSONAsyncTask jsonAsyncTask = new JSONAsyncTask();
                jsonAsyncTask.execute();
            } else {
                displayDialog();
            }
        }
    }

    public class JSONAsyncTask extends AsyncTask<String, Void, ArrayList<AllCharactersPojo>> {
        String s;
        ArrayList<AllCharactersPojo> charlist = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.loadingData));
            progressDialog.show();
        }

        @Override
        protected ArrayList<AllCharactersPojo> doInBackground(String... strings) {
            for (int i = 1; i < 21; i++) {
                String character = getString(R.string.url);
                AllCharactersPojo samplePojo;
                URL url = jsonConnection.buildUrl(character + i);
                String response = null;
                try {
                    response = jsonConnection.getTheResponse(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                samplePojo = loader(response);
                charlist.add(samplePojo);
            }
            return charlist;
        }


        @Override
        protected void onPostExecute(ArrayList<AllCharactersPojo> arraylist) {
            super.onPostExecute(arraylist);
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            gridLayoutManager = new GridLayoutManager(MainActivity.
                    this, calculateBestSpanCount(posterWidth));
            recyclerAdapter = new RecyclerAdapter(MainActivity.this, arraylist);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(recyclerAdapter);
            gridLayoutManager.scrollToPosition(position);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
