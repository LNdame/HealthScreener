package ansteph.cct.healthscreener;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import ansteph.cct.healthscreener.entity.School;
import ansteph.cct.healthscreener.localstorage.DbHelper;


public class EditSchool extends ActionBarActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_school);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_school, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
        Spinner cboSchool;

        EditText txtId;
        EditText txtschoolname;
        EditText txtprincipalname ;
        EditText txtPrincipalContact ;

        EditText txtpersonName;
        EditText txtpersonContact;
        EditText txtaddLine1;
        EditText txtaddLine2;
        EditText txtGPSCoordinate;
        public DbHelper databhelper ;

        ArrayList<School> schoolList = new ArrayList<School>();
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edit_school, container, false);

            try {
                databhelper= new DbHelper(getActivity());
                databhelper.createDatabase();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            schoolList =(ArrayList<School>) databhelper.getAlSchoolData();
            String[] schoollist = new String[schoolList.size()];
            int i=0;
            for(School sch:schoolList)
            {
                schoollist [i]= sch.getSchoolName();
                i++;
            }


            cboSchool = (Spinner) rootView.findViewById(R.id.cboSchool);
            ArrayAdapter<String> adapt =  new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, schoollist );
            adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            cboSchool.setOnItemSelectedListener(this);
            cboSchool.setAdapter(adapt);


            txtId = (EditText) rootView.findViewById(R.id.editID);
            txtschoolname = (EditText) rootView.findViewById(R.id.editSchoolname);
            txtprincipalname = (EditText) rootView.findViewById(R.id.editPrincipalname);
            txtPrincipalContact = (EditText) rootView.findViewById(R.id.editContactPrincipal);

            txtpersonName = (EditText) rootView.findViewById(R.id.edit2person);
            txtpersonContact = (EditText) rootView.findViewById(R.id.edit2ndContact);
            txtaddLine1 = (EditText) rootView.findViewById(R.id.editAddLine1);
            txtaddLine2 = (EditText) rootView.findViewById(R.id.editAddLine2);
            txtGPSCoordinate = (EditText) rootView.findViewById(R.id.editGPS);

            Button btnCancel = (Button)rootView. findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(this);

            Button btnSave = (Button) rootView.findViewById(R.id.btnSave)	;
            btnSave.setOnClickListener(this);


            return rootView;
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
            loadValue(   schoolList.get(pos));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            loadValue(   schoolList.get(0));
        }

        public void loadValue (School asch ){


            txtId.setText(asch.getId());
            txtschoolname.setText(asch.getSchoolName());
            txtprincipalname.setText(asch.getPrincipalName());
            txtPrincipalContact.setText(asch.getPrincipalContact());

            txtpersonName .setText(asch.getPersonName());
            txtpersonContact .setText(asch.getPersonContact());
            txtaddLine1 .setText(asch.getAddline1());
            txtaddLine2 .setText(asch.getAddline2());
            txtGPSCoordinate .setText(asch.getGPSCoordinate());

        }

    }
}
