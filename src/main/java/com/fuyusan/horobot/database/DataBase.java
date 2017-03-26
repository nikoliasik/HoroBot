package com.fuyusan.horobot.database;

import com.fuyusan.horobot.core.Config;

import java.sql.*;

public class DataBase {

	private static Connection con;

	public static void createGuildSchema() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE SCHEMA IF NOT EXISTS guilds;";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createGuildTable() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS guilds.guild (" +
										"id TEXT PRIMARY KEY NOT NULL," +
										"language TEXT NOT NULL," +
										"prefix TEXT," +
										"welcome TEXT);";
			statement.executeUpdate(sql);
			statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createChannelSchema() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE SCHEMA IF NOT EXISTS channels;";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createChannelTable() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS channels.channel (" +
										"id TEXT PRIMARY KEY NOT NULL," +
										"mod TEXT DEFAULT 'none');";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createQueueSchema() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE SCHEMA IF NOT EXISTS queue;";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createQueueTable() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS queue.queued (" +
					"id TEXT PRIMARY KEY NOT NULL," +
					"mod TEXT DEFAULT 'none');";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertGuild(String guildID) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("INSERT INTO guilds.guild (id, language, prefix, welcome) VALUES ('%s', 'en', '.horo', 'Welcome~!') ON CONFLICT DO NOTHING;",
										guildID);
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateGuild(String guildID, String index, String value) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("UPDATE guilds.guild SET %s='%s' WHERE id='%s';",
										index,
										value,
										guildID);
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertChannel(String channelID, String mod) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("INSERT INTO channels.channel (id, mod) VALUES ('%s', '%s') ON CONFLICT DO NOTHING;",
										channelID,
										mod);
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateChannel(String channelID, String value) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("UPDATE channels.channel SET mod='%s' WHERE id='%s';",
					value,
					channelID);
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String guildQuery(String guildID, String index) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("SELECT * FROM guilds.guild WHERE id='%s';", guildID);
			ResultSet set = statement.executeQuery(sql);
			if(set.next()) {
				return set.getString(index);
			} else {
				set.close();
				statement.close();
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "en";
	}

	public static String channelQuery(String channelID) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("SELECT * FROM channels.channel WHERE id='%s';", channelID);
			ResultSet set = statement.executeQuery(sql);
			while(set.next()) {
				return set.getString("mod");
			}
			set.close();
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "none";
	}

	public static void deleteGuild(String guildID) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("DELETE FROM guilds.guild WHERE id='%s';", guildID);
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteChannel(String channelID) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("DELETE FROM channels.channel WHERE id='%s';", channelID);
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", Config.dataBasePassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
