package me.dio.spring_pet_api.domain.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {
    private Long id;
    private LocalDate dataHora;
    private PetDTO pet;
    private VeterinarioDTO veterinario;

    public AgendamentoDTO(Long id, LocalDate dataHora) {
        this.id = id;
        this.dataHora = dataHora;
    }

}
