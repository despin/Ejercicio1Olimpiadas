package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HTTP {

	HttpURLConnection con;

	private static String host = null;
	private static String port = null;
	private static String route = null;

	public HTTP() {
		URL url = null;
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader("properties_http.json"));
			JSONObject jsonObject = (JSONObject) obj;
			host = (String) jsonObject.get("host");
			port = (String) jsonObject.get("port");
			route = (String) jsonObject.get("route");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			url = new URL("http://" + host + ":" + port + route);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONArray getInsumos() {

		try {

			BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String jsonString = "", inputLine;
			while ((inputLine = buffer.readLine()) != null)
				jsonString += inputLine + "\n";

			buffer.close();

			JSONParser parser = new JSONParser();

			Object obj = parser.parse(jsonString);
			return (JSONArray) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
