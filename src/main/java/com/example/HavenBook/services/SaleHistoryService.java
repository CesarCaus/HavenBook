package com.example.HavenBook.services;

import com.example.HavenBook.domain.SaleHistory;
import com.example.HavenBook.domain.Book;
import com.example.HavenBook.domain.interfaces.ISaleHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serviço para gerenciar o histórico de vendas, incluindo operações de leitura, adição, atualização e exclusão
 * de registros de vendas armazenados em um arquivo JSON.
 */
@Service
@PropertySource("classpath:application.properties")
public class SaleHistoryService implements ISaleHistoryService {

    private static final Logger LOGGER = Logger.getLogger(SaleHistoryService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<SaleHistory> saleHistories;
    private int nextId;

    /**
     * Construtor para o serviço {@code SaleHistoryService}.
     *
     * @param resourceLoader O carregador de recursos para obter o caminho absoluto do arquivo JSON.
     * @param jsonFilePath   O caminho relativo para o arquivo JSON que contém o histórico de vendas.
     */
    @Autowired
    public SaleHistoryService(ResourceLoader resourceLoader, @Value("static/saleHistories.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    /**
     * Inicializa o serviço carregando o histórico de vendas do arquivo JSON e definindo o próximo ID disponível.
     */
    @PostConstruct
    public void init() {
        this.saleHistories = getSaleHistoriesFromJson();
        if (this.saleHistories.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = saleHistories.stream().mapToInt(SaleHistory::getId).max().orElse(0) + 1;
        }
    }

    /**
     * Obtém o caminho absoluto do arquivo JSON a partir do caminho relativo fornecido.
     *
     * @param relativePath O caminho relativo do arquivo JSON.
     * @return O caminho absoluto do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao obter o caminho absoluto.
     */
    private String getAbsolutePath(String relativePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + relativePath);
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter o caminho absoluto", e);
            throw new RuntimeException("Erro ao obter o caminho absoluto", e);
        }
    }

    /**
     * Carrega a lista de registros de histórico de vendas do arquivo JSON.
     *
     * @return Uma lista de registros de histórico de vendas carregados do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao ler o arquivo JSON.
     */
    public List<SaleHistory> getSaleHistoriesFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, new ArrayList<SaleHistory>());
            }
            return mapper.readValue(file, new TypeReference<List<SaleHistory>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo JSON", e);
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    /**
     * Obtém todos os registros de histórico de vendas.
     *
     * @return Uma lista de todos os registros de histórico de vendas.
     */
    @Override
    public List<SaleHistory> getAllSaleHistories() {
        return List.of();
    }

    /**
     * Obtém um registro de histórico de vendas pelo ID.
     *
     * @param id O ID do registro de histórico de vendas a ser recuperado.
     * @return O registro de histórico de vendas correspondente ao ID fornecido ou {@code null} se não for encontrado.
     */
    public SaleHistory getSaleHistoryById(int id) {
        Optional<SaleHistory> saleHistory = saleHistories.stream().filter(s -> s.getId() == id).findFirst();
        return saleHistory.orElse(null);
    }

    /**
     * Adiciona um novo registro de histórico de vendas à lista e salva os registros no arquivo JSON.
     *
     * @param newSaleHistory O novo registro de histórico de vendas a ser adicionado.
     */
    public synchronized void addSaleHistory(SaleHistory newSaleHistory) {
        newSaleHistory.setId(nextId++);
        saleHistories.add(newSaleHistory);
        saveSaleHistoriesToJson();
    }

    /**
     * Atualiza um registro de histórico de vendas existente com base no ID fornecido e salva os registros no arquivo JSON.
     *
     * @param id                O ID do registro de histórico de vendas a ser atualizado.
     * @param updatedSaleHistory O registro de histórico de vendas atualizado.
     */
    public synchronized void updateSaleHistory(int id, SaleHistory updatedSaleHistory) {
        for (int i = 0; i < saleHistories.size(); i++) {
            if (saleHistories.get(i).getId() == id) {
                updatedSaleHistory.setId(id);
                saleHistories.set(i, updatedSaleHistory);
                saveSaleHistoriesToJson();
                return;
            }
        }
    }

    /**
     * Remove um registro de histórico de vendas com base no ID fornecido e salva os registros no arquivo JSON.
     *
     * @param id O ID do registro de histórico de vendas a ser removido.
     */
    public synchronized void deleteSaleHistory(int id) {
        saleHistories.removeIf(saleHistory -> saleHistory.getId() == id);
        saveSaleHistoriesToJson();
    }

    /**
     * Salva a lista atual de registros de histórico de vendas no arquivo JSON.
     *
     * @throws RuntimeException Se ocorrer um erro ao salvar o arquivo JSON.
     */
    private synchronized void saveSaleHistoriesToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            mapper.writeValue(file, saleHistories);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o arquivo JSON", e);
            throw new RuntimeException("Erro ao salvar o arquivo JSON", e);
        }
    }
}
