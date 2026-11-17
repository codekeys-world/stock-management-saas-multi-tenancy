package com.codekeys.saas.repositories;

import com.codekeys.saas.entities.StockMvt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMvtStkRepository extends JpaRepository<StockMvt, String> {
}
