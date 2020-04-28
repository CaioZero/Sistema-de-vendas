package com.spring.vendas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.spring.vendas.entity.Cliente;
import com.spring.vendas.entity.Pedido;
import com.spring.vendas.repository.ClienteRepository;
import com.spring.vendas.repository.PedidoRepository;

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
	public CommandLineRunner init (
		@Autowired ClienteRepository clienteRepository,
		@Autowired PedidoRepository pedidoRepository){
		return args->{
			System.out.println("Salvando os Clientes");
			Cliente fulano = new Cliente("Caio");
			clienteRepository.save(fulano);

			Pedido p = new Pedido();
			p.setCliente(fulano);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(100));
			pedidoRepository.save(p);

			// Cliente cliente = clienteRepository.findClienteFetchPedidos(fulano.getId());
			// System.out.println(cliente);
			// System.out.println(cliente.getPedidos());

			pedidoRepository.findByCliente(fulano).forEach(System.out::println);

			// boolean exist = clienteRepository.existsByNome("Caio");
			// System.out.println("Existe um cliente com o nome Caio? "+exist);

			// List<Cliente> result = clienteRepository.encontrarPorNomeSql("Caio");
			// result.forEach(System.out::println);

			// List<Cliente> result = clienteRepository.encontrarPorNome("Caio");
			// result.forEach(System.out::println);
			

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
