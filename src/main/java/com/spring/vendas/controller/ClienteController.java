package com.spring.vendas.controller;

import com.spring.vendas.entity.Cliente;
import com.spring.vendas.repository.ClienteRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

/**Com a Adicao da Annotation @RestController, nao eh mais necessario 
 * a anotattion @ResponseBody, pois agora eh automatico
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping(value ="/{id}")
    /**O response Body serve para retornar um objeto JSON */
    //  @ResponseBody
    /** Aqui eh o seguinte,
     * se encontrar o cliente ele ira retornar algo para o ResponseBody,
     * caso contrario, ira lancar uma excecao dizendo que o cliente nao foi encontrado
     */
    public Cliente getClienteById(@PathVariable Integer id){
            return clienteRepository
                    .findById(id)
                    .orElseThrow(()-> 
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente nao encontrado"));
    }

    @PostMapping(value="/")
    /**A resposta HTTP de status ok para Post eh a 201 */
    @ResponseStatus(HttpStatus.CREATED)
 //   @ResponseBody
    public Cliente save(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @DeleteMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id){
        clienteRepository.findById(id)
                         .map(cliente -> {
                             clienteRepository.delete(cliente);
                             return cliente;
                        }).orElseThrow(()-> 
                             new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente nao encontrado"));
      
    }

    @PutMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                      @RequestBody Cliente cliente){

       clienteRepository.findById(id)
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
                        return clienteExistente;
                        /**Caso ele nao encontre o cliente pelo findById, ele ira retornar um ResponseEntity not FOund */
                    }).orElseThrow(()-> 
                     new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente nao encontrado"));
    }

    @GetMapping("/")
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro,matcher);
        return clienteRepository.findAll(example);

    }
}