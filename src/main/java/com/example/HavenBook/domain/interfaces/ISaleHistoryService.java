package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.SaleHistory;

import java.util.List;

/**
 * Interface para o serviço de histórico de vendas.
 */
public interface ISaleHistoryService {

    /**
     * Obtém a lista de todos os históricos de vendas.
     *
     * @return Lista de {@code SaleHistory}.
     */
    List<SaleHistory> getAllSaleHistories();

    /**
     * Obtém um histórico de venda pelo seu ID.
     *
     * @param id ID do histórico de venda.
     * @return O {@code SaleHistory} com o ID especificado.
     */
    SaleHistory getSaleHistoryById(int id);

    /**
     * Adiciona um novo histórico de venda à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param newSaleHistory O novo {@code SaleHistory} a ser adicionado.
     */
    void addSaleHistory(SaleHistory newSaleHistory);

    /**
     * Atualiza um histórico de venda existente com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id               ID do histórico de venda a ser atualizado.
     * @param updatedSaleHistory O {@code SaleHistory} com as informações atualizadas.
     */
    void updateSaleHistory(int id, SaleHistory updatedSaleHistory);

    /**
     * Remove um histórico de venda da lista com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id ID do histórico de venda a ser removido.
     */
    void deleteSaleHistory(int id);
}
