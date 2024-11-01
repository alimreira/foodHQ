package com.eatwell.foodHQ;

import com.eatwell.foodHQ.payload.paginationCustomResponse.ReservationPaginationResponse;
import javax.persistence.Column;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class FoodHqApplication {
	@Bean
	public ModelMapper modelMapper () {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(FoodHqApplication.class, args);
	}

}
