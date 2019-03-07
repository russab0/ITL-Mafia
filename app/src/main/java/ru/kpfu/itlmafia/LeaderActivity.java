package ru.kpfu.itlmafia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static ru.kpfu.itlmafia.R.id.imageButtonExit;

public class LeaderActivity extends Activity {

    TextView textViewPlayerList;
    ArrayList<String> characters, names, playingRoles;
    SimpleAdapter adapter;
    boolean[][] clicked;
    int currentRole = -1;
    ListView listViewPlayers;
    Button buttonPlayingRole;
    ImageButton imageButtonInfo, imageButtonFlash, imageButtonExit, imageButtonNext;
    Camera cam;
    ArrayList<HashMap<String, String>> players;
    boolean[] checkedItems = {false, false, false, false, false};

    private void generatePlayersList(ArrayList<String> characters, ArrayList<String> names){
        HashMap<String, String> map;
        players = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < characters.size(); i++){
            map = new HashMap<String, String>();
            String s = names.get(i) + ". " + characters.get(i);
            map.put("Name", s);
            map.put("Cond", "");
            players.add(map);
        }
    }

    public void showExitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LeaderActivity.this);
        builder.setMessage("Вы уверены, что хотите закончить игру?")
                .setCancelable(false);

        builder.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        onDestroy();
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
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

    public void showLeaderAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LeaderActivity.this);
        builder.setMessage("Вы действительно ведущий?")
                .setCancelable(false);

        builder.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        names = getIntent().getStringArrayListExtra("names");
                        characters = getIntent().getStringArrayListExtra("characters");
                        playingRoles = getIntent().getStringArrayListExtra("playingRoles");
                        //adapter = new ArrayAdapter<String>(LeaderActivity.this,
                        //        android.R.layout.simple_list_item_1, players);
                        generatePlayersList(characters, names);
                        adapter = new SimpleAdapter(LeaderActivity.this, players, android.R.layout.simple_list_item_2,
                                new String[] {"Name", "Cond"},
                                new int[] {android.R.id.text1, android.R.id.text2});
                        listViewPlayers.setAdapter(adapter);
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void showLeaderChooseDialog(final int idPlayer) {
        final String[] checkConditions = {"Убит", "Рассмешон", "На рэп-баттле", "В храме", "В больнице"};
        //boolean[] checkedItems = {false, false, false, false};

        for (int j = 0; j < checkConditions.length; j++){
            checkedItems[j] = clicked[idPlayer][j];
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(LeaderActivity.this);
        builder = new AlertDialog.Builder(this);
        builder.setTitle(players.get((int) idPlayer).get("Name"))
                .setCancelable(true)
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setMultiChoiceItems(checkConditions, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedItems[which] = isChecked;
                            }
                        })
                .setPositiveButton("Готово",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {;
                                String state = "";
                                HashMap<String, String> map = new HashMap<String, String>();
                                for (int i = 0; i < checkConditions.length; i++) {
                                    if (checkedItems[i]) {
                                        if (!state.isEmpty())
                                            state += ", ";
                                        state += checkConditions[i];
                                    }
                                    clicked[idPlayer][i] = checkedItems[i];
                                }
                                if (clicked[idPlayer][0])
                                    state = "Убит";
                                map.put("Name", players.get((int) idPlayer).get("Name"));
                                map.put("Cond", state);
                                players.set((int) idPlayer,  map);
                                adapter.notifyDataSetChanged();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();//
    }


    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageButtonExit:
                showExitAlertDialog();
                break;
            case R.id.imageButtonFlash:
                if (cam == null) {
                    cam = Camera.open();
                }
                Camera.Parameters p = cam.getParameters();
                if (p.getFlashMode().equals("off")){
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    showToast("Фонарик включен");
                }
                else{
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    showToast("Фонарик выключен");
                }
                cam.setParameters(p);
                cam.startPreview();
                break;
            case R.id.imageButtonInfo:
                intent = new Intent(this, ReferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.imageButtonNext:
                currentRole = (currentRole + 1) % playingRoles.size();
                buttonPlayingRole.setText((String) playingRoles.get(currentRole));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);

        textViewPlayerList = (TextView) findViewById(R.id.textViewPlayersList);
        listViewPlayers = (ListView) findViewById(R.id.listViewPlayers);
        buttonPlayingRole = (Button) findViewById(R.id.buttonPlayingRole);
        imageButtonInfo = (ImageButton) findViewById(R.id.imageButtonInfo);
        imageButtonExit = (ImageButton) findViewById(R.id.imageButtonExit);
        imageButtonNext = (ImageButton) findViewById(R.id.imageButtonNext);
        imageButtonFlash = (ImageButton) findViewById(R.id.imageButtonFlash);


        buttonPlayingRole.setTypeface(Typeface.createFromAsset(
                getApplicationContext().getAssets(), "molot.ttf"));
        textViewPlayerList.setTypeface(Typeface.createFromAsset(
                getApplicationContext().getAssets(), "apical_bold.ttf"));
        textViewPlayerList.setShadowLayer(5f, 2f, 2f, 0xFFFFFFFF);

        showLeaderAlertDialog();

        clicked = new boolean[30][5];
        for (int i = 0; i < 30; i++){
            for (int j = 0; j < 5; j++){
                clicked[i][j] = false;
            }
        }

        listViewPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                showLeaderChooseDialog((int) id);
            }
        });

        imageButtonNext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (currentRole == -1)
                    return true;
                currentRole = (currentRole - 1 + playingRoles.size()) % playingRoles.size();
                buttonPlayingRole.setText((String) playingRoles.get(currentRole));
                return true;
            }
        });
    }


}
