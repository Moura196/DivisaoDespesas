package br.edu.utfpr.gabrielmoura.divisaodespesas.Sobre;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        setTitle(R.string.botao_sobre);
    }
}