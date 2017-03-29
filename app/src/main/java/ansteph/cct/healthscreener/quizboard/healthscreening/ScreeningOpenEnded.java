package ansteph.cct.healthscreener.quizboard.healthscreening;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.OpCategory;
import ansteph.cct.healthscreener.entity.OpenQuestion;
import ansteph.cct.healthscreener.entity.Referral;
import ansteph.cct.healthscreener.localstorage.DbHelper;
import ansteph.cct.healthscreener.quizboard.QuizActivity;
import ansteph.cct.healthscreener.quizboard.QuizDashboard;

import android.app.ActionBar.LayoutParams;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScreeningOpenEnded extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static ArrayList<OpCategory> OPCAT_LIST= new ArrayList<OpCategory>();
    static ArrayList<OpenQuestion> OPE_QU_LIST = new ArrayList<OpenQuestion>();
    EditText editReferee;
    TextView txtdateRef;
    TextView txtdatefoll;
    RadioButton radrefyes;
    private LinearLayout lQues;
    public DbHelper databhelper ;
    public static ScreeningOpenEnded newInstance (int sectionNumber) {
        ScreeningOpenEnded  fragment = new ScreeningOpenEnded ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_screening_open_ended, container, false);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Physical health Assessment");

        Button imgbtnback = (Button)rootView.findViewById(R.id.imgbtnBack);
        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, QuizDashboard.newInstance(1))
                        .commit();
            }
        });

        try {
            databhelper= new DbHelper(getActivity());
            databhelper.createDatabase();
        } catch (Exception e) {

            e.printStackTrace();
        }

        ControllerQuiz conQuiz= (ControllerQuiz)getActivity().getApplicationContext();



        OPCAT_LIST = ( ArrayList<OpCategory>)databhelper.getAllOPCategoryData();
        OPE_QU_LIST  =(ArrayList<OpenQuestion>) databhelper.getAllOpenQuestionData();


        editReferee = (EditText) rootView.findViewById(R.id.editReferee);
        txtdateRef = (TextView) rootView.findViewById(R.id.txtdateref);
        txtdatefoll = (TextView) rootView.findViewById(R.id.txtdatefoll);
        radrefyes = (RadioButton) rootView.findViewById(R.id.radrefYes);

        TextView txtQuCategory = (TextView) rootView.findViewById(R.id.txtTitle);

        txtQuCategory.setText("Currently assessing: "+conQuiz.getQuizzedPupil().getFirstname()+" "+conQuiz.getQuizzedPupil().getSurname());
        Drawable img =null;
        if(conQuiz.getQuizzedPupil().getGender().equalsIgnoreCase("female"))
        {
            img = getActivity().getResources().getDrawable(R.mipmap.ic_girl);
        }else{
            img = getActivity().getResources().getDrawable(R.mipmap.ic_boy);}


        txtQuCategory.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        lQues =  (LinearLayout) rootView.findViewById(R.id.linQuestion);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);


        //this for loop for the category
        for (int i = 0; i < OPCAT_LIST.size(); i++) {
            //lsQuest = linear single question


            TextView txtQuCategor = new TextView(getActivity());

            txtQuCategor.setText((i+1)+". " +OPCAT_LIST.get(i).getDescrip());
            txtQuCategor.setTypeface(null, Typeface.BOLD);
            txtQuCategor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            txtQuCategor.setBackgroundResource(R.color.catHighlight);
            params.topMargin=30;
            params.bottomMargin=30;

            lQues.addView(txtQuCategor,params);

            int h=1;//inner counter

            //this for loop for the questions of category
            for (int j = 0; j < OPE_QU_LIST.size(); j++) {

                if(Integer.parseInt(OPCAT_LIST.get(i).getId()) ==  Integer.parseInt( OPE_QU_LIST.get(j).getCatID())){

                    LinearLayout lSquest = new LinearLayout(getActivity());
                    lSquest.setOrientation(LinearLayout.VERTICAL);
                    //Create text
                    TextView txtQuestion = new TextView(getActivity());
                    txtQuestion.setText((i+1)+"."+h+") "+OPE_QU_LIST.get(j).getDescr());
                    txtQuestion.setTypeface(null, Typeface.BOLD_ITALIC);
                    txtQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                    //Create edittext
                    EditText txtAnswer = new EditText(getActivity());
                    //txtAnswer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

                    //dividing line
                    View lineView = new View(getActivity());
                    lineView.setBackgroundColor(Color.DKGRAY);
                    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,1);
                    lp.topMargin = 10;
                    lp.bottomMargin = 10;
                    lp.leftMargin = 10;
                    lp.rightMargin = 10;

                    lineView.setLayoutParams(lp);

                    lSquest.addView(txtQuestion);
                    lSquest.addView(txtAnswer);

                    lSquest.addView(lineView);

                    lQues.addView(lSquest);
                    h++;
                }
            }
            h=1;//reset inner counter to 1
        }


        Button btnSave = ( Button) rootView.findViewById(R.id.btnSave) ;

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                getAnswer();
               ResultFragment.TESTID ="3";
                ResultFragment.isOpen=true;
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container,ResultFragment.newInstance(4))
                        .commit();

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

        ImageButton btnGetDateRef = (ImageButton) rootView.findViewById(R.id.btngetdateref);
        btnGetDateRef.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDatePicker(R.id.btngetdateref);

            }
        });

        ImageButton btnGetDateFoll = (ImageButton) rootView.findViewById(R.id.btngetdatefoll);
        btnGetDateFoll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDatePicker(R.id.btngetdatefoll);
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
    AlertDialog alertDialog=null;
    public  void showDatePicker(final int senderID)
    {

        AlertDialog.Builder builder;
        //AlertDialog alertDialog=null;

        Context mContext =getActivity();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //getSystemService(LAYOUT_INFLATER_SERVICE);


        View layout = inflater.inflate(R.layout.custom_dater, null);

        //set action here
        final DatePicker datref =  (DatePicker) layout.findViewById(R.id.datRef) ;
        datref.init(2014, 0, 1, null);
        Button btnOk= (Button) layout.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String dateref = Integer.toString(datref.getDayOfMonth())+"/"+Integer.toString(datref.getMonth()+1)+"/"+Integer.toString(datref.getYear());    //(new StringBuilder().append(datdob.getYear()))
                switch (senderID) {
                    case R.id.btngetdateref:txtdateRef.setText(dateref);

                        break;
                    case R.id.btngetdatefoll:txtdatefoll.setText(dateref);

                        break;
                    default:
                        break;
                }

                alertDialog.dismiss();
            }
        });

        Button btnCancel =  (Button) layout.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
            }
        });

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();



    }



    public void getAnswer ()
    {
        ControllerQuiz conQuiz= (ControllerQuiz)getActivity().getApplicationContext();
        conQuiz.clearOpenAnswer();
        conQuiz.clearOpenQuestion();

        try {
            int childCount = lQues.getChildCount();

            View v = null;
            for (int i = 0; i < childCount; i++) {
                v= lQues.getChildAt(i);

                //get n linearlayout(with question)
                if(v instanceof LinearLayout)
                {
                    LinearLayout ll = (LinearLayout)v;
                    int subCount =  ll.getChildCount();

                    View innerV = null;
                    boolean token = false;
                    for (int j = 0; j < subCount; j++) {
                        innerV = ll.getChildAt(j);


                        //get question textview and add it to quizz controller
                        if (innerV instanceof EditText && token ) //get the anwser
                        {

                            EditText txtanswer = (EditText) innerV;
                            String ans="Nothing";
                            if(txtanswer.getText().toString()!=null && !txtanswer.getText().toString().isEmpty())
                            {
                                ans = txtanswer.getText().toString();
                            }
                            conQuiz.setOpenAnser(ans);

                        }else if(innerV instanceof TextView)
                        {
                            token = false;// indicate whether the question was capture and the boolean answer should be register

                            TextView tx = (TextView) innerV;
                            String question=(String) tx.getText();

                            String []modques = question.split("\\)");
                            String realQues = modques[1].trim();

                            //add the close question in the controller
                            for (int k = 0; k < OPE_QU_LIST.size(); k++) {
                                if(realQues.equalsIgnoreCase(OPE_QU_LIST.get(k).getDescr())){
                                    conQuiz.setOpenList(OPE_QU_LIST.get(k));
                                    token = true;
                                    break;
                                }
                            }


                        }



                    }
                }
            }





        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getActivity(), "Could not gather the information", Toast.LENGTH_LONG).show();

        }
        //add referall data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        if(radrefyes.isChecked())
        {
            String referee = editReferee.getText().toString();
            String dateref=  txtdateRef.getText().toString();
            String datefoll=  txtdatefoll.getText().toString();

            Date dref =null,dfoll = null;

            try {
                dref = formatter.parse(dateref);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            try {
                dfoll = formatter.parse(datefoll);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            conQuiz.setQuizzReferral(new Referral(true, referee, dref, dfoll, conQuiz.getQuizzedPupil().getId(), "10"));
        }else{

            Date dref =null,dfoll = null;

            try {
                dref = formatter.parse("01/01/2001");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            try {
                dfoll = formatter.parse("01/01/2001");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            conQuiz.setQuizzReferral(new Referral(false, "No Referral", dref, dfoll, conQuiz.getQuizzedPupil().getId(), "10"));

        }

    }


}
