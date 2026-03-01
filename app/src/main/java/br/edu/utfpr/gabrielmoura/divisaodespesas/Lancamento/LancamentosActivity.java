package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;
import br.edu.utfpr.gabrielmoura.divisaodespesas.Sobre.SobreActivity;

public class LancamentosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLancamentos;
    private RecyclerView.LayoutManager layoutManager;
    private LancamentoRecyclerViewAdapter lancamentoRecyclerViewAdapter;
    private LancamentoRecyclerViewAdapter.OnItemClickListener onItemClickListener;
    private List<Lancamento> listaLancamentos;
    
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

        onItemClickListener = new LancamentoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Lancamento lancamento = listaLancamentos.get(position);

                Toast.makeText(getApplicationContext(),
                        getString(R.string.lancamento_com_descriao) +
                                lancamento.getDescricao() +
                                getString(R.string.foi_clicado),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Lancamento lancamento = listaLancamentos.get(position);

                Toast.makeText(getApplicationContext(),
                        getString(R.string.lancamento_com_descriao) +
                                lancamento.getDescricao() +
                                getString(R.string.recebeu_um_clique_longo),
                        Toast.LENGTH_LONG).show();
            }
        };
        
        popularListaLancamentos();
    }

    private void popularListaLancamentos() {
//        String[] descricao_lancamento = getResources().getStringArray(R.array.lancamentos_descricao);
//        String[] valor_total_lancamento = getResources().getStringArray(R.array.lancamentos_valor_total);
//        String[] data_lancamento = getResources().getStringArray(R.array.lancamentos_data);
//        int[] morador_comprador_lancamento = getResources().getIntArray(R.array.lancamentos_morador_comprador);
//        int[] tipo_lancamento = getResources().getIntArray(R.array.lancamentos_tipo_lancamento);

        listaLancamentos = new ArrayList<>();

//        Lancamento lancamento;
//        boolean tipo_lancamento_boolean;
//        Date dataConvertida = null;
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM", Locale.getDefault());
//        sdf.setLenient(false);
//
//        for (int count = 0; count < descricao_lancamento.length; count++) {
//            tipo_lancamento_boolean = (tipo_lancamento[count] == 1 ? true : false);
//
//            // Convertendo a data de String para Date, considerando o formato "yyyy-dd-MM"
//            String dataString = (data_lancamento[count] == null ? "" : data_lancamento[count].trim());
//            if (!dataString.isEmpty()) {
//                dataString = dataString.replace('/', '-');
//                try {
//                    dataConvertida = sdf.parse(dataString);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            lancamento = new Lancamento(descricao_lancamento[count],
//                    Double.valueOf(valor_total_lancamento[count]),
//                    dataConvertida,
//                    morador_comprador_lancamento[count],
//                    tipo_lancamento_boolean);
//
//            listaLancamentos.add(lancamento);
//        }

        lancamentoRecyclerViewAdapter = new LancamentoRecyclerViewAdapter(listaLancamentos, this, onItemClickListener);
        recyclerViewLancamentos.setAdapter(lancamentoRecyclerViewAdapter);
    }

    public void abrirSobre(View view) {
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

                                lancamentoRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            });

    public void abrirCadastroLancamento(View view) {
        Intent intent = new Intent(this, CadastroLancamentoActivity.class);

        launcherNovoLancamento.launch(intent);

    }
}