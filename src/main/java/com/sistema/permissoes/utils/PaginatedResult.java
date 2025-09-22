package com.sistema.permissoes.utils;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResult<T> {
    private List<T> result;
    private Pagination pagination;

    public PaginatedResult(List<T> result, Pagination pagination) {
        this.result = result;
        this.pagination = pagination;
    }

    public PaginatedResult() {
    }
}

