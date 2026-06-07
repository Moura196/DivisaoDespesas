package br.edu.utfpr.gabrielmoura.divisaodespesas.Item;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;
import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Item;

public class CadastroItemActivity extends AppCompatActivity {

    public static final String KEY_DESCRICAO_ITEM = "KEY_DESCRICAO_ITEM";
    public static final String KEY_QUANTIDADE_ITEM = "KEY_QUANTIDADE_ITEM";
    public static final String KEY_VALOR_UNITARIO_ITEM = "KEY_VALOR_UNITARIO_ITEM";
    public static final String KEY_VALOR_DESCONTO_ITEM = "KEY_VALOR_DESCONTO_ITEM";
    public static final String KEY_VALOR_TOTAL_ITEM = "KEY_VALOR_TOTAL_ITEM";
    public static final String KEY_TIPO_RATEIO_ITEM = "KEY_TIPO_RATEIO_ITEM";
    public static final String KEY_CASAL_RATEIO_ITEM = "KEY_CASAL_RATEIO_ITEM";

    public static final String KEY_MODO = "MODO";
    public static final int MODO_CADASTRO = 0;
    public static final int MODO_EDITAR = 1;

    private EditText editTextDescricaoItem;
    private EditText editTextQuantidade;
    private EditText editTextNumDecUnitario;
    private EditText editTextNumDecDesconto;
    private EditText editTextNumDecTotal;
    private CheckBox checkBoxTipoRateio;
    private Spinner spinnerCasalRateio;
    private int modo;
    private Item itemOriginal;

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
                        View.VISIBLE :
                        View.GONE);

        checkBoxTipoRateio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                spinnerCasalRateio.setVisibility(View.VISIBLE);
            } else {
                spinnerCasalRateio.setVisibility(View.GONE);
            }
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateValorTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTextQuantidade.addTextChangedListener(watcher);
        editTextNumDecUnitario.addTextChangedListener(watcher);
        editTextNumDecDesconto.addTextChangedListener(watcher);

        Intent intentAbertura = getIntent();

        Bundle bundle = intentAbertura.getExtras();
        if (bundle != null) {
            modo = bundle.getInt(KEY_MODO);

            if (modo == MODO_CADASTRO) {
                setTitle(getString(R.string.novo_item));
            } else {
                setTitle(getString(R.string.editar_item));

                String descricao = bundle.getString(CadastroItemActivity.KEY_DESCRICAO_ITEM);
                int quantidade = bundle.getInt(CadastroItemActivity.KEY_QUANTIDADE_ITEM);
                Double valorUnitario = bundle.getDouble(CadastroItemActivity.KEY_VALOR_UNITARIO_ITEM);
                Double valorDesconto = bundle.getDouble(CadastroItemActivity.KEY_VALOR_DESCONTO_ITEM);
                Double valorTotal = bundle.getDouble(CadastroItemActivity.KEY_VALOR_TOTAL_ITEM);
                boolean tipoRateio = bundle.getBoolean(CadastroItemActivity.KEY_TIPO_RATEIO_ITEM);
                int casalRateio = bundle.getInt(CadastroItemActivity.KEY_CASAL_RATEIO_ITEM);

                itemOriginal = new Item(
                        descricao,
                        quantidade,
                        valorUnitario,
                        valorDesconto,
                        valorTotal,
                        tipoRateio,
                        casalRateio);

                editTextDescricaoItem.setText(descricao);
                editTextQuantidade.setText(String.valueOf(quantidade));
                editTextNumDecUnitario.setText(String.format("%.2f", valorUnitario));
                editTextNumDecDesconto.setText(String.format("%.2f", valorDesconto));
                editTextNumDecTotal.setText(String.format("%.2f", valorTotal));
                checkBoxTipoRateio.setChecked(tipoRateio);

                if (tipoRateio) {
                    spinnerCasalRateio.setSelection(casalRateio);
                }
            }
        }
    }

    private void updateValorTotal() {
        String quantidadeStr = editTextQuantidade.getText().toString();
        String valorUnitarioStr = editTextNumDecUnitario.getText().toString();
        String valorDescontoStr = editTextNumDecDesconto.getText().toString();

        if (quantidadeStr.isEmpty() || valorUnitarioStr.isEmpty() || valorDescontoStr.isEmpty()) {
            editTextNumDecTotal.setText("");
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            double valorUnitario = Double.parseDouble(valorUnitarioStr);
            double valorDesconto = Double.parseDouble(valorDescontoStr);

            Double valorTotal = calculoValorTotal(quantidade, valorUnitario, valorDesconto);

            editTextNumDecTotal.setText(String.format("%.2f", valorTotal));
        } catch (NumberFormatException e) {
            editTextNumDecTotal.setText("");
        }
    }

    public void limparCampos() {
        editTextDescricaoItem.setText(null);
        editTextQuantidade.setText(null);
        editTextNumDecUnitario.setText(null);
        editTextNumDecDesconto.setText(null);
        editTextNumDecTotal.setText(null);
        checkBoxTipoRateio.setChecked(false);
        spinnerCasalRateio.setSelection(0);

        editTextDescricaoItem.requestFocus();

        Toast.makeText(this,
                R.string.limpeza_das_entradas,
                Toast.LENGTH_LONG).show();
    }

    public void salvarValores() {
        String descricao = editTextDescricaoItem.getText().toString();
        if (descricao == null || descricao.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.descricao_do_item_obrigatoria,
                    Toast.LENGTH_LONG).show();

            editTextDescricaoItem.requestFocus();
            return;
        }

        int quantidadeItem = 1;
        String quantidadeStr = editTextQuantidade.getText().toString();
        if (quantidadeStr == null || quantidadeStr.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.informar_um_quantidade_valida_para_item,
                    Toast.LENGTH_LONG).show();

            editTextQuantidade.requestFocus();
            return;
        }

        try {
            quantidadeItem = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    R.string.informar_um_quantidade_valida_para_item,
                    Toast.LENGTH_LONG).show();
            editTextQuantidade.requestFocus();
            return;
        }

        if (quantidadeItem <= 0) {
            Toast.makeText(this,
                    R.string.informar_um_quantidade_valida_para_item,
                    Toast.LENGTH_LONG).show();

            editTextQuantidade.requestFocus();
            return;
        }

        String valorUnitarioStr = editTextNumDecUnitario.getText().toString();
        if (valorUnitarioStr == null || valorUnitarioStr.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.informe_valor_unitario_item,
                    Toast.LENGTH_LONG).show();

            editTextNumDecUnitario.requestFocus();
            return;
        }

        Double valorUnitario;
        try {
            valorUnitario = Double.valueOf(valorUnitarioStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    R.string.informe_valor_unitario_item,
                    Toast.LENGTH_LONG).show();

            editTextNumDecUnitario.requestFocus();
            return;
        }

        if (valorUnitario <= 0) {
            Toast.makeText(this,
                    R.string.informe_valor_unitario_item,
                    Toast.LENGTH_LONG).show();

            editTextNumDecUnitario.requestFocus();
            return;
        }

        String valorDescontoStr = editTextNumDecDesconto.getText().toString();
        if (valorDescontoStr == null || valorDescontoStr.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.informe_valor_desconto_item,
                    Toast.LENGTH_LONG).show();

            editTextNumDecDesconto.requestFocus();
            return;
        }

        Double valorDesconto;
        try {
            valorDesconto = Double.valueOf(valorDescontoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    R.string.informe_valor_desconto_item,
                    Toast.LENGTH_LONG).show();

            editTextNumDecDesconto.requestFocus();
            return;
        }

        if (valorDesconto < 0) {
            Toast.makeText(this,
                    R.string.informe_valor_desconto_item,
                    Toast.LENGTH_LONG).show();

            editTextNumDecDesconto.requestFocus();
            return;
        }

        String valorTotalStr = editTextNumDecTotal.getText().toString();
        Double valorTotal;
        try {
            valorTotal = Double.valueOf(valorTotalStr);
        } catch (NullPointerException nPEx) {
            Toast.makeText(this,
                    R.string.valor_total_invalido,
                    Toast.LENGTH_LONG).show();

            editTextNumDecTotal.requestFocus();
            return;
        }

        boolean tipoRateio = checkBoxTipoRateio.isChecked();

        int casalRateio = spinnerCasalRateio.getSelectedItemPosition();
        if (casalRateio == AdapterView.INVALID_POSITION) {
            Toast.makeText(this,
                    R.string.informar_casal_para_rateio,
                    Toast.LENGTH_LONG).show();

            return;
        }

        if (modo == MODO_EDITAR &&
                descricao.equalsIgnoreCase(itemOriginal.getDescricao()) &&
                quantidadeItem == itemOriginal.getQuantidade() &&
                valorUnitario.equals(itemOriginal.getValor_unitario()) &&
                valorDesconto.equals(itemOriginal.getValor_desconto()) &&
                valorTotal.equals(itemOriginal.getValor_total()) &&
                tipoRateio == itemOriginal.isRateio_casal() &&
                casalRateio == spinnerCasalRateio.getSelectedItemPosition()) {

            setResult(CadastroItemActivity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent intentResposta = new Intent();

        intentResposta.putExtra(KEY_DESCRICAO_ITEM, descricao);
        intentResposta.putExtra(KEY_QUANTIDADE_ITEM, quantidadeItem);
        intentResposta.putExtra(KEY_VALOR_UNITARIO_ITEM, valorUnitario);
        intentResposta.putExtra(KEY_VALOR_DESCONTO_ITEM, valorDesconto);
        intentResposta.putExtra(KEY_VALOR_TOTAL_ITEM, valorTotal);
        intentResposta.putExtra(KEY_TIPO_RATEIO_ITEM, tipoRateio);
        intentResposta.putExtra(KEY_CASAL_RATEIO_ITEM, casalRateio);

        setResult(CadastroItemActivity.RESULT_OK, intentResposta);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSalvarItem) {
            salvarValores();
            return true;
        } else if (idMenuItem == R.id.menuItemLimparItem) {
            limparCampos();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private Double calculoValorTotal(int quantidade, Double valorUnitario, Double valorDesconto) {
        return (Double) ((valorUnitario * quantidade) - valorDesconto);
    }
}