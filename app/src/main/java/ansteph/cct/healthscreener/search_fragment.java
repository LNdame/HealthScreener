package ansteph.cct.healthscreener;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
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

import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.Pupil;
import ansteph.cct.healthscreener.localstorage.DbHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 */
public class search_fragment extends Fragment implements View.OnClickListener {

  //  private OnFragmentInteractionListener mListener;
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    public  static int SENDERID=-1;

    ArrayList<Pupil> pupilList = new ArrayList<Pupil>();
    EditText txtSurname;
    EditText txtGrade;
    boolean isSurnameChecked = true;
    public DbHelper databhelper ;
    ControllerQuiz conQuiz;
    ArrayList <String> value;
    ListView listpup;
    public search_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_search_pupil, container, false);

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
                conQuiz = (ControllerQuiz) getActivity().getApplicationContext();

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
                conQuiz.setQuizzedPupil(quizPupil);

                if(SENDERID !=-1)
                {showEditPupFragment();}
                else showAssent();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listpup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                conQuiz = (ControllerQuiz) getActivity().getApplicationContext();

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
                conQuiz.setQuizzedPupil(quizPupil);

                if(SENDERID !=-1)
                {showEditPupFragment();}
                else showAssent();
            }
        });

        Button btnBack = (Button) rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), StartUp.class);
                startActivity(i);
            }
        });
        
        return rootView;
    }


    public void showAssent()
    {
        assent_form_fragment fragment = new assent_form_fragment();
        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments

        // fragment.setArguments(getIntent().getExtras());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragTrans = fm.beginTransaction();
        fragTrans.replace(R.id.frame_container, fragment);
        // Add the fragment to the 'container' FrameLayout
        fragTrans.commit();
    }


    public void showEditPupFragment()
    {
        EditPupilFragment fragment = new  EditPupilFragment();
        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments

        // fragment.setArguments(getIntent().getExtras());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragTrans = fm.beginTransaction();
        fragTrans.replace(R.id.frame_container, fragment);
        // Add the fragment to the 'container' FrameLayout
        fragTrans.commit();
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {

            case R.id.btnSearch:getPupilfromLocalSt();


               /* Fragment frag = new assent_form_fragment();
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
}
