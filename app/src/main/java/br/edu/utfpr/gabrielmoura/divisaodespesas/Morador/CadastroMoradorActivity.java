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

/* Responsable to register a 'Morador' at a 'ConjuntoFamiliar'.
 * It'll be the set of 'Moradores' of which live together and share the expenses */
public class CadastroMoradorActivity extends AppCompatActivity {

    private EditText editTextNome;
    private RadioGroup radioGroupGenero;
    private Spinner spinnerGrupoFamiliar;
    private CheckBox checkBoxRespContas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_morador);

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

        // refactor: Change all Toast with UtilsAlert
        Toast.makeText(this,
                R.string.limpeza_das_entradas,
                Toast.LENGTH_LONG).show();
    }

    public void salvarValores(View view) {
        // Get the values from the name field
        String nome = editTextNome.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.informar_um_nome,
                    Toast.LENGTH_LONG).show();

            editTextNome.requestFocus();
            return;
        }

        // Get the values from the gender field
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

        // Get the values from the grupo familiar field
        String grupoFamiliar = (String) spinnerGrupoFamiliar.getSelectedItem();

        // Get the values from the responsible for this account field
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