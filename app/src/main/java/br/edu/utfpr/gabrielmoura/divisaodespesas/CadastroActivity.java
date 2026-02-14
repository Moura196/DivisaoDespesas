package br.edu.utfpr.gabrielmoura.divisaodespesas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private RadioGroup radioGroupGenero;
    private Spinner spinnerGrupoFamiliar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_activity);

        editTextNome = findViewById(R.id.editTextNome);
        radioGroupGenero = findViewById(R.id.radioGroupGenero);
    }

    public void limparCampos(View view) {
        editTextNome.setText(null);
        radioGroupGenero.clearCheck();

        editTextNome.requestFocus();

        // VERIFICAR REQUISITOS DO PROFESSOR
        Toast.makeText(this,
                R.string.limpeza_das_entradas,
                Toast.LENGTH_LONG).show();
    }

    public void salvarValores(View view) {
        String nome = editTextNome.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {
            // VERIFICAR REQUISITOS DO PROFESSOR
            Toast.makeText(this,
                    R.string.informar_um_nome,
                    Toast.LENGTH_LONG).show();

            editTextNome.requestFocus();
            return;
        }

        int radioButtonId = radioGroupGenero.getCheckedRadioButtonId();

        String genero = "";
        if (radioButtonId == R.id.radioButtonMasculino) {
            genero = "Masculino";
        } else if (radioButtonId == R.id.radioButtonFeminino) {
            genero = "Feminino";
        } else {
            Toast.makeText(this,
                    "Faltou selecionar um gênero",
                    Toast.LENGTH_LONG).show();
        };

        Toast.makeText(this,
                "Nome: " + nome +
                        "\nGênero: " + genero,
                        Toast.LENGTH_LONG).show();
    }
}