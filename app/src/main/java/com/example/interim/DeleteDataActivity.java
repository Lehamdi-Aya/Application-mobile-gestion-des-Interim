package com.example.interim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import BD.AppDatabase;
import DAO.UserDao;
import DAO.EmployerDao;
import DAO.CandidatureDao;
import DAO.OfferDao;

public class DeleteDataActivity extends AppCompatActivity {

    private AppDatabase db;
    private UserDao userDao;
    private EmployerDao employerDao;
    private CandidatureDao candidatureDao;
    private OfferDao offerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        db = AppDatabase.getInstance(this);
        userDao = db.userDao();
        employerDao = db.employerDao();
        candidatureDao = db.candidatureDao();
        offerDao = db.offerDao();

        Button deleteUserButton = findViewById(R.id.deleteUserButton);
        Button deleteEmployerButton = findViewById(R.id.deleteEmployerButton);
        Button deleteCandidatureButton = findViewById(R.id.deleteCandidatureButton);
        Button deleteOfferButton = findViewById(R.id.deleteOfferButton);

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userDao.deleteAllUsers(); // Supprimer tous les utilisateurs
                    }
                }).start();
            }
        });

        deleteEmployerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        employerDao.deleteAllEmployers(); // Supprimer tous les employeurs
                    }
                }).start();
            }
        });

        deleteCandidatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        candidatureDao.deleteAllCandidatures(); // Supprimer toutes les candidatures
                    }
                }).start();
            }
        });

        deleteOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        offerDao.deleteAllOffers(); // Supprimer toutes les offres
                    }
                }).start();
            }
        });
    }
}
