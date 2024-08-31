package com.e_mail.item_post;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//TODO DTO для departure
//TODO добавление статуса


@SpringBootApplication
public class ItemPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemPostApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
