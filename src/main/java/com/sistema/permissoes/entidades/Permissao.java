package com.sistema.permissoes.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "permissoes")
@Data
public class Permissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // CONSULTAR, EDITAR, CADASTRAR, EXCLUIR
}


