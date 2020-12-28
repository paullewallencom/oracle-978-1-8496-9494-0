package com.packtpub.screencast.coherence.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.h2.jdbcx.JdbcDataSource;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;

public class CachePreLoader {

	public static void main(String[] args) {
	    String dummy;
        Scanner user_input = new Scanner( System.in );
        
		NamedCache cache = CacheFactory.getCache("customers");

		Set<Member> memberSet = CacheFactory.getCluster().getMemberSet();
		Member thisMember = CacheFactory.getCluster().getLocalMember();
		int count = 0;

		System.out.println("Total Cache Server:" + memberSet.size());
		System.out.println("This cache server's id:" + thisMember.getId());

		try {
			count = loadFromDatabase(cache, thisMember.getId(),
					memberSet.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Cache is loaded by cache server "
				+ thisMember.getId() + ", " + count + " objects added.");
		System.out.println("Cache size:" + cache.size());
		dummy = user_input.next();
	}

	private static int loadFromDatabase(NamedCache cache, int memberId,
			int numberOfMembers) throws SQLException {
		// TODO Auto-generated method stub
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:tcp://localhost/~/test");
		ds.setUser("sa");
		ds.setPassword("");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = null;
		Map<String, Customer> buffer = new HashMap<String, Customer>();
		conn = ds.getConnection();
		ps = conn
				.prepareStatement("SELECT ID,FIRSTNAME,LASTNAME,COUNTRY,ZIPCODE,CITIZENNO,AGE FROM CUSTOMERS WHERE MOD(ID,?) = ?");

		ps.setInt(1, numberOfMembers);
		ps.setInt(2, (memberId - 1));

		rs = ps.executeQuery();
		while (rs.next()) {
			customer = new Customer();
			customer.setId(rs.getString("ID"));
			customer.setFirstName(rs.getString("FIRSTNAME"));
			customer.setLastName(rs.getString("LASTNAME"));
			customer.setCountry(rs.getString("COUNTRY"));
			customer.setZipCode(rs.getInt("ZIPCODE"));
			customer.setCitizenNo(rs.getString("CITIZENNO"));
			customer.setAge(rs.getInt("AGE"));
			buffer.put(customer.getId(), customer);
		}

		cache.putAll(buffer);

		rs.close();
		ps.close();
		conn.close();
		return buffer.size();
	}
}
