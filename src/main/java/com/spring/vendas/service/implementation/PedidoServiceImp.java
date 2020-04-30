package com.spring.vendas.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.vendas.dto.ItemPedidoDTO;
import com.spring.vendas.dto.PedidoDTO;
import com.spring.vendas.entity.Cliente;
import com.spring.vendas.entity.ItemPedido;
import com.spring.vendas.entity.Pedido;
import com.spring.vendas.entity.Produto;
import com.spring.vendas.exception.RegraDeNegocioException;
import com.spring.vendas.repository.ClienteRepository;
import com.spring.vendas.repository.ItemPedidoRepository;
import com.spring.vendas.repository.PedidoRepository;
import com.spring.vendas.repository.ProdutoRepository;
import com.spring.vendas.service.PedidoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImp implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    /**A Annotation transacitional vai garantir que se caso aconteca algum erro, ira dar um rollback */
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        /**Aqui eu irei obter o cliente pelo ID, tendo em vista que 
         * o objeto PedidoDTO nao guarda o cliente, mas sim somente o ID atraves do getCliente
         * Entao esse getCliente() sera utilizado para achar o cliente atraves de seu ID
         */
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository.findById(idCliente)
                        /**Este erro ocorrera quando for enviado um ID de um cliente que NAO existe */
                         .orElseThrow(()-> new RegraDeNegocioException("Codigo de cliente invalido!"));

        /**Aqui eh onde se cria o pedido e seta os valores */
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedido =  converterItens(pedido, dto.getItems());
        /**Aqui ssalva um pedido */
        pedidoRepository.save(pedido);

        /**Aqui salva quantos itens forem */
        itemPedidoRepository.saveAll(itemPedido);
        pedido.setItens(itemPedido);
        return pedido;
    }
    /**metodo para converter os itemPedidoDTO em um ItemPedido */
    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraDeNegocioException("Nao foi possivel realizar um pedido sem itens");
        }

        return items
                .stream()
                .map( dto ->{
                    /**O dto.getProduto retorna um inteiro referente ao Id do produto */
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository.findById(idProduto)
                                     .orElseThrow(()-> new RegraDeNegocioException("Codigo de produto invalido! " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }


}