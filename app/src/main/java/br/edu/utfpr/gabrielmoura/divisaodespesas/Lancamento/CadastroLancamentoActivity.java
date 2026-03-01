package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class CadastroLancamentoActivity extends AppCompatActivity {

    public static final String KEY_DESCRICAO = "KEY_DESCRICAO";
    public static final String KEY_VALOR_TOTAL = "KEY_VALOR_TOTAL";
    public static final String KEY_DATA_LANCAMENTO = "KEY_DATA_LANCAMENTO";
    public static final String KEY_MORADOR_COMPRADOR = "KEY_MORADOR_COMPRADOR";
    public static final String KEY_TIPO_LANCAMENTO = "KEY_TIPO_LANCAMENTO";
    private EditText editTextDescricao;
    private EditText editTextValorTotal;
    private EditText editTextDate;
    private Spinner spinnerMoradorComprador;
    private CheckBox checkBoxTipoLancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_lancamento);
        setTitle(R.string.page_cadastro_lancamento);

        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextValorTotal = findViewById(R.id.editTextValorTotal);
        editTextDate = findViewById(R.id.editTextDate);
        spinnerMoradorComprador = findViewById(R.id.spinnerMoradorComprador);
        checkBoxTipoLancamento = findViewById(R.id.checkBoxTipoLancamento);
    }

    public void limparCampos(View view) {
        editTextDescricao.setText(null);
        editTextValorTotal.setText(null);
        editTextDate.setText(null);
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

        String valorTotalStr = editTextValorTotal.getText().toString();
        if (valorTotalStr == null || valorTotalStr.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.informar_valor_total,
                    Toast.LENGTH_LONG).show();

            editTextValorTotal.requestFocus();
            return;
        }

        Double valorTotal;
        try {
            valorTotal = Double.valueOf(valorTotalStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    R.string.informar_valor_total,
                    Toast.LENGTH_LONG).show();

            editTextValorTotal.requestFocus();
            return;
        }

        if (valorTotal <= 0) {
            Toast.makeText(this,
                    R.string.informar_valor_total,
                    Toast.LENGTH_LONG).show();

            editTextValorTotal.requestFocus();
            return;
        }

        Date dataLancamento = null;
        String dataStr = editTextDate.getText().toString();
        if (dataStr == null || dataStr.trim().isEmpty()) {
            Toast.makeText(
                    this,
                    R.string.informe_data_corretamente,
                    Toast.LENGTH_LONG).show();

            editTextDate.requestFocus();
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dataLancamento = sdf.parse(dataStr);
        } catch (ParseException e) {
            Toast.makeText(
                    this,
                    R.string.informe_data_corretamente,
                    Toast.LENGTH_LONG).show();

            editTextDate.requestFocus();
            return;
        }

        int moradorComprador = spinnerMoradorComprador.getSelectedItemPosition();
        if (moradorComprador == AdapterView.INVALID_POSITION) {
            Toast.makeText(this,
                    R.string.informar_morador_comprador,
                    Toast.LENGTH_LONG).show();

            return;
        }


        boolean tipoLancamento = checkBoxTipoLancamento.isChecked();

        Intent intentResposta = new Intent();
        intentResposta.putExtra(KEY_DESCRICAO, descricao);
        intentResposta.putExtra(KEY_VALOR_TOTAL, valorTotal);
        intentResposta.putExtra(KEY_DATA_LANCAMENTO, dataLancamento);
        intentResposta.putExtra(KEY_MORADOR_COMPRADOR, moradorComprador);
        intentResposta.putExtra(KEY_TIPO_LANCAMENTO, tipoLancamento);

        setResult(CadastroLancamentoActivity.RESULT_OK, intentResposta);

        finish();
    }
}