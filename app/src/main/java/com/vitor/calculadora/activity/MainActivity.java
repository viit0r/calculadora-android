package com.vitor.calculadora.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vitor.calculadora.R;
import com.vitor.calculadora.model.Calculadora;
import com.vitor.calculadora.model.Calculo;
import com.vitor.calculadora.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText txtValor1, txtValor2;
    private RadioGroup radioGroup;
    private RadioButton soma, subtrai, multiplica, divide, resto;
    private Button calcula, limpa;
    private TextView resultado;
    private ImageView operacao;
    private List<String> resultados = new ArrayList<>();
    private List<String> operacoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getReferences();

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        calcula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!txtValor1.getText().toString().isEmpty() && !txtValor2.getText().toString().isEmpty()) {
                        double num1 = Double.parseDouble(txtValor1.getText().toString());
                        double num2 = Double.parseDouble(txtValor2.getText().toString());
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
                                operacoes.add(num1 + " * " + num2);
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
                                Toast.makeText(getApplicationContext(), getString(R.string.selecione_uma_operacao), Toast.LENGTH_LONG).show();
                                break;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.digite_numeros), Toast.LENGTH_LONG).show();

                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.digite_somente_numeros), Toast.LENGTH_LONG).show();
                }
            }
        });

        limpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.clearCheck();
                txtValor1.setText("");
                txtValor2.setText("");
                operacao.setImageResource(0);
                resultado.setText(Const.ZERO_PONTO_ZERO);
            }
        });

        resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroResultado = resultado.getText().toString();
                if (!numeroResultado.isEmpty() && !numeroResultado.equals(Const.ZERO_PONTO_ZERO)) {
                    limpa.callOnClick();
                    txtValor1.setText(numeroResultado);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int idRadio) {
                trocaOperacaoPorId(idRadio);
            }
        });
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
                Calculo calculo = new Calculo(resultados, operacoes);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(Const.LISTA_RESULTADOS, (ArrayList<String>) resultados);
                bundle.putStringArrayList(Const.LISTA_OPERACOES, (ArrayList<String>) operacoes);
                Intent intent = new Intent(MainActivity.this, HistoricoActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Const.ABRE_HISTORICO);
                break;
            case R.id.actionSair:
                finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.ABRE_HISTORICO) {
            if (resultCode == Activity.RESULT_OK) {
                resultados = Calculo.getResultados();
                operacoes = Calculo.getOperacoes();
                txtValor1.setText(data.getStringArrayExtra(Const.RETORNO_NUMEROS_OPERACAO)[0]);
                txtValor2.setText(data.getStringArrayExtra(Const.RETORNO_NUMEROS_OPERACAO)[1]);
                resultado.setText(data.getStringExtra(Const.RETORNO_RESULTADO));

                trocaOperacaoPorCaractere(data.getStringExtra(Const.RETORNO_OPERACAO));
            }
        }
    }

    public void trocaOperacaoPorCaractere(String caractere) {
        switch (caractere) {
            case "+":
                soma.setChecked(true);
                operacao.setImageResource(R.drawable.icon_add);
                break;
            case "-":
                subtrai.setChecked(true);
                operacao.setImageResource(R.drawable.icon_minus);
                break;
            case "*":
                multiplica.setChecked(true);
                operacao.setImageResource(R.drawable.icon_mult);
                break;
            case "/":
                divide.setChecked(true);
                operacao.setImageResource(R.drawable.icon_equals);
                break;
            case "%":
                resto.setChecked(true);
                operacao.setImageResource(R.drawable.icon_percent);
                break;
            default:
                Toast.makeText(this, getString(R.string.selecione_uma_operacao), Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void trocaOperacaoPorId(int idRadio) {
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
                Toast.makeText(this, getString(R.string.selecione_uma_operacao), Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void getReferences() {
        toolbar = findViewById(R.id.toolbar);
        txtValor1 = findViewById(R.id.txtValor1);
        txtValor2 = findViewById(R.id.txtValor2);
        calcula = findViewById(R.id.cmdCalcular);
        limpa = findViewById(R.id.cmdLimpar);
        resultado = findViewById(R.id.lblResultado);
        radioGroup = findViewById(R.id.radioGroup);
        soma = findViewById(R.id.optSoma);
        subtrai = findViewById(R.id.optSubtrai);
        multiplica = findViewById(R.id.optMultiplica);
        divide = findViewById(R.id.optDivide);
        resto = findViewById(R.id.optResto);
        operacao = findViewById(R.id.imgOperacao);
    }
}