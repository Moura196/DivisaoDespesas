package br.edu.utfpr.gabrielmoura.divisaodespesas.Morador;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class MoradoresActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMoradores;
    private RecyclerView.LayoutManager layoutManager;
    private MoradorRecyclerViewAdapter moradorRecyclerViewAdapter;
    private List<Morador> listaMoradores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_moradores);

        recyclerViewMoradores = findViewById(R.id.recyclerViewMoradores);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewMoradores.setLayoutManager(layoutManager);
        recyclerViewMoradores.setHasFixedSize(true);
        recyclerViewMoradores.addItemDecoration(
                new DividerItemDecoration(
                        this,
                        LinearLayout.VERTICAL
                )
        );

        popularListaMoradores();
    }

    private void popularListaMoradores() {
        String[] nome_morador = getResources().getStringArray(R.array.morador_comprador_cadastrado);
        int[] genero_morador = getResources().getIntArray(R.array.genero_morador);
        int[] grupo_familiar_morador = getResources().getIntArray(R.array.grupo_familiar_morador);
        int[] responsavel_contas_morador = getResources().getIntArray(R.array.responsavel_contas_morador);

        listaMoradores = new ArrayList<>();

        Morador morador;
        Genero genero;
        boolean responsavel_contas_boolean;

        Genero[] generos = Genero.values();
        for (int count = 0; count < nome_morador.length; count++) {
            genero = generos[genero_morador[count]];

            responsavel_contas_boolean = (responsavel_contas_morador[count] == 1 ? true : false);

            morador = new Morador(Long.valueOf(count),
                    nome_morador[count],
                    genero,
                    grupo_familiar_morador[count],
                    responsavel_contas_boolean
            );

            listaMoradores.add(morador);
        }

        moradorRecyclerViewAdapter = new MoradorRecyclerViewAdapter(
                this,
                listaMoradores
        );
        recyclerViewMoradores.setAdapter(moradorRecyclerViewAdapter);
    }
}