package com.example.personalizator;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "ustawienia_app";
    private static final String KEY_COLOR_INDEX = "colorIndex";

    private ImageView bgImage;
    private LinearLayout rootLayout;
    private LinearLayout panel;
    private TextView tvText;
    private Button btnChangeText;
    private Button btnChangeColor;
    private Button btnChangeBg;
    private SwitchCompat switchNight;

    private int[] images;
    private int[] colors;
    private String[] texts;

    private int imageIndex = 0;
    private int textIndex = 0;
    private int colorIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bgImage = findViewById(R.id.bgImage);
        rootLayout = findViewById(R.id.rootLayout);
        panel = findViewById(R.id.panel);
        tvText = findViewById(R.id.tvText);
        btnChangeText = findViewById(R.id.btnChangeText);
        btnChangeColor = findViewById(R.id.btnChangeColor);
        btnChangeBg = findViewById(R.id.btnChangeBg);
        switchNight = findViewById(R.id.switchNight);
        texts = getResources().getStringArray(R.array.texts);
        images = new int[]{
                R.drawable.tlo1,
                R.drawable.tlo2,
                R.drawable.tlo3
        };
        colors = new int[]{
                ContextCompat.getColor(this, R.color.text_color),
                ContextCompat.getColor(this, R.color.text_opt2),
                ContextCompat.getColor(this, R.color.text_opt3)
        };
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        colorIndex = prefs.getInt(KEY_COLOR_INDEX, 0);
        if (colorIndex < 0 || colorIndex >= colors.length) {
            colorIndex = 0;
        }
        bgImage.setImageResource(images[imageIndex]);
        tvText.setText(texts[textIndex]);
        tvText.setTextColor(colors[colorIndex]);
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        boolean isNightMode;
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            isNightMode = true;
        } else if (currentMode == AppCompatDelegate.MODE_NIGHT_NO) {
            isNightMode = false;
        } else {
            int uiNight = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            isNightMode = (uiNight == Configuration.UI_MODE_NIGHT_YES);
        }
        switchNight.setChecked(isNightMode);
        btnChangeText.setOnClickListener(v -> {
            textIndex = (textIndex + 1) % texts.length;
            tvText.setText(texts[textIndex]);
        });
        btnChangeColor.setOnClickListener(v -> {
            colorIndex = (colorIndex + 1) % colors.length;
            tvText.setTextColor(colors[colorIndex]);
            prefs.edit().putInt(KEY_COLOR_INDEX, colorIndex).apply();
        });
        btnChangeBg.setOnClickListener(v -> {
            imageIndex = (imageIndex + 1) % images.length;
            bgImage.setImageResource(images[imageIndex]);
        });
        switchNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }
}