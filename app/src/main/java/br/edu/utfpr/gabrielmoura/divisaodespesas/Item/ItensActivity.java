package br.edu.utfpr.gabrielmoura.divisaodespesas.Item;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento.Lancamento;
import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class ItensActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItens;
    private RecyclerView.LayoutManager layoutManager;
    private List<Lancamento> listaItens;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;

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

        recyclerViewItens.setAdapter(itemRecyclerViewAdapter);
    }
}