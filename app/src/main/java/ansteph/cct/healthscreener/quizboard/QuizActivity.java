package ansteph.cct.healthscreener.quizboard;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.StartUp;
import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.quizboard.healthscreening.BMIScreening;
import ansteph.cct.healthscreener.quizboard.healthscreening.ChronicListPage;
import ansteph.cct.healthscreener.quizboard.healthscreening.ScreeningCloseEnded;
import ansteph.cct.healthscreener.quizboard.healthscreening.ScreeningOpenEnded;
import ansteph.cct.healthscreener.slidingmenu.adapter.MyAdapter;

public class QuizActivity extends ActionBarActivity
         {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    String TITLES [];
    int ICONS [] ;/*={R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};*/

    private TypedArray navMenuIcons;
    String NAME = "Currently assessing:";
    String EMAIL ="Loic.Ansteph@random.com";
    int Profile  = R.drawable.girl;

    private Toolbar toolbar;

    RecyclerView mRecyclerView ;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout  mDrawer;
 ControllerQuiz conQuiz;
    ActionBarDrawerToggle mDrawerToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        TITLES = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        ICONS = new int[navMenuIcons.length()];
        for (int i=0; i<navMenuIcons.length();i++)
        {
            ICONS[i]= navMenuIcons.getResourceId(i, -1) ;
        }

        conQuiz = (ControllerQuiz) getApplicationContext();
        EMAIL = conQuiz.getQuizzedPupil().getFirstname()+" "+conQuiz.getQuizzedPupil().getSurname();

        if(conQuiz.getQuizzedPupil().getGender().equalsIgnoreCase("female"))
        {
           Profile  = R.drawable.girl;
        }else{
         Profile  = R.drawable.boy;}





        mRecyclerView =(RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES,ICONS, NAME, EMAIL, Profile,this);
        mRecyclerView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(QuizActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());



                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    mDrawer.closeDrawers();
                    Toast.makeText(QuizActivity.this, "From the main The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    switch (recyclerView.getChildPosition(child))
                    {
                        case 1:     fragmentManager.beginTransaction()
                                    .replace(R.id.container, QuizDashboard.newInstance( 1))
                                    .commit(); break;
                        case 2:     fragmentManager.beginTransaction()
                                    .replace(R.id.container, ScreeningCloseEnded.newInstance(2, 1))
                                    .commit(); break;
                        case 3:fragmentManager.beginTransaction()
                                .replace(R.id.container, ScreeningCloseEnded.newInstance(2, 2))
                                .commit(); break;
                        case 4: fragmentManager.beginTransaction()
                                .replace(R.id.container, ScreeningCloseEnded.newInstance(2, 3))
                                .commit(); break;
                        case 5: fragmentManager.beginTransaction()
                                .replace(R.id.container, ScreeningOpenEnded.newInstance(3))
                                .commit(); break;
                        case 6: fragmentManager.beginTransaction()
                                .replace(R.id.container, BMIScreening.newInstance(4))
                                .commit(); break;
                        case 7: fragmentManager.beginTransaction()
                                .replace(R.id.container, ChronicListPage.newInstance(2))
                                .commit(); break;


                    }



                    return true;

                }

                return false;

            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });





        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);


        mDrawer =(DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        };

        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
       FragmentManager fragmentManager = getSupportFragmentManager();
       fragmentManager.beginTransaction()
                .replace(R.id.container, QuizDashboard.newInstance( 1))
                .commit();
    }



    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


     @Override
     public void onBackPressed(){


      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.quiz, menu);
           // restoreActionBar();
            return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.action_gohome){
            Intent i = new Intent(this, StartUp.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((QuizActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
