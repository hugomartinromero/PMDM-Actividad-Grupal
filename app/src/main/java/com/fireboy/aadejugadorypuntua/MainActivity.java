package com.fireboy.aadejugadorypuntua;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RadioGroup rgEquipos;
    RadioButton rbRealMadrid, rbBarcelona, rbSevilla, rbBetis;
    EditText txtNombre;
    Button btnAniadir;
    Spinner spJugadores;
    TextView lblNombre, lblEstrellas, lblMediaEquipo;
    RatingBar ratingBar;
    LinearLayout estrellasJugador;

    Map<String, Float> jMadrid = new HashMap<>();
    Map<String, Float> jBarcelona = new HashMap<>();
    Map<String, Float> jSevilla = new HashMap<>();
    Map<String, Float> jBetis = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgEquipos = findViewById(R.id.rgEquipos);
        rbRealMadrid = findViewById(R.id.rbRealMadrid);
        rbBarcelona = findViewById(R.id.rbBarcelona);
        rbSevilla = findViewById(R.id.rbSevilla);
        rbBetis = findViewById(R.id.rbBetis);
        txtNombre = findViewById(R.id.txtNombre);
        btnAniadir = findViewById(R.id.btnAniadir);
        spJugadores = findViewById(R.id.spJugadores);
        lblNombre = findViewById(R.id.lblNombre);
        lblEstrellas = findViewById(R.id.lblEstrellas);
        lblMediaEquipo = findViewById(R.id.lblMediaEquipo);
        ratingBar = findViewById(R.id.ratingBar);
        estrellasJugador = findViewById(R.id.estrellasJugador);

        rgEquipos.setOnCheckedChangeListener((radioGroup, i) -> cambiarLista());
        btnAniadir.setOnClickListener(v -> aniadirJugador());
        spJugadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarJugador();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            darValor();
            mostrarMediaEquipo();
        });
    }

    private void cambiarLista() {
        ArrayList<String> array = new ArrayList<>(getJugadoresEquipo().keySet());

        if (array != null) {
            spJugadores.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array));

            if (spJugadores.getSelectedItem() != null) {
                spJugadores.setVisibility(View.VISIBLE);
                estrellasJugador.setVisibility(View.VISIBLE);
            } else {
                spJugadores.setVisibility(View.INVISIBLE);
                estrellasJugador.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void aniadirJugador() {
        String nombreJugador = txtNombre.getText().toString();

        if (!nombreJugador.isEmpty()) {
            getJugadoresEquipo().put(nombreJugador, ratingBar.getRating());
            cambiarLista();
        }
    }

    private void mostrarJugador() {
        String jugadorSeleccionado = (String) spJugadores.getSelectedItem();
        if (jugadorSeleccionado != null) {
            lblNombre.setText(jugadorSeleccionado);
            ratingBar.setRating(getJugadoresEquipo().get(jugadorSeleccionado));
        }
    }

    private void darValor() {
        float estrellas = ratingBar.getRating();

        getJugadoresEquipo().put(txtNombre.getText().toString(), estrellas);
        lblEstrellas.setText(String.valueOf(estrellas));
    }

    private void mostrarMediaEquipo() {
        Map<String, Float> equipo = getJugadoresEquipo();

        String nombreEquipo = getNombreEquipo();
        float media = 0;

        for (Float valor : equipo.values()) {
            media += valor;
        }

        media /= equipo.size();

        lblMediaEquipo.setText(String.format("El equipo %s tiene una media de %.2f", nombreEquipo, media));
    }

    private String getNombreEquipo() {
        if (rgEquipos.getCheckedRadioButtonId() == R.id.rbRealMadrid) {
            return rbRealMadrid.getText().toString();
        } else if (rgEquipos.getCheckedRadioButtonId() == R.id.rbBarcelona) {
            return rbBarcelona.getText().toString();
        } else if (rgEquipos.getCheckedRadioButtonId() == R.id.rbSevilla) {
            return rbSevilla.getText().toString();
        } else if (rgEquipos.getCheckedRadioButtonId() == R.id.rbBetis) {
            return rbBetis.getText().toString();
        }
        return "";
    }

    private Map<String, Float> getJugadoresEquipo() {
        if (getNombreEquipo().equals("Real Madrid")) {
            return jMadrid;
        } else if (getNombreEquipo().equals("Barcelona")) {
            return jBarcelona;
        } else if (getNombreEquipo().equals("Sevilla FC")) {
            return jSevilla;
        } else if (getNombreEquipo().equals("Real Betis")) {
            return jBetis;
        }
        return null;
    }
}