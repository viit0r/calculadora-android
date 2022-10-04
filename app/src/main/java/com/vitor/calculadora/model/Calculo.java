package com.vitor.calculadora.model;

import java.util.List;

public class Calculo {
    private static List<String> resultados;
    private static List<String> operacoes;

    public Calculo(List<String> resultados, List<String> operacoes) {
        this.resultados = resultados;
        this.operacoes = operacoes;
    }

    public static List<String> getResultados() {
        return resultados;
    }

    public static List<String> getOperacoes() {
        return operacoes;
    }

    public static void removeResultado(int iPosicao){
        resultados.remove(iPosicao);
    }

    public static void removeOperacao(int iPosicao){
        operacoes.remove(iPosicao);
    }
}
