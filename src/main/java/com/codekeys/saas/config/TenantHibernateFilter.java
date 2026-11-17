package com.codekeys.saas.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 * Active automatiquement le filtre de hibernate
 * Sans aspect if faut activer le filtre manuellement
 */
@Aspect
@Component
public class TenantHibernateFilter {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Intercepte tous les appels aux méthodes de repositories
     */
    @Before("execution(* com.codekeys.saas.services.*.*(..))")
    public void activateTenantFilter(){
        final String tenantId = TenantContext.getCurrentTenant();
        if(tenantId != null){
            final Session session = this.entityManager.unwrap(Session.class);
            //activer le filtre et injecte le parametre tenantId
            session.enableFilter("tenantFilter")
                    .setParameter("tenantId", tenantId);
        }
    }
}
