package ansteph.cct.healthscreener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ansteph.cct.healthscreener.entity.School;
import ansteph.cct.healthscreener.localstorage.DbHelper;


public class RegisterSchool extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_school, menu);
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
    public static class PlaceholderFragment extends Fragment implements OnClickListener {

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


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_register_school, container, false);

            //Geotagging
           // LocationManager mlocManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        //    LocationListener mlocListener = new MyLocationListener();

          //  mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
            //Geotagging

            txtId = (EditText) rootView.findViewById(R.id.editID);
            txtschoolname = (EditText) rootView.findViewById(R.id.editSchoolname);
            txtprincipalname = (EditText) rootView.findViewById(R.id.editPrincipalname);
            txtPrincipalContact = (EditText) rootView.findViewById(R.id.editContactPrincipal);

            txtpersonName = (EditText) rootView.findViewById(R.id.edit2person);
            txtpersonContact = (EditText) rootView.findViewById(R.id.edit2ndContact);
            txtaddLine1 = (EditText) rootView.findViewById(R.id.editAddLine1);
            txtaddLine2 = (EditText) rootView.findViewById(R.id.editAddLine2);
            txtGPSCoordinate = (EditText) rootView.findViewById(R.id.editGPS);


            Button btncont = (Button)rootView. findViewById(R.id.btnCancel);
            btncont.setOnClickListener(this);

            Button btnSave = (Button) rootView.findViewById(R.id.btnSave)	;
            btnSave.setOnClickListener(this);


            try {
                databhelper= new DbHelper(getActivity());
                databhelper.createDatabase();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return rootView;
        }




        public void saveSchoolLocalstorage()
        {
            String id="";
            if(!txtId.getText().toString().isEmpty())
                id = txtId.getText().toString();

            String schoolname = "Undisclosed";
            String principalname = "Undisclosed";
            String principalContact = "Undisclosed";
            //String gender = txtGender.getText().toString();
            String personName = "Undisclosed";
            String personContact = "Undisclosed";
            String addline1 = "Undisclosed";
            String addline2 = "Undisclosed";
            String gpsCoordinate = "Undisclosed";




            if(txtschoolname.getText().toString()!=null && !txtschoolname.getText().toString().isEmpty())
                schoolname = txtschoolname.getText().toString();
            else
                schoolname ="Undisclosed";


            if(txtprincipalname.getText().toString()!=null && !txtprincipalname.getText().toString().isEmpty())
                principalname  = txtprincipalname.getText().toString();
            else
                principalname ="Undisclosed";

            if(txtPrincipalContact.getText().toString()!=null && !txtPrincipalContact.getText().toString().isEmpty())
                principalContact = txtPrincipalContact.getText().toString();
            else
                principalContact ="Undisclosed";



            if(txtpersonName.getText().toString()!=null && !txtpersonName.getText().toString().isEmpty())
                personName = txtpersonName.getText().toString();
            else
                personName="Undisclosed";

            if(txtpersonContact.getText().toString()!=null && !txtpersonContact.getText().toString().isEmpty())
                personContact = txtpersonContact.getText().toString();
            else
                personContact="Undisclosed";

            if(txtaddLine2.getText().toString()!=null && !txtaddLine2.getText().toString().isEmpty())
                addline2 = txtaddLine2.getText().toString();
            else
                addline2 ="Undisclosed";


            if(txtaddLine1.getText().toString()!=null && !txtaddLine1.getText().toString().isEmpty())
                addline1 = txtaddLine1.getText().toString();
            else
                addline1 ="Undisclosed";

            if(txtGPSCoordinate.getText().toString()!=null && !txtGPSCoordinate.getText().toString().isEmpty())
                gpsCoordinate = txtGPSCoordinate.getText().toString();
            else
                gpsCoordinate ="Undisclosed";



            School aSchool = new School(id, schoolname,principalname,principalContact,personName,personContact,addline1,addline2,gpsCoordinate);
            if(databhelper.addSchoolData(aSchool))
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
            text.setText("The School has been registered successfully");

            Button btnOk= (Button) layout.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new OnClickListener() {

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

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSave: saveSchoolLocalstorage();

                    break;
                case R.id.btnCancel:Intent i = new Intent(getActivity().getApplicationContext(), StartUp.class);
                    startActivity(i);
                    break;
                default:
                    break;
            }
        }

        public class MyLocationListener implements LocationListener

        {

            @Override

            public void onLocationChanged(Location loc)

            {

                loc.getLatitude();

                loc.getLongitude();

                String gpsText = "My current location is: " + "Latitude = " + loc.getLatitude() + "Longitude = " + loc.getLongitude();

                txtGPSCoordinate.setText(gpsText);

                //Toast.makeText( getActivity(). getApplicationContext(),gpsText,Toast.LENGTH_SHORT).show();

            }


            @Override

            public void onProviderDisabled(String provider)

            {

                //Toast.makeText(getActivity(). getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();

            }


            @Override

            public void onProviderEnabled(String provider)

            {

                //Toast.makeText( getActivity().getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();

            }


            @Override

            public void onStatusChanged(String provider, int status, Bundle extras)

            {


            }

        }

    }
}
