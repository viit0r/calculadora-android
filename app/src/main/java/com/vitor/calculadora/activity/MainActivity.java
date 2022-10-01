package com.vitor.calculadora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vitor.calculadora.R;
import com.vitor.calculadora.model.Calculadora;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText numero1, numero2;
    private RadioGroup radioGroup;
    private Button calcula, limpa;
    private TextView resultado;
    private ImageView operacao;
    private List<String> resultados = new ArrayList<>();
    private List<String> operacoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getReferences();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int idRadio) {
                trocaOperacao(idRadio);
            }
        });
        calcula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!numero1.getText().toString().isEmpty() && !numero2.getText().toString().isEmpty()) {
                        double num1 = Double.parseDouble(numero1.getText().toString());
                        double num2 = Double.parseDouble(numero2.getText().toString());
                        Calculadora calculadora = new Calculadora(num1, num2);

                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.optSoma:
                                resultado.setText(String.valueOf(calculadora.somar()));
                                resultados.add(String.valueOf(calculadora.somar()));
                                operacoes.add(num1 + " + " + num2);
                                break;
                            case R.id.optSubtrai:
                                resultado.setText(String.valueOf(calculadora.subtrair()));
                                resultados.add(String.valueOf(calculadora.subtrair()));
                                operacoes.add(num1 + " - " + num2);
                                break;
                            case R.id.optMultiplica:
                                resultado.setText(String.valueOf(calculadora.multiplicar()));
                                resultados.add(String.valueOf(calculadora.multiplicar()));
                                operacoes.add(num1 + " x " + num2);
                                break;
                            case R.id.optDivide:
                                resultado.setText(String.valueOf(calculadora.dividir()));
                                resultados.add(String.valueOf(calculadora.dividir()));
                                operacoes.add(num1 + " / " + num2);
                                break;
                            case R.id.optResto:
                                resultado.setText(String.valueOf(calculadora.resto()));
                                resultados.add(String.valueOf(calculadora.resto()));
                                operacoes.add(num1 + " % " + num2);
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Selecione ao menos uma operação", Toast.LENGTH_LONG).show();
                                break;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Digite os números para efetuar o cálculo", Toast.LENGTH_LONG).show();

                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Digite somente números para efetuar o cálculo", Toast.LENGTH_LONG).show();
                }
            }
        });
        limpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numero1.setText("");
                numero2.setText("");
                operacao.setImageResource(0);
                radioGroup.clearCheck();
                resultado.setText("0.0");
            }
        });
    }

    public void trocaOperacao(int idRadio) {
        switch (idRadio) {
            case R.id.optSoma:
                operacao.setImageResource(R.drawable.icon_add);
                break;
            case R.id.optSubtrai:
                operacao.setImageResource(R.drawable.icon_minus);
                break;
            case R.id.optMultiplica:
                operacao.setImageResource(R.drawable.icon_mult);
                break;
            case R.id.optDivide:
                operacao.setImageResource(R.drawable.icon_equals);
                break;
            case R.id.optResto:
                operacao.setImageResource(R.drawable.icon_percent);
                break;
            default:
                resultado.setText("Selecione ao menos uma operação.");
                break;
        }
    }

    private void getReferences() {
        numero1 = findViewById(R.id.txtValor1);
        numero2 = findViewById(R.id.txtValor2);
        calcula = findViewById(R.id.cmdCalcular);
        limpa = findViewById(R.id.cmdLimpar);
        resultado = findViewById(R.id.lblResultado);
        radioGroup = findViewById(R.id.radioGroup);
        operacao = findViewById(R.id.imgOperacao);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculadora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionHistorico:
                Intent intent = new Intent(MainActivity.this, HistoricoActivity.class);
                for (int i = 0; i < resultados.size(); i++){
                    intent.putExtra("numero " + i, resultados.get(i));
                }
                for (int i = 0; i < resultados.size(); i++){
                    intent.putExtra("operacao " + i, operacoes.get(i));
                }
                startActivity(intent);
                break;
            case R.id.actionSair:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}