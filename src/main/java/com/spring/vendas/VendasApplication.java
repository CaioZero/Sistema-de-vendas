package com.spring.vendas;

import java.util.List;

import com.spring.vendas.entity.Cliente;
import com.spring.vendas.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
public class VendasApplication {

	
	@Value("${application.name}")
	private String applicationName;

	@GetMapping("/hello")
	public String HelloWorld() {
		return applicationName;
	}

	@Bean
	public CommandLineRunner init (@Autowired ClienteRepository clienteRepository){
		return args->{
			clienteRepository.salvar(new Cliente("Caio"));
			clienteRepository.salvar(new Cliente("Sandy"));
			List<Cliente> todosClientes = clienteRepository.obterTodos();
			todosClientes.forEach(System.out::println);

			todosClientes.forEach(c->{
				c.setNome(c.getNome()+" atualizado");
				clienteRepository.atualizar(c);
			});
			
			System.out.println("Atualizando clientes");
			todosClientes = clienteRepository.obterTodos();
			todosClientes.forEach(System.out::println);

			System.out.println("Buscando clientes");
            clienteRepository.buscarPorNome("Cli").forEach(System.out::println);

           System.out.println("deletando clientes");
           clienteRepository.obterTodos().forEach(c -> {
               clienteRepository.deletar(c);
           });

			System.out.println("Buscando clientes depois do delete");
            todosClientes = clienteRepository.obterTodos();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
            }else{
                todosClientes.forEach(System.out::println);
            }

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
