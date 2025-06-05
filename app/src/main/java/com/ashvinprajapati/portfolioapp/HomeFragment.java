package com.ashvinprajapati.portfolioapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// For use fragment initialize views in onCreateView() method and set listeners and start animations in onViewCreated() method : this is important to do otherwise your can crash

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LottieAnimationView sprinkleAnimation;
    View heroSection,skillsSection,contactSection;
    ImageView profileImageView;
    MaterialButton btn_github,btn_linkedIn;
    TextView TypeWritterText,nameTV, footerText;
    String text = "Android Developer | Web Developer ";
    String text2 = " Ashvin Prajapati ";
    String text3 = "Let’s build something amazing together 🚀";
    int index = 0;
    long delay = 100; // Delay in ms
    Handler handler = new Handler();

    Runnable name = new Runnable() {
        @Override
        public void run() {
            if (index <= text2.length()) {
                nameTV.setText(text2.substring(0, index));
                index++;
                handler.postDelayed(this, delay);
            }
        }
    };
    Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            if (index <= text.length()) {
                TypeWritterText.setText(text.substring(0, index));
                index++;
                handler.postDelayed(this, delay);
            }
        }
    };
    Runnable footertext = new Runnable() {
        @Override
        public void run() {
            if (index <= text3.length()) {
                footerText.setText(text3.substring(0, index));
                index++;
                handler.postDelayed(this, delay);
            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    private void typeWriter() {
        index = 0;
        handler.post(name);
        handler.post(characterAdder);
        handler.post(footertext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TypeWritterText = rootView.findViewById(R.id.TypeWritterText);
        nameTV = rootView.findViewById(R.id.nameTV);
        footerText = rootView.findViewById(R.id.footerText);
        profileImageView = rootView.findViewById(R.id.profileImageView);
        sprinkleAnimation = rootView.findViewById(R.id.sprinkleAnimation);
        heroSection = rootView.findViewById(R.id.heroSection);
        skillsSection = rootView.findViewById(R.id.skills);
        contactSection = rootView.findViewById(R.id.contact_info);
        btn_github = rootView.findViewById(R.id.btn_github);
        btn_linkedIn = rootView.findViewById(R.id.btn_linkedIn);
        return  rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Now views are initialized, set listeners and start animations

        btn_github.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ashvin424"));
            startActivity(i);
        });

        btn_linkedIn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/prajapati-ashvin-4965b828a"));
            startActivity(i);
        });

        typeWriter();

        Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        profileImageView.setOnLongClickListener(v -> {
            Toast.makeText(this.getContext(), "You Found Secret Animation 🎉", Toast.LENGTH_SHORT).show();
            v.startAnimation(bounce);
            sprinkleAnimation.setVisibility(View.VISIBLE);
            sprinkleAnimation.playAnimation();

            sprinkleAnimation.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    sprinkleAnimation.setVisibility(View.GONE);
                    sprinkleAnimation.removeAnimatorListener(this);
                }
            });
            return true;
        });

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_slide_up);
        heroSection.startAnimation(anim);
        skillsSection.startAnimation(anim);
        contactSection.startAnimation(anim);
    }
}