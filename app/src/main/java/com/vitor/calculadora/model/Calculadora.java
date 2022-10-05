package com.vitor.calculadora.model;

import com.vitor.calculadora.utils.Const;

public class Calculadora {

    private static double dPrimeiroNumero, dSegundoNumero;

    public Calculadora(double primeiroNumeroParam, double segundoNumeroParam) {
        dPrimeiroNumero = primeiroNumeroParam;
        dSegundoNumero = segundoNumeroParam;
    }

    public static double fazOperacao(String operacao) {
        switch (operacao) {
            case Const.SOMA:
                return somar();
            case Const.SUBTRACAO:
                return subtrair();
            case Const.MULTIPLICACAO:
                return multiplicar();
            case Const.DIVISAO:
                return dividir();
            case Const.RESTO_DIVISAO:
                return resto();
            default:
                return 0;
        }
    }

    public static double somar() {
        return dPrimeiroNumero + dSegundoNumero;
    }

    public static double subtrair() {
        return dPrimeiroNumero - dSegundoNumero;
    }

    public static double multiplicar() {
        return dPrimeiroNumero * dSegundoNumero;
    }

    public static double dividir() {
        return dPrimeiroNumero / dSegundoNumero;
    }

    public static double resto() {
        return dPrimeiroNumero % dSegundoNumero;
    }
}
