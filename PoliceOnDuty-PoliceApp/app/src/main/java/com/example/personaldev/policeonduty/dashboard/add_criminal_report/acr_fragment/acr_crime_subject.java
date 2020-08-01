package com.example.personaldev.policeonduty.dashboard.add_criminal_report.acr_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.add_criminal_report.Add_Criminal_Report;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.Criminal_detailed_view;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link acr_crime_subject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class acr_crime_subject extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btn_cs_nxt_fragment;
    ImageView backActivity;

    public acr_crime_subject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment acr_crime_subject.
     */
    // TODO: Rename and change types and number of parameters
    public static acr_crime_subject newInstance(String param1, String param2) {
        acr_crime_subject fragment = new acr_crime_subject();
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
        View v = inflater.inflate(R.layout.fragment_acr_crime_subject, container, false);

        btn_cs_nxt_fragment = v.findViewById(R.id.acr_cs_btn_next_frag);
        btn_cs_nxt_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acr_criminal_location_details acr_cld = new acr_criminal_location_details();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.acr_main_holder, acr_cld);
                transaction.commit();

            }
        });

        backActivity = v.findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Dashboard.class));
            }
        });


        return v;
    }
}
