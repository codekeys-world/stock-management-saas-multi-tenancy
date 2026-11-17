package com.codekeys.saas.config;

/**
 * TenantContext - Stocke l'identifiant du tenant courant dans un TheadLocal
 * Chaque requette http est traitée par un thread
 * Le ThreadLocal garantit que le tenant_id est isole par thread
 * Meme en cas de requette simultanée de tenants différents
 */
public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    /**
     * Defini l'identifiant du tenant par le thread courrant
     * @Param tenant
     */
    public static void setCurrentTenant(final String tenant){
        CURRENT_TENANT.set(tenant);
    }

    /**
     * Recupere l'identifiant du tenant pour le thread courrant
     */
    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    /**
     * Nettoie le tenant du thread courrant
     * IMPORTANT : doit etre appélé dans un bloc finaly
     * Pour eviter les fuites de memoires
     * et les fuites de données entre les requettes
     */
    public static void clear(){
        CURRENT_TENANT.remove();
    }
}
