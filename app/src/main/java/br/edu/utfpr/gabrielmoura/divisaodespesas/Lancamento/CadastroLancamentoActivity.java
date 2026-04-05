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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import br.edu.utfpr.gabrielmoura.divisaodespesas.Item.CadastroItemActivity;
import br.edu.utfpr.gabrielmoura.divisaodespesas.Item.Item;
import br.edu.utfpr.gabrielmoura.divisaodespesas.Item.ItemRecyclerViewAdapter;
import br.edu.utfpr.gabrielmoura.divisaodespesas.R;
import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Lancamento;
import br.edu.utfpr.gabrielmoura.divisaodespesas.persistencia.LancamentosDatabase;
import br.edu.utfpr.gabrielmoura.divisaodespesas.utils.UtilsAlert;

public class CadastroLancamentoActivity extends AppCompatActivity {

    public static final String KEY_MODO = "MODO";
    public static final String KEY_ID = "ID";

    public static final String KEY_SUGERIR_MORADOR_COMPRADOR = "SUGERIR_MORADOR_COMPRADOR";
    public static final String KEY_ULTIMO_MORADOR_COMPRADOR = "ULTIMO_MORADOR_COMPRADOR";

    public static final String KEY_DESCRICAO_ITEM = "KEY_DESCRICAO_ITEM";
    public static final String KEY_QUANTIDADE_ITEM = "KEY_QUANTIDADE_ITEM";
    public static final String KEY_VALOR_UNITARIO_ITEM = "KEY_VALOR_UNITARIO_ITEM";
    public static final String KEY_VALOR_DESCONTO_ITEM = "KEY_VALOR_DESCONTO_ITEM";
    public static final String KEY_VALOR_TOTAL_ITEM = "KEY_VALOR_TOTAL_ITEM";
    public static final String KEY_TIPO_RATEIO_ITEM = "KEY_TIPO_RATEIO_ITEM";
    public static final String KEY_CASAL_RATEIO_ITEM = "KEY_CASAL_RATEIO_ITEM";

    public static final int MODO_CADASTRO = 0;
    public static final int MODO_EDITAR = 1;

    private EditText editTextDescricao;
    private EditText editTextValorTotal;
    private EditText editTextDate;
    private Spinner spinnerMoradorComprador;
    private CheckBox checkBoxTipoLancamento;
    private FloatingActionButton fabAddItem;
    private RecyclerView recyclerViewItens;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;
    private int modo;
    private Lancamento lancamentoOriginal;

    private boolean sugerirMoradorComprador = false;
    private int ultimoMoradorComprador = 0;

    private ArrayList<Item> listaItens = new ArrayList<>();


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
        recyclerViewItens = findViewById(R.id.recyclerViewItens);
        recyclerViewItens.setLayoutManager(new LinearLayoutManager(this));
        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(listaItens, this);
        recyclerViewItens.setAdapter(itemRecyclerViewAdapter);

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
                setTitle(getString(R.string.novo_lancamento));

                if (sugerirMoradorComprador) {
                    spinnerMoradorComprador.setSelection(ultimoMoradorComprador);
                }
            } else {
                setTitle(getString(R.string.editar_lancamemento));

                long id = bundle.getLong(KEY_ID);

                LancamentosDatabase database = LancamentosDatabase.getInstance(this);

                lancamentoOriginal = database.getLancamentoDao().queryForId(id);

                editTextDescricao.setText(lancamentoOriginal.getDescricao());
                editTextValorTotal.setText(String.valueOf(lancamentoOriginal.getValor_total()));

                SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                editTextDate.setText(displayFormat.format(lancamentoOriginal.getData()));

                spinnerMoradorComprador.setSelection(lancamentoOriginal.getMorador_comprador());
                checkBoxTipoLancamento.setChecked(lancamentoOriginal.isTipo_lancamento());

                editTextDescricao.requestFocus();
                editTextDescricao.setSelection(editTextDescricao.getText().length());
            }
        }

        fabAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroItemActivity.class);
            intent.putExtra("LISTA_ITENS", listaItens);
            launcherNovoItem.launch(intent);
        });
    }

    private ActivityResultLauncher<Intent> launcherNovoItem = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == CadastroLancamentoActivity.RESULT_OK &&
                            result.getData() != null) {
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String descricaoItem = bundle.getString(KEY_DESCRICAO_ITEM);
                            int quantidadeItem = bundle.getInt(KEY_QUANTIDADE_ITEM);
                            Double valorUnitarioItem = bundle.getDouble(KEY_VALOR_UNITARIO_ITEM);
                            Double valorDescontoItem = bundle.getDouble(KEY_VALOR_DESCONTO_ITEM);
                            Double valorTotalItem = bundle.getDouble(KEY_VALOR_TOTAL_ITEM);
                            boolean tipoRateioItem = bundle.getBoolean(KEY_TIPO_RATEIO_ITEM);
                            int casalRateioItem = bundle.getInt(KEY_CASAL_RATEIO_ITEM);

                            Item item = new Item(
                                    descricaoItem,
                                    quantidadeItem,
                                    valorUnitarioItem,
                                    valorDescontoItem,
                                    valorTotalItem,
                                    tipoRateioItem,
                                    casalRateioItem);

                            listaItens.add(item);
                            itemRecyclerViewAdapter.notifyDataSetChanged();
                        }

