package ansteph.cct.healthscreener;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ansteph.cct.healthscreener.entity.ControllerQuiz;
import ansteph.cct.healthscreener.entity.Nurse;
import ansteph.cct.healthscreener.localstorage.DbHelper;


public class SignIn extends ActionBarActivity {
    private Toolbar toolbar;
    public DbHelper databhelper ;
    ControllerQuiz conQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        conQuiz= (ControllerQuiz) getApplicationContext();
        try {
            databhelper= new DbHelper(this);
            databhelper.createDatabase();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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

        public void gotoAccount(View view)
        {
            Intent i = new Intent(getApplicationContext(), Account.class);
            startActivity(i);
        }

    public void btnLogIn_OnClick(View view)
    {
        String UserName = ((EditText) findViewById(R.id.editusername)).getText().toString(),
                Password  = ((EditText) findViewById(R.id.editPassword)).getText().toString();

        if (!(UserName.equals("") || Password.equals(""))) {

            if(UserName.equals("admin")&&Password.equals("admin"))
            {
                conQuiz.setLoggedNurse(new Nurse("admin", " "));
                Intent i = new Intent(getApplicationContext(), StartUp.class);
                startActivity(i);
                return;
            }else if(databhelper.UserExist(UserName,Password)){

                conQuiz.setLoggedNurse(databhelper.loggedNurse(UserName,Password));

                Intent i = new Intent(getApplicationContext(), StartUp.class);
                startActivity(i);
                return;

            }


        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please complete both fields.", Toast.LENGTH_LONG).show();
        }

    }

}
