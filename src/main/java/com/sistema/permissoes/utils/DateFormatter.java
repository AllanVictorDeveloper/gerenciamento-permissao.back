package com.sistema.permissoes.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatter {

    public static String formatDate(LocalDate date, String format) {
        if (date == null) {
            throw new IllegalArgumentException("O parâmetro date não pode ser nulo");
        }

        DateTimeFormatter formatter;
        switch (format) {
            case "DD/MM/YYYY":
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                break;
            case "YYYY-MM-DD":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                break;
            case "MM-DD-YYYY":
                formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                break;
            default:
                throw new IllegalArgumentException("Formato de data inválido. Use DD/MM/YYYY, YYYY-MM-DD ou MM-DD-YYYY.");
        }

        return date.format(formatter);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            throw new IllegalArgumentException("O parâmetro date não pode ser nulo");
        }

        SimpleDateFormat formatter;
        switch (format) {
            case "DD/MM/YYYY":
                formatter = new SimpleDateFormat("dd/MM/yyyy");
                break;
            case "YYYY-MM-DD":
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case "MM-DD-YYYY":
                formatter = new SimpleDateFormat("MM-dd-yyyy");
                break;
            default:
                throw new IllegalArgumentException("Formato de data inválido. Use DD/MM/YYYY, YYYY-MM-DD ou MM-DD-YYYY.");
        }

        return formatter.format(date);
    }
}
