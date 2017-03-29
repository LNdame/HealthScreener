package ansteph.cct.healthscreener.quizboard;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.assent_form_fragment;
import ansteph.cct.healthscreener.quizboard.healthscreening.BMIScreening;
import ansteph.cct.healthscreener.quizboard.healthscreening.ChronicListPage;
import ansteph.cct.healthscreener.quizboard.healthscreening.ScreeningCloseEnded;
import ansteph.cct.healthscreener.quizboard.healthscreening.ScreeningOpenEnded;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizDashboard extends Fragment  {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static QuizDashboard newInstance(int sectionNumber) {

        QuizDashboard  fragment = new QuizDashboard ();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_quiz_dashboard, container, false);

        setTitle();



        Button btnOral = (Button) rootView.findViewById(R.id.btnOral); //trigger quiizID 1
        Button btnphysical = (Button) rootView.findViewById(R.id.btnphysical);
        Button btnCalcuBmi = (Button) rootView.findViewById(R.id.btnCalBmi);
        Button btnVision = (Button) rootView.findViewById(R.id.btnVision);//trigger quiizID 3
        Button btnHearing = (Button) rootView.findViewById(R.id.btnHearing);//trigger quiizID 2
        Button btnChronic = (Button) rootView.findViewById(R.id.btnChronicDisease);


        btnOral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // getActivity().getActionBar().setTitle("Oral Health Screening");
                ScreeningCloseEnded.TESTID = "2";
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, ScreeningCloseEnded.newInstance(2,1))
                        .commit();
            }
        });

        btnphysical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, ScreeningOpenEnded.newInstance(3))
                        .commit();
            }
        });

        btnCalcuBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, BMIScreening.newInstance(4))
                        .commit();
            }
        });

        btnVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getActivity().getActionBar().setTitle("Oral Health Screening");
                ScreeningCloseEnded.TESTID = "5";
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, ScreeningCloseEnded.newInstance(2,3))
                        .commit();
            }
        });


     btnHearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getActivity().getActionBar().setTitle("Oral Health Screening");
                ScreeningCloseEnded.TESTID = "4";
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, ScreeningCloseEnded.newInstance(2,2))
                        .commit();
            }
        });


        btnChronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getActivity().getActionBar().setTitle("Oral Health Screening");
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, ChronicListPage.newInstance(2))
                        .commit();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((QuizActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private void setTitle()
    {
        String title ="Dashboard";


        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(title);
    }
}
