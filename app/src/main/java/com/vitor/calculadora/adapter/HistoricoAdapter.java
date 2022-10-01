package com.vitor.calculadora.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitor.calculadora.R;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {

    private List<String> listNumeros, listOperacoes;

    public HistoricoAdapter(List<String> listNumeros, List<String> listOperacoes) {
        this.listNumeros = listNumeros;
        this.listOperacoes = listOperacoes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoAdapter.ViewHolder holder, int position) {
        holder.resultado.setText(listNumeros.get(position));
        holder.operacao.setText(listOperacoes.get(position));
    }

    @Override
    public int getItemCount() {
        return (listNumeros != null && listNumeros.size() > 0 ? listNumeros.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView resultado, operacao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            resultado = itemView.findViewById(R.id.lblResultadoLista);
            operacao = itemView.findViewById(R.id.lblOperacao);
        }
    }
}
