package com.spring.vendas.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.spring.vendas.enums.*;

/**@Data equivale ao Getter, Setter, toString e EqualHashCode */
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pedido")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**Muitos pedidos para um mesmo cliente */
    @ManyToOne
    /**A coluna que esta sendo referenciada na tabela cliente */
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    /**Tamanho maximo de 20 caracteres com duas casas decimais de precisao */
    @Column(name = "total", precision =  20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", dataPedido='" + getDataPedido() + "'" +
            ", total='" + getTotal() + "'" +
            "}";
    }
}