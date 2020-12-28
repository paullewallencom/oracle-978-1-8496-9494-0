package com.packtpub.screencast.coherence.cache;

import java.util.Scanner;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheGet {

	public static void main(String[] args)
	{
        NamedCache cache = CacheFactory.getCache("customers");
        System.out.println("Cache size:"+cache.size());
        Customer customer;
        
        String dummy;
        Scanner user_input = new Scanner( System.in );
        
        while ( true )
        {
        	System.out.println("Enter an ID (0-"+cache.size()+"):");
            dummy = user_input.next( );
            customer = (Customer) cache.get(dummy);
            
            System.out.println("Customer information");
            System.out.println("--------------------");
            System.out.println("Name:"+customer.getFirstName());
            System.out.println("SurName:"+customer.getLastName());
            System.out.println("Country:"+customer.getCountry());
            System.out.println("ZipCode:"+customer.getZipCode());
            System.out.println("Citizen No:"+customer.getCitizenNo());
            System.out.println("Age:"+customer.getAge());
            System.out.println("--------------------");
            
            
        }
        
        
        
	}
}
