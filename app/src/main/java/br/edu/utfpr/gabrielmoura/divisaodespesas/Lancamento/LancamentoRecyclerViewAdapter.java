package br.edu.utfpr.gabrielmoura.divisaodespesas.Lancamento;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private OnItemLongClickListener onItemLongClickListener;
    private OnCreateContextMenu onCreateContextMenu;
    private OnContextMenuClickListener onContextMenuClickListener;

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    interface OnCreateContextMenu {
        void onCreateContextMenu(ContextMenu menu,
                                 View view,
                                 ContextMenu.ContextMenuInfo menuInfo,
                                 int position,
                                 MenuItem.OnMenuItemClickListener menuItemClickListener);
    }

    interface OnContextMenuClickListener {
        boolean onContextMenuItemClick(MenuItem item, int position);
    }

    public LancamentoRecyclerViewAdapter(List<Lancamento> listaLancamentos, Context context) {
        this.listaLancamentos = listaLancamentos;
        this.context = context;

        listaMoradores = context.getResources().getStringArray(R.array.morador_comprador_cadastrado);
    }

    public class LancamentoHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

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
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAbsoluteAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v, getAbsoluteAdapterPosition());
                return true;
            }
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (onCreateContextMenu != null) {
                onCreateContextMenu.onCreateContextMenu(
                        menu,
                        v,
                        menuInfo,
                        getAbsoluteAdapterPosition(),
                        onMenuItemClickListener);
            }
        }

        MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if (onContextMenuClickListener != null) {
                    onContextMenuClickListener.onContextMenuItemClick(
                            item,
                            getAbsoluteAdapterPosition()
                    );
                    return true;
                }
                return false;
            }
        };
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

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnCreateContextMenu getOnCreateContextMenu() {
        return onCreateContextMenu;
    }

    public void setOnCreateContextMenu(OnCreateContextMenu onCreateContextMenu) {
        this.onCreateContextMenu = onCreateContextMenu;
    }

    public OnContextMenuClickListener getOnContextMenuClickListener() {
        return onContextMenuClickListener;
    }

    public void setOnContextMenuClickListener(OnContextMenuClickListener onContextMenuClickListener) {
        this.onContextMenuClickListener = onContextMenuClickListener;
    }
}
