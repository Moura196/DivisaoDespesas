package br.edu.utfpr.gabrielmoura.divisaodespesas;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroItemActivity extends AppCompatActivity {

    private EditText editTextDescricaoItem;
    private EditText editTextQuantidade;
    private EditText editTextNumDecUnitario;
    private EditText editTextNumDecDesconto;
    private EditText editTextNumDecTotal;
    private CheckBox checkBoxTipoRateio;
    private Spinner spinnerCasalRateio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_item);
        setTitle(getString(R.string.cadastro_de_item));

        editTextDescricaoItem = findViewById(R.id.editTextDescricaoItem);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        editTextNumDecUnitario = findViewById(R.id.editTextNumDecUnitario);
        editTextNumDecDesconto = findViewById(R.id.editTextNumDecDesconto);
        editTextNumDecTotal = findViewById(R.id.editTextNumDecTotal);
        checkBoxTipoRateio = findViewById(R.id.checkBoxTipoRateio);
        spinnerCasalRateio = findViewById(R.id.spinnerCasalRateio);

        spinnerCasalRateio.setVisibility(
                checkBoxTipoRateio.isChecked() ?
                        View.GONE :
                        View.VISIBLE);

        checkBoxTipoRateio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                spinnerCasalRateio.setVisibility(View.GONE);
            } else {
                spinnerCasalRateio.setVisibility(View.VISIBLE);
            }
        });
    }
}