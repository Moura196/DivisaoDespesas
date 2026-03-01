package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class LancamentoRecyclerViewAdapter extends RecyclerView.Adapter<LancamentoRecyclerViewAdapter.LancamentoHolder> {

    private List<Lancamento> listaLancamentos;
    private Context context;
    private String[] listaMoradores;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public LancamentoRecyclerViewAdapter(List<Lancamento> listaLancamentos, Context context, OnItemClickListener listener) {
        this.listaLancamentos = listaLancamentos;
        this.context = context;
        this.onItemClickListener = listener;

        listaMoradores = context.getResources().getStringArray(R.array.morador_comprador_cadastrado);
    }

    public class LancamentoHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        public TextView textViewValorDescricao;
        public TextView textViewValorTotal;
        public TextView textViewValorData;
        public TextView textViewValorComprador;
        public TextView textViewTipoLancamento;

        public LancamentoHolder(@NonNull View itemView) {
            super(itemView);

            textViewValorDescricao = itemView.findViewById(R.id.textViewValorDescricao);
            textViewValorTotal = itemView.findViewById(R.id.textViewValorValorTotal);
            textViewValorData = itemView.findViewById(R.id.textViewValorData);
            textViewValorComprador = itemView.findViewById(R.id.textViewValorComprador);
            textViewTipoLancamento = itemView.findViewById(R.id.textViewValorTipoLancamento);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemLongClick(v, position);
                    return true;
                }
            }
            return false;
        }
    }

    @NonNull
    @Override
    public LancamentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.linha_lista_lancamentos, parent, false);

        return new LancamentoHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull LancamentoHolder holder, int position) {
        Lancamento lancamento = listaLancamentos.get(position);

        holder.textViewValorDescricao.setText(lancamento.getDescricao());
        holder.textViewValorTotal.setText(String.valueOf(lancamento.getValor_total()));

        // Formatando a data para exibir como dd-MM-yyyy
        Date data = lancamento.getData();
        if (data != null) {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            holder.textViewValorData.setText(displayFormat.format(data));
        } else {
            holder.textViewValorData.setText("");
        }

        holder.textViewValorComprador.setText(listaMoradores[lancamento.getMorador_comprador()]);

        if (lancamento.isTipo_lancamento()) {
            holder.textViewTipoLancamento.setText(R.string.tipo_conta_casa);
        } else {
            holder.textViewTipoLancamento.setText(R.string.tipo_conta_mercado);
        }
    }

    @Override
    public int getItemCount() {
        return listaLancamentos.size();
    }
}
