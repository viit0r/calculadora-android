package com.vitor.calculadora.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vitor.calculadora.R;
import com.vitor.calculadora.adapter.HistoricoAdapter;
import com.vitor.calculadora.model.Calculo;
import com.vitor.calculadora.utils.ClickListener;
import com.vitor.calculadora.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoricoAdapter historicoAdapter;
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
            resultados = bundle.getStringArrayList(Const.LISTA_RESULTADOS);
            operacoes = bundle.getStringArrayList(Const.LISTA_OPERACOES);

            if (temResultados()) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
                historicoAdapter = new HistoricoAdapter(resultados, operacoes);
                recyclerView.setAdapter(historicoAdapter);
                historicoAdapter.setOnItemClickListener(new ClickListener() {
                    @Override
                    public void onItemClick(int posicao, View view) {
                        if (temResultados()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra(Const.RETORNO_POSICAO, posicao);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onItemLongClick(int position, View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HistoricoActivity.this);
                        builder.setMessage(getString(R.string.deseja_remover_historico));
                        builder.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                resultados.remove(position);
                                operacoes.remove(position);
                                Calculo.removeResultado(position);
                                Calculo.removeOperacao(position);
                                historicoAdapter.notifyDataSetChanged();
                                temResultados();
                            }
                        });
                        builder.setNegativeButton(getString(R.string.nao), null);
                        builder.create().show();
                    }
                });
            }
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

    public boolean temResultados() {
        if (resultados.isEmpty() || resultados.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            semHistorico.setText(R.string.sem_historico);
            semHistorico.setVisibility(View.VISIBLE);
            return false;
        } else {
            semHistorico.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            return true;
        }
    }
}