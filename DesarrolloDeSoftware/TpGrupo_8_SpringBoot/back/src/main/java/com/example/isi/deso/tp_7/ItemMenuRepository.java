package com.example.isi.deso.tp_7;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemMenuRepository extends JpaRepository<ItemMenu, Long> {
    @Query("SELECT i FROM ItemMenu i WHERE i.deleted = false")
    List<ItemMenu> findAllActive();

    @Query("SELECT i FROM ItemMenu i WHERE i.id = :id AND i.deleted = false")
    Optional<ItemMenu> findActiveById(@Param("id") Long id);
}


