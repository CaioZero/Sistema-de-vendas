package com.spring.vendas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter @Setter @NoArgsConstructor
public class Cliente {

    /**A @id define o primary key 
     * @generatedvalue eh o auto_increment
     * @Column nao eh obrigatorio, mas pode colocar se quiser trocar o nome
    */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;

    public Cliente(Integer id, String nome) {
        this.nome = nome;
        this.id = id;
    }

    public Cliente(String nome) {
        this.nome = nome;
    }
    
    
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }
}