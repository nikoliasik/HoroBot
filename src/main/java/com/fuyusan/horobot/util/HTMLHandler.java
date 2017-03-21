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

package com.fuyusan.horobot.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.util.Random;

public class HTMLHandler {
	
	public enum KONA_RATING {
		SAFE,
		ECCHI,
		NSFW
	}

	public static void postStats(String botToken, int shard, int shardCount, int serverCount) {
		JSONObject obj = new JSONObject();
		obj.put("server_count", serverCount);
		obj.put("shard_id", shard);
		obj.put("shard_count", shardCount);
		System.out.println(serverCount);
		System.out.println(shard);
		System.out.println(shardCount);

		try {
			Unirest.post("https://bots.discord.pw/api/bots/289381714885869568/stats")
			.header("Authorization", botToken)
			.header("Content-Type", "application/json")
			.body(obj.toString())
			.asJson();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String requestCat() {
		try {
			GetRequest request = Unirest.get("http://www.random.cat/meow");
			HttpResponse<JsonNode> response = request.asJson();
			response.getBody().getObject();
			return response.getBody().getObject().getString("file");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "Error";
	}

	public static String requestKona(String[] tags, KONA_RATING rating) {
		try {
			HttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet("http://www.konachan.com/post.xml");
			URIBuilder builder = new URIBuilder(request.getURI()).addParameter("limit", "100");

			String msgSearch = "";
			String searchOrig = "";
			for(int i = 0; i < tags.length; i++) {
				if(tags.length == 1) {
					searchOrig += tags[i];
				} else {
					searchOrig += searchOrig + "+" + tags[i];
				}
			}

			if (rating == KONA_RATING.SAFE) {
				msgSearch = "order:score rating:safe " + searchOrig;
			} else if (rating == KONA_RATING.ECCHI) {
				msgSearch = "order:score rating:questionable " + searchOrig;
			} else if (rating == KONA_RATING.NSFW) {
				msgSearch = "order:score rating:explicit " + searchOrig;
			}

			builder.addParameter("tags", msgSearch);

			URI uri = builder.build();

			request.setURI(uri);

			org.apache.http.HttpResponse response;

			response = client.execute(request);
			int status = response.getStatusLine().getStatusCode();

			if(status == 200) {
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = db.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("post");
				int index;
				if(list.getLength() == 1) {
					index = 0;
				} else if(list.getLength() > 1) {
					Random rand = new Random();
					index = rand.nextInt(list.getLength());
				} else {
					return Localisation.getPMMessage("html-no-results");
				}
				Node node = list.item(index);
				NamedNodeMap map = node.getAttributes();
				Node url = map.getNamedItem("file_url");
				String name = "http:" + url.getNodeValue();
				return name;
			} else {
				return Localisation.getPMMessage("html-server-error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Localisation.getPMMessage("html-error");
	}
}