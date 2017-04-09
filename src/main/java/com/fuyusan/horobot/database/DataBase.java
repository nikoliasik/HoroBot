package com.fuyusan.horobot.database;

import com.fuyusan.horobot.core.Config;
import com.fuyusan.horobot.profile.ProfileTemplate;
import com.fuyusan.horobot.wolf.WolfTemplate;
import sx.blah.discord.handle.obj.IUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

	public static void createWolfSchema() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE SCHEMA IF NOT EXISTS wolves;";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createWolfTable() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS wolves.wolf(" +
					"id TEXT PRIMARY KEY NOT NULL," +
					"name TEXT NOT NULL DEFAULT 'Wolf'," +
					"level INTEGER NOT NULL DEFAULT 1," +
					"hunger INTEGER NOT NULL DEFAULT 0," +
					"maxHunger INTEGER NOT NULL DEFAULT 8," +
					"fedTimes INTEGER NOT NULL DEFAULT 0," +
					"background TEXT NOT NULL DEFAULT 'None'," +
					"hat TEXT NOT NULL DEFAULT 'None'," +
					"body TEXT NOT NULL DEFAULT 'None'," +
					"paws TEXT NOT NULL DEFAULT 'None'," +
					"tail TEXT NOT NULL DEFAULT 'None'," +
					"shirt TEXT NOT NULL DEFAULT 'None'," +
					"nose TEXT NOT NULL DEFAULT 'None'," +
					"eye TEXT NOT NULL DEFAULT 'None'," +
					"neck TEXT NOT NULL DEFAULT 'None');";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createUserSchema() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE SCHEMA IF NOT EXISTS users;";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createItemTable() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS users.item(" +
					"id TEXT NOT NULL," +
					"item TEXT NOT NULL," +
					"PRIMARY KEY (id, item));";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void createUserTable() {
		try {
			Statement statement = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS users.user(" +
					"id TEXT PRIMARY KEY NOT NULL," +
					"description TEXT NOT NULL DEFAULT 'I like trains'," +
					"level INTEGER NOT NULL DEFAULT 1," +
					"xp INTEGER NOT NULL DEFAULT 0," +
					"maxXp INTEGER NOT NULL DEFAULT 300," +
					"foxCoins INTEGER NOT NULL DEFAULT 0," +
					"background TEXT NOT NULL DEFAULT 'None');";
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertUser(IUser user) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format(
					"INSERT INTO users.user (id) VALUES (%s) ON CONFLICT DO NOTHING;",
					user.getID());
			statement.executeUpdate(sql);
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateUser(IUser user, String index, Object value) {
		try {
			PreparedStatement statement = con.prepareStatement("UPDATE users.user SET " + index + " = ? WHERE id = ?");
			if(value instanceof String) statement.setString(1, (String) value);
			if(value instanceof Integer) statement.setInt(1, (int) value);
			statement.setString(2, user.getID());
			statement.executeUpdate();
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ProfileTemplate queryUser(IUser user) {
		ProfileTemplate template = null;
		try {
			Statement statement = con.createStatement();
			String sql = String.format(
					"SELECT * FROM users.user WHERE id='%s'",
					user.getID());
			ResultSet set = statement.executeQuery(sql);
			while(set.next()) {
				template = new ProfileTemplate(
						user.getName(),
						set.getString("description"),
						set.getInt("level"),
						set.getInt("xp"),
						set.getInt("maxXp"),
						set.getInt("foxCoins"),
						set.getString("background"));
			}
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return template;
	}

	public static int queryRank(IUser user) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format(
					"SELECT id, level, rank() OVER (ORDER BY level DESC) FROM users.user;",
					user.getID());
			ResultSet set = statement.executeQuery(sql);
			while(set.next()) {
				if(set.getString("id").equals(user.getID())) {
					return set.getInt("rank");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void insertItem(IUser user, String item) {
		try {
			String sql = "INSERT INTO users.item (id, item) VALUES (?, ?) ON CONFLICT DO NOTHING;";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, user.getID());
			statement.setString(2, item);
			statement.executeUpdate();
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String queryItem(IUser user, String item) {
		try {
			String sql = "SELECT item FROM users.item WHERE id=? AND item=?;";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, user.getID());
			statement.setString(2, item);
			ResultSet set = statement.executeQuery();
			if(!set.next())
				return null;
			return set.getString("item");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> queryItems(IUser user) {
		try {
			String sql = "SELECT item FROM users.item WHERE id=?;";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, user.getID());
			ResultSet set = statement.executeQuery();
			List<String> list = new ArrayList<>();
			while (set.next()) {
				list.add(String.valueOf(set.getString("item")));
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void insertWolf(IUser user) {
		try {
			String sql = "INSERT INTO wolves.wolf (id) VALUES (?) ON CONFLICT DO NOTHING;";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, user.getID());
			statement.executeUpdate();
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateWolf(IUser user, String index, Object value) {
		try {
			PreparedStatement statement = con.prepareStatement("UPDATE wolves.wolf SET " + index + " = ? WHERE id = ?");
			if(value instanceof String) statement.setString(1, (String) value);
			if(value instanceof Integer) statement.setInt(1, (int) value);
			statement.setString(2, user.getID());
			statement.executeUpdate();
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertGuild(String guildID) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format(
					"INSERT INTO guilds.guild (id, language, prefix, welcome) VALUES ('%s', 'en', '.horo', 'Welcome~!') ON CONFLICT DO NOTHING;",
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
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String channelQuery(String channelID) {
		try {
			Statement statement = con.createStatement();
			String sql = String.format("SELECT * FROM channels.channel WHERE id='%s';", channelID);
			ResultSet set = statement.executeQuery(sql);
			while(set.next()) {
				String mod = set.getString("mod");
				set.close();
				statement.close();
				return mod;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "None";
	}

	public static WolfTemplate wolfQuery(IUser user) {
		WolfTemplate template = null;
		try {
			Statement statement = con.createStatement();
			String sql = String.format("SELECT * FROM wolves.wolf WHERE id='%s'", user.getID());
			ResultSet set = statement.executeQuery(sql);
			while(set.next()) {
				template = new WolfTemplate(
						set.getString("name"),
						set.getInt("level"),
						set.getInt("hunger"),
						set.getInt("maxHunger"),
						set.getInt("fedTimes"),
						set.getString("background"),
						set.getString("hat"),
						set.getString("body"),
						set.getString("paws"),
						set.getString("tail"),
						set.getString("shirt"),
						set.getString("nose"),
						set.getString("eye"),
						set.getString("neck")
				);
			}
			set.close();
			statement.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return template;
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

	public static void deleteWolf(IUser user) {
		try {
			PreparedStatement statement = con.prepareStatement("DELETE FROM wolves.wolf WHERE id=?;");
			statement.setString(1, user.getID());
			statement.executeUpdate();
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
