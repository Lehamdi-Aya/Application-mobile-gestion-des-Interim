package com.example.interim;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executors;

import BD.AppDatabase;
import entity.Offer;

public class ThirdFragment extends Fragment {

    private static final String TAG = "ThirdFragment";

    private EditText editTextTitle;
    private EditText editTextMinSalary;
    private EditText editTextMaxSalary;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextPostalAddress;
    private EditText editTextCity;
    private EditText editTextCountry;
    private EditText editTextContractType;
    private EditText editTextCategory;
    private EditText editTextProfession;
    private EditText editTextDescription;
    private Button buttonCreateOffer;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle fragment arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        editTextTitle = view.findViewById(R.id.edit_text_new_offer_title);
        editTextMinSalary = view.findViewById(R.id.edit_text_new_offer_min_salary);
        editTextMaxSalary = view.findViewById(R.id.edit_text_new_offer_max_salary);
        editTextStartDate = view.findViewById(R.id.edit_text_new_offer_start_date);
        editTextEndDate = view.findViewById(R.id.edit_text_new_offer_end_date);
        editTextPostalAddress = view.findViewById(R.id.edit_text_new_offer_postal_address);
        editTextCity = view.findViewById(R.id.edit_text_new_offer_city);
        editTextCountry = view.findViewById(R.id.edit_text_new_offer_country);
        editTextContractType = view.findViewById(R.id.edit_text_new_offer_contract_type);
        editTextCategory = view.findViewById(R.id.edit_text_new_offer_category);
        editTextProfession = view.findViewById(R.id.edit_text_new_offer_profession);
        editTextDescription = view.findViewById(R.id.edit_text_new_offer_description);
        buttonCreateOffer = view.findViewById(R.id.button_job_offer_new);

        buttonCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOffer();
            }
        });

        return view;
    }

    private void saveOffer() {
        String title = editTextTitle.getText().toString();
        double minSalary = Double.parseDouble(editTextMinSalary.getText().toString());
        double maxSalary = Double.parseDouble(editTextMaxSalary.getText().toString());
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();
        String postalAddress = editTextPostalAddress.getText().toString();
        String city = editTextCity.getText().toString();
        String country = editTextCountry.getText().toString();
        String contractType = editTextContractType.getText().toString();
        String category = editTextCategory.getText().toString();
        String profession = editTextProfession.getText().toString();
        String description = editTextDescription.getText().toString();

        // Log the input values
        Log.d(TAG, "Title: " + title);
        Log.d(TAG, "Min Salary: " + minSalary);
        Log.d(TAG, "Max Salary: " + maxSalary);
        Log.d(TAG, "Start Date: " + startDate);
        Log.d(TAG, "End Date: " + endDate);
        Log.d(TAG, "Postal Address: " + postalAddress);
        Log.d(TAG, "City: " + city);
        Log.d(TAG, "Country: " + country);
        Log.d(TAG, "Contract Type: " + contractType);
        Log.d(TAG, "Category: " + category);
        Log.d(TAG, "Profession: " + profession);
        Log.d(TAG, "Description: " + description);

        // Assuming the employerId is available
        int employerId = 1; // Replace with actual employer ID
        Log.d(TAG, "Employer ID: " + employerId);

        Offer offer = new Offer();
        offer.setTitle(title);
        offer.setMinSalary(minSalary);
        offer.setMaxSalary(maxSalary);
        offer.setStartDate(startDate);
        offer.setEndDate(endDate);
        offer.setPostalCode(postalAddress);
        offer.setCity(city);
        offer.setCity(country); // Correct the set method for country
        offer.setContractType(contractType);
        offer.setCategory(category);
        offer.setProfession(profession);
        offer.setDescription(description);
        offer.setEmployerId(employerId);

        AppDatabase db = AppDatabase.getInstance(getContext());

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.offerDao().insert(offer);
                    Log.d(TAG, "Offer successfully inserted into the database.");

                    // Show toast and reset fields on the main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Offre ajoutée avec succès", Toast.LENGTH_SHORT).show();
                            resetFields();
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Error inserting offer: " + e.getMessage(), e);
                }
            }
        });
    }

    private void resetFields() {
        editTextTitle.setText("");
        editTextMinSalary.setText("");
        editTextMaxSalary.setText("");
        editTextStartDate.setText("");
        editTextEndDate.setText("");
        editTextPostalAddress.setText("");
        editTextCity.setText("");
        editTextCountry.setText("");
        editTextContractType.setText("");
        editTextCategory.setText("");
        editTextProfession.setText("");
        editTextDescription.setText("");
    }
}
