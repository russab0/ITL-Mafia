package ru.kpfu.itlmafia;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ReferenceActivity extends Activity {
    TextView textViewReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        textViewReference = (TextView) findViewById(R.id.textViewReference);
        textViewReference.setText(Html.fromHtml(getString(R.string.reference)));
    }
}
