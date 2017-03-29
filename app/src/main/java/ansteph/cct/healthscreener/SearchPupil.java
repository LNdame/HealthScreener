package ansteph.cct.healthscreener;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ansteph.cct.healthscreener.entity.AttemptCount;
import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.Pupil;
import ansteph.cct.healthscreener.localstorage.DbHelper;


public class SearchPupil extends ActionBarActivity  {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pupil);

      toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if(findViewById(R.id.frame_container)!=null){
            if (savedInstanceState == null) {
                return;
            }
            // Create an instance of searchFragment

           // getFragmentManager().beginTransaction().add(R.id.frame_container, searchfrg).commit();
        }



    }




    @Override
    protected void onStart()
    {
super.onStart();

        // Create an instance of searchFragment
        search_fragment searchfrg = new search_fragment();
        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        searchfrg.SENDERID =-1;
        searchfrg.setArguments(getIntent().getExtras());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragTrans = fm.beginTransaction();
        fragTrans.replace(R.id.frame_container, searchfrg);
        // Add the fragment to the 'container' FrameLayout
        fragTrans.commit();
    }

    /** // The user selected the headline of an article from the HeadlinesFragment

     // Capture the article fragment from the activity layout
     ArticleFragment articleFrag = (ArticleFragment)
     getSupportFragmentManager().findFragmentById(R.id.article_fragment);

     if (articleFrag != null) {
     // If article frag is available, we're in two-pane layout...

     // Call a method in the ArticleFragment to update its content
     articleFrag.updateArticleView(position);

     } else {
     // If the frag is not available, we're in the one-pane layout and must swap frags...

     // Create fragment and give it an argument for the selected article
     ArticleFragment newFragment = new ArticleFragment();
     Bundle args = new Bundle();
     args.putInt(ArticleFragment.ARG_POSITION, position);
     newFragment.setArguments(args);
     FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

     // Replace whatever is in the fragment_container view with this fragment,
     // and add the transaction to the back stack so the user can navigate back
     transaction.replace(R.id.fragment_container, newFragment);
     transaction.addToBackStack(null);

     // Commit the transaction
     transaction.commit();
     }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_pupil, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */



    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        ArrayList<Pupil> pupilList = new ArrayList<Pupil>();
        EditText txtSurname;
        EditText txtGrade;
        boolean isSurnameChecked = true;
        public DbHelper databhelper ;
        ControllerQuiz conQuizz;
        ArrayList <String> value;
        ListView listpup;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search_pupil, container, false);

            try {
                databhelper= new DbHelper(getActivity());
                databhelper.createDatabase();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Button btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(this);

            txtSurname = (EditText) rootView.findViewById(R.id.editSurname);
            txtGrade = (EditText) rootView.findViewById(R.id.editGrade);
            final TableRow tbrg = (TableRow) rootView.findViewById(R.id.tbrGrade);
            final TableRow tbrs = (TableRow) rootView.findViewById(R.id.tbrSurname);

            RadioGroup radCrit = (RadioGroup) rootView.findViewById(R.id.radgrSelected);
            radCrit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    switch (checkedId) {
                        case R.id.radSurname:
                            tbrs.setVisibility(View.VISIBLE);
                            tbrg.setVisibility(View.GONE);
                            tbrs.setFocusable(true);
                            isSurnameChecked = true;
                            break;
                        case R.id.radGrade:
                            tbrg.setVisibility(View.VISIBLE);
                            tbrs.setVisibility(View.GONE);
                            tbrg.setFocusable(true);
                            isSurnameChecked = false;
                            break;
                        default:
                            tbrs.setVisibility(View.VISIBLE);
                            tbrg.setVisibility(View.GONE);
                            tbrs.setFocusable(true);
                            isSurnameChecked = true;
                            break;
                    }
                }
            });

            listpup = (ListView) rootView.findViewById(R.id.list);

            value = new ArrayList<String>();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, value);

            listpup.setAdapter(adapter);

            listpup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    conQuizz= (ControllerQuiz) getActivity().getApplicationContext();

                    Pupil quizPupil = new Pupil(
                            pupilList.get(position).getId(),
                            pupilList.get(position).getFirstname(),
                            pupilList.get(position).getSurname(),
                            pupilList.get(position).getGrade(),
                            pupilList.get(position).getGender(),
                            pupilList.get(position).getAddline1(),
                            pupilList.get(position).getAddline2(),""
                    );
                    quizPupil.setDOB(pupilList.get(position).getDOB());
                    conQuizz.setQuizzedPupil(quizPupil);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            return rootView;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId())
            {

                case R.id.btnSearch:getPupilfromLocalSt();


                   Fragment frag = new assent_form_fragment();
                    getActivity().getActionBar().setTitle("Assent Form");
                    Bundle args = new Bundle();

                    frag.setArguments(args);


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.container, frag);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    /**	GetPupil gtpup = new GetPupil(); // activate for network action
                     if(isSurnameChecked)

                     gtpup.execute(new String[]{url_get_pupil_surname})	;
                     else
                     gtpup.execute(new String[]{url_get_pupil_grade })	;*/

                    break;
                default: break;



            }


        }

        public void showPupilList( String[] itemLst)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select a Pupil");
            builder.setInverseBackgroundForced(true);
            //builder.setCancelable(true);
            builder.setItems(itemLst, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int selected) {
                    // TODO Auto-generated method stub
                    conQuizz= (ControllerQuiz) getActivity().getApplicationContext();

                    Pupil quizPupil = new Pupil(
                            pupilList.get(selected).getId(),
                            pupilList.get(selected).getFirstname(),
                            pupilList.get(selected).getSurname(),
                            pupilList.get(selected).getGrade(),
                            pupilList.get(selected).getGender(),
                            pupilList.get(selected).getAddline1(),
                            pupilList.get(selected).getAddline2(),""
                    );
                    quizPupil.setDOB(pupilList.get(selected).getDOB());
                    conQuizz.setQuizzedPupil(quizPupil);
                    //sent to new activities
                    /**GetPupilAttemptCount att = new GetPupilAttemptCount(); //network action
                     att.execute(new String[]{url_get_pupil_attempt});*/
                   // showConsent();
                    //	getAttempt();

                }
            });

            AlertDialog userlistDialog = builder.create();

            userlistDialog.show();
        }


        public void getPupilfromLocalSt() {
            pupilList.clear();
            value.clear();
            String param = "";
            if (isSurnameChecked)
                param = txtSurname.getText().toString();
            else
                param = txtGrade.getText().toString();


            if (isSurnameChecked)
                pupilList = (ArrayList<Pupil>) databhelper.getAllPupilDatabySurname(param);
            else
                pupilList = (ArrayList<Pupil>) databhelper.getAllPupilDatabyGrade(param);


            if (pupilList.size() > 0) {
               // String[] item = new String[pupilList.size()];
                String item="";
                int i = 0;
                for (Pupil ap : pupilList) {
                    item = ap.getFirstname() + " " + ap.getSurname();
                    value.add(item);
                    i++;
                }
                ((ArrayAdapter<Object>) listpup.getAdapter()).notifyDataSetChanged();
               // showPupilList(item);

            } else {
                Toast.makeText(getActivity(), "No Result found", Toast.LENGTH_SHORT).show();
            }

        }


        public void getAttempt() {
            AttemptCount attcnt = new AttemptCount(0, 0, 0, 0, 0);  //openc=0, hearc=0,visc=0,oralc=0;

            attcnt = databhelper.getPupilAttempt(conQuizz.getQuizzedPupil().getId());
            conQuizz.setattcount(attcnt);

           /* Intent i = new Intent(getActivity().getApplicationContext(), QuizzActivity.class);
            i.putExtra("bmi", attcnt.getBmiCount());
            i.putExtra("close", attcnt.getCloseQuizzCount());
            i.putExtra("open", attcnt.getOpenQuizzCount());
            i.putExtra("hearing", attcnt.getHearingCount());
            i.putExtra("vision", attcnt.getVisionCount());
            i.putExtra("oral", attcnt.getOralCount());
            startActivity(i);*/

        }

    }
    public class SearchArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap= new HashMap<String, Integer>();

        public SearchArrayAdapter(Context context,
                                  int textViewResourceId, List<String> objects) {
            super(context,  textViewResourceId, objects);
            // TODO Auto-generated constructor stub
            for (int i = 0; i < objects.size(); i++) {
                mIdMap.put(objects.get(i), i);
            }
        }
        @Override
        public long getItemId(int position)
        {
            String item =getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }
    }

}
