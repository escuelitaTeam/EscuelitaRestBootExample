package com.escuelita.rest.service;

import java.util.List;

import com.escuelita.rest.model.Representative;

public interface OrdersService {

	List<Representative> listAllRepresentative();

	Representative findByAccount(String account);

	boolean isRepresentativeExist(Representative user);

	void saveRepresentative(Representative rep);

	void updateRepresentative(Representative rep);

	void deleteRepresentativeByAccount(String account);

	void deleteAllRepresentatives();

}
