package nit.com.womensafty;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2;
    CheckBox cb;
    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1= (EditText)findViewById(R.id.login_username);
        et2 = (EditText)findViewById(R.id.login_password);
        cb=(CheckBox)findViewById(R.id.cb1);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    et2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    et2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void openRegister(View v)
    {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void validateUser(View v)
    {
        String uname = et1.getText().toString().trim();
        String pass = et2.getText().toString().trim();

        if (uname.isEmpty())
        {
            et1.requestFocus();
            et1.setError("Empty");
        }
        else
        {
            if (pass.isEmpty())
            {
                et2.requestFocus();
                et2.setError("Empty");
            }
            else
            {
                MyDatabase md = new MyDatabase(this);
                SQLiteDatabase db = md.getWritableDatabase();

                String qry = "select password from user_details where email = '"+uname+"'";
                Cursor c = db.rawQuery(qry,null);
                boolean res = c.moveToFirst();
                if (res)
                {
                    String db_pass = c.getString(0);
                    if (db_pass.equals(pass))
                    {
                        finish();
                        Intent i = new Intent(this,WelcomeActivity.class);
                        i.putExtra("k1",uname);
                        startActivity(i);
                    }
                    else
                    {
                        et2.requestFocus();
                        et2.setError("Invalid Password");
                    }
                }
                else
                {
                    Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}