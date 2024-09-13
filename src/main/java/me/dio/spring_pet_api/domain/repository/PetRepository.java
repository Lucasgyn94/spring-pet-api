package me.dio.spring_pet_api.domain.repository;

import me.dio.spring_pet_api.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
