package ansteph.cct.healthscreener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ansteph.cct.healthscreener.localstorage.DbHelper;
import au.com.bytecode.opencsv.CSVWriter;


public class ExportData extends ActionBarActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);

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
        getMenuInflater().inflate(R.menu.menu_export_data, menu);
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
    public static class PlaceholderFragment extends Fragment {

        public static  String DATABASE_NAME = "healthscreen.db";

        EditText txtEmail, txtsubject, txtMessage;
        Uri URI  = null;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_export_data, container, false);

            txtEmail = (EditText) rootView.findViewById(R.id.editEmail);
            txtsubject = (EditText) rootView.findViewById(R.id.editSubject);
            txtMessage = (EditText) rootView.findViewById(R.id.editMessage);



            Button btnSendEmail = (Button) rootView.findViewById(R.id.btnSendmail);
            btnSendEmail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try{
                        String email = txtEmail.getText().toString();
                        String subject = txtsubject.getText().toString();
                        String msg = txtMessage.getText().toString();

                        ExportallDatatoCSV();

                        String pathname=	 Environment.getExternalStorageDirectory().getPath();
                        String filename=	"ExportallDb.csv";
                        File file=new File(pathname, filename);

                        final Intent emailItent = new Intent (android.content.Intent.ACTION_SEND);
                        emailItent.setType("plain/text");
                        emailItent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
                        emailItent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

                        if(file.exists())
                        {
                            emailItent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        }
                        emailItent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
                        getActivity().startActivity(Intent.createChooser(emailItent, "Sending email..."));

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Email not sent", Toast.LENGTH_LONG).show();
                    }


                }
            });


            Button btnexport = (Button) rootView.findViewById(R.id.btnExport);
            btnexport.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    //ExportDatabaseCSVTask export = new ExportDatabaseCSVTask();
                    //	export.execute(new String[]{""});

                    ProgressDialog Dialog = new ProgressDialog(getActivity());
                    Dialog.setMessage("Please wait..Exporting Database...");
                    Dialog.show();

                    ExportallDatatoCSV();
                    Dialog.dismiss();
                    Toast.makeText(getActivity(), "Exported", Toast.LENGTH_LONG).show();
                }
            });

            return rootView;
        }

        public File getDatabsePath(String dbname) {
            // TODO Auto-generated method stub

            String path= getActivity().getFilesDir().getPath()+dbname;  // "/data/data/ansteph.healthscreen/databases/";
            //String path = DB_PATH+DATABASE_NAME;
            File dbfile = new File(path);
            if(dbfile.exists()){
                return dbfile;
            }

            return null;
        }


        public void ExportallDatatoCSV()
        {
            DbHelper DBob = null;
            try {
                DBob= new DbHelper(getActivity());
                DBob.createDatabase();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            File exportDir  = new File (Environment.getExternalStorageDirectory().getPath(),"");

            //create the string array of table
            String [] tableList = { DbHelper.pupil_TABLE,
                    DbHelper.altenateTest_TABLE,
                    DbHelper.alternateTesting_TABLE,
                    DbHelper.closeAnswer_TABLE,
                    DbHelper.closeQuestion_TABLE,
                    DbHelper.openAnswer_TABLE,
                    DbHelper.openQuestion_TABLE,
                    DbHelper.questionCategory_TABLE,
                    DbHelper.referral_TABLE,
                    DbHelper.school_TABLE,
                    DbHelper.questionCategoryOpen_TABLE,
                    DbHelper.takenCountRecord_TABLE,
                    DbHelper.assent_TABLE
            };



            if(!exportDir.exists())
            {
                exportDir.mkdirs();
            }

            File file = new File(exportDir,"ExportallDb.csv");

            try {

                file.createNewFile();
                CSVWriter csWrite = new CSVWriter(new FileWriter(file));
                SQLiteDatabase db =  DBob.getReadableDatabase();

                for (int i = 0; i < tableList.length; i++) {

                    String sql=  "SELECT * FROM "+ tableList[i];
                    Cursor curCSV = db.rawQuery(sql, null);
                    csWrite.writeNext(new String[]{" "});
                    csWrite.writeNext(new String[]{tableList[i]});
                    csWrite.writeNext(new String[]{" "});
                    csWrite.writeNext(curCSV.getColumnNames());
                    int count = curCSV.getColumnCount();
                    while(curCSV.moveToNext())
                    {
                        ArrayList<String> item  = new ArrayList<String>();
                        for (int j = 0; j < count; j++) {
                            item.add(curCSV.getString(j));
                        }
                        String [] arg = new String[item.size()];//  (String[]) item.toArray()	;
                        arg= item.toArray(arg);
                        csWrite.writeNext(arg);
                    }
                    curCSV.close();
                }
                csWrite.close();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }



        public void ExportCaseReportToCSV( List<String> listDataHeader,  HashMap<String, List<String>>listDataChild)
        {
            DbHelper databhelper = new DbHelper(getActivity());;
            try {

                databhelper.createDatabase();
            } catch (Exception e) {

                e.printStackTrace();
            }


           String Title = "Health Assessment Report";
            String daterow  = "Assessment Date:.....";
            String subrow   = "Type of Assessment";
            String [] columnTitle = {"Grades","BMI","Oral","Physical","Hearing","Vision"};

            File exportDir  = new File (Environment.getExternalStorageDirectory().getPath(),"");

            if(!exportDir.exists())
            {
                exportDir.mkdirs();
            }

            File file = new File(exportDir,"CaseReport.csv");

            try{
                file.createNewFile();
                CSVWriter csWrite = new CSVWriter(new FileWriter(file));

                csWrite.writeNext(new String[]{Title});
                csWrite.writeNext(new String[]{daterow});
                csWrite.writeNext(new String[]{subrow});
                csWrite.writeNext(new String[]{" "});
                csWrite.writeNext(new String[]{" "});
                csWrite.writeNext(columnTitle);



                for(String s: listDataHeader)
                {
                    ArrayList<String> item  = new ArrayList<String>();
                    HashMap<String, String > attempCnt = new HashMap<>();
                    attempCnt = databhelper.getPupilAttemptbyGrade(s);

                    item.add("Grade "+s);
                    for(String k: attempCnt.keySet())
                    {
                        item.add(attempCnt.get(k).toString());
                    }
                    String [] arg = new String[item.size()];
                    arg= item.toArray(arg);
                    csWrite.writeNext(arg);
                }

                csWrite.close();


            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }
}
