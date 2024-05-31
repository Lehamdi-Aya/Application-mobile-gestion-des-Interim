package com.example.interim;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import BD.AppDatabase;
import entity.Offer;

public class Utilisateur_Anonyme extends AppCompatActivity {

    private static final String TAG = "Utilisateur_Anonyme";
    private static final int PERMISSION_REQUEST_LOCATION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView recyclerView;
    private OfferAdapterAnonyme offersAdapter;
    private EditText editTextJobOfferSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_utilisateur_anonyme);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        recyclerView = findViewById(R.id.recycler_view_offers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        offersAdapter = new OfferAdapterAnonyme(new ArrayList<>());
        recyclerView.setAdapter(offersAdapter);

        editTextJobOfferSearch = findViewById(R.id.edit_text_job_offer_list_search);
        editTextJobOfferSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchOffers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Vérifier la permission de localisation
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Demander la permission
            Log.d(TAG, "Demande de permission de localisation.");
            requestLocationPermission();
        } else {
            // La permission est déjà accordée, obtenir la localisation
            Log.d(TAG, "Permission de localisation déjà accordée.");
            getLocationAndShowOffers();
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSION_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, obtenir la localisation
                Log.d(TAG, "Permission de localisation accordée.");
                getLocationAndShowOffers();
            } else {
                // Permission refusée, afficher un message à l'utilisateur et lui demander d'activer la localisation
                Log.d(TAG, "Permission de localisation refusée.");
                showLocationSettingsDialog();
            }
        }
    }

    private void showLocationSettingsDialog() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // Tous les paramètres de localisation sont satisfaits. On peut initier les demandes de localisation.
                    getLocationAndShowOffers();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Les paramètres de localisation ne sont pas satisfaits. Mais cela peut être corrigé en montrant une boîte de dialogue.
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(Utilisateur_Anonyme.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Les paramètres de localisation ne sont pas satisfaits. Cependant, nous n'avons aucun moyen de les corriger.
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                // L'utilisateur a activé les paramètres de localisation.
                getLocationAndShowOffers();
            } else {
                // L'utilisateur n'a pas activé les paramètres de localisation.
                Toast.makeText(this, "La localisation est nécessaire pour afficher les offres.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLocationAndShowOffers() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permissions de localisation non accordées.");
            return;
        }

        Log.d(TAG, "Obtention de la localisation...");
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            Log.d(TAG, "Localisation obtenue: " + location);
                            String city = getCityFromLocation(location);
                            if (city != null) {
                                Log.d(TAG, "Votre localisation est: " + city);
                                Toast.makeText(Utilisateur_Anonyme.this, "Votre localisation est: " + city, Toast.LENGTH_LONG).show();
                                fetchOffersFromDatabase(city);
                            } else {
                                Log.d(TAG, "Impossible de déterminer la ville.");
                                Toast.makeText(Utilisateur_Anonyme.this, "Impossible de déterminer la ville.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "La localisation est null.");
                            // Gérer le cas où la localisation est null
                        }
                    }
                });
    }

    private String getCityFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                String city = addresses.get(0).getLocality();
                Log.d(TAG, "Ville obtenue du geocoder: " + city);
                return city;
            }
        } catch (IOException e) {
            Log.e(TAG, "Erreur lors de l'obtention de la ville: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void fetchOffersFromDatabase(String city) {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Recherche d'offres pour la ville: " + city);
                List<Offer> offers = db.offerDao().getOffersByCity(city);
                Log.d(TAG, "Nombre d'offres trouvées pour " + city + ": " + offers.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayOffers(offers);
                    }
                });
            }
        }).start();
    }

    private void displayOffers(List<Offer> offers) {
        // Mettre à jour l'adaptateur du RecyclerView avec les offres récupérées
        if (offersAdapter != null) {
            Log.d(TAG, "Affichage des offres dans le RecyclerView.");
            offersAdapter.updateOffers(offers);
        } else {
            Log.d(TAG, "L'adaptateur d'offres est null.");
        }
    }

    private void searchOffers(String query) {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Recherche d'offres avec la requête: " + query);
                List<Offer> offers = db.offerDao().searchOffers("%" + query + "%");
                Log.d(TAG, "Nombre d'offres trouvées pour la recherche '" + query + "': " + offers.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayOffers(offers);
                    }
                });
            }
        }).start();
    }
}
