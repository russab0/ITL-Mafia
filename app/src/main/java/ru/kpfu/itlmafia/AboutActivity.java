package ru.kpfu.itlmafia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {

    Button buttonBack;
    TextView textViewAlex, textViewRuslan, textViewReview, textViewAppNameAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        textViewAlex = (TextView) findViewById(R.id.textViewAlex);
        textViewRuslan = (TextView) findViewById(R.id.textViewRuslan);
        textViewReview = (TextView) findViewById(R.id.textViewReview);
        textViewAppNameAbout = (TextView) findViewById(R.id.textViewAppNameAbout);

        buttonBack.setTypeface(Typeface.createFromAsset(
                getApplicationContext().getAssets(), "molot.ttf"));
        textViewAppNameAbout.setTypeface(Typeface.createFromAsset(
                getApplicationContext().getAssets(), "aurora_bd.ttf"));
        textViewAppNameAbout.setShadowLayer(
                5f,   //float radius
                2f,  //float dx
                2f,  //float dy
                0xFFFFFFFF //int color
        );

    }

    //@Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.textViewAlex:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/lexasolovev"));
                startActivity(intent);
                break;
            case R.id.textViewRuslan:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/russabirov1998"));
                startActivity(intent);
                break;
            case R.id.textViewReview:
                intent = new Intent(Intent.ACTION_SEND);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/itlmafia"));
                startActivity(intent);
                break;
            case R.id.buttonBack:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }
}
