package com.vitor.calculadora.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitor.calculadora.R;
import com.vitor.calculadora.utils.ClickListener;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {

    private List<String> listNumeros, listOperacoes;
    private ClickListener clickListener;

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

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView resultado, operacao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resultado = itemView.findViewById(R.id.lblResultadoLista);
            operacao = itemView.findViewById(R.id.lblOperacao);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }
}
