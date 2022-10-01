package com.vitor.calculadora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vitor.calculadora.R;
import com.vitor.calculadora.adapter.HistoricoAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView semHistorico;
    private List<String> resultados = new ArrayList<>();
    private List<String> operacoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        getReferences();

        try {
            Bundle bundle = getIntent().getExtras();
            for (int i = 0; i < bundle.size() / 2; i++) {
                resultados.add(bundle.getString("numero " + i));
                operacoes.add(bundle.getString("operacao " + i));
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
            recyclerView.setAdapter(new HistoricoAdapter(resultados, operacoes));
        } catch (NullPointerException e) {
            recyclerView.setVisibility(View.GONE);
            semHistorico.setText(R.string.sem_historico);
            semHistorico.setVisibility(View.VISIBLE);
        } catch (RuntimeException e) {
            recyclerView.setVisibility(View.GONE);
            semHistorico.setText(e.getMessage());
            semHistorico.setVisibility(View.VISIBLE);
        }
    }

    private void getReferences() {
        recyclerView = findViewById(R.id.rvResultados);
        semHistorico = findViewById(R.id.lblSemHistorico);
    }
}