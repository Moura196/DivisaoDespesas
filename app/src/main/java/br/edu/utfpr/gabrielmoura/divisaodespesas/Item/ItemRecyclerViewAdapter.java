package br.edu.utfpr.gabrielmoura.divisaodespesas.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemHolder> {

    private List<Item> listaItens;
    private Context context;
    private String[] listaCasal;

    public ItemRecyclerViewAdapter(List<Item> listaItens, Context context) {
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
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.linha_lista_itens, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = listaItens.get(position);

        holder.textViewValorDescricaoItem.setText(item.getDescricao());
        holder.textViewValorQuant.setText(String.valueOf(item.getQuantidade()));
        holder.textViewValorUnitario.setText(String.format("%.2f", item.getValor_unitario()));
        holder.textViewValorDesconto.setText(String.format("%.2f", item.getValor_desconto()));
        holder.textViewValorTotalItem.setText(String.format("%.2f", item.getValor_total()));
        if (item.isRateio_casal() && item.getCasal_rateio() >= 0 && item.getCasal_rateio() < listaCasal.length) {
            holder.textViewValorCasalRateio.setText(listaCasal[item.getCasal_rateio()]);
        } else {
            holder.textViewValorCasalRateio.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return listaItens.size();
    }

}
