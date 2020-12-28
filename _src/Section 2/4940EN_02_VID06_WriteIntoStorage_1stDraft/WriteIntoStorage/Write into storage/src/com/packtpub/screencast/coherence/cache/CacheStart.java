package com.packtpub.screencast.coherence.cache;

import java.util.Scanner;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class CacheStart {

	
	public static void main(String[] args)
	{
        NamedCache cache = CacheFactory.getCache("customers");
        System.out.println("Cache size:"+cache.size());
              
        System.out.println("To stop cache server type something and press enter:");
        
        String dummy;
        Scanner user_input = new Scanner( System.in );
        dummy = user_input.next( );
        
         
	}
}
