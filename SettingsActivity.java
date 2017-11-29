package app.gahomatherapy.agnihotramitra;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
//TextView trise,tset;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsfile);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();



        Switch  hours24 = (Switch) findViewById(R.id.radioButton2);
        Switch  wake = (Switch) findViewById(R.id.radioButton);
         Switch  meditation = (Switch) findViewById(R.id.button3min);
        Switch  countdown = (Switch) findViewById(R.id.switchcountd);

countdown.setChecked(sharedPreferences.getBoolean("countdown",true));
        wake.setChecked(sharedPreferences.getBoolean("Wakelock",false));
        hours24.setChecked(sharedPreferences.getBoolean("hours24",false));

        meditation.setChecked(sharedPreferences.getBoolean("meditation",false));

        countdown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("countdown",isChecked);
                editor.commit();
            }
        });

        wake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    editor.putBoolean("Wakelock",isChecked);

                editor.commit();
            }
        });

        hours24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("hours24",isChecked);
                editor.commit();
            }
        });


        meditation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("meditation",isChecked);
                editor.commit();
                           }
        });


        final TextView tvhom = (TextView) findViewById(R.id.textViewhomeloc);
        int homeloc = sharedPreferences.getInt("homelocation",1);
        tvhom.setText(sharedPreferences.getString("Location"+homeloc,"Home location not set"));
        Button homebutton=(Button) findViewById(R.id.buttonsethome);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                final List<String> locationsname = new ArrayList<>();

                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int i = sharedPreferences.getInt("Location", 0);
                if (!sharedPreferences.getBoolean("Empty1", true)) {
                    locationsname.add((sharedPreferences.getString("Location1", " Location Not Set ")));
                }
                if (!sharedPreferences.getBoolean("Empty2", true)) {
                    locationsname.add((sharedPreferences.getString("Location2", " Location Not Set ")));
                }
                if (!sharedPreferences.getBoolean("Empty3", true)) {
                    locationsname.add((sharedPreferences.getString("Location3", " Location Not Set ")));
                }
                final CharSequence[] items = locationsname.toArray(new CharSequence[locationsname.size()]);
                alertDialog.setTitle("Set home location");
                alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (locationsname.get(which) == sharedPreferences.getString("Location1", " Location not set ")) {
                            editor.putInt("homelocation", 1);

                        }
                        if (locationsname.get(which) == sharedPreferences.getString("Location2", " Location not set ")) {
                            editor.putInt("homelocation", 2);
                        }
                        if (locationsname.get(which) == sharedPreferences.getString("Location3", " Location not set ")) {
                            editor.putInt("homelocation", 3);

                        }
                        editor.commit();
                        tvhom.setText(locationsname.get(which));
                    }

                });
                alertDialog.create().show();
            }
        });
    }


}
