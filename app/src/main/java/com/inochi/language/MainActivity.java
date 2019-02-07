package com.inochi.language;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ViewItem> viewItems;
    private Language langEn;
    private Language langId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        langEn = new Language(this, "en");
        langId = new Language(this, "id");

        viewItems = new ArrayList<>();

        ViewGroup viewGroup = findViewById(R.id.fraMain);
        registerTextViews(viewGroup);
    }

    private void registerTextViews(ViewGroup viewGroup){
        int viewChiledCount = viewGroup.getChildCount();
        for (int i = 0; i < viewChiledCount; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof TextView){
                TextView textView = (TextView) child;

                int id = textView.getId();
                String text = textView.getText().toString();

                ViewItem viewItem = new ViewItem();
                viewItem.setId(id);
                viewItem.setText(text);
                viewItem.setTextView(textView);

                viewItems.add(viewItem);
                String translate = langEn.getTranslate(text);

                textView.setText(translate);
            } else if (child instanceof ViewGroup){
                this.registerTextViews((ViewGroup) child);
            }
        }
    }

    private void changeLanguage(String lang){
        if (viewItems != null){
            if (viewItems.size() > 0){
                for (ViewItem viewItem : viewItems){
                    TextView textView = viewItem.getTextView();
                    String text = viewItem.getText();

                    if (lang.equals("id")){
                        textView.setText(langId.getTranslate(text));
                    } else {
                        textView.setText(langEn.getTranslate(text));
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_en) {
            changeLanguage("en");
        } else if (id == R.id.action_id){
            changeLanguage("id");
        }

        return super.onOptionsItemSelected(item);
    }
}
