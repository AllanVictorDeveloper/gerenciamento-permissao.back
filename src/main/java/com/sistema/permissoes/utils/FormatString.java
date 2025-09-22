package com.sistema.permissoes.utils;

import java.util.ArrayList;
import java.util.List;

public class FormatString {


    public static List<String> separarStringPelaBarra(String input) {
        // Encontrar a posição da última ocorrência de '/'
        int lastIndex = input.lastIndexOf('/');

        // Encontrar a posição da penúltima ocorrência de '/'
        int secondLastIndex = input.lastIndexOf('/', lastIndex - 1);

        // Se houver menos de duas ocorrências, retornar a string original em uma lista
        if (lastIndex == -1 || secondLastIndex == -1) {

            List<String> consultas = List.of(input.split(" "));

            return consultas;
        }

        // Divida a string em três partes
        List<String> result = new ArrayList<>();
        result.add(input.substring(0, secondLastIndex)); // Parte antes das duas últimas ocorrências
        result.add(input.substring(secondLastIndex + 1, lastIndex)); // Parte entre a penúltima e a última ocorrência
        result.add(input.substring(lastIndex + 1)); // Parte após a última ocorrência
        return result;
    }

}
