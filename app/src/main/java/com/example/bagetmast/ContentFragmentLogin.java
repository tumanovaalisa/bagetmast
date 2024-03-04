package com.example.bagetmast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentFragmentLogin extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Map<String, List<String>> usersInfo = new HashMap<>();

    public ContentFragmentLogin(){
        super(R.layout.auto_fragment);
    }
    public static ContentFragmentLogin newInstance(){
        return new ContentFragmentLogin();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CurrentUser.initialization();
        CurrentAssortement.initialization();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<String> info = new ArrayList<>();
                                String keyEmail = "";
                                info.add(document.getId().toString());
                                for(Map.Entry<String,Object> docs : document.getData().entrySet()){
                                    if (docs.getKey().equals("Email")) keyEmail = docs.getValue().toString();
                                    info.add(docs.getValue().toString());
                                }
                                usersInfo.put(keyEmail, info);
                            }
                        }
                    }
                });

        Intent intent = new Intent(getActivity(), MainActivity.class);
        Intent intent2 = new Intent(getActivity(), AdminActivity.class);
        Button toRegBtn = view.findViewById(R.id.signup);
        Button toSignInBtn = view.findViewById(R.id.signin);
        EditText emailText = view.findViewById(R.id.email_auth);
        EditText passwText = view.findViewById(R.id.passw_auth);

        toRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, ContentFragmentReg.newInstance());
                ft.commit();
            }
        });
        toSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailText.getText().toString().equals("") && !passwText.getText().toString().equals("")) {
                    mAuth.signInWithEmailAndPassword(emailText.getText().toString(), passwText.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        CurrentUser.email = emailText.getText().toString();

                                        CurrentUser.getUser(emailText.getText().toString(),
                                                usersInfo.get(emailText.getText().toString()).get(8),
                                                usersInfo.get(emailText.getText().toString()).get(6),
                                                usersInfo.get(emailText.getText().toString()).get(7),
                                                usersInfo.get(emailText.getText().toString()).get(5),
                                                usersInfo.get(emailText.getText().toString()).get(3),
                                                usersInfo.get(emailText.getText().toString()).get(2),
                                                usersInfo.get(emailText.getText().toString()).get(0),
                                                Integer.parseInt(usersInfo.get(emailText.getText().toString()).get(1)));

                                        if (CurrentUser.role.equals("а")) startActivity(intent2);
                                        else startActivity(intent);
                                    } else {
                                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getContext(), "Неверные данные",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(getContext(), "Введите данные",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
