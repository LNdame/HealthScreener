package ansteph.cct.healthscreener.report;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.localstorage.DbHelper;
import ansteph.cct.healthscreener.slidingmenu.adapter.ExpandableListAdapter;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaseManagement extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String[]>> listDataChild;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public DbHelper databhelper ;

    public static CaseManagement newInstance(int sectionNumber) {
        // Required empty public constructor
        CaseManagement fragment = new CaseManagement();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View rootView = inflater.inflate(R.layout.fragment_case_management, container, false);

        try {
            databhelper= new DbHelper(getActivity());
            databhelper.createDatabase();
        } catch (Exception e) {

            e.printStackTrace();
        }


        //get the exp list
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        //prepare the data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        //setting the list adap
        expListView.setAdapter(listAdapter);


        Button btnSavetoSD = (Button) rootView.findViewById(R.id.btnSaveToSD);
        btnSavetoSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportCaseReportToCSV();
            }
        });

        Button btnEmail = (Button) rootView.findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailReport();
            }
        });
        return rootView;
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String[]>>();


       List<String> grade= new ArrayList<>();

        grade = databhelper.getAllDatabyGrade();
        HashMap<String, String > attempCnt = new HashMap<>();
        int i=0;

        for (String s :grade )
        {
            listDataHeader.add("Grade "+ s);
            attempCnt = new HashMap<>();
            attempCnt = databhelper.getPupilAttemptbyGrade(s);
            List<String[]> child = new ArrayList<>();
            child .add( new String[]{"Name of Assessment","[Number of assessment]"} );
            for(String k: attempCnt.keySet())
            {
               // String h = Strings.padEnd(k, 40,'*');
                child.add(new String[]{k, "["+attempCnt.get(k).toString()+"]"}  );
            }


            listDataChild.put(listDataHeader.get(i), child);
            i++;
        }

    }

    public void ExportCaseReportToCSV()
    {
        List<String> gradelst = new ArrayList<>();
        gradelst=databhelper.getAllDatabyGrade();

        String Title = "Health Assessment Report";
        String daterow  = "Assessment Date:.....";
        String subrow   = "Number of Assessment per Grade";
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



            for(String s: gradelst)
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


        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            Toast.makeText(getActivity(), "Exported", Toast.LENGTH_LONG).show();
        }

    }

    public void emailReport()
    {
        try{
            String email = "ls20045@gmail.com";
            String subject = "Case Management Email";
            String msg = "Regards";

            ExportCaseReportToCSV();

            String pathname=	 Environment.getExternalStorageDirectory().getPath();
            String filename=	"CaseReport.csv";
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

}
/**
 * // Adding child data
 listDataHeader.add("Top 250");
 listDataHeader.add("Now Showing");
 listDataHeader.add("Coming Soon..");

 // Adding child data
 List<String> top250 = new ArrayList<String>();
 top250.add("The Shawshank Redemption");
 top250.add("The Godfather");
 top250.add("The Godfather: Part II");
 top250.add("Pulp Fiction");
 top250.add("The Good, the Bad and the Ugly");
 top250.add("The Dark Knight");
 top250.add("12 Angry Men");

 List<String> nowShowing = new ArrayList<String>();
 nowShowing.add("The Conjuring");
 nowShowing.add("Despicable Me 2");
 nowShowing.add("Turbo");
 nowShowing.add("Grown Ups 2");
 nowShowing.add("Red 2");
 nowShowing.add("The Wolverine");

 List<String> comingSoon = new ArrayList<String>();
 comingSoon.add("2 Guns");
 comingSoon.add("The Smurfs 2");
 comingSoon.add("The Spectacular Now");
 comingSoon.add("The Canyons");
 comingSoon.add("Europa Report");

 listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
 listDataChild.put(listDataHeader.get(1), nowShowing);
 listDataChild.put(listDataHeader.get(2), comingSoon);
 * */