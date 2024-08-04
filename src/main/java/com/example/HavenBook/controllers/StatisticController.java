package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.AuthorSales;
import com.example.HavenBook.domain.BestSellingBook;
import com.example.HavenBook.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para estatísticas.
 * Esta classe fornece endpoints para obter diversas estatísticas relacionadas às vendas e aos livros.
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    /**
     * Construtor para inicializar o controlador com o serviço de estatísticas.
     *
     * @param statisticService O serviço de estatísticas a ser usado pelo controlador.
     */
    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    /**
     * Obtém as estatísticas de vendas por autor.
     *
     * @return Uma lista de estatísticas de vendas por autor.
     */
    @GetMapping("/author-sales")
    public List<AuthorSales> getAuthorSalesStatistics() {
        return statisticService.getAuthorSalesStatistics();
    }

    /**
     * Obtém a receita total.
     *
     * @return O valor da receita total.
     */
    @GetMapping("/total-revenue")
    public double getTotalRevenue() {
        return statisticService.getTotalRevenue();
    }

    /**
     * Obtém a contagem total de livros.
     *
     * @return O número total de livros.
     */
    @GetMapping("/total-book-count")
    public long getTotalBookCount() {
        return statisticService.getTotalBookCount();
    }

    /**
     * Obtém os livros mais vendidos.
     *
     * @return Uma lista de livros mais vendidos.
     */
    @GetMapping("/best-selling-books")
    public List<BestSellingBook> getBestSellingBooks() {
        return statisticService.getBestSellingBooks();
    }

    /**
     * Obtém a contagem de livros nunca vendidos.
     *
     * @return O número de livros nunca vendidos.
     */
    @GetMapping("/never-sold-book-count")
    public long getLeastSellingBookCount() {
        return statisticService.getNeverSoldBookCount();
    }

    /**
     * Obtém as estatísticas de vendas por autor em um intervalo de datas.
     *
     * @param startDate A data de início do intervalo.
     * @param endDate   A data de fim do intervalo.
     * @return Uma lista de estatísticas de vendas por autor dentro do intervalo fornecido.
     */
    @GetMapping("/author-sales-by-date")
    public List<AuthorSales> getAuthorSalesStatisticsByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return statisticService.getAuthorSalesStatistics(startDate, endDate);
    }

    /**
     * Obtém a receita total em um intervalo de datas.
     *
     * @param startDate A data de início do intervalo.
     * @param endDate   A data de fim do intervalo.
     * @return O valor da receita total dentro do intervalo fornecido.
     */
    @GetMapping("/total-revenue-by-date")
    public double getTotalRevenueByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return statisticService.getTotalRevenue(startDate, endDate);
    }

    /**
     * Obtém a contagem total de livros em um intervalo de datas.
     *
     * @param startDate A data de início do intervalo.
     * @param endDate   A data de fim do intervalo.
     * @return O número total de livros dentro do intervalo fornecido.
     */
    @GetMapping("/total-book-count-by-date")
    public long getTotalBookCountByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return statisticService.getTotalBookCount(startDate, endDate);
    }

    /**
     * Obtém os livros mais vendidos em um intervalo de datas.
     *
     * @param startDate A data de início do intervalo.
     * @param endDate   A data de fim do intervalo.
     * @return Uma lista de livros mais vendidos dentro do intervalo fornecido.
     */
    @GetMapping("/best-selling-books-by-date")
    public List<BestSellingBook> getBestSellingBooksByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return statisticService.getBestSellingBooks(startDate, endDate);
    }

    /**
     * Obtém a contagem de livros nunca vendidos em um intervalo de datas.
     *
     * @param startDate A data de início do intervalo.
     * @param endDate   A data de fim do intervalo.
     * @return O número de livros nunca vendidos dentro do intervalo fornecido.
     */
    @GetMapping("/never-sold-book-count-by-date")
    public long getNeverSoldBookCountByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return statisticService.getNeverSoldBookCount(startDate, endDate);
    }
}
