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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ContentFragmentReg extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public ContentFragmentReg(){
        super(R.layout.reg_fragment);
    }

    public static ContentFragmentReg newInstance(){
        return new ContentFragmentReg();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        Button toLoginBtn = view.findViewById(R.id.back);
        Button toCreateBtn = view.findViewById(R.id.done);
        EditText loginText = view.findViewById(R.id.FIO_reg);
        EditText emailText = view.findViewById(R.id.email_reg);
        EditText passwText = view.findViewById(R.id.passw_reg);
        EditText companyText = view.findViewById(R.id.company_reg);
        EditText phoneText = view.findViewById(R.id.phone_reg);

        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, ContentFragmentLogin.newInstance());
                ft.commit();
            }
        });
        toCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailText.getText().toString().equals("") && !loginText.getText().toString().equals("") && !passwText.getText().toString().equals("")) {
                    mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwText.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Аккаунт успешно создан",
                                                Toast.LENGTH_SHORT).show();

                                        Map<String, Object> user1 = new HashMap<>();

                                        String[] FIO = loginText.getText().toString().split(" ");
                                        int k = 0;
                                        for (String name : FIO){
                                            if (k == 0){
                                                user1.put("Lastname", name);
                                                k++;
                                            }
                                            else if (k == 1){
                                                user1.put("Name", name);
                                                k++;
                                            }
                                            else if (k == 2) user1.put("Patronymic", name);
                                        }
                                        user1.put("Email", emailText.getText().toString());
                                        user1.put("Company", companyText.getText().toString());
                                        user1.put("Phone", phoneText.getText().toString());
                                        user1.put("Sale", "0");
                                        user1.put("Role", "п");

                                        db.collection("Users")
                                                .add(user1)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                    } else {
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getContext(), "Введены неверные данные или данная почта уже зарегистрирована!",
                                                Toast.LENGTH_LONG).show();
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