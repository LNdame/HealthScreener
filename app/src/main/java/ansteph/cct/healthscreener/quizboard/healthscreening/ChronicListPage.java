package ansteph.cct.healthscreener.quizboard.healthscreening;

import android.app.Activity;



import android.support.v4.app.Fragment;

import android.os.Bundle;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ansteph.cct.healthscreener.R;

import ansteph.cct.healthscreener.quizboard.QuizActivity;
import ansteph.cct.healthscreener.quizboard.QuizDashboard;
import ansteph.cct.healthscreener.quizboard.healthscreening.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>

 * interface.
 */
public class ChronicListPage extends ListFragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    Fragment frag;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public static  ChronicListPage newInstance(int sectionNumber) {
        ChronicListPage  fragment = new ChronicListPage ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
       // args.putInt(CAT_ID, catID);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chronicd, container, false);
        setTitle();
        String [] screentest = getActivity().getResources().getStringArray(R.array.chronic_disease);

        //ListAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.chronic_list_item, screentest, to)

        ArrayAdapter< String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.chronic_list_item, R.id.txtscreentext, screentest);
        setListAdapter(adapter);

        Button btnback =  (Button) rootView.findViewById(R.id.imgbtnBack);
        btnback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, QuizDashboard.newInstance(1))
                        .commit();

            }
        });

        return rootView;
        // TODO: Change Adapter to display your content

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((QuizActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

        //  ((QuizActivity) activity).setTitle("Oral health Screening");
    }
    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }



    private void setTitle()
    {
        String title ="Chronic Disease Assessment";


        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(title);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        int catid= 1;

        switch (position)
        {
            case 0: catid = 6; break;
            case 1: catid = 13; break;
            case 2: catid = 14; break;
            case 3: catid = 15; break;
            case 4: catid = 16; break;
            case 5: catid = 17; break;
            case 6: catid = 18; break;

        }

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.container, ChronicDisease.newInstance(2,catid))
                .commit();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
