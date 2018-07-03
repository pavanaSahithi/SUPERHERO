package pavanasahithi.mymarvel;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    private static int pos;
    ArrayList<AllCharactersPojo> allCharactersPojos;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    final static String DATA="data_pojo";
    final static String POSITION="position";
    final static String CHARACTERPOJO="characterpojo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        pos = getIntent().getIntExtra(POSITION, 0);
        allCharactersPojos = getIntent().getParcelableArrayListExtra(DATA);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);

    }



    public static class PlaceholderFragment extends Fragment {
        public boolean isPresent(String id) {
            Uri uri = ContractClass.TableEntry.CONTENT_URI.buildUpon().appendPath(id).build();
            Cursor cursor = getContext().getContentResolver().query(uri, null, null,
                    null, null);
            int count = cursor.getCount();
            if (count == 0) {
                return false;
            } else {
                return true;
            }
        }
        ImageView image;
        TextView power_stats, biography, appearance, work;
        private String id;
        private String name;
        private String url;
        private String durability;
        private String intelligence;
        private String strength;
        private String speed;
        private String power;
        private String combat;
        private String fullName;
        private String alteregos;
        private String aliases;
        private String birth;
        private String firstApp;
        private String publisher;
        private String alignment;
        private String gender;
        private String race;
        private String height;
        private String weight;
        private String eye;
        private String hair;
        private String occupation;
        private String group;
        private String relatives;
        FavoritesDBHelper favoritesDBHelper;
        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(AllCharactersPojo allCharactersPojo) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelable(CHARACTERPOJO, allCharactersPojo);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_display, container, false);
            image = (ImageView) rootView.findViewById(R.id.id_image);
            power_stats = (TextView) rootView.findViewById(R.id.id_section_powerstats);
            biography = (TextView) rootView.findViewById(R.id.id_section_biography);
            appearance = (TextView) rootView.findViewById(R.id.id_section_appearance);
            work = (TextView) rootView.findViewById(R.id.id_section_work);
            final AllCharactersPojo allCharactersPojo1 = getArguments().getParcelable("characterpojo");
            favoritesDBHelper = new FavoritesDBHelper(getContext());
            id = allCharactersPojo1.getId();
            name = allCharactersPojo1.getName();
            url = allCharactersPojo1.getUrl();
            intelligence = allCharactersPojo1.getIntelligence();
            strength = allCharactersPojo1.getStrength();
            speed = allCharactersPojo1.getSpeed();
            durability = allCharactersPojo1.getDurability();
            power = allCharactersPojo1.getPower();
            combat = allCharactersPojo1.getCombat();
            fullName = allCharactersPojo1.getFull_name();
            alteregos = allCharactersPojo1.getAlter_egos();
            aliases = allCharactersPojo1.getAliases();
            birth = allCharactersPojo1.getPlace_of_birth();
            firstApp = allCharactersPojo1.getFirst_appearance();
            publisher = allCharactersPojo1.getPublisher();
            alignment = allCharactersPojo1.getAlignment();
            gender = allCharactersPojo1.getGender();
            race = allCharactersPojo1.getRace();
            height = allCharactersPojo1.getHeight();
            weight = allCharactersPojo1.getWeight();
            eye = allCharactersPojo1.getEye_color();
            hair = allCharactersPojo1.getHair_color();
            occupation = allCharactersPojo1.getOccupation();
            group = allCharactersPojo1.getGroup_affiliation();
            relatives = allCharactersPojo1.getRelatives();

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isPresent(id)) {
                        Uri uri = ContractClass.TableEntry.CONTENT_URI.buildUpon().appendPath
                                (allCharactersPojo1.getId()).build();
                        int i = getContext().getContentResolver().delete(uri, null, null);
                        Toast.makeText(getContext(), R.string.deleted_from_fav, Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues cv = new ContentValues();
                        cv.put(ContractClass.TableEntry.ColumnId, id);
                        cv.put(ContractClass.TableEntry.ColumnName, name);
                        cv.put(ContractClass.TableEntry.ColumnIntelligence, intelligence);
                        cv.put(ContractClass.TableEntry.ColumnSTRENGTH, strength);
                        cv.put(ContractClass.TableEntry.ColumnSPEED, speed);
                        cv.put(ContractClass.TableEntry.ColumnDURABILITY, durability);
                        cv.put(ContractClass.TableEntry.ColumnPOWER, power);
                        cv.put(ContractClass.TableEntry.ColumnCOMBAT, combat);
                        cv.put(ContractClass.TableEntry.ColumnFullName, fullName);
                        cv.put(ContractClass.TableEntry.ColumnAlterEGOS, alteregos);
                        cv.put(ContractClass.TableEntry.ColumnAliases, aliases);
                        cv.put(ContractClass.TableEntry.ColumnBIRTH, birth);
                        cv.put(ContractClass.TableEntry.ColumnFirstApp, firstApp);
                        cv.put(ContractClass.TableEntry.ColumnPublisher, publisher);
                        cv.put(ContractClass.TableEntry.ColumnAlignment, alignment);
                        cv.put(ContractClass.TableEntry.ColumnGender, gender);
                        cv.put(ContractClass.TableEntry.ColumnRace, race);
                        cv.put(ContractClass.TableEntry.ColumnHeight, height);
                        cv.put(ContractClass.TableEntry.ColumnWeight, weight);
                        cv.put(ContractClass.TableEntry.ColumnEye, eye);
                        cv.put(ContractClass.TableEntry.ColumnHair, hair);
                        cv.put(ContractClass.TableEntry.ColumnOccupation, occupation);
                        cv.put(ContractClass.TableEntry.ColumnAff, group);
                        cv.put(ContractClass.TableEntry.ColumnRelatives, relatives);
                        cv.put(ContractClass.TableEntry.ColumnURL, url);
                        Uri uri = getContext().getContentResolver().insert
                                (ContractClass.TableEntry.CONTENT_URI, cv);
                        if(uri!=null) {
                            Toast.makeText(getContext(), R.string.addFav, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            Picasso.get().load(url).into(image);
            String stats = getString(R.string.displayName)+name+getString(R.string.intelligenceDisplay) +
                    intelligence + getString(R.string.strengthDisplay) + strength + getString(R.string.speedDisplay) + speed +
                    getString(R.string.durabilityDisplay) + durability + getString(R.string.powerDisplay) + power +
                    getString(R.string.combatDisplay) + combat;
            power_stats.setText(stats);
            String bio = getString(R.string.fullnameDisplay) + fullName + getString(R.string.alterEgosDisplay) +
                    alteregos + getString(R.string.aliasesDisplay) + aliases +
                    getString(R.string.birthDisplay) + birth + getString(R.string.firstAppDisplay) + firstApp +
                    getString(R.string.publisherDisplay) + publisher + getString(R.string.alignmentDisplay) + alignment;
            biography.setText(bio);
            String apprnce = getString(R.string.genderDisplay) + gender + getString(R.string.raceDisplay) + race +
                    getString(R.string.heightDisplay) + height + getString(R.string.weightDisplay) + weight +
                    getString(R.string.eyeDisplay) + eye + getString(R.string.hairDisplay) + hair;
            appearance.setText(apprnce);
            String wrk = getString(R.string.occupationDisplay) + occupation + getString(R.string.groupDisplay) +
                    group + getString(R.string.relativesDisplay) + relatives;
            work.setText(wrk);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(allCharactersPojos.get(position));
        }

        @Override
        public int getCount() {
            return allCharactersPojos.size();
        }
    }
}
