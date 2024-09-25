package me.dio.spring_pet_api.domain.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import me.dio.spring_pet_api.domain.model.StatusAgendamento;

import java.time.LocalDateTime;
import java.util.List;


public record AgendamentoDTO(
        Long id,

        @NotNull
        @FutureOrPresent
        LocalDateTime dataHora,

        @NotNull
        Long petId,

        @NotNull
        Long veterinarioId,

        @NotEmpty
        List<Long> servicoIds,

        @NotNull
        StatusAgendamento status
) { }