package ansteph.cct.healthscreener.quizboard.healthscreening;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ActionBar.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.localstorage.DbHelper;
import ansteph.cct.healthscreener.quizboard.QuizActivity;
import ansteph.cct.healthscreener.quizboard.QuizDashboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    public DbHelper databhelper ;

    public static String TESTID;
    public static boolean isOpen= false;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ResultFragment newInstance(int sectionNumber) {
        ResultFragment fragment = new ResultFragment ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_result, container, false);

        LinearLayout lresult =  (LinearLayout) rootView.findViewById(R.id.linResult);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        ControllerQuiz conQuiz = ( ControllerQuiz) getActivity().getApplicationContext();
        TextView txtQuCategory = (TextView) rootView.findViewById(R.id.txtTitle);

        txtQuCategory.setText("Currently screening: "+conQuiz.getQuizzedPupil().getFirstname()+" "+conQuiz.getQuizzedPupil().getSurname());
        Drawable img =null;
        if(conQuiz.getQuizzedPupil().getGender().equalsIgnoreCase("female"))
        {
            img = getActivity().getResources().getDrawable(R.mipmap.ic_girl);
        }else{
            img = getActivity().getResources().getDrawable(R.mipmap.ic_boy);}
        txtQuCategory.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        if(isOpen) {

            for (int i = 0; i < conQuiz.getOpenListSize(); i++) {
                TextView txtQuestion = new TextView(getActivity());
                params.topMargin=20;
                params.bottomMargin=20;
                txtQuestion.setText("Q: "+conQuiz.getOpenQuestion(i).getDescr()+" -A: "+ conQuiz.getOpenAnswer(i));
                txtQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                lresult.addView(txtQuestion, params);
            }

        }else{
            for (int i = 0; i < conQuiz.getCloseListSize(); i++) {
                TextView txtQuestion = new TextView(getActivity());
                params.topMargin = 20;
                params.bottomMargin = 20;
                //txtQuestion.setText("Q: "+conQuizz.getCloseQuestion(i).getDescr()+" - A: "+  (conQuizz.getCloseAnswer(i)) != null?"Yes":"No"    Boolean.valueOf(conQuizz.getCloseAnswer(i)).toString());
                txtQuestion.setText("Q: " + conQuiz.getCloseQuestion(i).getDescr() + " - A: " + (conQuiz.getCloseAnswer(i) == null ? "Uncertain" : (conQuiz.getCloseAnswer(i) == true ? "Yes" : "No")));  //(conQuizz.getCloseAnswer(i) == true?"Yes":"No")

                //(conQuizz.getCloseAnswer(i) =!null ?(conQuizz.getCloseAnswer(i) == true?"Yes":"No"):"Uncertain")

                txtQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                lresult.addView(txtQuestion, params);
            }
        }
//Added referral details
        TextView txtHasDetail = new TextView(getActivity());
        txtHasDetail.setText("Referral Details");
        txtHasDetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);

        TextView txtHasref = new TextView(getActivity());
        txtHasref.setText("Has referral: "+ Boolean.valueOf(conQuiz.getQuizzReferral().getHasReferral()).toString());
        txtHasref.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView txtReferee = new TextView(getActivity());
        txtReferee.setText("Referral: "+conQuiz.getQuizzReferral().getReferee() );
        txtReferee.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView txtDateref = new TextView(getActivity());
        txtDateref.setText("Date of referral: "+conQuiz.getQuizzReferral().getDateOfReferal().toString());
        txtDateref.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView txtDatefoll = new TextView(getActivity());
        txtDatefoll.setText("Date of follow-up: "+conQuiz.getQuizzReferral().getDateOfFollowUp().toString());
        txtDatefoll.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        //adding to linear layout
        lresult.addView(txtHasDetail, params);
        lresult.addView(txtHasref, params);
        lresult.addView(txtReferee, params);
        lresult.addView(txtDateref, params);
        lresult.addView(txtDatefoll, params);

        Button btnSave = ( Button) rootView.findViewById(R.id.btnSave) ;

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                InsertCloseAnswerLocalStorage ();
                /**	InsertCloseAnswer insclo = new InsertCloseAnswer(); // network action
                 insclo.execute(new String[]{url_insert_closeanswer, url_insert_alt});*/
            }
        });

        Button btnCancel = (Button) rootView.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, QuizDashboard.newInstance(0))
                        .commit();
            }
        });

        Button btnBack = (Button) rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, ScreeningCloseEnded.newInstance(3,1))
                        .commit();

            }
        });

        try {
            databhelper= new DbHelper(getActivity());
            databhelper.createDatabase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((QuizActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }




    public void InsertCloseAnswerLocalStorage(){

       boolean isEntered= false;

       ProgressDialog Dialog = new ProgressDialog(getActivity());
        Dialog.setMessage("Please wait...");
        Dialog.show();
        ControllerQuiz conQuiz= (ControllerQuiz)getActivity().getApplicationContext();

        for (int i = 0; i < conQuiz.getCloseListSize(); i++) {

            if(	databhelper.addCLoseAnswerData(conQuiz.getQuizzedPupil().getId(),conQuiz.getCloseQuestion(i).getId(),
                    Boolean.valueOf(conQuiz.getCloseAnswer(i)).toString())){

                isEntered = true;

            }
        }

        if(	isEntered )
            databhelper.addReferralData(conQuiz.getQuizzReferral());
        databhelper.addAlternatetestingData(TESTID, conQuiz.getQuizzedPupil().getId(), "", "");

        Dialog.dismiss();
        if(isEntered)
            showapproved();

    }


    AlertDialog alertDialog=null;

    public void showapproved()
    {


        //copied code
        AlertDialog.Builder builder;
        //AlertDialog alertDialog=null;

        Context mContext =getActivity();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //getSystemService(LAYOUT_INFLATER_SERVICE);


        View layout = inflater.inflate(R.layout.layout_approved, null);

        //(R.layout.layout_approved,(ViewGroup) findViewById(R.id.layout_root));

        TextView text = (TextView) layout.findViewById(R.id.txtMessage);
        text.setText("The screening results have been saved");

        Button btnOk= (Button) layout.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, QuizDashboard.newInstance(0))
                        .commit();
            }
        });

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();


    }

    public void showdisapproved()
    {

        AlertDialog.Builder builder;
        //AlertDialog alertDialog=null;

        Context mContext =getActivity();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        View layout = inflater.inflate(R.layout.layout_dissaproved, null);

        //TextView text = (TextView) layout.findViewById(R.id.txtMessage);
        //text.setText("Oups!Something went wrong");

        Button btnOk= (Button) layout.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, QuizDashboard.newInstance(0))
                        .commit();
            }
        });

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();


    }










}









