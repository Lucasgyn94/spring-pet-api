package me.dio.spring_pet_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public record VeterinarioDTO (
        Long id,
        String nome,
        String especialidade,
        List<AgendamentoDTO> agendamentos
) {


}
