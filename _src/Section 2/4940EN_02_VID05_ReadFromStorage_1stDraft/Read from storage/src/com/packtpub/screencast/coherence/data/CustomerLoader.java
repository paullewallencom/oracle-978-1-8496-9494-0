package com.packtpub.screencast.coherence.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.h2.*;
import org.h2.jdbcx.JdbcDataSource;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.net.cache.AbstractCacheLoader;

public class CustomerLoader extends AbstractCacheLoader {
	private DataSource dataSource;

	public CustomerLoader() {
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:tcp://localhost/~/test");
		ds.setUser("sa");
		ds.setPassword("");

		this.dataSource = ds;

	}

	public Object load(Object key) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = null;
		Statement stmt = null;

		try {
			conn = dataSource.getConnection();
			ps = conn
					.prepareStatement("SELECT ID,FIRSTNAME,LASTNAME,COUNTRY,ZIPCODE,CITIZENNO,AGE FROM CUSTOMERS WHERE ID=?");
			
			ps.setString(1, (String) key);
			rs = ps.executeQuery();
			if (rs.next()) {
				customer = new Customer();
				customer.setId(rs.getString("ID"));
				customer.setFirstName(rs.getString("FIRSTNAME"));
				customer.setLastName(rs.getString("LASTNAME"));
				customer.setCountry(rs.getString("COUNTRY"));
				customer.setZipCode(rs.getInt("ZIPCODE"));
				customer.setCitizenNo(rs.getString("CITIZENNO"));
				customer.setAge(rs.getInt("AGE"));
				
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO LOGS VALUES('"+key.toString()+"',SYSTIMESTAMP)");
				stmt.close();
				//System.out.println("CACHE LOADER:Customer object whose key value is "+key.toString()+" has been retrieved from the database to cache.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return customer;

	}
}
