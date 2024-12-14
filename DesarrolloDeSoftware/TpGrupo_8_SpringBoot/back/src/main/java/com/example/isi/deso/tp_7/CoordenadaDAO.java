package com.example.isi.deso.tp_7;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadaDAO extends JpaRepository<Coordenada, Long> {
    Coordenada findByLatitudAndLongitud(double latitud, double longitud);
}
