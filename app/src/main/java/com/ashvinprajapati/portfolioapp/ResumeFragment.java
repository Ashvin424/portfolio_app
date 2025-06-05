package com.ashvinprajapati.portfolioapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResumeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResumeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View experienceCard, educationCard, skillsCard, certificateCard;
    MaterialButton btn_resume;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResumeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResumeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResumeFragment newInstance(String param1, String param2) {
        ResumeFragment fragment = new ResumeFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        experienceCard = view.findViewById(R.id.experienceCard);
        educationCard = view.findViewById(R.id.educationCard);
        skillsCard = view.findViewById(R.id.keySkillCard);
        certificateCard = view.findViewById(R.id.certificationCard);
        btn_resume = view.findViewById(R.id.downloadResumeBtn);
        btn_resume.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                savePdfToDownloads("ashvin_prajapati_resume.pdf");
            }
        });
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_slide_up);
        experienceCard.startAnimation(anim);
        educationCard.startAnimation(anim);
        certificateCard.startAnimation(anim);
        skillsCard.startAnimation(anim);
        btn_resume.startAnimation(anim);

    }

    private void savePdfToDownloads(String assetFileName) {
        try {
            InputStream inputStream = requireContext().getAssets().open(assetFileName);

            // Create content values for MediaStore
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, assetFileName);
            values.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");
            values.put(MediaStore.Downloads.IS_PENDING, 1);

            // Insert into MediaStore Downloads collection
            ContentResolver resolver = requireContext().getContentResolver();
            Uri collection = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            }
            Uri fileUri = resolver.insert(collection, values);

            if (fileUri == null) {
                Toast.makeText(requireContext(), "Failed to create file", Toast.LENGTH_SHORT).show();
                return;
            }

            // Write the PDF file
            OutputStream outputStream = resolver.openOutputStream(fileUri);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();

            // Mark the file as ready
            values.clear();
            values.put(MediaStore.Downloads.IS_PENDING, 0);
            resolver.update(fileUri, values, null, null);

            Toast.makeText(requireContext(), "Resume saved to Downloads", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error saving Resume", Toast.LENGTH_SHORT).show();
        }
    }

}