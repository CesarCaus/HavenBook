package com.example.HavenBook;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filtro CORS personalizado para gerenciar políticas de compartilhamento de recursos entre origens diferentes.
 * Permite solicitações apenas de origens específicas e define cabeçalhos CORS apropriados.
 */
@Component
public class CorsFilter implements Filter {

    /**
     * Processa a solicitação e resposta, configurando cabeçalhos CORS apropriados.
     *
     * @param req  A solicitação do servlet.
     * @param res  A resposta do servlet.
     * @param chain A cadeia de filtros para processar a solicitação.
     * @throws IOException      Se ocorrer um erro de entrada/saída.
     * @throws ServletException Se ocorrer um erro durante o processamento do filtro.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", getAllowedOrigin(origin));
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(req, res);
    }

    /**
     * Retorna a origem permitida para o cabeçalho CORS.
     *
     * @param origin A origem da solicitação.
     * @return A origem permitida ou "null" se não for permitida.
     */
    private String getAllowedOrigin(String origin) {
        if ("http://localhost:4200".equals(origin) || "https://havenbook.netlify.app".equals(origin)) {
            return origin;
        }
        return "null"; // Pode resultar em um cabeçalho inválido para CORS.
    }

    /**
     * Inicializa o filtro. Não é utilizado neste exemplo.
     *
     * @param filterConfig Configurações do filtro.
     * @throws ServletException Se ocorrer um erro durante a inicialização.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Destrói o filtro. Não é utilizado neste exemplo.
     */
    @Override
    public void destroy() {
    }
}