//                        if (data.hasExtra("LISTA_ITENS")) {
//                            listaItens = (ArrayList<Item>) data.getSerializableExtra("LISTA_ITENS");
//
//                            itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(listaItens, CadastroLancamentoActivity.this);
//
//                            recyclerViewItens.setAdapter(itemRecyclerViewAdapter);
//                        }
                    }
                }
            }
    );

    public void limparCampos() {
        editTextDescricao.setText(null);
        editTextValorTotal.setText(null);
        editTextDate.setText(null);
        spinnerMoradorComprador.setSelection(0);
        checkBoxTipoLancamento.setChecked(false);

        editTextDescricao.requestFocus();

        UtilsAlert.mostrarAviso(this, R.string.limpeza_das_entradas);
    }

    public void salvarValores() {
        String descricao = editTextDescricao.getText().toString();

        if (descricao == null || descricao.trim().isEmpty()) {
            UtilsAlert.mostrarAviso(this, R.string.informar_descricao);

            editTextDescricao.requestFocus();
            return;
        }

        String valorTotalStr = editTextValorTotal.getText().toString();
        if (valorTotalStr == null || valorTotalStr.trim().isEmpty()) {
            UtilsAlert.mostrarAviso(this, R.string.informar_valor_total);

            editTextValorTotal.requestFocus();
            return;
        }

        Double valorTotal;
        try {
            valorTotal = Double.valueOf(valorTotalStr);
        } catch (NumberFormatException e) {
            UtilsAlert.mostrarAviso(this, R.string.informar_valor_total);

            editTextValorTotal.requestFocus();
            return;
        }

        if (valorTotal <= 0) {
            UtilsAlert.mostrarAviso(this, R.string.informar_valor_total);

            editTextValorTotal.requestFocus();
            return;
        }

        Date dataLancamento = null;
        String dataStr = editTextDate.getText().toString();
        if (dataStr == null || dataStr.trim().isEmpty()) {
            UtilsAlert.mostrarAviso(this, R.string.informe_data_corretamente);

            editTextDate.requestFocus();
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dataLancamento = sdf.parse(dataStr);
        } catch (ParseException e) {
            UtilsAlert.mostrarAviso(this, R.string.informe_data_corretamente);

            editTextDate.requestFocus();
            return;
        }

        int moradorComprador = spinnerMoradorComprador.getSelectedItemPosition();
        if (moradorComprador == AdapterView.INVALID_POSITION) {
            UtilsAlert.mostrarAviso(this, R.string.informar_morador_comprador);

            return;
        }

        boolean tipoLancamento = checkBoxTipoLancamento.isChecked();

        Lancamento lancamento = new Lancamento(
                descricao,
                valorTotal,
                dataLancamento,
                moradorComprador,
                tipoLancamento);

        if (lancamento.equals(lancamentoOriginal)) {
            setResult(CadastroLancamentoActivity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent intentResposta = new Intent();

        LancamentosDatabase database = LancamentosDatabase.getInstance(this);

        if (modo == MODO_CADASTRO) {
            long novoId = database.getLancamentoDao().inserir(lancamento);

            if (novoId <= 0) {
                UtilsAlert.mostrarAviso(this, R.string.erro_ao_salvar_lancamento);
                return;
            }

            lancamento.setId_lancamento(novoId);
        } else {
            lancamento.setId_lancamento(lancamentoOriginal.getId_lancamento());

            int quantidadeAlterada = database.getLancamentoDao().atualizar(lancamento);
            if (quantidadeAlterada != 1) {
                UtilsAlert.mostrarAviso(this, R.string.erro_ao_atualizar_lancamento);
                return;
            }
        }

        salvarUltimoMoradorComprador(moradorComprador);

        intentResposta.putExtra(KEY_ID, lancamento.getId_lancamento());

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