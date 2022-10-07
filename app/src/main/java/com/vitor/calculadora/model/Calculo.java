package com.vitor.calculadora.model;

import com.vitor.calculadora.utils.Const;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculo {
    private static List<String> resultados;
    private static List<String> operacoes;

    public Calculo(List<String> resultados, List<String> operacoes) {
        this.resultados = resultados;
        this.operacoes = operacoes;
    }

    public static String getResultado(int iPosicao) {
        String resultado = "";
        resultado = resultados.get(iPosicao);
        return resultado;
    }

    public static Map<String, String> getOperacao(int iPosicao) {
        Map<String, String> mapCalculo = new HashMap<>();
        String primeiroNumeroOperacao = "";
        String segundoNumeroOperacao = "";
        String operacao = "";

        primeiroNumeroOperacao = operacoes.get(iPosicao).split(Const.REGEX_NUMEROS_OPERACAO)[0].trim();
        segundoNumeroOperacao = operacoes.get(iPosicao).split(Const.REGEX_NUMEROS_OPERACAO)[1].trim();
        operacao = operacoes.get(iPosicao).split(Const.REGEX_OPERACOES)[1].trim();

        mapCalculo.put(Const.PRIMEIRO_NUMERO_OPERACAO, primeiroNumeroOperacao);
        mapCalculo.put(Const.SEGUNDO_NUMERO_OPERACAO, segundoNumeroOperacao);
        mapCalculo.put(Const.OPERACAO, operacao);
        return mapCalculo;
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
