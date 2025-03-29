package com.eon.hierbasanta;

import com.eon.hierbasanta.model.Productos;
import com.eon.hierbasanta.repository.PedidoRepository;
import com.eon.hierbasanta.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class HierbaSantaApplication {


	public static void main(String[] args) {
		SpringApplication.run(HierbaSantaApplication.class, args);
	}

}
