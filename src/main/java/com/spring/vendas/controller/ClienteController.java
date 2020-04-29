package com.spring.vendas.controller;

import java.util.Optional;

import com.spring.vendas.entity.Cliente;
import com.spring.vendas.repository.ClienteRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping(value ="/{id}")
    /**O response Body serve para retornar um objeto JSON */
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id){

        /**Retorna uma instancia de Optional porque pode existir ou nao um cliente */
        Optional<Cliente> cliente =  clienteRepository.findById(id);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value="/")
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente) {
       Cliente clienteCreated = clienteRepository.save(cliente); 
       return ResponseEntity.ok(clienteCreated);
    }
    
    @DeleteMapping(value ="/{id}")
    /**O response Body serve para retornar um objeto JSON */
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id){

        /**Retorna uma instancia de Optional porque pode existir ou nao um cliente */
        Optional<Cliente> cliente =  clienteRepository.findById(id);
        if(cliente.isPresent()){
            /**O .get() eh porque cliente eh um Optional */
            clienteRepository.delete(cliente.get());
            /**No content eh o status que deve retornar quando se faz a remocao */
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value ="/{id}")
    /**O response Body serve para retornar um objeto JSON */
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente){

       return clienteRepository
                    .findById(id)
                    /**Esse map eh para ele criar uma instancia que ira substituir o cliente caso ele exista */
                    .map(clienteExistente ->{
                        /**So foi setado no ID para que todos os campos referentes 
                         * ao cliente encontrado seja substituido e, ao inves de substituir campo por campo,
                         * ele ja ira substituir todos
                         */
                        cliente.setId(clienteExistente.getId());
                        clienteRepository.save(cliente);
                        /**Todo map deve retornar um Objeto, que neste caso eh um ResponseEntity
                         * e , se tudo ta certo, ele volta um noContent
                         */
                        return ResponseEntity.noContent().build();
                        /**Caso ele nao encontre o cliente pelo findById, ele ira retornar um ResponseEntity not FOund */
                    }).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro,matcher);
        List<Cliente> lista = clienteRepository.findAll(example);
        return ResponseEntity.ok(lista);

    }
}