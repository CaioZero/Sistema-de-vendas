package com.spring.vendas.controller;

import java.util.List;

import javax.validation.Valid;

import com.spring.vendas.entity.Produto;
import com.spring.vendas.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**Retornando todos os Produtos */
    @GetMapping(value = "/")
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,matcher);
        return produtoRepository.findAll(example);
    }

    @GetMapping(value = "/{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return produtoRepository
                .findById(id)
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto){
        return produtoRepository.save(produto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto){
        produtoRepository.findById(id)
                         .map(produtoExiste->{
                             produto.setId(produtoExiste.getId());
                             return produtoRepository.save(produto);
                         }).orElseThrow(()->
                             new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
            produtoRepository.findById(id)
                             .map(produtoExiste->{
                                 produtoRepository.delete(produtoExiste);
                                 return produtoExiste;
                             }).orElseThrow(()->
                             new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }
}