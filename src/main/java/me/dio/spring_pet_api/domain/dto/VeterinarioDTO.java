package me.dio.spring_pet_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VeterinarioDTO {
    private Long id;
    private String nome;
    private String especialidade;
    private List<AgendamentoDTO> agendamentos;


}
