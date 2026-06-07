package br.edu.utfpr.gabrielmoura.divisaodespesas.Item;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;
import br.edu.utfpr.gabrielmoura.divisaodespesas.modelo.Item;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemHolder> {

    private List<Item> listaItens;
    private Context context;
    private String[] listaCasal;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnCreateContextMenu onCreateContextMenu;
    private OnContextMenuClickListener onContextMenuClickListener;

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
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

    public ItemRecyclerViewAdapter(List<Item> listaItens, Context context) {
        this.listaItens = listaItens;
        this.context = context;

        listaCasal = context.getResources().getStringArray(R.array.grupo_familiar);
    }

    public class ItemHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

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
