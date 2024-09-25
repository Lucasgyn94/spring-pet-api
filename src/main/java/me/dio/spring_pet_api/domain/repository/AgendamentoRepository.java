package me.dio.spring_pet_api.domain.repository;

import me.dio.spring_pet_api.domain.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.pet JOIN FETCH a.veterinario JOIN FETCH a.servicos WHERE a.id = :id")
    Optional<Agendamento> findByIdWithDetails(@Param("id") Long id);
}
