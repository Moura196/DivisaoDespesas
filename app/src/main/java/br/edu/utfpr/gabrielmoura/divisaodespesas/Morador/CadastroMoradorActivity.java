package br.edu.utfpr.gabrielmoura.divisaodespesas.Morador;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class CadastroMoradorActivity extends AppCompatActivity {

    private EditText editTextNome;
    private RadioGroup radioGroupGenero;
    private Spinner spinnerGrupoFamiliar;
    private CheckBox checkBoxRespContas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_morador_activity);

        editTextNome = findViewById(R.id.editTextNome);
        radioGroupGenero = findViewById(R.id.radioGroupGenero);
        spinnerGrupoFamiliar = findViewById(R.id.spinnerGrupoFamiliar);
        checkBoxRespContas = findViewById(R.id.checkBoxResponsavel);

    }

    public void limparCampos(View view) {
        editTextNome.setText(null);
        radioGroupGenero.clearCheck();
        spinnerGrupoFamiliar.setSelection(0);
        checkBoxRespContas.setChecked(false);

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
                    R.string.faltou_selecionar_um_genero,
                    Toast.LENGTH_LONG).show();
        };

        String grupoFamiliar = (String) spinnerGrupoFamiliar.getSelectedItem();

        boolean responsavel = checkBoxRespContas.isChecked();

        Toast.makeText(this,
                "Nome: " + nome +
                        "\nGênero: " + genero +
                        "\nGrupo Familiar: " + grupoFamiliar +
                        "\nResponsável: " + (responsavel ?
                            getString(R.string.responsavel_pelas_contas) :
                            getString(R.string.nao_responsavel)),
                        Toast.LENGTH_LONG).show();
    }
}