package ansteph.cct.healthscreener;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import ansteph.cct.healthscreener.entity.Assent;
import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.localstorage.DbHelper;
import ansteph.cct.healthscreener.quizboard.QuizActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
//
 * to handle interaction events.
 */
public class assent_form_fragment extends Fragment {

   // private OnFragmentInteractionListener mListener;
    ControllerQuiz conQuiz;
    public DbHelper databhelper ;
    public assent_form_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_assent_form, container, false);
        conQuiz = (ControllerQuiz) getActivity().getApplicationContext();

        try {
            databhelper= new DbHelper(getActivity());
            databhelper.createDatabase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        TextView txtassent = (TextView) rootView.findViewById(R.id.txtAssent);
        String msg= "I " + conQuiz.getQuizzedPupil().getFirstname() +" "+ conQuiz.getQuizzedPupil().getSurname()+", aged "
                + getAge(conQuiz.getQuizzedPupil().getDOB()) + " years, hereby assent/do not assent to participate in the Integrated School Health Programme. I understand that the participation in the Integrated School Health Programme is voluntarily and that I may choose to undergo all or some of the screening tests.";

        txtassent.setText(msg);

        final EditText editPreEXist = (EditText) rootView.findViewById(R.id.editPreCondition);
        //getAge(conQuiz.getQuizzedPupil().getDOB())  editPreCondition
        Button btnAccept= (Button) rootView.findViewById(R.id.btnAccept);
        final RadioButton radYes = (RadioButton) rootView.findViewById(R.id.radYes);

        btnAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Calendar cal = Calendar.getInstance();
                Date asDate = new Date (cal.getTimeInMillis());
                Assent as = new Assent(conQuiz.getQuizzedPupil().getId(),editPreEXist.getText().toString(),asDate ,(radYes.isChecked())? true: false);
                databhelper.addAssentData(as);

                Intent i = new Intent(getActivity() , QuizActivity.class);
                startActivity(i);
                //getAttempt();
            }
        });

        Button btnDecline =  (Button) rootView.findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                search_fragment fragment = new search_fragment();
                // In case this activity was started with special instructions from an Intent,
                // pass the Intent's extras to the fragment as arguments

                // fragment.setArguments(getIntent().getExtras());
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragTrans = fm.beginTransaction();
                fragTrans.replace(R.id.frame_container, fragment);
                // Add the fragment to the 'container' FrameLayout
                fragTrans.commit();
            }
        });

        return rootView;
    }

    public int getAge(Date dob)
    {
        if(dob!=null)
        {
            Calendar birth = Calendar.getInstance();
            birth.setTime(dob);
            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR)- birth.get(Calendar.YEAR);

            if((birth.get(Calendar.MONTH)<today.get(Calendar.MONTH))||
                    ((birth.get(Calendar.MONTH)==today.get(Calendar.MONTH)) &&(birth.get(Calendar.DAY_OF_MONTH)<today.get(Calendar.DAY_OF_MONTH)) ))
            {
                --age;
            }

            if(age<0) age= 0;

            return age;
        }else{return 0;}

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
