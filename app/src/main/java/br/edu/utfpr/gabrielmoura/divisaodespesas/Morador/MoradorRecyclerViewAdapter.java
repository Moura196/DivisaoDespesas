package br.edu.utfpr.gabrielmoura.divisaodespesas.Morador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.utfpr.gabrielmoura.divisaodespesas.R;

public class MoradorRecyclerViewAdapter extends RecyclerView.Adapter<MoradorRecyclerViewAdapter.MoradorHolder> {

    private Context context;
    private List<Morador> listaMoradores;
    private String[] lista_grupo_familiar;

    public MoradorRecyclerViewAdapter(Context context, List<Morador> listaMoradores) {
        this.context = context;
        this.listaMoradores = listaMoradores;

        lista_grupo_familiar = context.getResources().getStringArray(R.array.grupo_familiar_morador);
    }

    public class MoradorHolder extends RecyclerView.ViewHolder {

        public TextView textViewValorNome;
        public TextView textViewValorGenero;
        public TextView textViewValorGrupoFamiliar;
        public TextView textViewValorResponsavel;

        public MoradorHolder(@NonNull View itemView) {
            super(itemView);

            textViewValorNome = itemView.findViewById(R.id.textViewValorNome);
            textViewValorGenero = itemView.findViewById(R.id.textViewValorGenero);
            textViewValorGrupoFamiliar = itemView.findViewById(R.id.textViewValorGrupoFamiliar);
            textViewValorResponsavel = itemView.findViewById(R.id.textViewValorResponsavel);
        }
    }

    @NonNull
    @Override
    public MoradorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.linha_lista_moradores, parent, false);

        return new MoradorHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoradorHolder holder, int position) {
        Morador morador = listaMoradores.get(position);

        // Atualiza valor de Nome -> EditText
        holder.textViewValorNome.setText(morador.getNome());

        // Atualiza valor de Gênero -> RadioButton
        switch (morador.getGenero()) {
            case MASCULINO:
                holder.textViewValorGenero.setText("Masculino");
                break;
            case FEMININO:
                holder.textViewValorGenero.setText("Feminino");
                break;
        }

        // Atualiza valor de Grupo Familiar -> Spinner
        holder.textViewValorGrupoFamiliar.setText(lista_grupo_familiar[morador.getGrupo_familiar()]);

        // Atualiza valor de Responsável pelas Contas -> CheckBox
        if (morador.isResponsavel_contas()) {
            holder.textViewValorResponsavel.setText("Responsável pelas contas");
        } else {
            holder.textViewValorResponsavel.setText("Não é responsável pelas contas");
        }
    }

    @Override
    public int getItemCount() {
        return listaMoradores.size();
    }
}
