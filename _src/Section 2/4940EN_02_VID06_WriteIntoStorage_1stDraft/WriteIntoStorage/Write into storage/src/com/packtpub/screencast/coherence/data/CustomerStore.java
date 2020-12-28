package com.packtpub.screencast.coherence.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.h2.*;
import org.h2.jdbcx.JdbcDataSource;

import com.packtpub.screencast.coherence.customers.Customer;
import com.tangosol.net.cache.AbstractCacheLoader;
import com.tangosol.net.cache.CacheStore;

public class CustomerStore implements CacheStore {
	private DataSource dataSource;

	public CustomerStore() {
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

			ps.setString(1, String.valueOf(key));
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
				stmt.executeUpdate("INSERT INTO LOGS VALUES('" + key.toString()
						+ "',SYSTIMESTAMP)");
				stmt.close();
				// System.out.println("CACHE LOADER:Customer object whose key value is "+key.toString()+" has been retrieved from the database to cache.");
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

	@Override
	public Map loadAll(Collection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void erase(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eraseAll(Collection arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void store(Object key, Object value) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;

		Customer customer = (Customer) value;

		try {
			conn = dataSource.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (load(key) != null) {
			try {
				ps = conn.prepareStatement("UPDATE CUSTOMERS SET "
						+ "FIRSTNAME=?, " + "LASTNAME=?," + "COUNTRY=?,"
						+ "ZIPCODE=?," + "CITIZENNO=?," + "AGE=? WHERE ID=?");
				ps.setString(1, customer.getFirstName());
				ps.setString(2, customer.getLastName());
				ps.setString(3, customer.getCountry());
				ps.setInt(4, customer.getZipCode());
				ps.setString(5, customer.getCitizenNo());
				ps.setInt(6, customer.getAge());
				ps.executeUpdate();
				conn.commit();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				ps = conn
						.prepareStatement("INSERT INTO CUSTOMERS VALUES(?,?,?,?,?,?,?)");
				ps.setString(1, customer.getId());
				ps.setString(2, customer.getFirstName());
				ps.setString(3, customer.getLastName());
				ps.setString(4, customer.getCountry());
				ps.setInt(5, customer.getZipCode());
				ps.setString(6, customer.getCitizenNo());
				ps.setInt(7, customer.getAge());
				ps.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void storeAll(Map arg0) {
		// TODO Auto-generated method stub

	}
}
