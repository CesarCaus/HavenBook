package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.SaleHistory;
import com.example.HavenBook.services.SaleHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar históricos de vendas.
 * Esta classe fornece endpoints para criar, ler, atualizar e excluir históricos de vendas.
 */
@RestController
@RequestMapping("/api/salehistory")
public class SaleHistoryController {

    private final SaleHistoryService saleHistoryService;

    /**
     * Construtor para inicializar o controlador com o serviço de históricos de vendas.
     *
     * @param saleHistoryService O serviço de históricos de vendas a ser usado pelo controlador.
     */
    @Autowired
    public SaleHistoryController(SaleHistoryService saleHistoryService) {
        this.saleHistoryService = saleHistoryService;
    }

    /**
     * Obtém todos os históricos de vendas.
     *
     * @return Uma lista de históricos de vendas.
     */
    @GetMapping
    public List<SaleHistory> getAllSaleHistories() {
        return saleHistoryService.getSaleHistoriesFromJson();
    }

    /**
     * Obtém um histórico de vendas pelo ID.
     *
     * @param id O ID do histórico de vendas a ser recuperado.
     * @return O histórico de vendas correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public SaleHistory getSaleHistoryById(@PathVariable int id) {
        return saleHistoryService.getSaleHistoryById(id);
    }

    /**
     * Adiciona um novo histórico de vendas.
     *
     * @param newSaleHistory O histórico de vendas a ser adicionado.
     */
    @PostMapping
    public void addSaleHistory(@RequestBody SaleHistory newSaleHistory) {
        saleHistoryService.addSaleHistory(newSaleHistory);
    }

    /**
     * Atualiza um histórico de vendas existente.
     *
     * @param id               O ID do histórico de vendas a ser atualizado.
     * @param updatedSaleHistory O histórico de vendas com as novas informações.
     */
    @PutMapping("/{id}")
    public void updateSaleHistory(@PathVariable int id, @RequestBody SaleHistory updatedSaleHistory) {
        saleHistoryService.updateSaleHistory(id, updatedSaleHistory);
    }

    /**
     * Remove um histórico de vendas pelo ID.
     *
     * @param id O ID do histórico de vendas a ser removido.
     */
    @DeleteMapping("/{id}")
    public void deleteSaleHistory(@PathVariable int id) {
        saleHistoryService.deleteSaleHistory(id);
    }
}
