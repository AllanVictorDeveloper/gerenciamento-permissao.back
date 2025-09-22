package com.sistema.permissoes.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormatList {

    public static List<String> deleteBrackets(String string) {

        String cleanedMessage = string.replace("[", "").replace("]", "");

        String[] errorsArray = cleanedMessage.split(", ");
        List<String> errorsList = new ArrayList<>(Arrays.asList(errorsArray));

        return errorsList;
    }

    public static String[] combinarArrays(String[]... arrays) {
        return Arrays.stream(arrays)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }
}
