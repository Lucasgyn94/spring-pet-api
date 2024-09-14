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
public class PetDTO {
    private Long id;
    private String nome;
    private String tipo;
    private ClienteDTO cliente;
    private List<ServicoDTO> servicos;
    private List<AgendamentoDTO> agendamentos;


    public PetDTO(Long id, String nome, String tipo, List<ServicoDTO> servicos, List<AgendamentoDTO> agendamentos) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.servicos = servicos;
        this.agendamentos = agendamentos;
    }
}
