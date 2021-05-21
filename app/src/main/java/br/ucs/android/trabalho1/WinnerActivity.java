package br.ucs.android.trabalho1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Intent it = getIntent();
        Bitmap bitmap = it.getParcelableExtra("bitmap");

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }
}