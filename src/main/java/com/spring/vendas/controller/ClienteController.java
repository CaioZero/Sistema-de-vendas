package com.spring.vendas.controller;

import java.util.Optional;

import com.spring.vendas.entity.Cliente;
import com.spring.vendas.repository.ClienteRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity delete(@PathVariable Integer id){

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

}