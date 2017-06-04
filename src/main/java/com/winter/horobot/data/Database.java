package com.winter.horobot.data;

import com.winter.horobot.Main;
import com.winter.horobot.exceptions.NoResultsException;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;

public class Database {

	/**
	 * The connection pool
	 */
	private static PGPoolingDataSource poolingDataSource;

	/**
	 * Executes an update to the database
	 * @param sql String containing an SQL statement to be executed.
	 */
	public static void set(String sql, Object... params) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = poolingDataSource.getConnection();
			statement = con.prepareStatement(sql);
			for (int i = 1; i <= params.length; i++) {
				if (params[i] instanceof String)
					statement.setString(i, (String) params[i]);
				else if (params[i] instanceof Integer)
					statement.setInt(i, (int) params[i]);
				else if (params[i] instanceof Boolean)
					statement.setBoolean(i, (boolean) params[i]);
				else if (params[i] instanceof Long)
					statement.setLong(i, (long) params[i]);
				else if (params[i] instanceof Double)
					statement.setDouble(i, (double) params[i]);
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignored) { }
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignored) { }
			}
		}
	}

	/**
	 * Execute a query to the database
	 * @param sql The SQL query string following a prepared statement format
	 * @param params The parameters to fill the query with
	 * @return The resulting ResultSet
	 * @throws NoResultsException If there were no results
	 */
	public static ResultSet get(String sql, Object... params) throws NoResultsException {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		try {
			con = poolingDataSource.getConnection();
			statement = con.prepareStatement(sql);
			for (int i = 1; i <= params.length; i++) {
				if (params[i] instanceof String)
					statement.setString(i, (String) params[i]);
				else if (params[i] instanceof Integer)
					statement.setInt(i, (int) params[i]);
				else if (params[i] instanceof Boolean)
					statement.setBoolean(i, (boolean) params[i]);
				else if (params[i] instanceof Long)
					statement.setLong(i, (long) params[i]);
				else if (params[i] instanceof Double)
					statement.setDouble(i, (double) params[i]);
			}
			set = statement.executeQuery();
			if (!set.next())
				throw new NoResultsException("Index not found in database");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (set != null) {
				try {
					set.close();
				} catch (SQLException ignored) { }
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignored) { }
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignored) { }
			}
		}
		return set;
	}

	/**
	 * Fills up the connection pool with valid connections to the database
	 */
	public static void connect() {
		poolingDataSource = new PGPoolingDataSource();
		poolingDataSource.setDataSourceName("DataSource");
		poolingDataSource.setServerName("localhost");
		poolingDataSource.setDatabaseName("postgres");
		poolingDataSource.setUser("postgres");
		poolingDataSource.setPassword(Main.config.get(Main.ConfigValue.DB_PASS));
		poolingDataSource.setMaxConnections(1000);
	}
}