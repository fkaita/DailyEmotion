package com.example.dailyemotion;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_WRITE_EX_STR = 1;

    ActivityResultLauncher resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent resultData  = result.getData();
                    if (resultData  != null) {
                        Uri uri = resultData.getData();

                        try(OutputStream outputStream =
                                    getContentResolver().openOutputStream(uri)) {
                            if(outputStream != null){
                                Date date=new Date();
                                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                                String behaviorTime = "Data saved on " + formatter.format(date) + "\n";
                                outputStream.write(behaviorTime.getBytes());

                                OpenHelper helper = new OpenHelper(getApplicationContext());
                                SQLiteDatabase db = helper.getReadableDatabase();

                                Log.d("debug","**********Cursor");

                                Cursor cursor = db.query(
                                        "behavior_db",
                                        new String[] { "timeSaved", "behavior", "startEnd", "time" },
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                );

                                //どうにかしてエクセルの二つのシートで保存するなどが良さげ
                                //データベースに複数クラスからアクセスする方法は要検討。

                                cursor.moveToFirst();
                                for (int i = 0; i < cursor.getCount(); i++) {
                                    StringBuilder sbuilder = new StringBuilder();
                                    sbuilder.append(cursor.getString(0));
                                    sbuilder.append(", ");
                                    sbuilder.append(cursor.getString(1));
                                    sbuilder.append(", ");
                                    sbuilder.append(cursor.getString(2));
                                    sbuilder.append(", ");
                                    sbuilder.append(cursor.getString(3));
                                    sbuilder.append("\n");
                                    outputStream.write(sbuilder.toString().getBytes());
                                    cursor.moveToNext();
                                }
                                // 忘れずに！
                                cursor.close();

                                String splitMark = "*****\n";
                                outputStream.write(splitMark.getBytes());

                                Cursor cursor2 = db.query(
                                        "emotion_db",
                                        new String[] { "timeSaved", "place", "fatigue", "upset", "hostile", "alert", "ashamed", "inspired", "nervous", "determined",
                                        "attentive", "afraid", "active", "valence", "arousal"},
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                );

                                //どうにかしてエクセルの二つのシートで保存するなどが良さげ
                                //データベースに複数クラスからアクセスする方法は要検討。

                                cursor2.moveToFirst();
                                for (int i = 0; i < cursor2.getCount(); i++) {
                                    StringBuilder sbuilder = new StringBuilder();
                                    for (int j=0; j<14; j++) {
                                        sbuilder.append(cursor2.getString(j));
                                        sbuilder.append(", ");
                                    }
                                    sbuilder.append(cursor2.getString(14));
                                    sbuilder.append("\n");
                                    outputStream.write(sbuilder.toString().getBytes());
                                    cursor2.moveToNext();
                                }
                                // 忘れずに！
                                cursor2.close();
                            }

                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button behaviorBtn = findViewById(R.id.behaviorBtn);
        Button emotionBtn = findViewById(R.id.emotionBtn);
        Button exportBtn = findViewById(R.id.exportBtn);

        if (Build.VERSION.SDK_INT >= 23) {
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_CONTACTS
                        },
                        PERMISSION_WRITE_EX_STR);
            }
        }

        behaviorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BehaviorActivity.class);
                startActivity(intent);
            }
        });

        emotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmotionActivity.class);
                startActivity(intent);
            }
        });

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); //ACTION_CREATE_DOCUMENT
                String time = formatter.format(date);
                String fileName = "dailyEmotion" + time + ".txt";
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, fileName);
                resultLauncher.launch(intent);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permission, int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (grantResults.length <= 0) {
            return;
        }
        switch (requestCode) {
            case PERMISSION_WRITE_EX_STR: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /// 許可が取れた場合・・・
                    /// 必要な処理を書いておく
                } else {
                    /// 許可が取れなかった場合・・・
                    Toast.makeText(this,
                            "Cannot launch the app", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            return;
        }
    }
}