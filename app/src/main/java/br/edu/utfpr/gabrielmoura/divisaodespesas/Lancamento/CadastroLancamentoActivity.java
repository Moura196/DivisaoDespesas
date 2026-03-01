package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class CadastroLancamentoActivity extends AppCompatActivity {

    private EditText editTextDescricao;
    private EditText editTextValorTotal;
    private EditText editTextDate;
    private Spinner spinnerMoradorComprador;
    private CheckBox checkBoxTipoLancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_lancamento);

        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextValorTotal = findViewById(R.id.editTextValorTotal);
        editTextDate = findViewById(R.id.editTextDate);
        spinnerMoradorComprador = findViewById(R.id.spinnerMoradorComprador);
        checkBoxTipoLancamento = findViewById(R.id.checkBoxTipoLancamento);
    }

    public void limparCampos(View view) {
        editTextDescricao.setText(null);
        editTextValorTotal.setText(null);
        editTextDate.setSelection(0);
        spinnerMoradorComprador.setSelection(0);
        checkBoxTipoLancamento.setChecked(false);

        editTextDescricao.requestFocus();

        // VERIFICAR REQUISITOS DO PROFESSOR
        Toast.makeText(this,
                R.string.limpeza_das_entradas,
                Toast.LENGTH_LONG).show();
    }

    public void salvarValores(View view) {
        String descricao = editTextDescricao.getText().toString();

        if (descricao == null || descricao.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.informar_descricao,
                    Toast.LENGTH_LONG).show();

            editTextDescricao.requestFocus();
            return;
        }

        Double valorTotal = Double.valueOf(editTextValorTotal.getText().toString());

        if (valorTotal == null || valorTotal <= 0) {
            Toast.makeText(this,
                    R.string.informar_valor_total,
                    Toast.LENGTH_LONG).show();

            editTextValorTotal.requestFocus();
            return;
        }

        Date dataLancamento = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataLancamento = sdf.parse(editTextDate.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(
                    this,
                    R.string.informe_data_corretamente,
                    Toast.LENGTH_LONG).show();

            editTextDate.requestFocus();
            return;
        }

        String moradorComprador = (String) spinnerMoradorComprador.getSelectedItem();

        boolean tipoLancamento = checkBoxTipoLancamento.isChecked();

        Toast.makeText(this,
                "Descrição: " + descricao +
                        "\nValor Total: " + valorTotal +
                        "\nData: " + dataLancamento +
                        "\nMorador Comprador: " + moradorComprador +
                        "\nTipo de lançamento: " + (tipoLancamento ?
                        getString(R.string.tipo_conta_casa) :
                        getString(R.string.tipo_conta_mercado)),
                Toast.LENGTH_LONG).show();
    }
}