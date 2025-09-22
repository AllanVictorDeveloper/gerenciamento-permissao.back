package com.sistema.permissoes.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SequenceIdService {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    public int proximaSequenciaId(String tabela) {

        String sql = "SELECT MAX(id) FROM " + tabela;

        Integer maxId = jdbcTemplate.queryForObject(sql, Integer.class);

        // Se não houver registros na tabela, o ID inicial pode ser 1
        if (maxId == null) {
            return 1;
        }

        // Incrementa o ID máximo encontrado
        return maxId + 1;
    }
}
