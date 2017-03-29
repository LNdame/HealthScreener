package ansteph.cct.healthscreener.report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ansteph.cct.healthscreener.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PupilAssessed extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public  static PupilAssessed newInstance(int sectionNumber) {
        // Required empty public constructor
        PupilAssessed  fragment = new PupilAssessed ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pupil_assessed, container, false);
    }


}
