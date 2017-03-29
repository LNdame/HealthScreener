package ansteph.cct.healthscreener;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.Nurse;
import ansteph.cct.healthscreener.quizboard.QuizDashboard;
import ansteph.cct.healthscreener.quizboard.healthscreening.BMIScreening;
import ansteph.cct.healthscreener.quizboard.healthscreening.ChronicListPage;
import ansteph.cct.healthscreener.quizboard.healthscreening.ScreeningCloseEnded;
import ansteph.cct.healthscreener.quizboard.healthscreening.ScreeningOpenEnded;
import ansteph.cct.healthscreener.report.Report;
import ansteph.cct.healthscreener.slidingmenu.adapter.MyAdapter;


public class StartUp extends ActionBarActivity {

    private Toolbar toolbar;

    String TITLES [];
    int ICONS [];

    private TypedArray navMenuIcons;
    String NAME = "Currently logged in:";
    String EMAIL ="admin";
    int Profile  = R.drawable.nurse;

    RecyclerView mRecyclerView ;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout mDrawer;
    ControllerQuiz conQuiz;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        conQuiz= (ControllerQuiz) getApplicationContext();

        TITLES = getResources().getStringArray(R.array.nav_drawer_start_items);

        Nurse logNurse =conQuiz.getLoggedNurse();
        EMAIL = logNurse.getFirstname()+" "+logNurse.getSurname();

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_start_icons);
        ICONS = new int[navMenuIcons.length()];
        for (int i=0; i<navMenuIcons.length();i++)
        {
            ICONS[i]= navMenuIcons.getResourceId(i, -1) ;
        }

        mRecyclerView =(RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(TITLES,ICONS, NAME, EMAIL, Profile,this);
        mRecyclerView.setAdapter(mAdapter);
        //add the touch gesture here
        final GestureDetector mGestureDetector = new GestureDetector(StartUp.this, new GestureDetector.SimpleOnGestureListener() {

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
                    //Toast.makeText(QuizActivity.this, "From the main The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                     Intent i=null;

                    switch (recyclerView.getChildPosition(child))
                    {

                        case 1:  i = new Intent(getApplicationContext(), Report.class);    break;
                        case 2: i = new Intent(getApplicationContext(), SignIn.class)  ; break;

                    }

                    startActivity(i);

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

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), RegisterPupil.class);
                startActivity(i);
            }
        });

        Button btnRegisterSchool  = (Button) findViewById(R.id.btnRegisterSchool);
        btnRegisterSchool.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), RegisterSchool.class);
                startActivity(i);
            }
        });

        Button btnEdit  = (Button) findViewById(R.id.btnEditPupil);
        btnEdit .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), EditPupil.class);
                startActivity(i);
            }
        });

        Button btnSearch  = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), SearchPupil.class);
                startActivity(i);
            }
        });

        Button btnExport  = (Button) findViewById(R.id.btnExport);
        btnExport  .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), ExportData.class);
                startActivity(i);
            }
        });

        Button btnEditSchool=  (Button) findViewById(R.id.btnEditSchool);
        btnEditSchool.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), EditSchool.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_up, menu);
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

        if (id == R.id.action_showrep) {
            Intent i = new Intent(getApplicationContext(), Report.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
