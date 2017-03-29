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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.ActionBar.LayoutParams;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;




import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.entity.Category;
import ansteph.cct.healthscreener.entity.CloseQuestion;
import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.Referral;
import ansteph.cct.healthscreener.localstorage.DbHelper;
import ansteph.cct.healthscreener.quizboard.QuizActivity;
import ansteph.cct.healthscreener.quizboard.QuizDashboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChronicDisease extends Fragment implements View.OnClickListener {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CAT_ID = "catId";
    public ArrayList<Category> CAT_LIST= new ArrayList<Category>();
    public ArrayList<CloseQuestion> CLO_QU_LIST = new ArrayList<CloseQuestion>();
    private LinearLayout lQues;

    private int catId;
    EditText editReferee;
    TextView txtdateRef;
    TextView txtdatefoll;
    RadioButton radrefyes;
    public DbHelper databhelper ;

    public  static ChronicDisease newInstance(int sectionNumber, int catID) {
        ChronicDisease fragment = new ChronicDisease ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(CAT_ID, catID);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_chronic_disease, container, false);

        catId = getArguments().getInt(CAT_ID,1);

        setTitle();
       // ActionBar actionBar = getActionBar();
       // actionBar.setDisplayShowTitleEnabled(true);
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
       // actionBar.setTitle("Oral health Screening");

        Button imgbtnback = (Button)rootView.findViewById(R.id.imgbtnBack);
        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container, ChronicListPage.newInstance(2))
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

        CAT_LIST= ( ArrayList<Category>)databhelper.getAllCategoryData();
        CLO_QU_LIST  =(ArrayList<CloseQuestion>) databhelper.getAlCloseQuestionData();



        editReferee = (EditText) rootView.findViewById(R.id.editReferee);
        txtdateRef = (TextView) rootView.findViewById(R.id.txtdateref);
        txtdatefoll = (TextView) rootView.findViewById(R.id.txtdatefoll);
        radrefyes = (RadioButton) rootView.findViewById(R.id.radrefYes);


        TextView txtQuCategory = (TextView) rootView.findViewById(R.id.txtTitle);

        txtQuCategory.setText("Currently Assessing: "+conQuiz.getQuizzedPupil().getFirstname()+" "+conQuiz.getQuizzedPupil().getSurname());
        Drawable img =null;
        if(conQuiz.getQuizzedPupil().getGender().equalsIgnoreCase("female"))
        {
            img = getActivity().getResources().getDrawable(R.mipmap.ic_girl);
        }else{
            img = getActivity().getResources().getDrawable(R.mipmap.ic_boy);}

        txtQuCategory.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);


        lQues =  (LinearLayout) rootView.findViewById(R.id.linQuestion);

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        //	LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LayoutParams., height)
        int idcount =1;//radbutton id int

        //this for loop for the category
        for (int i = 0; i < CAT_LIST.size(); i++) {

            if(Integer.parseInt(CAT_LIST.get(i).getId())==catId)//set to the identity (catid) of the category in the database
            {
                TextView txtQuCategor = (TextView) rootView.findViewById(R.id.txtCatTitle);
                txtQuCategor.setText(CAT_LIST.get(i).getDescrip());
                txtQuCategor.setTypeface(null, Typeface.BOLD);

                int h=1;//inner counter
                //this for loop for the questions of category
                for (int j = 0; j < CLO_QU_LIST.size(); j++) {

                    if(Integer.parseInt(CAT_LIST.get(i).getId()) ==  Integer.parseInt( CLO_QU_LIST.get(j).getCatID())){

                        LinearLayout lSquest = new LinearLayout(getActivity());
                        lSquest.setOrientation(LinearLayout.VERTICAL);
                        //Create text
                        TextView txtQuestion = new TextView(getActivity());
                        txtQuestion.setText(h+") "+CLO_QU_LIST.get(j).getDescr());
                        txtQuestion.setTypeface(null, Typeface.BOLD_ITALIC);
                        txtQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                        //create radio group
                        RadioGroup radGrp = new RadioGroup(getActivity());
                        RadioGroup.LayoutParams rprms= new RadioGroup.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);

                        //create radiobutton
                        RadioButton radYes = new RadioButton(getActivity());
                        radYes.setId(idcount++);
                        radYes.setText("Yes");

                        RadioButton radNo = new RadioButton(getActivity());
                        radNo.setId(idcount++);
                        radNo.setText("No");

                        //radGrp.check(radNo.getId());;

                        radGrp.addView(radYes, rprms);
                        radGrp.addView(radNo, rprms);
                        //dividing line
                        View lineView = new View(getActivity());
                        lineView.setBackgroundColor(Color.DKGRAY);
                        android.app.ActionBar.LayoutParams lp = new android.app.ActionBar.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT,1);
                        lp.topMargin = 10;
                        lp.bottomMargin = 10;
                        lp.leftMargin = 10;
                        lp.rightMargin = 10;

                        lineView.setLayoutParams(lp);

                        lSquest.addView(txtQuestion);
                        lSquest.addView(radGrp);

                        lSquest.addView(lineView);

                        lQues.addView(lSquest);
                        h++;
                    }

                }
                h=1;//reset inner counter to 1
                break;
            }

        }

        Button btnSave = ( Button) rootView.findViewById(R.id.btnSave) ;

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getAnswer();
                ResultFragment.TESTID ="2";
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container,ResultFragment.newInstance(4))
                        .commit();
                // ResultFragment.TESTID ="2";


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

                showDatePicker(R.id.btngetdateref);

            }
        });

        ImageButton btnGetDateFoll = (ImageButton) rootView.findViewById(R.id.btngetdatefoll);
        btnGetDateFoll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

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

        //  ((QuizActivity) activity).setTitle("Oral health Screening");
    }
    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private void setTitle()
    {
        String title ="Assessment";
       switch (catId){
           case 6: title ="Tuberculosis Assessment"; break;
           case 13: title ="Asthma Assessment"; break;
           case 14: title ="Epilepsy Assessment"; break;
           case 15: title ="Allergies Assessment"; break;
           case 16: title ="Diabetes Assessment"; break;
           case 17: title ="Cerebral Palsy Assessment"; break;
           case 18: title ="Anaemia and Haemoglobin Assessment"; break;
       }

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(title);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.btnOral:

            default:
        }

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
        datref.init(2015, 0, 1, null);
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
        conQuiz.clearCloseAnswer();
        conQuiz.clearCloseQuestion();

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
                        if(innerV instanceof TextView)
                        {
                            token = false;// indicate whether the question was capture and the boolean answer should be register

                            TextView tx = (TextView) innerV;
                            String question=(String) tx.getText();

                            String []modques = question.split("\\)");
                            String realQues = modques[1].trim();

                            //add the close question in the controller
                            for (int k = 0; k < CLO_QU_LIST.size(); k++) {
                                if(realQues.equalsIgnoreCase(CLO_QU_LIST.get(k).getDescr())){
                                    conQuiz.setCloseList(CLO_QU_LIST.get(k));
                                    token = true;
                                    break;
                                }
                            }


                        }else if (innerV instanceof RadioGroup && token ) //get the anwser
                        {
                            RadioGroup radg = (RadioGroup) innerV;
                            for (int k = 0; k < radg.getChildCount(); k++) {
                                RadioButton radb = (RadioButton) radg.getChildAt(k);
                                if( ((String)radb.getText()).equalsIgnoreCase("yes"))
                                {
                                    if(radb.isChecked())
                                        conQuiz.setCloseAnser(true);
                                    else{
                                        conQuiz.setCloseAnser(false);break;
                                    }

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


        //get referral details
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

            conQuiz.setQuizzReferral(new Referral(true, referee, dref, dfoll, conQuiz.getQuizzedPupil().getId(), "1"));
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

            conQuiz.setQuizzReferral(new Referral(false, "No Referral", dref, dfoll, conQuiz.getQuizzedPupil().getId(), "1"));

        }

    }



}
