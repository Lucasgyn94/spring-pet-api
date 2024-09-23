package me.dio.spring_pet_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.dio.spring_pet_api.domain.model.Cliente;
import me.dio.spring_pet_api.domain.model.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SerializationTest {

    @Test
    public void testSerialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Zeca Silva");
        cliente.setTelefone("62987654521");

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setNome("Tampinha");
        pet.setTipo("Pitbull");
        pet.setCliente(cliente);

        cliente.setPets(List.of(pet));

        String clienteJson = objectMapper.writeValueAsString(cliente);
        System.out.println(clienteJson);

        assertNotNull(clienteJson);
    }
}