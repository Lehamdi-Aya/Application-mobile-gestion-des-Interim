package com.example.interim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Postuller_a_unOffre extends AppCompatActivity {
    private Button buttonSend;
    private ActivityResultLauncher<Intent> cvLauncher;
    private ActivityResultLauncher<Intent> letterLauncher;
    private TextView textViewCvChooserHint;
    private TextView textViewCoverLetterHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postuller_aun_offre);

        textViewCvChooserHint = findViewById(R.id.text_view_cv_chooser_hint_candidacy_new);
        textViewCoverLetterHint = findViewById(R.id.text_view_cover_letter_hint_candidacy_new);
        buttonSend = findViewById(R.id.button_send);
        cvLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        textViewCvChooserHint.setText(uri.getPath());
                        // Do something with the URI, e.g., store it or display the file name
                    }
                }
        );

        letterLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        textViewCoverLetterHint.setText(uri.getPath());
                        // Do something with the URI, e.g., store it or display the file name
                    }
                }
        );

        findViewById(R.id.linear_layout_cv_choose).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            cvLauncher.launch(intent);
        });

        findViewById(R.id.linear_layout_cover_letter_choose).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            letterLauncher.launch(intent);
        });

        buttonSend.setOnClickListener(view -> {
            Toast.makeText(Postuller_a_unOffre.this, "Votre candidature a été bien envoyée", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Postuller_a_unOffre.this, ActivityNavigation.class);
            startActivity(intent);
        });
    }
}
