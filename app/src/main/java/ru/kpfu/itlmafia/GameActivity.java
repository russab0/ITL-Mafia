package ru.kpfu.itlmafia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;

public class GameActivity extends Activity {

    ArrayList characters, playingRoles;
    ArrayList <String> names = new ArrayList<String>();
    ImageView imageView;

    int playerNumber = 0;

    private void showToast(String str){
        Toast toast = Toast.makeText(getApplicationContext(),
                str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, -100);
        toast.show();
    }

    public void showAlertDialog(String message, byte type) {
        boolean b;

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage(message)
                .setCancelable(false);
        if (type == 1) { // Подтверждение запоминания
            builder.setPositiveButton("Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            imageView.setImageResource(R.drawable.empty);
                            playerNumber += 1;
                            if (playerNumber >= characters.size()){
                                Intent intent = new Intent(getApplication(), LeaderActivity.class);
                                intent.putExtra("playingRoles", playingRoles);
                                intent.putExtra("characters", characters);
                                intent.putExtra("names", names);
                                startActivity(intent);
                                return;
                            }
                            //showToast("Передайте телефон следующему игроку");
                            showAlertDialog("Вы действительно Игрок " + (playerNumber + 1) + "?", (byte) 2);
                        }
                    });
            builder.setNegativeButton("Нет",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }
        else{ // Подтверждение личности
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            final EditText input = new EditText(GameActivity.this);

            input.setText("Игрок " + (playerNumber + 1));
            input.setLayoutParams(lp);
            input.setSingleLine(true);
            builder.setView(input);
            builder.setPositiveButton("Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            names.add(input.getText().toString());
                            dialog.cancel();
                            showRole();
                        }
                    });
        }
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void showRole(){
        String c = (String) characters.get(playerNumber);

        if (c.equals(getString(R.string.mafia)))
            imageView.setImageResource(R.drawable.mafia);
        if (c.equals(getString(R.string.don)))
            imageView.setImageResource(R.drawable.don);
        if (c.equals(getString(R.string.doctor)))
            imageView.setImageResource(R.drawable.doctor);
        if (c.equals(getString(R.string.hacker)))
            imageView.setImageResource(R.drawable.hacker);
        if (c.equals(getString(R.string.sister)))
            imageView.setImageResource(R.drawable.sister);
        if (c.equals(getString(R.string.sheriff)))
            imageView.setImageResource(R.drawable.sheriff);
        if (c.equals(getString(R.string.peaceful)))
            imageView.setImageResource(R.drawable.peaceful);
        if (c.equals(getString(R.string.joker)))
            imageView.setImageResource(R.drawable.joker);
        if (c.equals(getString(R.string.maniac)))
            imageView.setImageResource(R.drawable.maniac);
        if (c.equals(getString(R.string.rapper)))
            imageView.setImageResource(R.drawable.rapper);
        if (c.equals(getString(R.string.pariychuk)))
            imageView.setImageResource(R.drawable.pariychuk);
    }

    public void game(View view){
        showAlertDialog("Вы запомнили свою роль, " + names.get(playerNumber) + "?", (byte) 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        imageView = (ImageView) findViewById(R.id.imageView);

        characters = getIntent().getStringArrayListExtra("characters");
        playingRoles = getIntent().getStringArrayListExtra("playingRoles");
        String s = "";
        for (int i = 0; i < characters.size(); i++)
            s += characters.get(i) + " ";
        //showToast(s);

        showAlertDialog("Вы действительно Игрок " + (playerNumber + 1) + "?", (byte) 2);

    }
}
