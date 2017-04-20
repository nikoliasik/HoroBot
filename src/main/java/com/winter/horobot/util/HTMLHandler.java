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

import com.mashape.unirest.http.exceptions.UnirestException;
import com.winter.horobot.core.Config;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.EmbedBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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

	public static String requestCat() throws UnirestException {
		GetRequest request = Unirest.get("http://www.random.cat/meow");
		HttpResponse<JsonNode> response = request.asJson();
		response.getBody().getObject();
		return response.getBody().getObject().getString("file");
	}

	public static String searchYouTube(String search) throws UnirestException, UnsupportedEncodingException {
		String temp = URLEncoder.encode(search, "UTF-8");
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + temp + "&maxResults=10&key=" + Config.youtubeKey;
		HttpResponse<JsonNode> response = Unirest.get(url)
				.header("Accept", "text/plain")
				.asJson();
		if(response.getStatus() == 200) {
			return "https://www.youtube.com/watch?v=" + response.getBody().getObject().getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
		}
		return null;
	}

	public static EmbedObject requestTranslation(String from, String to, String text, String author, String authorIcon) throws UnsupportedEncodingException, UnirestException {
		String temp = URLEncoder.encode(text, "UTF-8");
		String url = "http://www.transltr.org/api/translate?text=" + temp + "&to=" + to + "&from=" + from;
		HttpResponse<JsonNode> response = Unirest.get(url)
				.header("X-Mashape-Key", Config.mashapeKey)
				.header("Accept", "text/plain")
				.asJson();

		if(response.getStatus() == 200) {
			String translation = response.getBody().getObject().getString("translationText");
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.withColor(Color.CYAN);
			embedBuilder.withAuthorName("Requested by @" + author);
			embedBuilder.withAuthorIcon(authorIcon);
			embedBuilder.appendField(text, translation, false);
			return embedBuilder.build();
		}
		return null;
	}

	public static String requestUrban(String term, String guildID) {
		try {
			HttpResponse<JsonNode> response = Unirest.get("https://mashape-community-urban-dictionary.p.mashape.com/define?term=" + term)
					.header("X-Mashape-Key", Config.mashapeKey)
					.header("Accept", "text/plain")
					.asJson();

			return response.getBody().getObject().getJSONArray("list").getJSONObject(0).getString("definition");
		} catch (Exception e) {
			return Localisation.getMessage(guildID, "html-error");
		}
	}

	public static EmbedObject requestAnime(String[] tags, String author, String authorIcon, int searchType) throws URISyntaxException, IOException, ParserConfigurationException, SAXException {
		HttpGet request;
		if(searchType == 0) {
			request = new HttpGet("https://myanimelist.net/api/anime/search.xml");
		} else {
			request = new HttpGet("https://myanimelist.net/api/manga/search.xml");
		}
		String auth = Config.malCredentials;
		byte[] encodedPath = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String(encodedPath);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

		String msgSearch = "";
		String searchOrig = "";
		for(int i = 0; i < tags.length; i++) {
			if(tags.length == 1) {
				searchOrig += tags[i];
			} else {
				searchOrig += msgSearch + tags[i] + " ";
			}
		}

		URIBuilder builder = new URIBuilder(request.getURI());
		builder.addParameter("q", searchOrig);
		request.setURI(builder.build());

		HttpClient client = HttpClientBuilder.create().build();
		org.apache.http.HttpResponse response = client.execute(request);

		response = client.execute(request);
		int status = response.getStatusLine().getStatusCode();

		if (status == 200) {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(response.getEntity().getContent());

			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.withColor(Color.CYAN);
			embedBuilder.withAuthorName("Requested by @" + author);
			embedBuilder.withAuthorIcon(authorIcon);

			NodeList results = doc.getElementsByTagName("entry");
			if(results.getLength() == 0) {
				return null;
			} else if(results.getLength() > 5) {
				embedBuilder.withTitle("Too many results found, please be more specific");
				return embedBuilder.build();
			}

			Element thumb = (Element) results.item(0);
			embedBuilder.withThumbnail(thumb.getElementsByTagName("image").item(0).getTextContent());

			for(int i = 0; i < results.getLength(); i++) {
				Element element = (Element) results.item(i);

				StringBuilder stringBuilder = new StringBuilder();

				if(searchType == 0) {
					String episodes = element.getElementsByTagName("episodes").item(0).getTextContent();
					stringBuilder.append("**Episodes:** " + episodes + "\n");
				}

				String type = element.getElementsByTagName("type").item(0).getTextContent();
				stringBuilder.append("**Type:** " + type + "\n");

				String animeStatus = element.getElementsByTagName("status").item(0).getTextContent();
				stringBuilder.append("**Status:** " + animeStatus + "\n");

				String desc = element.getElementsByTagName("synopsis").item(0).getTextContent();
				stringBuilder.append("**Description:** " + desc + "\n");

				String unescaped = stringBuilder.toString();
				unescaped = StringEscapeUtils.unescapeXml(unescaped);
				unescaped = unescaped.replaceAll("\\<.*?>","");
				unescaped = unescaped.replaceAll("\\[.*?]","");

				unescaped = unescaped.substring(0, Math.min(unescaped.length(), 1024));
				embedBuilder.appendField(StringEscapeUtils.unescapeXml(element.getElementsByTagName("title").item(0).getTextContent()), unescaped, false);
			}
			return embedBuilder.build();
		}
		return null;
	}

	public static String requestKona(String[] tags, KONA_RATING rating) throws URISyntaxException, IOException, ParserConfigurationException, SAXException {
		HttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://www.konachan.com/post.xml");
		URIBuilder builder = new URIBuilder(request.getURI()).addParameter("limit", "100");

		String msgSearch = "";
		String searchOrig = "";
		for(int i = 0; i < tags.length; i++) {
			if(tags.length == 1) {
				searchOrig += tags[i];
			} else {
				searchOrig += msgSearch + tags[i] + " ";
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
			System.out.println(name);
			return name;
		}
		return Localisation.getPMMessage("html-error");
	}
}