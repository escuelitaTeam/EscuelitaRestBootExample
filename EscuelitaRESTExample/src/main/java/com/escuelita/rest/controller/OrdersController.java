package com.escuelita.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.escuelita.rest.model.Representative;
import com.escuelita.rest.service.OrdersService;
import com.escuelita.rest.util.CustomErrorType;


//Original: http://websystique.com/spring-boot/spring-boot-rest-api-example/
	
@RestController
@RequestMapping("/orders")
public class OrdersController {
	public static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

	@Autowired
	private OrdersService ordersService; 
	
	
	@RequestMapping(value = "/representative/", method = RequestMethod.GET)
	public ResponseEntity<List<Representative>> listAllRepresentative() {
		List<Representative> representatives = ordersService.listAllRepresentative();
		if (representatives.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Representative>>(representatives, HttpStatus.OK);
	}

	@RequestMapping(value = "/representative/{account}", method = RequestMethod.GET)
	public ResponseEntity<?> getRepresentative(@PathVariable("account") String account) {
		logger.info("Fetching Representative with id {}", account);
		Representative Representative = ordersService.findByAccount(account);
		if (Representative == null) {
			logger.error("Representative with account {} not found.", account);
			return new ResponseEntity(new CustomErrorType("Repre with account " + account 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Representative>(Representative, HttpStatus.OK);
	}

	// -------------------Create a Representative-------------------------------------------

	@RequestMapping(value = "/representative/", method = RequestMethod.POST)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public ResponseEntity<?> createRepresentative(@RequestBody Representative Representative, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Representative : {}", Representative);

		if (ordersService.isRepresentativeExist(Representative)) {
			logger.error("Unable to create. A Repre with name {} already exist", Representative.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Representative with name " + 
			Representative.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		ordersService.saveRepresentative(Representative);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/Representative/{account}").buildAndExpand(Representative.getAccount()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Representative ------------------------------------------------

	@RequestMapping(value = "/representative/{account}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRepresentative(@PathVariable("account") String account, @RequestBody Representative rep) {
		logger.info("Updating Representative with account {}", account);

		Representative currentRepresentative = ordersService.findByAccount(account);

		if (currentRepresentative == null) {
			logger.error("Unable to update. Representative with account {} not found.", account);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Representative with id " + account + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentRepresentative.setName(rep.getName());
		currentRepresentative.setAddress1(rep.getAddress1());
		currentRepresentative.setZone(rep.getZone());

		ordersService.updateRepresentative(currentRepresentative);
		return new ResponseEntity<Representative>(currentRepresentative, HttpStatus.OK);
	}

	// ------------------- Delete a Representative-----------------------------------------

	@RequestMapping(value = "/representative/{account}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRepresentative(@PathVariable("account") String account) {
		logger.info("Fetching & Deleting Representative with account {}", account);

		Representative Representative = ordersService.findByAccount(account);
		if (Representative == null) {
			logger.error("Unable to delete. Representative with account {} not found.", account);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Representative with account " + account + " not found."),
					HttpStatus.NOT_FOUND);
		}
		ordersService.deleteRepresentativeByAccount(account);
		return new ResponseEntity<Representative>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Representatives-----------------------------

	@RequestMapping(value = "/representative/", method = RequestMethod.DELETE)
	public ResponseEntity<Representative> deleteAllRepresentatives() {
		logger.info("Deleting All representatives");

		ordersService.deleteAllRepresentatives();
		return new ResponseEntity<Representative>(HttpStatus.NO_CONTENT);
	}
}
