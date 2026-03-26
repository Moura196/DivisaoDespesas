package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    public static final String KEY_MODO = "MODO";
    public static final String KEY_SUGERIR_MORADOR_COMPRADOR = "SUGERIR_MORADOR_COMPRADOR";
    public static final String KEY_ULTIMO_MORADOR_COMPRADOR = "ULTIMO_MORADOR_COMPRADOR";

    public static final int MODO_CADASTRO = 0;
    public static final int MODO_EDITAR = 1;

    private EditText editTextDescricao;
    private EditText editTextValorTotal;
    private EditText editTextDate;
    private Spinner spinnerMoradorComprador;
    private CheckBox checkBoxTipoLancamento;
    private FloatingActionButton fabAddItem;
    private int modo;
    private Lancamento lancamentoOriginal;

    private boolean sugerirMoradorComprador = false;
    private int ultimoMoradorComprador = 0;

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
        fabAddItem = findViewById(R.id.fabAddItem);

        fabAddItem.setVisibility(
                checkBoxTipoLancamento.isChecked() ?
                        View.GONE :
                        View.VISIBLE);

        checkBoxTipoLancamento.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fabAddItem.setVisibility(View.GONE);
            } else {
                fabAddItem.setVisibility(View.VISIBLE);
            }
        });

        lerPreferencias();

        Intent intentAbertura = getIntent();

        Bundle bundle = intentAbertura.getExtras();
        if (bundle != null) {
            modo = bundle.getInt(KEY_MODO);

            if (modo == MODO_CADASTRO) {
                setTitle("Novo Lançamento");

                if (sugerirMoradorComprador) {
                    spinnerMoradorComprador.setSelection(ultimoMoradorComprador);
                }
            } else {
                setTitle("Editar Lançamemento");

                String descricao = bundle.getString(CadastroLancamentoActivity.KEY_DESCRICAO);
                Double valorTotal = bundle.getDouble(CadastroLancamentoActivity.KEY_VALOR_TOTAL);
                Date dataLancamento = (Date) bundle.getSerializable(CadastroLancamentoActivity.KEY_DATA_LANCAMENTO);
                int moradorComprador = bundle.getInt(CadastroLancamentoActivity.KEY_MORADOR_COMPRADOR);
                boolean tipoLancamento = bundle.getBoolean(CadastroLancamentoActivity.KEY_TIPO_LANCAMENTO);

                lancamentoOriginal = new Lancamento(descricao, valorTotal, dataLancamento, moradorComprador, tipoLancamento);

                editTextDescricao.setText(descricao);
                editTextValorTotal.setText(String.valueOf(valorTotal));

                SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                editTextDate.setText(displayFormat.format(dataLancamento));

                spinnerMoradorComprador.setSelection(moradorComprador);
                checkBoxTipoLancamento.setChecked(tipoLancamento);
            }
        }
    }

    public void limparCampos() {
        editTextDescricao.setText(null);
        editTextValorTotal.setText(null);
        editTextDate.setText(null);
        spinnerMoradorComprador.setSelection(0);
        checkBoxTipoLancamento.setChecked(false);

        editTextDescricao.requestFocus();

        Toast.makeText(this,
                R.string.limpeza_das_entradas,
                Toast.LENGTH_LONG).show();
    }

    public void salvarValores() {
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

        if (modo == MODO_EDITAR &&
            descricao.equalsIgnoreCase(lancamentoOriginal.getDescricao()) &&
            valorTotal.equals(lancamentoOriginal.getValor_total()) &&
            dataLancamento == lancamentoOriginal.getData() &&
            moradorComprador == lancamentoOriginal.getMorador_comprador() &&
            tipoLancamento == lancamentoOriginal.isTipo_lancamento()) {

            setResult(CadastroLancamentoActivity.RESULT_CANCELED);
            finish();
            return;
        }

        salvarUltimoMoradorComprador(moradorComprador);

        Intent intentResposta = new Intent();

        intentResposta.putExtra(KEY_DESCRICAO, descricao);
        intentResposta.putExtra(KEY_VALOR_TOTAL, valorTotal);
        intentResposta.putExtra(KEY_DATA_LANCAMENTO, dataLancamento);
        intentResposta.putExtra(KEY_MORADOR_COMPRADOR, moradorComprador);
        intentResposta.putExtra(KEY_TIPO_LANCAMENTO, tipoLancamento);

        setResult(CadastroLancamentoActivity.RESULT_OK, intentResposta);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lancamento_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menuItemSugerir);
        item.setChecked(sugerirMoradorComprador);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSalvar) {
            salvarValores();
            return true;
        } else if (idMenuItem == R.id.menuItemLimpar) {
            limparCampos();
            return true;
        } else if (idMenuItem == R.id.menuItemSugerir) {
            boolean valor = !item.isChecked();

            salvarSugerirMoradorComprador(valor);
            item.setChecked(valor);

            if (sugerirMoradorComprador) {
                spinnerMoradorComprador.setSelection(ultimoMoradorComprador);
            }

            return true;
        } else if (idMenuItem == R.id.fabAddItem) {

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void lerPreferencias() {
        SharedPreferences shared = getSharedPreferences(
                LancamentosActivity.ARQUIVO_PREFERENCIAS,
                Context.MODE_PRIVATE);

        sugerirMoradorComprador = shared.getBoolean(KEY_SUGERIR_MORADOR_COMPRADOR, sugerirMoradorComprador);
        ultimoMoradorComprador = shared.getInt(KEY_ULTIMO_MORADOR_COMPRADOR, ultimoMoradorComprador);
    }

    private void salvarSugerirMoradorComprador(boolean novoValor) {
        SharedPreferences shared = getSharedPreferences(
                LancamentosActivity.ARQUIVO_PREFERENCIAS,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_SUGERIR_MORADOR_COMPRADOR, novoValor);
        editor.commit();

        sugerirMoradorComprador = novoValor;
    }

    private void salvarUltimoMoradorComprador(int novoValor) {
        SharedPreferences shared = getSharedPreferences(
                LancamentosActivity.ARQUIVO_PREFERENCIAS,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(KEY_ULTIMO_MORADOR_COMPRADOR, novoValor);
        editor.commit();

        ultimoMoradorComprador = novoValor;
    }
}