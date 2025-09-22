package com.sistema.permissoes.utils;

import lombok.Data;

@Data
public class Pagination {
    private int xPage;
    private int xTotalPages;
    private long xTotalCount;

    public Pagination(int xPage, int xTotalPages, long xTotalCount) {
        this.xPage = xPage;
        this.xTotalPages = xTotalPages;
        this.xTotalCount = xTotalCount;
    }

    public Pagination() {
    }
}
