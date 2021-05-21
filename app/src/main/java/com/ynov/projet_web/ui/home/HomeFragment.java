package com.ynov.projet_web.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ynov.projet_web.LoginActivity;
import com.ynov.projet_web.R;
import com.ynov.projet_web.databinding.FragmentHomeBinding;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    EditText editTextComment, editTextName;

    Button buttonSend;
    Button buttonLogout;

    Context context;

    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Upload upload;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();
        editTextComment = root.findViewById(R.id.Comment);
        editTextName = root.findViewById(R.id.Name);
        String commentText = editTextComment.getText().toString();

        upload = new Upload();

        buttonLogout = root.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(context, LoginActivity.class));
        });

        buttonSend = root.findViewById(R.id.buttonSend);

        databaseReference = firebaseDatabase.getInstance().getReference().child("Data");

        buttonSend.setOnClickListener(v -> {

                    upload.setName(editTextName.getText().toString());
                    upload.setComment(editTextComment.getText().toString());

                    String id = databaseReference.push().getKey();
                    databaseReference.child(id).setValue(upload);

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}