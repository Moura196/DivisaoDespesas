package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;
import br.edu.utfpr.gabrielmoura.divisaodespesas.Sobre.SobreActivity;
import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Lancamento;
import br.edu.utfpr.gabrielmoura.divisaodespesas.utils.UtilsAlert;

public class LancamentosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLancamentos;
    private RecyclerView.LayoutManager layoutManager;
    private LancamentoRecyclerViewAdapter lancamentoRecyclerViewAdapter;
    private List<Lancamento> listaLancamentos;
    private int posicaoItemSelecionado = -1;

    private ActionMode actionMode;

    private View viewSelecionada;
    private Drawable backgroundDrawable;

    public static final String ARQUIVO_PREFERENCIAS = "br.edu.utfpr.gabrielmoura.divisaodespesas.PREFERENCIAS";
    public static final String KEY_ORDENACAO_CRESCENTE = "ORDENACAO_CRESCENTE";
    public static final boolean PADRAO_INICIAL_ORDENACAO_CRESCENTE = true;

    private boolean ordenacaoCrescente = PADRAO_INICIAL_ORDENACAO_CRESCENTE;
    private MenuItem menuItemOrdenacao;

    private ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.lancamentos_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
            int idMenuItem = menuItem.getItemId();

            if (idMenuItem == R.id.menuItemEditar) {
                editarLancamento();
                return true;
            } else if (idMenuItem == R.id.menuItemExcluir) {
                excluirLancamento();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode = null;
            viewSelecionada = null;
            backgroundDrawable = null;

            recyclerViewLancamentos.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_lancamentos);
        setTitle(R.string.page_listagem_lancamentos);
        
        recyclerViewLancamentos = findViewById(R.id.recyclerViewLancamentos);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewLancamentos.setLayoutManager(layoutManager);
        recyclerViewLancamentos.setHasFixedSize(true);
        recyclerViewLancamentos.addItemDecoration(
                new DividerItemDecoration(
                        this,
                        LinearLayout.VERTICAL
                )
        );

        lerPreferencias();

        popularListaLancamentos();
    }

    private void popularListaLancamentos() {
        listaLancamentos = new ArrayList<>();

        lancamentoRecyclerViewAdapter = new LancamentoRecyclerViewAdapter(listaLancamentos, this);

        lancamentoRecyclerViewAdapter.setOnItemClickListener(new LancamentoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                posicaoItemSelecionado = position;
                editarLancamento();
            }
        });

        lancamentoRecyclerViewAdapter.setOnItemLongClickListener(new LancamentoRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                if (actionMode != null) {
                    return false;
                }

                posicaoItemSelecionado = position;
                viewSelecionada = view;
                backgroundDrawable = view.getBackground();

                view.setBackgroundColor(getColor(R.color.corSelecionada));

                recyclerViewLancamentos.setEnabled(false);
                actionMode = startSupportActionMode(actionModeCallBack);
                return true;
            }
        });

        recyclerViewLancamentos.setAdapter(lancamentoRecyclerViewAdapter);
    }

    public void abrirSobre() {
        Intent intent = new Intent(this, SobreActivity.class);

        startActivity(intent);
    }

    ActivityResultLauncher<Intent> launcherNovoLancamento = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == LancamentosActivity.RESULT_OK) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Bundle bundle = intent.getExtras();

                            if (bundle != null) {
                                String descricao = bundle.getString(CadastroLancamentoActivity.KEY_DESCRICAO);
                                Double valorTotal = bundle.getDouble(CadastroLancamentoActivity.KEY_VALOR_TOTAL);
                                Date dataLancamento = (Date) bundle.getSerializable(CadastroLancamentoActivity.KEY_DATA_LANCAMENTO);
                                int moradorComprador = bundle.getInt(CadastroLancamentoActivity.KEY_MORADOR_COMPRADOR);
                                boolean tipoLancamento = bundle.getBoolean(CadastroLancamentoActivity.KEY_TIPO_LANCAMENTO);

                                Lancamento lancamento = new Lancamento(
                                        descricao,
                                        valorTotal,
                                        dataLancamento,
                                        moradorComprador,
                                        tipoLancamento
                                );

                                listaLancamentos.add(lancamento);

                                ordenarLista();
                            }
                        }
                    }
                }
            });

    public void abrirCadastroLancamento() {
        Intent intent = new Intent(this, CadastroLancamentoActivity.class);

        intent.putExtra(CadastroLancamentoActivity.KEY_MODO, CadastroLancamentoActivity.MODO_CADASTRO);

        launcherNovoLancamento.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lancamentos_opcoes, menu);

        menuItemOrdenacao = menu.findItem(R.id.menuItemOrdenacao);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        atualizarIconeOrdenacao();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemAdicionar) {
            abrirCadastroLancamento();
            return true;
        } else if (idMenuItem == R.id.menuItemSobre) {
            abrirSobre();
            return true;
        } else if (idMenuItem == R.id.menuItemOrdenacao) {
            salvarPreferenciaOrdenacaoCrescente(!ordenacaoCrescente);
            atualizarIconeOrdenacao();
            ordenarLista();
            return true;
        } else if (idMenuItem == R.id.menuItemRestaurar) {
            confirmarRestaurarPadroes();
            return true;
        } else {
                return super.onOptionsItemSelected(item);
        }

    }

    public void  excluirLancamento() {
        Lancamento lancamento = listaLancamentos.get(posicaoItemSelecionado);

        String mensagem = getString(R.string.deseja_apagar, lancamento.getDescricao());

        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaLancamentos.remove(posicaoItemSelecionado);

                lancamentoRecyclerViewAdapter.notifyItemRemoved(posicaoItemSelecionado);

                actionMode.finish();
            }
        };

        UtilsAlert.confirmarAcao(this, mensagem, listenerSim, null);
    }

    ActivityResultLauncher<Intent> launcherEditarLancamento = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == LancamentosActivity.RESULT_OK) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            Bundle bundle = intent.getExtras();

                            if (bundle != null) {
                                String descricao = bundle.getString(CadastroLancamentoActivity.KEY_DESCRICAO);
                                Double valorTotal = bundle.getDouble(CadastroLancamentoActivity.KEY_VALOR_TOTAL);
                                Date dataLancamento = (Date) bundle.getSerializable(CadastroLancamentoActivity.KEY_DATA_LANCAMENTO);
                                int moradorComprador = bundle.getInt(CadastroLancamentoActivity.KEY_MORADOR_COMPRADOR);
                                boolean tipoLancamento = bundle.getBoolean(CadastroLancamentoActivity.KEY_TIPO_LANCAMENTO);

                                Lancamento lancamento = listaLancamentos.get(posicaoItemSelecionado);

                                lancamento.setDescricao(descricao);
                                lancamento.setValor_total(valorTotal);
                                lancamento.setData(dataLancamento);
                                lancamento.setMorador_comprador(moradorComprador);
                                lancamento.setTipo_lancamento(tipoLancamento);

                                ordenarLista();
                            }
                        }
                    }

                    posicaoItemSelecionado = -1;

                    if (actionMode != null) {
                        actionMode.finish();
                    }
                }
            }
    );

    private void editarLancamento() {
        Lancamento lancamento = listaLancamentos.get(posicaoItemSelecionado);

        Intent intentEdicao = new Intent(this, CadastroLancamentoActivity.class);

        intentEdicao.putExtra(CadastroLancamentoActivity.KEY_MODO, CadastroLancamentoActivity.MODO_EDITAR);

        intentEdicao.putExtra(CadastroLancamentoActivity.KEY_DESCRICAO, lancamento.getDescricao());
        intentEdicao.putExtra(CadastroLancamentoActivity.KEY_VALOR_TOTAL, lancamento.getValor_total());
        intentEdicao.putExtra(CadastroLancamentoActivity.KEY_DATA_LANCAMENTO, lancamento.getData());
        intentEdicao.putExtra(CadastroLancamentoActivity.KEY_MORADOR_COMPRADOR, lancamento.getMorador_comprador());
        intentEdicao.putExtra(CadastroLancamentoActivity.KEY_TIPO_LANCAMENTO, lancamento.isTipo_lancamento());

        launcherEditarLancamento.launch(intentEdicao);
    }

    private void atualizarIconeOrdenacao() {
        if (ordenacaoCrescente) {
            menuItemOrdenacao.setIcon(R.drawable.ic_action_crescente_order);
        } else {
            menuItemOrdenacao.setIcon(R.drawable.ic_action_decrescente_order);
        }
    }

    private void lerPreferencias() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        ordenacaoCrescente = shared.getBoolean(KEY_ORDENACAO_CRESCENTE, ordenacaoCrescente);
    }

    private void salvarPreferenciaOrdenacaoCrescente(boolean novoValor) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_ORDENACAO_CRESCENTE, novoValor);
        editor.commit();

        ordenacaoCrescente = novoValor;
    }

    private void ordenarLista() {
        if (ordenacaoCrescente) {
            Collections.sort(listaLancamentos, Lancamento.ordenacaoCrescente);
        } else {
            Collections.sort(listaLancamentos, Lancamento.ordenacaoDecrescente);
        }

        lancamentoRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void confirmarRestaurarPadroes() {
        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                restaurarPadroes();
                atualizarIconeOrdenacao();
                ordenarLista();

                Toast.makeText(LancamentosActivity.this,
                        R.string.configuracoes_restauradas_padroes,
                        Toast.LENGTH_LONG).show();
            }
        };

        UtilsAlert.confirmarAcao(this, R.string.deseja_restaurar_padroes, listenerSim, null);
    }

    private void restaurarPadroes() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();

        ordenacaoCrescente = PADRAO_INICIAL_ORDENACAO_CRESCENTE;
    }
}