package ansteph.cct.healthscreener;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.Pupil;
import ansteph.cct.healthscreener.entity.School;
import ansteph.cct.healthscreener.localstorage.DbHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditPupilFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    EditText txtId;
    EditText txtFirstname;
    EditText txtLastname ;
    EditText txtGrade ;

    EditText txtschool;
    EditText txtaddLine1;
    EditText txtaddLine2;

    EditText txtParentName;
    EditText txtParentContact;
    RadioButton radYes;
    RadioButton radNo;
    Spinner cboSchool;
    private String gender="";
    TextView txtdatdob;
    RadioGroup rdg ;
    ArrayList<String> sch2;
    Spinner cboGender;
    DatePicker datdob;
    ArrayList<School> schoolList = new ArrayList<School>();
    public DbHelper databhelper ;
    ControllerQuiz conQuiz;

    public EditPupilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_register_pupil, container, false);
        try {
            databhelper= new DbHelper(getActivity());
            databhelper.createDatabase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        conQuiz = (ControllerQuiz) getActivity().getApplicationContext();
        txtId = (EditText) rootView.findViewById(R.id.editID);
        txtFirstname = (EditText) rootView.findViewById(R.id.editFirstname);
        txtLastname = (EditText) rootView.findViewById(R.id.editSurname);
        txtGrade = (EditText) rootView.findViewById(R.id.editGrade);
        //txtGender=(EditText) rootView.findViewById(R.id.editGender);

        txtParentName = (EditText) rootView.findViewById(R.id.editParentName);
        txtParentContact = (EditText) rootView.findViewById(R.id.editParentContact);
        radYes = (RadioButton) rootView.findViewById(R.id.radYes);
        radNo = (RadioButton) rootView.findViewById(R.id.radNo);
        rdg = (RadioGroup)rootView.findViewById(R.id.radioGroup1);
        //txtschool = (EditText) rootView.findViewById(R.id.editSchool);
        txtaddLine1 = (EditText) rootView.findViewById(R.id.editAddLine1);
        txtaddLine2 = (EditText) rootView.findViewById(R.id.editAddLine2);
        txtdatdob= (TextView) rootView.findViewById(R.id.txtdatDOB);


        Button btncont = (Button)rootView. findViewById(R.id.btnCancel);
        btncont.setOnClickListener(this);

        Button btnSave = (Button) rootView.findViewById(R.id.btnSave)	;
        btnSave.setOnClickListener(this);


        ImageButton btnGetDateDOB = (ImageButton) rootView.findViewById(R.id.btngetdateDOB);
        btnGetDateDOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDatePicker(R.id.btngetdateDOB);

            }
        });

       cboGender = (Spinner) rootView.findViewById(R.id.cboGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboGender.setAdapter(adapter);
        cboGender.setOnItemSelectedListener(this);

        schoolList =(ArrayList<School>) databhelper.getAlSchoolData();
        String[] schoollist = new String[schoolList.size()];
        int i=0;
        for(School sch:schoolList)
        {
            schoollist [i]= sch.getSchoolName();
            i++;
        }


        String[] schlist = new String[schoolList.size()];
        int j=0;
        sch2 = new ArrayList<String>() ;

        for(School sch:schoolList)
        {
            sch2.add(sch.getSchoolName());
            schlist [j]= sch.getSchoolName();
            j++;
        }

        cboSchool = (Spinner) rootView.findViewById(R.id.cboSchool);
        ArrayAdapter<String> adapt =  new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, schoollist );
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cboSchool.setAdapter(adapt);

        return rootView;
    }




    @Override
    public void onStart() {
        super.onStart();

        txtId.setText(conQuiz.getQuizzedPupil().getId());
        txtFirstname.setText(conQuiz.getQuizzedPupil().getFirstname());
        txtLastname .setText(conQuiz.getQuizzedPupil().getSurname());
        txtGrade.setText(conQuiz.getQuizzedPupil().getGrade());
        //EditText txtGender ;
        //txtschool.setText(conQuizz.getQuizzedPupil().getSchool());
        txtaddLine1.setText(conQuiz.getQuizzedPupil().getAddline1());
        txtaddLine2.setText(conQuiz.getQuizzedPupil().getAddline2());

        txtParentName.setText(conQuiz.getQuizzedPupil().getParentName());
        txtParentContact. setText(conQuiz.getQuizzedPupil().getParentContact());
       try {
              if (conQuiz.getQuizzedPupil().getHasRoadtoHealth())
         rdg.check(radYes.getId());
    else
        rdg.check(radNo.getId());
        }catch (Exception e){}

        int pos=0;

        pos =  sch2.indexOf(conQuiz.getQuizzedPupil().getSchool());
        cboSchool.setSelection(pos);

        if(conQuiz.getQuizzedPupil().getGender().equalsIgnoreCase("female"))
        {
            cboGender.setSelection(0);
        }else{cboGender.setSelection(1);}

        if(conQuiz.getQuizzedPupil().getDOB()!=null){

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            txtdatdob.setText(formatter.format(conQuiz.getQuizzedPupil().getDOB()));


        }
    }


    public void savePupilLocalstorage()
    {
        String id="";
        if(!txtId.getText().toString().isEmpty())
            id = txtId.getText().toString();

        String firstname = "Undisclosed";
        String lastname = "Undisclosed";
        String grade = "Undisclosed";
        //String gender = txtGender.getText().toString();
        String school = "Undisclosed";
        String addline1 = "Undisclosed";
        String addline2 = "Undisclosed";

        String parentName = "Undisclosed";
        String parentContact = "Undisclosed";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String datedob = txtdatdob.getText().toString();//Integer.toString(datdob.getDayOfMonth())+"/"+Integer.toString(datdob.getMonth()+1)+"/"+Integer.toString(datdob.getYear());    //(new StringBuilder().append(datdob.getYear()))

        java.util.Date dob=null;

        try {
            dob =formatter.parse(datedob);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }



        if(txtFirstname.getText().toString()!=null && !txtFirstname.getText().toString().isEmpty())
            firstname = txtFirstname.getText().toString();
        else
            firstname ="Undisclosed";


        if(txtLastname.getText().toString()!=null && !txtLastname.getText().toString().isEmpty())
            lastname  = txtLastname.getText().toString();
        else
            lastname ="Undisclosed";

        if(txtGrade.getText().toString()!=null && !txtGrade.getText().toString().isEmpty())
            grade = txtGrade.getText().toString();
        else
            grade ="Undisclosed";



		/*if(txtschool.getText().toString()!=null && !txtschool.getText().toString().isEmpty())
			school = txtschool.getText().toString();
		else
			school="Undisclosed";*/


        if(txtParentName.getText().toString()!=null && !txtParentName.getText().toString().isEmpty())
            parentName = txtParentName.getText().toString();
        else
            parentName="Undisclosed";

        if(txtParentContact.getText().toString()!=null && !txtParentContact.getText().toString().isEmpty())
            parentContact = txtParentContact.getText().toString();
        else
            parentContact="Undisclosed";

        if(txtaddLine2.getText().toString()!=null && !txtaddLine2.getText().toString().isEmpty())
            addline2 = txtaddLine2.getText().toString();
        else
            addline2 ="Undisclosed";


        if(txtaddLine1.getText().toString()!=null && !txtaddLine1.getText().toString().isEmpty())
            addline1 = txtaddLine1.getText().toString();
        else
            addline1 ="Undisclosed";

        school = cboSchool.getSelectedItem().toString();
        boolean hasRoadtoHealth = false;
        if(radYes.isChecked())
            hasRoadtoHealth= true;

        Pupil apup = new Pupil(id, firstname,lastname,grade,gender,addline1,addline2,school,dob,parentName,parentContact,hasRoadtoHealth);

        if(databhelper.editPupilData(apup))
            showapproved();
    }


    AlertDialog alertDialog=null;

    public void showapproved()
    {

        AlertDialog.Builder builder;
        //AlertDialog alertDialog=null;

        Context mContext =getActivity();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        View layout = inflater.inflate(R.layout.layout_approved, null);

        TextView text = (TextView) layout.findViewById(R.id.txtMessage);
        text.setText("The pupil has been registered successfully");

        Button btnOk= (Button) layout.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
                Intent i = new Intent(getActivity().getApplicationContext(), StartUp.class);
                startActivity(i);
            }
        });

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();


    }

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
                    case R.id.btngetdateDOB:txtdatdob.setText(dateref);

                        break;

                    default:txtdatdob.setText(dateref);
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

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnCancel:
                Intent i = new Intent(getActivity().getApplicationContext(), StartUp.class);
                startActivity(i);
                break;
            case R.id.btnSave: savePupilLocalstorage();

                break;
            default: break;


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (pos) {
            case 0: gender="female";// Toast.makeText(getActivity(), "female selected", Toast.LENGTH_SHORT).show();
                break;
            case 1: gender="male";//Toast.makeText(getActivity(), "male selected", Toast.LENGTH_SHORT).show();
                break;
            default: gender="female";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        gender="female";
    }
}
