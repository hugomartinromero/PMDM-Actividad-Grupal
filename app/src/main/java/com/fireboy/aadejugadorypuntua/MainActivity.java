package com.fireboy.aadejugadorypuntua;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    RadioGroup rgEquipos;
    EditText txtNombre;
    Button btnAniadir;
    Spinner spJugadores;
    TextView lblNombre, lblEstrellas, lblMediaEquipo;
    RatingBar ratingBar;
    Set<String> jMadrid = new HashSet<>();
    Set<String> jBarcelona = new HashSet<>();
    Set<String> jSevilla = new HashSet<>();
    Set<String> jBetis = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgEquipos = findViewById(R.id.rgEquipos);
        txtNombre = findViewById(R.id.txtNombre);
        btnAniadir = findViewById(R.id.btnAniadir);
        spJugadores = findViewById(R.id.spJugadores);
        lblNombre = findViewById(R.id.lblNombre);
        lblEstrellas = findViewById(R.id.lblEstrellas);
        lblMediaEquipo = findViewById(R.id.lblMediaEquipo);
        ratingBar = findViewById(R.id.ratingBar);

        rgEquipos.setOnCheckedChangeListener((radioGroup, i) -> cambiarLista());
        btnAniadir.setOnClickListener(v -> aniadirJugador());
        spJugadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarJugador();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> darValor());
    }

    private void mostrarJugador() {
        lblNombre.setText(spJugadores.getSelectedItem().toString());
    }

    private void cambiarLista() {
        ArrayAdapter<String> adapter = null;
        ArrayList<String> array = null;

        switch (verificarEquipo()) {
            case 1:
                array = new ArrayList<>(jMadrid);
            break;
            case 2:
                array = new ArrayList<>(jBarcelona);
                break;
            case 3:
                array = new ArrayList<>(jSevilla);
                break;
            case 4:
                array = new ArrayList<>(jBetis);
                break;
        }

        spJugadores.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array));
    }

    private void aniadirJugador() {
        switch (verificarEquipo()) {
            case 1:
                jMadrid.add(txtNombre.getText().toString());
                break;
            case 2:
                jBarcelona.add(txtNombre.getText().toString());
                break;
            case 3:
                jSevilla.add(txtNombre.getText().toString());
                break;
            case 4:
                jBetis.add(txtNombre.getText().toString());
                break;
        }

        cambiarLista();
    }

    private void darValor() {
        int valor = (int) ratingBar.getRating();
        lblEstrellas.setText(valor);
    }

    private int verificarEquipo() {
        if (rgEquipos.getCheckedRadioButtonId() == R.id.rbRealMadrid)  {
            return 1;
        } else if (rgEquipos.getCheckedRadioButtonId() == R.id.rbBarcelona)  {
            return 2;
        } else if (rgEquipos.getCheckedRadioButtonId() == R.id.rbSevilla)  {
            return 3;
        } else if (rgEquipos.getCheckedRadioButtonId() == R.id.rbBetis)  {
            return 4;
        }
        return 0;
    }
}