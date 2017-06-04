package com.winter.horobot.data;

import com.winter.horobot.Main;
import com.winter.horobot.exceptions.NoResultsException;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.HashMap;

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
	 */
	public static HashMap<String, Object> get(String sql, Object... params) {
		HashMap<String, Object> results = null;
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
			ResultSetMetaData md = set.getMetaData();
			int columns = md.getColumnCount();
			if (set.next())
				for (int i = 1; i <= columns; i++)
					results.put(md.getColumnName(i), set.getObject(i));
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
		return results;
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