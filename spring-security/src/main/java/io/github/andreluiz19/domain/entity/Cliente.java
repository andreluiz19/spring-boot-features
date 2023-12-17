package io.github.andreluiz19.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity // Quando usa o @Entity, o JPA entende que todas as propriedades são colunas da BASE, e seus nomes são os mesmos da BASE
@Table(name = "cliente")
// Se o nome da Entidade for igual ao da Tabela na Base de Dados, não é obrigatório usar @Table, caso contrário, é obrigatório
// Ex: se o nome da minha tabela fosse TB_CLIENTE, deveria usar: @Table(name = "TB_CLIENTE)
// Também existem outras definições para o @Table, como o (schema = "MY_SCHEMA")
public class Cliente {

    @Id // Define a Primary Key da entidade.
    //@GeneratedValue(strategy = GenerationType.AUTO) // Represanta o Autoincrement
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para o MySql
    @Column(name = "id") // Se a propriedade for o mesmo nome da coluna na BASE, não precisa do @Column
    private Integer id;

    @Column(name = "nome", length = 100) // Se a propriedade for o mesmo nome da coluna na BASE, não precisa do @Column
    @NotEmpty(message = "{campo.nome.obrigatorio}") // null ou string vazia
    private String nome;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @JsonIgnore // Ignora esta propriedade no retorno da requisição
    @OneToMany(mappedBy = "cliente") // Um cliente para muitos pedidos - Nome da entidade desse relacionamento, no Pedido
    private Set<Pedido> pedidos; // Set não aceita itens repetidos

    public Cliente() {
    }

    public Cliente(Integer id, String nome) {
        this.nome = nome;
        this.id = id;
    }

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY) // Nome da entidade desse relacionamento, na Pedido
    public Set<Pedido> getPedidos() { // EAGER: toda vez que buscar um Cliente vai trazes os Pedidos também
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
