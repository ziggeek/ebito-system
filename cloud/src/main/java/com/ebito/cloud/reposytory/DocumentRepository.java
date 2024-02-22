package com.ebito.cloud.reposytory;

import com.ebito.cloud.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    /**
     * Запрос для получения всех документов по клиенту. Документы будут отсортированы по убыванию id.
     *
     * @param clientId - id клиента
     * @return - список документов клиента.
     */
    @Query("select d from DocumentEntity d where d.clientId = :clientId" +
            " ORDER BY d.id DESC ")
    List<DocumentEntity> findAllByClientId(@Param("clientId") String clientId);

}