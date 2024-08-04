package com.example.HavenBook.services;

import com.example.HavenBook.domain.Book;
import com.example.HavenBook.domain.interfaces.IBookService;
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
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serviço para gerenciar os livros, incluindo operações de leitura, adição, atualização e exclusão de livros
 * armazenados em um arquivo JSON.
 */
@Service
@PropertySource("classpath:application.properties")
public class BookService implements IBookService {

    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<Book> books;
    private int nextId;

    /**
     * Construtor para o serviço {@code BookService}.
     *
     * @param resourceLoader O carregador de recursos para obter o caminho absoluto do arquivo JSON.
     * @param jsonFilePath   O caminho relativo para o arquivo JSON que contém os livros.
     */
    @Autowired
    public BookService(ResourceLoader resourceLoader, @Value("static/books.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    /**
     * Inicializa o serviço carregando os livros do arquivo JSON e definindo o próximo ID disponível.
     */
    @PostConstruct
    public void init() {
        this.books = getBooksFromJson();
        if (this.books.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
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
     * Carrega a lista de livros do arquivo JSON.
     *
     * @return Uma lista de livros carregados do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao ler o arquivo JSON.
     */
    public List<Book> getBooksFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, List.of());
            }
            return mapper.readValue(file, new TypeReference<List<Book>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo JSON", e);
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    /**
     * Obtém um livro pelo ID.
     *
     * @param id O ID do livro a ser recuperado.
     * @return O livro correspondente ao ID fornecido ou {@code null} se não for encontrado.
     */
    public Book getBookById(int id) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        return book.orElse(null);
    }

    /**
     * Adiciona um novo livro à lista e salva os livros no arquivo JSON.
     *
     * @param newBook O novo livro a ser adicionado.
     */
    public synchronized void addBook(Book newBook) {
        newBook.setId(nextId++);
        books.add(newBook);
        saveBooksToJson();
    }

    /**
     * Atualiza um livro existente com base no ID fornecido e salva os livros no arquivo JSON.
     *
     * @param id            O ID do livro a ser atualizado.
     * @param updatedBook   O livro atualizado.
     */
    public synchronized void updateBook(int id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                updatedBook.setId(id);
                books.set(i, updatedBook);
                saveBooksToJson();
                return;
            }
        }
    }

    /**
     * Remove um livro com base no ID fornecido e salva os livros no arquivo JSON.
     *
     * @param id O ID do livro a ser removido.
     */
    public synchronized void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
        saveBooksToJson();
    }

    /**
     * Salva a lista atual de livros no arquivo JSON.
     *
     * @throws RuntimeException Se ocorrer um erro ao salvar o arquivo JSON.
     */
    private synchronized void saveBooksToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            mapper.writeValue(file, books);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o arquivo JSON", e);
            throw new RuntimeException("Erro ao salvar o arquivo JSON", e);
        }
    }
}
