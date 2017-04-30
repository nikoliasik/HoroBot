/*
	HoroBot - An open-source Discord bot
	Copyright (C) 2017	WiNteR

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.winter.horobot.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Permission {

	public static void createFile(File file, IGuild guild) {
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void addUserToFile(File file, IUser user) {
		try {

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean hasUserPermission(IGuild guild, IUser user, String permission) {
		try {
			File file = new File("guilds" + File.separator + guild.getStringID() + ".xml");
			if(!file.exists())
				createFile(file, guild);

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.normalize();

			NodeList users = doc.getElementsByTagName("user");
			for(int i = 0; i < users.getLength(); i++) {
				Node node = users.item(i);
				if(node.getNodeName().equals(user.getStringID())) {
					Element element = (Element) node;
					NodeList perms = element.getElementsByTagName("permissions");
					for(int x = 0; x < perms.getLength(); x++) {
						Element perm = (Element) perms.item(x);
						if(perm.getTextContent().equals(permission))
							return true;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
