package br.edu.utfpr.gabrielmoura.divisaodespesas.Item;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;
import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Item;

public class ItensActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItens;
    private RecyclerView.LayoutManager layoutManager;
    private List<Item> listaItens;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;
    private int posicaoItemSelecionado = -1;
    private ActionMode actionMode;
    private View viewSelecionada;
    private Drawable backgroundDrawable;

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.itens_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEditarItem) {
                editarItem();
                return true;
            } else if (idMenuItem == R.id.menuItemExcluirItem) {
                excluirItem();
                mode.finish();
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

            recyclerViewItens.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_itens);
        setTitle(getString(R.string.itens_listing));

        recyclerViewItens = findViewById(R.id.recyclerViewItens);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewItens.setLayoutManager(layoutManager);
        recyclerViewItens.setHasFixedSize(true);
        recyclerViewItens.addItemDecoration(
                new DividerItemDecoration(
                        this,
                        LinearLayout.VERTICAL
                )
        );

        popularListaItens();
    }

    private void popularListaItens() {
        listaItens = new ArrayList<>();

        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(listaItens, this);

        itemRecyclerViewAdapter.setOnItemClickListener(new ItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                posicaoItemSelecionado = position;
                editarItem();
            }
        });

        itemRecyclerViewAdapter.setOnItemLongClickListener(new ItemRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                if (actionMode != null) {
                    return false;
                }

                posicaoItemSelecionado = position;
                viewSelecionada = view;
                backgroundDrawable = view.getBackground();

                view.setBackgroundColor(Color.LTGRAY);

                recyclerViewItens.setEnabled(false);
                actionMode = startSupportActionMode(actionModeCallback);
                return false;
            }
        });

        recyclerViewItens.setAdapter(itemRecyclerViewAdapter);
    }

    private void excluirItem() {
        listaItens.remove(posicaoItemSelecionado);

        itemRecyclerViewAdapter.notifyDataSetChanged();
    }

    ActivityResultLauncher<Intent> launcherNovoItem = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == ItensActivity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();
                        if (bundle != null) {
                            String descricao = bundle.getString(CadastroItemActivity.KEY_DESCRICAO_ITEM);
                            int quantidade = bundle.getInt(CadastroItemActivity.KEY_QUANTIDADE_ITEM);
                            Double valorUnitario = bundle.getDouble(CadastroItemActivity.KEY_VALOR_UNITARIO_ITEM);
                            Double valorDesconto = bundle.getDouble(CadastroItemActivity.KEY_VALOR_DESCONTO_ITEM);
                            Double valorTotal = bundle.getDouble(CadastroItemActivity.KEY_VALOR_TOTAL_ITEM);
                            boolean tipoRateio = bundle.getBoolean(CadastroItemActivity.KEY_TIPO_RATEIO_ITEM);
                            int casalRateio = bundle.getInt(CadastroItemActivity.KEY_CASAL_RATEIO_ITEM);

                            Item item = new Item(
                                    descricao,
                                    quantidade,
                                    valorUnitario,
                                    valorDesconto,
                                    valorTotal,
                                    tipoRateio,
                                    casalRateio
                            );

                            listaItens.add(item);
                        }
                    }
                }
            });

    public void abrirCadastroItem() {
        Intent intent = new Intent(this, CadastroItemActivity.class);

        intent.putExtra(CadastroItemActivity.KEY_MODO, CadastroItemActivity.MODO_CADASTRO);

        launcherNovoItem.launch(intent);
    }

    ActivityResultLauncher<Intent> launcherEditarItem = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == ItensActivity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();
                        if (bundle != null) {
                            String descricao = bundle.getString(CadastroItemActivity.KEY_DESCRICAO_ITEM);
                            int quantidade = bundle.getInt(CadastroItemActivity.KEY_QUANTIDADE_ITEM);
                            Double valorUnitario = bundle.getDouble(CadastroItemActivity.KEY_VALOR_UNITARIO_ITEM);
                            Double valorDesconto = bundle.getDouble(CadastroItemActivity.KEY_VALOR_DESCONTO_ITEM);
                            Double valorTotal = bundle.getDouble(CadastroItemActivity.KEY_VALOR_TOTAL_ITEM);
                            boolean tipoRateio = bundle.getBoolean(CadastroItemActivity.KEY_TIPO_RATEIO_ITEM);
                            int casalRateio = bundle.getInt(CadastroItemActivity.KEY_CASAL_RATEIO_ITEM);

                            Item item = listaItens.get(posicaoItemSelecionado);

                            item.setDescricao(descricao);
                            item.setQuantidade(quantidade);
                            item.setValor_unitario(valorUnitario);
                            item.setValor_desconto(valorDesconto);
                            item.setValor_total(valorTotal);
                            item.setRateio_casal(tipoRateio);
                            item.setCasal_rateio(casalRateio);
                        }
                    }

                    posicaoItemSelecionado = -1;

                    if (actionMode != null) {
                        actionMode.finish();
                    }
                }
            });

    private void editarItem() {
        Item item = listaItens.get(posicaoItemSelecionado);

        Intent intentEdicao = new Intent(this, CadastroItemActivity.class);

        intentEdicao.putExtra(CadastroItemActivity.KEY_MODO, CadastroItemActivity.MODO_EDITAR);

        intentEdicao.putExtra(CadastroItemActivity.KEY_DESCRICAO_ITEM, item.getDescricao());
        intentEdicao.putExtra(CadastroItemActivity.KEY_QUANTIDADE_ITEM, item.getQuantidade());
        intentEdicao.putExtra(CadastroItemActivity.KEY_VALOR_UNITARIO_ITEM, item.getValor_unitario());
        intentEdicao.putExtra(CadastroItemActivity.KEY_VALOR_DESCONTO_ITEM, item.getValor_desconto());
        intentEdicao.putExtra(CadastroItemActivity.KEY_VALOR_TOTAL_ITEM, item.getValor_total());
        intentEdicao.putExtra(CadastroItemActivity.KEY_TIPO_RATEIO_ITEM, item.isRateio_casal());
        intentEdicao.putExtra(CadastroItemActivity.KEY_CASAL_RATEIO_ITEM, item.getCasal_rateio());

        launcherEditarItem.launch(intentEdicao);
    }
}
