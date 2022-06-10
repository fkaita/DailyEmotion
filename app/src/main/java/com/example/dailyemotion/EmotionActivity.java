package com.example.dailyemotion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmotionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        // Back button to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Spinners
        Spinner spiPlace = findViewById(R.id.spiPlace);
        Spinner spiFatigue = findViewById(R.id.spiFatigue);
        Spinner spiUpset = findViewById(R.id.spiUpset);
        Spinner spiHostile = findViewById(R.id.spiHostile);
        Spinner spiAlert = findViewById(R.id.spiAlert);
        Spinner spiAshamed = findViewById(R.id.spiAshamed);
        Spinner spiInspired = findViewById(R.id.spiInspired);
        Spinner spiNervous = findViewById(R.id.spiNervous);
        Spinner spiDetermined = findViewById(R.id.spiDetermined);
        Spinner spiAttentive = findViewById(R.id.spiAttentive);
        Spinner spiAfraid = findViewById(R.id.spiAfraid);
        Spinner spiActive = findViewById(R.id.spiActive);
        Spinner spiValence = findViewById(R.id.spiValence);
        Spinner spiArousal = findViewById(R.id.spiArousal);
        Spinner spiStress = findViewById(R.id.spiStress);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(this,
                R.array.places, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> fatigueAdapter = ArrayAdapter.createFromResource(this,
                R.array.fatigue, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> panasAdapter = ArrayAdapter.createFromResource(this,
                R.array.panasValues, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> valenceAdapter = ArrayAdapter.createFromResource(this,
                R.array.valencelValues, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arousalAdapter = ArrayAdapter.createFromResource(this,
                R.array.arousalValues, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> stressAdapter = ArrayAdapter.createFromResource(this,
                R.array.stress, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fatigueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        panasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        valenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arousalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spiPlace.setAdapter(placeAdapter);
        spiFatigue.setAdapter(fatigueAdapter);
        spiUpset.setAdapter(panasAdapter);
        spiHostile.setAdapter(panasAdapter);
        spiAlert.setAdapter(panasAdapter);
        spiAshamed.setAdapter(panasAdapter);
        spiInspired.setAdapter(panasAdapter);
        spiNervous.setAdapter(panasAdapter);
        spiDetermined.setAdapter(panasAdapter);
        spiAttentive.setAdapter(panasAdapter);
        spiAfraid.setAdapter(panasAdapter);
        spiActive.setAdapter(panasAdapter);
        spiValence.setAdapter(valenceAdapter);
        spiArousal.setAdapter(arousalAdapter);
        spiStress.setAdapter(stressAdapter);

        // Set on item listener
        spiPlace.setOnItemSelectedListener(null);
        spiPlace.setSelection(0, false);
        spiPlace.setOnItemSelectedListener(this);
        spiFatigue.setOnItemSelectedListener(null);
        spiFatigue.setSelection(3, false);
        spiFatigue.setOnItemSelectedListener(this);
        spiUpset.setOnItemSelectedListener(null);
        spiUpset.setSelection(2, false);
        spiUpset.setOnItemSelectedListener(this);
        spiHostile.setOnItemSelectedListener(null);
        spiHostile.setSelection(2, false);
        spiHostile.setOnItemSelectedListener(this);
        spiAlert.setOnItemSelectedListener(null);
        spiAlert.setSelection(2, false);
        spiAlert.setOnItemSelectedListener(this);
        spiAshamed.setOnItemSelectedListener(null);
        spiAshamed.setSelection(2, false);
        spiAshamed.setOnItemSelectedListener(this);
        spiInspired.setOnItemSelectedListener(null);
        spiInspired.setSelection(2, false);
        spiInspired.setOnItemSelectedListener(this);
        spiNervous.setOnItemSelectedListener(null);
        spiNervous.setSelection(2, false);
        spiNervous.setOnItemSelectedListener(this);
        spiDetermined.setOnItemSelectedListener(null);
        spiDetermined.setSelection(2, false);
        spiDetermined.setOnItemSelectedListener(this);
        spiAttentive.setOnItemSelectedListener(null);
        spiAttentive.setSelection(2, false);
        spiAttentive.setOnItemSelectedListener(this);
        spiAfraid.setOnItemSelectedListener(null);
        spiAfraid.setSelection(2, false);
        spiAfraid.setOnItemSelectedListener(this);
        spiActive.setOnItemSelectedListener(null);
        spiActive.setSelection(2, false);
        spiActive.setOnItemSelectedListener(this);
        spiValence.setOnItemSelectedListener(null);
        spiValence.setSelection(2, false);
        spiValence.setOnItemSelectedListener(this);
        spiArousal.setOnItemSelectedListener(null);
        spiArousal.setSelection(2, false);
        spiArousal.setOnItemSelectedListener(this);
        spiStress.setOnItemSelectedListener(null);
        spiStress.setSelection(0, false);
        spiStress.setOnItemSelectedListener(this);

        Button saveEmotionBtn = findViewById(R.id.saveEmotionBtn);
        saveEmotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.saveEmotionBtn) {
                    Date date = new Date();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                    String timeSaved = formatter.format(date);
                    String place = (String) spiPlace.getSelectedItem();
                    String fatigue = (String) spiFatigue.getSelectedItem();
                    String upset = (String) spiUpset.getSelectedItem();
                    String hostile = (String) spiHostile.getSelectedItem();
                    String alert = (String) spiAlert.getSelectedItem();
                    String ashamed = (String) spiAshamed.getSelectedItem();
                    String inspired = (String) spiInspired.getSelectedItem();
                    String nervous = (String) spiNervous.getSelectedItem();
                    String determined = (String) spiDetermined.getSelectedItem();
                    String attentive = (String) spiAttentive.getSelectedItem();
                    String afraid = (String) spiAfraid.getSelectedItem();
                    String active = (String) spiActive.getSelectedItem();
                    String valence = (String) spiValence.getSelectedItem();
                    String arousal = (String) spiArousal.getSelectedItem();
                    String stress = (String) spiStress.getSelectedItem();

                    OpenHelper helper = new OpenHelper(getApplicationContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
//                    helper.onUpgrade(db, 0,1);
                    if(db == null){
                        helper.onUpgrade(db, 0,1);
                    }

                    insertData(db, timeSaved, place, fatigue, upset, hostile, alert, ashamed, inspired, nervous, determined, attentive, afraid, active, valence, arousal, stress);
                    finish();

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg, View view, int position, long id) {
        position = position + 1;
        switch (arg.getId()) {
            case R.id.spiPlace:
                TextView txtPlace = findViewById(R.id.txtPlace);
                txtPlace.append(": " + position);
                break;
            case R.id.spiFatigue:
                TextView txtFatigue = findViewById(R.id.txtFatigue);
                txtFatigue.append(": "+ position);
                break;
            case R.id.spiUpset:
                TextView txtUpset = findViewById(R.id.txtUpset);
                txtUpset.append(": "+ position);
                break;
            case R.id.spiHostile:
                TextView txtHostile = findViewById(R.id.txtHostile);
                txtHostile.append(": "+ position);
                break;
            case R.id.spiAlert:
                TextView txtAlert = findViewById(R.id.txtAlert);
                txtAlert.append(": "+ position);
                break;
            case R.id.spiAshamed:
                TextView txtAshamed = findViewById(R.id.txtAshamed);
                txtAshamed.append(": "+ position);
                break;
            case R.id.spiInspired:
                TextView txtInspired = findViewById(R.id.txtInspired);
                txtInspired.append(": "+ position);
                break;
            case R.id.spiNervous:
                TextView txtNervous = findViewById(R.id.txtNervous);
                txtNervous.append(": "+ position);
                break;
            case R.id.spiDetermined:
                TextView txtDetermined = findViewById(R.id.txtDetermined);
                txtDetermined.append(": "+ position);
                break;
            case R.id.spiAttentive:
                TextView txtAttentive = findViewById(R.id.txtAttentive);
                txtAttentive.append(": "+ position);
                break;
            case R.id.spiAfraid:
                TextView txtAfraid = findViewById(R.id.txtAfraid);
                txtAfraid.append(": "+ position);
                break;
            case R.id.spiActive:
                TextView txtActive = findViewById(R.id.txtActive);
                txtActive.append(": "+ position);
                break;
            case R.id.spiValence:
                TextView txtValence = findViewById(R.id.txtValence);
                txtValence.append(": "+ position);
                break;
            case R.id.spiArousal:
                TextView txtArousal = findViewById(R.id.txtArousal);
                txtArousal.append(": "+ position);
                break;
            case R.id.spiStress:
                TextView txtStress = findViewById(R.id.txtStress);
                txtStress.append(": "+ position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.saveEmotionBtn) {

        }

    }

    private void insertData(SQLiteDatabase db, String timeSaved, String place, String fatigue, String upset, String hostile, String alert,
                            String ashamed, String inspired, String nervous, String determined, String attentive, String afraid, String active, String valence, String arousal, String stress) {
        ContentValues values = new ContentValues();
        values.put("timeSaved", timeSaved);
        values.put("place", place);
        values.put("fatigue", fatigue);
        values.put("upset", upset);
        values.put("hostile", hostile);
        values.put("alert", alert);
        values.put("ashamed", ashamed);
        values.put("inspired", inspired);
        values.put("nervous", nervous);
        values.put("determined", determined);
        values.put("attentive", attentive);
        values.put("afraid", afraid);
        values.put("active", active);
        values.put("valence", valence);
        values.put("arousal", arousal);
        values.put("stress", stress);

        db.insert("emotion_db", null, values);
    }
}