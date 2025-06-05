package com.ashvinprajapati.portfolioapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ashvinprajapati.portfolioapp.Models.ContactForm;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    View emailCard, numberCard, locationCard, sendMessageCard,responseCard;
    EditText name,email,subject,message;
    DatabaseReference databaseRef;
    Button send_btn;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        emailCard = view.findViewById(R.id.emailCard);
        numberCard = view.findViewById(R.id.numberCard);
        locationCard = view.findViewById(R.id.locationCard);
        sendMessageCard = view.findViewById(R.id.sendMessageCard);
        responseCard = view.findViewById(R.id.responseCard);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        subject = view.findViewById(R.id.subject);
        message = view.findViewById(R.id.message);
        send_btn = view.findViewById(R.id.send_btn);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_slide_up);
        emailCard.startAnimation(anim);
        numberCard.startAnimation(anim);
        locationCard.startAnimation(anim);
        sendMessageCard.startAnimation(anim);
        responseCard.startAnimation(anim);

        Log.d("ContactFragment", "Fragment Loaded");

        databaseRef = FirebaseDatabase.getInstance().getReference("contact_messages");

        send_btn.setOnClickListener(v -> {
            String name_text = name.getText().toString();
            String email_text = email.getText().toString();
            String subject_text = subject.getText().toString();
            String message_text = message.getText().toString();

            if (name_text.isEmpty()) {
                name.setError("Name is required");
                return;
            }
            if (email_text.isEmpty()) {
                email.setError("Email is required");
                return;
            }
            if (subject_text.isEmpty()) {
                subject.setError("Subject is required");
                return;
            }
            if (message_text.isEmpty()) {
                message.setError("Message is required");
                return;
            }

            // Save data to Firebase
            String id = databaseRef.push().getKey(); // Generate unique key
            ContactForm form = new ContactForm(name_text, email_text, subject_text, message_text);

            if (id != null) {
                databaseRef.child(id).setValue(form)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            subject.setText("");
                            message.setText("");
                        })
                        .addOnFailureListener(e -> Toast.makeText(getContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

}