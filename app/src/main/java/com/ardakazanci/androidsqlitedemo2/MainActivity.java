package com.ardakazanci.androidsqlitedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ardakazanci.androidsqlitedemo2.helper.DBHelper;
import com.ardakazanci.androidsqlitedemo2.model.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Account> lstUser = new ArrayList<Account>();
    DBHelper dbHelper;
    Button btnGetData;
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = findViewById(R.id.button_get_data);
        container = findViewById(R.id.container);

        dbHelper = new DBHelper(getApplicationContext());
        dbHelper.createDatabase();

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                lstUser = dbHelper.getAllData();


                for (Account account : lstUser) {

                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View addView = layoutInflater.inflate(R.layout.row, null);

                    TextView tvUserName = addView.findViewById(R.id.row_user_name);
                    TextView tvEmail = addView.findViewById(R.id.row_email);

                    tvUserName.setText(account.getUserName());
                    tvEmail.setText(account.getEmail());

                    container.addView(addView);


                }


            }
        });


    }
}
