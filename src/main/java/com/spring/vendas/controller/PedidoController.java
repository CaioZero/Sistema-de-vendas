package com.spring.vendas.controller;

import com.spring.vendas.dto.PedidoDTO;
import com.spring.vendas.entity.Pedido;
import com.spring.vendas.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    /**Retornar o ID do Pedido */
    public Integer save(@RequestBody PedidoDTO dto){
        /**Agora devo transformar o Dto para um modelo que possa ser persistido pelo servico*/
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
}