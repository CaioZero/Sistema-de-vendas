package com.spring.vendas.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.spring.vendas.dto.AtualizacaoStatusPedidoDTO;
import com.spring.vendas.dto.InformacaoItemPedidoDTO;
import com.spring.vendas.dto.InformacoesPedidoDTO;
import com.spring.vendas.dto.PedidoDTO;
import com.spring.vendas.entity.ItemPedido;
import com.spring.vendas.entity.Pedido;
import com.spring.vendas.enums.StatusPedido;
import com.spring.vendas.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;




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
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        /**Agora devo transformar o Dto para um modelo que possa ser persistido pelo servico*/
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById( @PathVariable Integer id ){
        return service
                .obterPedidoCompleto(id)
                .map( p -> converter(p) )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    /**PAra atualizar somente campos especificos, utiliza-se a requisicao PATCH, pois o PUT atualiza todo o objeto
     * e, neste caso, so eh necessario atualizar o campo Status
     */
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id ,
                             @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
}
    

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converter(pedido.getItens()))
                /**o .name() eh pq esta pegando um valor de uma classe enum e, assim que pega o valor, o metodo transforma
                 * o valor em string
                 */
                .status(pedido.getStatus().name())
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                            .builder().descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco())
                            .quantidade(item.getQuantidade())
                            .build()
        ).collect(Collectors.toList());
    }
}