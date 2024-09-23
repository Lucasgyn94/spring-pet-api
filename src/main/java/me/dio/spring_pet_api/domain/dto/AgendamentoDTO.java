package me.dio.spring_pet_api.domain.dto;

import lombok.*;

import java.time.LocalDate;


public record AgendamentoDTO(
        Long id,
        LocalDate dataHora,
        PetDTO petDTO,
        VeterinarioDTO veterinarioDTO
) {

}
