package com.example.HavenBook.services;

import com.example.HavenBook.domain.AuthorSales;
import com.example.HavenBook.domain.BestSellingBook;
import com.example.HavenBook.domain.Book;
import com.example.HavenBook.domain.SaleHistory;
import com.example.HavenBook.exceptions.InvalidDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço para obter estatísticas de vendas, incluindo vendas por autor, receita total, contagem de livros e livros mais vendidos.
 */
@Service
public class StatisticService {

    @Autowired
    private SaleHistoryService saleHistoryService;

    @Autowired
    private BookService _bookService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Obtém estatísticas de vendas por autor, que incluem o número total de livros vendidos por cada autor.
     *
     * @return Uma lista de {@code AuthorSales} contendo o nome do autor e o número total de livros vendidos.
     */
    public List<AuthorSales> getAuthorSalesStatistics() {
        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();

        Map<String, Integer> authorBookCount = new HashMap<>();

        for (SaleHistory sale : saleHistories) {
            for (Book book : sale.getBooks()) {
                authorBookCount.put(book.getAuthor(), authorBookCount.getOrDefault(book.getAuthor(), 0) + 1);
            }
        }

        return authorBookCount.entrySet()
                .stream()
                .map(entry -> new AuthorSales(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Calcula a receita total gerada pelas vendas.
     *
     * @return O valor total da receita.
     */
    public double getTotalRevenue() {
        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();
        return saleHistories.stream()
                .mapToDouble(SaleHistory::getTotalValue)
                .sum();
    }

    /**
     * Calcula o número total de livros vendidos.
     *
     * @return O número total de livros vendidos.
     */
    public long getTotalBookCount() {
        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();
        return saleHistories.stream()
                .mapToLong(sale -> sale.getBooks().size())
                .sum();
    }

    /**
     * Obtém uma lista de livros mais vendidos, ordenada pelo número de vezes que foram vendidos.
     *
     * @return Uma lista de {@code BestSellingBook} contendo o título do livro e o número total de vendas.
     */
    public List<BestSellingBook> getBestSellingBooks() {
        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();
        Map<String, Long> bookSalesCount = new HashMap<>();

        for (SaleHistory sale : saleHistories) {
            for (Book book : sale.getBooks()) {
                bookSalesCount.put(book.getTitle(), bookSalesCount.getOrDefault(book.getTitle(), 0L) + 1);
            }
        }

        return bookSalesCount.entrySet()
                .stream()
                .map(entry -> new BestSellingBook(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(BestSellingBook::getCount).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Conta o número de livros que nunca foram vendidos.
     *
     * @return O número de livros que nunca foram vendidos.
     */
    public long getNeverSoldBookCount() {
        List<Book> allBooks = _bookService.getBooksFromJson();

        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();
        Set<String> soldBookTitles = new HashSet<>();

        for (SaleHistory sale : saleHistories) {
            for (Book book : sale.getBooks()) {
                soldBookTitles.add(book.getTitle());
            }
        }

        return allBooks.stream()
                .map(Book::getTitle)
                .filter(title -> !soldBookTitles.contains(title))
                .count();
    }

    /**
     * Analisa uma string de data para um objeto {@code LocalDate}.
     *
     * @param dateStr A string que representa a data.
     * @return O objeto {@code LocalDate} correspondente à string fornecida.
     * @throws InvalidDateException Se a string de data for nula, vazia ou estiver em um formato inválido.
     */
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            throw new InvalidDateException("A data não pode ser nula ou vazia");
        }
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Formato de data inválido: " + dateStr);
        }
    }

    /**
     * Valida se a data de início não é posterior à data de término.
     *
     * @param startDate A data de início.
     * @param endDate   A data de término.
     * @throws InvalidDateException Se a data de início for posterior à data de término.
     */
    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateException("A data de início não pode ser posterior à data de término.");
        }
    }

    /**
     * Obtém estatísticas de vendas por autor para um intervalo de datas específico.
     *
     * @param startDateStr A string representando a data de início do intervalo.
     * @param endDateStr   A string representando a data de término do intervalo.
     * @return Uma lista de {@code AuthorSales} contendo o nome do autor e o número total de livros vendidos no intervalo.
     * @throws InvalidDateException Se as datas fornecidas forem inválidas ou o intervalo de datas for inválido.
     */
    public List<AuthorSales> getAuthorSalesStatistics(String startDateStr, String endDateStr) {
        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);
        validateDateRange(startDate, endDate);

        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();

        List<SaleHistory> filteredSales = saleHistories.stream()
                .filter(sale -> {
                    LocalDate saleDate = parseDate(sale.getSaleDate());
                    return !saleDate.isBefore(startDate) && !saleDate.isAfter(endDate);
                })
                .collect(Collectors.toList());

        Map<String, Integer> authorBookCount = new HashMap<>();

        for (SaleHistory sale : filteredSales) {
            for (Book book : sale.getBooks()) {
                authorBookCount.put(book.getAuthor(), authorBookCount.getOrDefault(book.getAuthor(), 0) + 1);
            }
        }

        return authorBookCount.entrySet()
                .stream()
                .map(entry -> new AuthorSales(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Calcula a receita total gerada pelas vendas dentro de um intervalo de datas específico.
     *
     * @param startDateStr A string representando a data de início do intervalo.
     * @param endDateStr   A string representando a data de término do intervalo.
     * @return A receita total gerada dentro do intervalo de datas.
     * @throws InvalidDateException Se as datas fornecidas forem inválidas ou o intervalo de datas for inválido.
     */
    public double getTotalRevenue(String startDateStr, String endDateStr) {
        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);
        validateDateRange(startDate, endDate);

        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();

        return saleHistories.stream()
                .filter(sale -> {
                    LocalDate saleDate = parseDate(sale.getSaleDate());
                    return !saleDate.isBefore(startDate) && !saleDate.isAfter(endDate);
                })
                .mapToDouble(SaleHistory::getTotalValue)
                .sum();
    }

    /**
     * Calcula o número total de livros vendidos dentro de um intervalo de datas específico.
     *
     * @param startDateStr A string representando a data de início do intervalo.
     * @param endDateStr   A string representando a data de término do intervalo.
     * @return O número total de livros vendidos dentro do intervalo de datas.
     * @throws InvalidDateException Se as datas fornecidas forem inválidas ou o intervalo de datas for inválido.
     */
    public long getTotalBookCount(String startDateStr, String endDateStr) {
        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);
        validateDateRange(startDate, endDate);

        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();

        return saleHistories.stream()
                .filter(sale -> {
                    LocalDate saleDate = parseDate(sale.getSaleDate());
                    return !saleDate.isBefore(startDate) && !saleDate.isAfter(endDate);
                })
                .mapToLong(sale -> sale.getBooks().size())
                .sum();
    }

    /**
     * Obtém uma lista de livros mais vendidos dentro de um intervalo de datas específico, ordenada pelo número de vezes que foram vendidos.
     *
     * @param startDateStr A string representando a data de início do intervalo.
     * @param endDateStr   A string representando a data de término do intervalo.
     * @return Uma lista de {@code BestSellingBook} contendo o título do livro e o número total de vendas dentro do intervalo.
     * @throws InvalidDateException Se as datas fornecidas forem inválidas ou o intervalo de datas for inválido.
     */
    public List<BestSellingBook> getBestSellingBooks(String startDateStr, String endDateStr) {
        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);
        validateDateRange(startDate, endDate);

        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();

        List<SaleHistory> filteredSales = saleHistories.stream()
                .filter(sale -> {
                    LocalDate saleDate = parseDate(sale.getSaleDate());
                    return !saleDate.isBefore(startDate) && !saleDate.isAfter(endDate);
                })
                .collect(Collectors.toList());

        Map<String, Long> bookSalesCount = new HashMap<>();

        for (SaleHistory sale : filteredSales) {
            for (Book book : sale.getBooks()) {
                bookSalesCount.put(book.getTitle(), bookSalesCount.getOrDefault(book.getTitle(), 0L) + 1);
            }
        }

        return bookSalesCount.entrySet()
                .stream()
                .map(entry -> new BestSellingBook(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(BestSellingBook::getCount).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Conta o número de livros que nunca foram vendidos dentro de um intervalo de datas específico.
     *
     * @param startDateStr A string representando a data de início do intervalo.
     * @param endDateStr   A string representando a data de término do intervalo.
     * @return O número de livros que nunca foram vendidos dentro do intervalo de datas.
     * @throws InvalidDateException Se as datas fornecidas forem inválidas ou o intervalo de datas for inválido.
     */
    public long getNeverSoldBookCount(String startDateStr, String endDateStr) {
        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);
        validateDateRange(startDate, endDate);

        List<Book> allBooks = _bookService.getBooksFromJson();

        List<SaleHistory> saleHistories = saleHistoryService.getSaleHistoriesFromJson();

        Set<String> soldBookTitles = saleHistories.stream()
                .filter(sale -> {
                    LocalDate saleDate = parseDate(sale.getSaleDate());
                    return !saleDate.isBefore(startDate) && !saleDate.isAfter(endDate);
                })
                .flatMap(sale -> sale.getBooks().stream())
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        return allBooks.stream()
                .map(Book::getTitle)
                .filter(title -> !soldBookTitles.contains(title))
                .count();
    }
}
