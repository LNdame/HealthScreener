package ansteph.cct.healthscreener.quizboard.healthscreening;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.Pupil;
import ansteph.cct.healthscreener.localstorage.DbHelper;
import ansteph.cct.healthscreener.quizboard.QuizActivity;
import ansteph.cct.healthscreener.quizboard.QuizDashboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class BMIScreening extends Fragment implements View.OnClickListener{

    private double bmi =0;
    private double weight=0 ;
    private double height=0;

    TableRow tblUnder;
    TableRow tblNormal;
    TableRow tblOver;
    TableRow tblObese;
    TableRow tblMorObese;
    Pupil quizzedPup ;
    TextView txtBMIResult;
    TextView txtComment;
    TextView txtBobytype;
    Button btnSave;

    Button btnbody1,btnbody2,btnbody3,btnbody4,btnbody5,btnbody6,btnbody7;
GridLayout gridlt ;
    DbHelper databhelper;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static  BMIScreening newInstance(int sectionNumber) {
        BMIScreening fragment = new BMIScreening ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView= inflater.inflate(R.layout.fragment_bmiscreening, container, false);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("BMI Calculation");

        ControllerQuiz conQuiz= (ControllerQuiz)getActivity().getApplicationContext();

        quizzedPup =conQuiz.getQuizzedPupil();

        TextView txtQuCategory = (TextView) rootView.findViewById(R.id.txtTitle);

        txtQuCategory.setText("Currently assessing: "+conQuiz.getQuizzedPupil().getFirstname()+" "+conQuiz.getQuizzedPupil().getSurname());
        Drawable img =null;
        if(conQuiz.getQuizzedPupil().getGender().equalsIgnoreCase("female"))
        {
            img = getActivity().getResources().getDrawable(R.mipmap.ic_girl);
        }else{
            img = getActivity().getResources().getDrawable(R.mipmap.ic_boy);}


        txtQuCategory.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        tblUnder = (TableRow) rootView.findViewById(R.id.tblUnder);
        tblNormal = (TableRow) rootView.findViewById(R.id.tblNormal);
        tblOver = (TableRow) rootView.findViewById(R.id.tblOver);
        tblObese = (TableRow) rootView.findViewById(R.id.tblObe);
        tblMorObese = (TableRow) rootView.findViewById(R.id.tblMorObe);

        final EditText edtHeight = (EditText) rootView.findViewById(R.id.editHeight);
        final EditText edtWeight = (EditText) rootView.findViewById(R.id.editWeight);


        // weight =Double.parseDouble(edtWeight.getText().toString()) ;
        //	 height =Double.parseDouble(edtHeight.getText().toString()) ;

        txtBMIResult = (TextView) rootView.findViewById(R.id.txtBmi);
        txtComment= (TextView) rootView.findViewById(R.id.txtComment);

        Button btnClear = (Button) rootView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                edtHeight.setText("0",TextView.BufferType.EDITABLE);
                edtWeight.setText("0",TextView.BufferType.EDITABLE);

            }
        });



        Button btnCalculate =(Button) rootView.findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(edtWeight.getText().toString()!=null && !edtWeight.getText().toString().isEmpty()){
                    weight =Double.parseDouble(edtWeight.getText().toString()) ;
                }else {
                    weight=0;
                }

                if(edtHeight.getText().toString()!=null && !edtHeight.getText().toString().isEmpty()){
                    height =Double.parseDouble(edtHeight.getText().toString()) ;
                }else{
                    height= 0;
                }

                bmi= calculateBMI(weight, height);
                //DecimalFormat df = new DecimalFormat("###.##");
                txtBMIResult.setText(Double.toString(bmi) );

                if (bmi<=18.5)
                {
                    tblUnder.setBackgroundColor(Color.BLUE);

                    tblNormal.setBackgroundColor(Color.WHITE);
                    tblOver.setBackgroundColor(Color.WHITE);
                    tblObese.setBackgroundColor(Color.WHITE);
                    tblMorObese.setBackgroundColor(Color.WHITE);
                    txtComment.setText("Height = "+height +" Weight = "+weight+" BMI status = Underweight");
                    btnSave.setEnabled(true);
                }else if(bmi<=25)
                {
                    tblNormal.setBackgroundColor(Color.GREEN);

                    tblOver.setBackgroundColor(Color.WHITE);
                    tblObese.setBackgroundColor(Color.WHITE);
                    tblUnder.setBackgroundColor(Color.WHITE);
                    tblMorObese.setBackgroundColor(Color.WHITE);
                    txtComment.setText("Height = "+height +" Weight = "+weight+" BMI status = Normal");
                    btnSave.setEnabled(true);
                }else if (bmi>=25 && bmi<=29.9)
                {
                    tblOver.setBackgroundColor(Color.YELLOW);

                    tblObese.setBackgroundColor(Color.WHITE);
                    tblUnder.setBackgroundColor(Color.WHITE);
                    tblNormal.setBackgroundColor(Color.WHITE);
                    tblMorObese.setBackgroundColor(Color.WHITE);
                    txtComment.setText("Height = "+height +" Weight = "+weight+" BMI status = Overweight");
                    btnSave.setEnabled(true);
                }else if (bmi>=30 && bmi<=39.9)
                {
                    tblObese.setBackgroundColor(Color.RED);

                    tblUnder.setBackgroundColor(Color.WHITE);
                    tblNormal.setBackgroundColor(Color.WHITE);
                    tblOver.setBackgroundColor(Color.WHITE);
                    tblMorObese.setBackgroundColor(Color.WHITE);
                    txtComment.setText("Height = "+height +" Weight = "+weight+" BMI status = Obese");
                    btnSave.setEnabled(true);
                }else if (bmi>=40)
                {
                    tblMorObese.setBackgroundColor(Color.MAGENTA);

                    tblUnder.setBackgroundColor(Color.WHITE);
                    tblNormal.setBackgroundColor(Color.WHITE);
                    tblOver.setBackgroundColor(Color.WHITE);
                    tblObese.setBackgroundColor(Color.WHITE);
                    txtComment.setText("Height = "+height +" Weight = "+weight+" BMI status = Morbidily Obese");
                    btnSave.setEnabled(true);
                }

            }
        });



        btnSave = ( Button) rootView.findViewById(R.id.btnSave) ;
        btnSave.setEnabled(false);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(!(txtBobytype.getText().toString().equals("----") ||txtBobytype.getText().toString().equals(""))){
                InsertBmiLocalStorage();}
                else{
                    Toast.makeText(getActivity(), "Please select the body type", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        try {
            databhelper= new DbHelper(getActivity());
            databhelper.createDatabase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        btnbody1 = (Button) rootView.findViewById(R.id.bobImg1);
        btnbody2 = (Button) rootView.findViewById(R.id.bobImg2);
        btnbody3 = (Button) rootView.findViewById(R.id.bobImg3);
        btnbody4 = (Button) rootView.findViewById(R.id.bobImg4);
        btnbody5 = (Button) rootView.findViewById(R.id.bobImg5);
        btnbody6 = (Button) rootView.findViewById(R.id.bobImg6);
        btnbody7 = (Button) rootView.findViewById(R.id.bobImg7);

        gridlt = (GridLayout) rootView.findViewById(R.id.buttongrid);
        txtBobytype =(TextView)rootView.findViewById(R.id.txtbodytype);
        setBodyGraph();
        return rootView;
    }


   public void setBodyGraph()
   {
       ControllerQuiz conQuiz= (ControllerQuiz)getActivity().getApplicationContext();

       quizzedPup =conQuiz.getQuizzedPupil();
       Drawable img =null;
       TypedArray navMenuIcons=getResources()
           .obtainTypedArray(R.array.nav_drawer_icons);

       if(conQuiz.getQuizzedPupil().getGender().equalsIgnoreCase("female"))
       {
           navMenuIcons=getResources()
                   .obtainTypedArray(R.array.nav_body_girl_pic);

       }else{
           navMenuIcons=getResources()
                   .obtainTypedArray(R.array.nav_body_boy_pic);}


       for (int i = 0; i <gridlt.getChildCount() ; i++) {
           if(gridlt.getChildAt(i)  instanceof Button ){

               Button btn =(Button) gridlt.getChildAt(i);

               img = getActivity().getResources().getDrawable(navMenuIcons.getResourceId(i,-1));

               btn.setCompoundDrawablesWithIntrinsicBounds(null, img, null,null);
               btn.setOnClickListener(this);
           }

       }




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
    public double calculateBMI(double weight, double height)
    {
        double bmi =0;
        try {
            //bmi =Math.floor(1e2*( weight/(height*height)));
            bmi = (weight)/((height/100)*(height/100));
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getActivity(), "Please check some of the number fields", Toast.LENGTH_LONG).show();
        }

        double newbmi=Math.round(bmi*100.0)/100.0;

        return newbmi;

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
        text.setText("The BMI results have been saved");

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



    public void InsertBmiLocalStorage()
    {
        ProgressDialog Dialog = new ProgressDialog(getActivity());
        boolean isEntered= false;
        Dialog.setMessage("Please wait...");
        Dialog.show();

        String result = txtBMIResult.getText().toString();
        String comment = txtComment.getText().toString() +" "+txtBobytype .getText().toString();
        //ControllerQuizz conQuizz= (ControllerQuizz)getActivity().getApplicationContext();


        isEntered=	databhelper.addAlternatetestingData("1", quizzedPup.getId(), result, comment );
        Dialog.dismiss();
        if(isEntered)
            showapproved();
    }

    @Override
    public void onClick(View view) {
        if(view instanceof  Button)
        {
            Button btn = (Button) view;
            String txt = btn.getText().toString();
            txtBobytype.setText(txt);

        }
    }
}






