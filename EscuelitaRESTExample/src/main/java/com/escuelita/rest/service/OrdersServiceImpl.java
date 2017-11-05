package com.escuelita.rest.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.escuelita.rest.model.Representative;


@Service("ordersService")
public class OrdersServiceImpl implements OrdersService{

   private static List<Representative> representatives;	
	
   static {
	  initRepresentatives();
	   
   }
   
	public List<Representative> listAllRepresentative() {
		return representatives;
	}

	private static void initRepresentatives() {
			
		representatives= new ArrayList<Representative>();
		representatives.add(new Representative("00011101","Perengana Rodriguez","Someplace", "0610"));
		representatives.add(new Representative("00011102","Tomasa Pérez","Anyplace", "0610"));
		representatives.add(new Representative("00011103","Silvania López","ThisPlace", "0610"));
		
	}

	public Representative findByAccount(String account) {
		for(Representative rep : representatives){
			if(rep.getAccount().equals(account)){
					return rep;
			}
		}
		return null;
	}
	

	public void saveRepresentative(Representative rep) {
		
		representatives.add(rep);
	}

	public void updateRepresentative(Representative rep) {
		int index = representatives.indexOf(rep);
		representatives.set(index, rep);
	}

	public void deleteRepresentativeByAccount(String account) {
		
		for (Iterator<Representative> iterator = representatives.iterator(); iterator.hasNext(); ) {
			Representative rep = iterator.next();
		    if (rep.getAccount().equals(account)) {
		        iterator.remove();
		    }
		}
	}
	public Representative findByName(String name) {
		for(Representative rep : representatives){
			if(rep.getName().equalsIgnoreCase(name)){
				return rep;
			}
		}
		return null;
	}

	public boolean isRepresentativeExist(Representative rep) {
		return findByName(rep.getName())!=null;
	}
	
	public void deleteAllRepresentatives(){
		representatives.clear();
	}





}
