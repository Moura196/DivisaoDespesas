package br.edu.utfpr.gabrielmoura.divisaodespesas.Item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento.Lancamento;
import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemHolder> {

    private List<Lancamento> listaItens;
    private Context context;
    private String[] listaCasal;

    public ItemRecyclerViewAdapter(List<Lancamento> listaItens, Context context) {
        this.listaItens = listaItens;
        this.context = context;

        listaCasal = context.getResources().getStringArray(R.array.grupo_familiar);
    }

    public class ItemHolder
            extends RecyclerView.ViewHolder {

        public TextView textViewValorDescricaoItem;
        public TextView textViewValorQuant;
        public TextView textViewValorUnitario;
        public TextView textViewValorDesconto;
        public TextView textViewValorTotalItem;
        public TextView textViewValorCasalRateio;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            textViewValorDescricaoItem = itemView.findViewById(R.id.textViewValorDescricaoItem);
            textViewValorQuant = itemView.findViewById(R.id.textViewValorQuant);
            textViewValorUnitario = itemView.findViewById(R.id.textViewValorUnitario);
            textViewValorDesconto = itemView.findViewById(R.id.textViewValorDesconto);
            textViewValorTotalItem = itemView.findViewById(R.id.textViewValorTotalItem);
            textViewValorCasalRateio = itemView.findViewById(R.id.textViewValorCasalRateio);
        }
    }

    @NonNull
    @Override
    public ItemRecyclerViewAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerViewAdapter.ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
