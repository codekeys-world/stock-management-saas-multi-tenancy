package com.codekeys.saas.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Tenant - Intercepte pour chaque requette http pour identifier le tenant
 * Il s'execute avant tout les controleurs
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter implements Filter {
    private final static String TENANT_HEADER="X-Tenant-ID";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String tenantId = resolveTenant(request);
        if(tenantId == null || tenantId.isBlank()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Teant ID is missing int the request header, please add the header X-Tenant-ID\"}"
            );
            //chain.doFilter(request, response);
            return;
        }
        try {
            //Stocker le teant dans le thread local
            TenantContext.setCurrentTenant(tenantId);
            //Continuer la chaine de filtre
            chain.doFilter(request, response);
        }finally {
            //Toujours nettoyer le tread local apres la requette.
            TenantContext.clear();
        }
        //chain.doFilter(request, response);
    }

    private String resolveTenant(HttpServletRequest request) {
        final String tenantId = request.getHeader(TENANT_HEADER);
        if(tenantId != null && !tenantId.isBlank()){
            return tenantId.trim().toUpperCase();
        }
        return null;
    }
}
