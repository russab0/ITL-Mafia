package ru.kpfu.itlmafia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SettingsActivity extends Activity {

    TextView textViewSettings;
    Button buttonBack, buttonNext;
    EditText editTextCntPlayers, editTextCntMafia;
    AppCompatCheckBox checkBoxDoctor, checkBoxSheriff, checkBoxHacker, checkBoxSister, checkBoxDon,
            checkBoxJoker, checkBoxManiac, checkBoxRapper, checkBoxPariychuk;
    ArrayList<String> characters = new ArrayList<String>();
    ArrayList<String> playingRoles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextCntMafia = (EditText) findViewById(R.id.editTextCntMafia);
        editTextCntPlayers = (EditText) findViewById(R.id.editTextCntPlayers);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        checkBoxDon = (AppCompatCheckBox) findViewById(R.id.checkBoxDon);
        checkBoxDoctor = (AppCompatCheckBox) findViewById(R.id.checkBoxDoctor);
        checkBoxHacker = (AppCompatCheckBox) findViewById(R.id.checkBoxHacker);
        checkBoxSister = (AppCompatCheckBox) findViewById(R.id.checkBoxSister);
        checkBoxSheriff = (AppCompatCheckBox) findViewById(R.id.checkBoxSheriff);
        checkBoxJoker = (AppCompatCheckBox) findViewById(R.id.checkBoxJoker);
        checkBoxManiac = (AppCompatCheckBox) findViewById(R.id.checkBoxManiac);
        checkBoxRapper = (AppCompatCheckBox) findViewById(R.id.checkBoxRapper);
        checkBoxPariychuk = (AppCompatCheckBox) findViewById(R.id.checkBoxPariychuk);
        textViewSettings = (TextView) findViewById(R.id.textViewSettigs);

        textViewSettings.setTextSize(45);
        Typeface typeMolot = Typeface.createFromAsset(
                getApplicationContext().getAssets(), "molot.ttf");
        textViewSettings.setTypeface(Typeface.createFromAsset(
                getApplicationContext().getAssets(), "apical_bold.ttf"));
        buttonBack.setTypeface(typeMolot);
        buttonNext.setTypeface(typeMolot);
        textViewSettings.setShadowLayer(5f, 2f, 2f, 0xFFFFFFFF);


        editTextCntPlayers.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    editTextCntMafia.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editTextCntMafia.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextCntMafia.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }

    private void showError(String error){
        Toast toast = Toast.makeText(getApplicationContext(),
                error, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    private int boolToInt(Boolean b){
        if (b)
            return 1;
        return 0;
    }

    //@Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonBack:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonNext:
                if (editTextCntPlayers.getText().length() == 0){
                    showError("Не указано количество игроков!"); break;}
                if (editTextCntMafia.getText().length() == 0){
                    showError("Не указано количество мафий!"); break;}

                int cntPlayers = Integer.parseInt(editTextCntPlayers.getText().toString());
                int cntMafia = Integer.parseInt(editTextCntMafia.getText().toString());
                boolean isDon = (boolean) checkBoxDon.isChecked();
                boolean isDoctor = (boolean) checkBoxDoctor.isChecked();
                boolean isHacker = (boolean) checkBoxHacker.isChecked();
                boolean isSister = (boolean) checkBoxSister.isChecked();
                boolean isSheriff = (boolean) checkBoxSheriff.isChecked();
                boolean isJoker = (boolean) checkBoxJoker.isChecked();
                boolean isManiac = (boolean) checkBoxManiac.isChecked();
                boolean isRapper = (boolean) checkBoxRapper.isChecked();
                boolean isPariychuk = (boolean) checkBoxPariychuk.isChecked();
                int c = cntMafia + boolToInt(isDon) + boolToInt(isDoctor) + boolToInt(isHacker)
                        + boolToInt(isSister) + boolToInt(isSheriff) + boolToInt(isJoker)
                        + boolToInt(isManiac) + boolToInt(isRapper) + boolToInt(isPariychuk);

                if (cntPlayers < 3){
                    showError("Вы указали слишком малое число игроков!"); break; }
                if (cntPlayers > 30){
                    showError("Вы указали слишком большое число игроков!"); break; }
                if (cntMafia + boolToInt(isDon) == 0){
                    showError("Вы указали слишком малое число мафий (включая Дона)!"); break; }
                if (!(cntPlayers / 4 <= cntMafia + boolToInt(isDon)
                    && cntMafia + boolToInt(isDon) <= cntPlayers / 3)){
                    showError("Количество мафий (включая Дона) должно быть от " + Math.max(1, cntPlayers / 4) + " до " + cntPlayers / 3 + "!"); break;}
                if (c > cntPlayers){
                    showError("Количество ролей превышает количество игроков!"); break; }

                if (isPariychuk) characters.add(getString(R.string.pariychuk));
                if (isRapper) characters.add(getString(R.string.rapper));
                if (isSister) characters.add(getString(R.string.sister));
                if (isDoctor) characters.add(getString(R.string.doctor));
                if (isDon) characters.add(getString(R.string.don));
                if (isManiac) characters.add(getString(R.string.maniac));
                if (isSheriff) characters.add(getString(R.string.sheriff));
                if (isJoker) characters.add(getString(R.string.joker));
                if (isHacker) characters.add(getString(R.string.hacker));
                for (int i = 0; i < cntMafia; i++)
                    characters.add(getString(R.string.mafia));
                for (int i = 0; i < cntPlayers - c; i++)
                    characters.add(getString(R.string.peaceful));

                
                if (isRapper) playingRoles.add(getString(R.string.rapper));
                if (isSister) playingRoles.add(getString(R.string.sister));
                if (isDoctor) playingRoles.add(getString(R.string.doctor));
                if (cntMafia > 0) playingRoles.add(getString(R.string.mafia));
                if (isDon) playingRoles.add(getString(R.string.don));
                if (isManiac) playingRoles.add(getString(R.string.maniac));
                if (isSheriff) playingRoles.add(getString(R.string.sheriff));
                if (isJoker) playingRoles.add(getString(R.string.joker));
                if (isHacker) playingRoles.add(getString(R.string.hacker));
                playingRoles.add(getString(R.string.day));

                //showError(playingRoles.size() + "");
                long seed = System.nanoTime();
                Collections.shuffle(characters, new Random(seed));
                intent = new Intent(SettingsActivity.this, GameActivity.class);
                intent.putExtra("characters", characters);
                intent.putExtra("playingRoles", playingRoles);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
