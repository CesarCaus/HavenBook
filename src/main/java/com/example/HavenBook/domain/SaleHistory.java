package com.example.HavenBook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o histórico de vendas, contendo uma lista de livros vendidos, a data da venda e o valor total.
 */
@Setter
@Getter
@NoArgsConstructor
public class SaleHistory {
    private int id;
    private String saleDate;
    private double totalValue;
    private List<Book> books = new ArrayList<>();

    /**
     * Construtor para criar um histórico de vendas com data e valor total fornecidos.
     *
     * @param saleDate   Data da venda.
     * @param totalValue Valor total da venda.
     */
    public SaleHistory(String saleDate, double totalValue) {
        this.saleDate = saleDate;
        this.totalValue = totalValue;
    }

    /**
     * Adiciona um livro ao histórico de vendas.
     *
     * @param book Livro a ser adicionado.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Remove um livro do histórico de vendas.
     *
     * @param book Livro a ser removido.
     */
    public void removeBook(Book book) {
        books.remove(book);
    }
}
