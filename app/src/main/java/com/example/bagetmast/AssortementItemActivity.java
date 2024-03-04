package com.example.bagetmast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AssortementItemActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView costView;
    private TextView countView;
    private TextView weightView;
    private TextView textView;
    private ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assortement);

        String assName = getIntent().getStringExtra("selectedName");
        double assCost = Double.parseDouble(getIntent().getStringExtra("selectedCost"));
        int assCount = Integer.parseInt(getIntent().getStringExtra("selectedCount"));
        int assWeight = Integer.parseInt(getIntent().getStringExtra("selectedWeight"));
        String assDescriptio = getIntent().getStringExtra("selectedDescription");
        String assImage = getIntent().getStringExtra("selectedImage");

        nameView = (TextView) findViewById(R.id.name1_TV);
        imageView = (ImageView) findViewById(R.id.img1_IV);
        costView = (TextView) findViewById(R.id.cost1_TV);
        countView = (TextView) findViewById(R.id.count1_TV);
        weightView = (TextView) findViewById(R.id.weight1_TV);
        textView = (TextView) findViewById(R.id.description1_TV);

        nameView.setText(assName);
        costView.setText(Double.toString(assCost) + " руб./кор.");
        countView.setText(Integer.toString(assCount) + " шт./в 1 коробке");
        weightView.setText(Integer.toString(assWeight) + "   г.");
        textView.setText(assDescriptio);

        Picasso.get()
                .load(assImage)
                .resize(400, 350)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public void backAss(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}