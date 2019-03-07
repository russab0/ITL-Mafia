package ru.kpfu.itlmafia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonStartGame, buttonAbout, buttonHelp, buttonExit;
    TextView textViewAppNameMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartGame = (Button) findViewById(R.id.buttonStartGame);
        buttonAbout = (Button) findViewById(R.id.buttonAbout);
        buttonHelp = (Button) findViewById(R.id.buttonHelp);
        buttonExit = (Button) findViewById(R.id.buttonExit);
        textViewAppNameMain = (TextView) findViewById(R.id.textViewAppNameMain);

        Typeface typeMolot = Typeface.createFromAsset(
                getApplicationContext().getAssets(), "molot.ttf");
        Typeface typeAuroraBd = Typeface.createFromAsset(
                getApplicationContext().getAssets(), "aurora_bd.ttf");
        textViewAppNameMain.setTypeface(typeAuroraBd);
        buttonStartGame.setTypeface(typeMolot);
        buttonAbout.setTypeface(typeMolot);
        buttonHelp.setTypeface(typeMolot);
        buttonExit.setTypeface(typeMolot);
        textViewAppNameMain.setShadowLayer(5f, 2f, 2f, 0xFFFFFFFF);
    }

    public void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setCancelable(false);

            builder.setPositiveButton("Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            moveTaskToBack(true);
                            //super.
                            onDestroy();
                            System.exit(0);
                        }
                    });
            builder.setNegativeButton("Нет",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog("Вы уверены, что хотите закрыть приложение?");
    }

    //@Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonStartGame:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonAbout:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonHelp:
                intent = new Intent(this, ReferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonExit:
                    /*moveTaskToBack(true);
                    super.onDestroy();
                    System.exit(0);*/
                    showAlertDialog("Вы уверены, что хотите закрыть приложение?");
                    break;
            default:
                break;
        }
    }
}
