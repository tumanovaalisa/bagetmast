package com.example.bagetmast;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

public class InfoFragment extends Fragment {
    public InfoFragment(){super(R.layout.info_fragment);
    }

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        Button toRegBtn = view.findViewById(R.id.back_Btn);
        Intent intent1 = new Intent(getActivity(), EntryActivity.class);
        Button toHistoryBtn = view.findViewById(R.id.history_BTN);
        Intent intent2 = new Intent(getActivity(), HistoryActivity.class);


        TextView textView = view.findViewById(R.id.infoBM);
        String text="";
        try {
            InputStream inputStream = getContext().getAssets().open("bagetmastInfo.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText(text);

        toRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
        toHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(intent2); }
        });
        return view;
    }
}
