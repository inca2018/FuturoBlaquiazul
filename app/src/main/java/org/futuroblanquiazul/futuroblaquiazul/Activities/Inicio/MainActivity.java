package org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.R;


public class MainActivity extends AppCompatActivity {
    private ProgressBar p;
    private TextView txtProgress;
    private int pStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        p = (ProgressBar) findViewById(R.id.loading_spinner);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (pStatus <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            p.setProgress(pStatus);
                            txtProgress.setText(pStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        mover();
                    }
                });

            }

        }).start();


    }

    void mover(){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
