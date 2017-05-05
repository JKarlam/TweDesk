package karlamsoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

public class AppUser implements Serializable {
	
	private static final long serialVersionUID = -4616002271232567294L;
	
	private String OAUTH_TOKEN;
	private String OAUTH_TOKEN_SECRET;
	
	private String ACCESS_TOKEN;
	private String ACCESS_TOKEN_SECRET;
	private String user_id;
	private String user_screen_name;
	
	private String show_tweets_number;
	
	public AppUser() { }
	
	// Serializar a archivo
	public void saveUserConfig() {
		File userConfigFile = new File("user.conf");
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(userConfigFile));
			oos.writeObject(this);
			System.out.println("Configuration file written");
		} catch (IOException e) { 
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	// Solicitar OAUTH_TOKEN y OAUTH_TOKEN_SECRET (claves de la aplicación)
	public void requestOAuthToken() {
		String method = "POST";
		
		// Direcciones
		String host = "api.twitter.com";
		String path = "/oauth/request_token";
		String endpoint = "https://" + host + path;
		
		// NONCE = Number used ONCE, alfanumérico generado aleatoriamente cada vez
		String nonce_string = UUID.randomUUID().toString().replaceAll("-", "");
		// Hora actual en segundos sin modificaciones de horario (UTC)
		String time_stamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
		
		// Parámetros de la petición
		// oauth_callback = oob provoca login por pin
		String parameter_string = "oauth_callback=oob&oauth_consumer_key=" + Utils.OAUTH_CONSUMER_KEY + "&oauth_nonce=" + nonce_string + "&oauth_signature_method=HMAC-SHA1" + "&oauth_timestamp=" + time_stamp + "&oauth_version=1.0";
		// Petición final sin firmar (firmar = encriptar)
		String unsigned_string = method + "&" + Utils.encode(endpoint) + "&" + Utils.encode(parameter_string);
		
		// Petición final firmada usando el CONSUMER_SECRET
		String signed_string = "";
		try {
			signed_string = Utils.computeSignature(unsigned_string, Utils.OAUTH_CONSUMER_SECRET + "&"); // La & es por el parámetro ACCESS_TOKEN del usuario, que en éste momento no tenemos ni es necesario
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace(); 
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		// Parámetro Authorization del header
		String header_authorization_string = "OAuth oauth_callback=\"oob\", oauth_nonce=\""+nonce_string+"\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"" +time_stamp+ "\", oauth_consumer_key=\""+Utils.OAUTH_CONSUMER_KEY+"\", oauth_signature=\""+Utils.encode(signed_string)+"\", oauth_version=\"1.0\"";
		
		String response = null;
		try {
			HttpsURLConnection con = (HttpsURLConnection) new URL("https", host, path).openConnection();
			con.setRequestMethod(method);
			con.setRequestProperty("Authorization", header_authorization_string);
			con.connect();
			
			InputStreamReader isr = new InputStreamReader(con.getInputStream());
			BufferedReader bis = new BufferedReader(isr);
			response = bis.readLine();
			
			System.out.println(response);
		} catch (IOException e) { e.printStackTrace(); }
		
		parseOAuthToken(response);
	}
	
	// Obtener claves de la respuesta
	void parseOAuthToken(String response) {
		String[] response_parts = response.split("&");
		
		setOAUTH_TOKEN(response_parts[0].split("oauth_token=")[1]);
		setOAUTH_TOKEN_SECRET(response_parts[1].split("oauth_token_secret=")[1]);
	}
	
	// Solicitar ACCESS_TOKEN y ACCESS_TOKEN_SECRET (claves del usuario)
	public void requestAccessToken(String pin) {
		String method = "POST";
		
		String host = "api.twitter.com";
		String path = "/oauth/access_token";
		String endpoint = "https://" + host + path;
		
		String nonce_string = UUID.randomUUID().toString().replaceAll("-", "");
		String time_stamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
		
		String parameter_string = "oauth_consumer_key=" + Utils.OAUTH_CONSUMER_KEY + "&oauth_nonce=" + nonce_string + "&oauth_signature_method=HMAC-SHA1" + "&oauth_timestamp=" + time_stamp + "&oauth_token=" + Utils.encode(OAUTH_TOKEN) + "&oauth_verifier="+pin+"&oauth_version=1.0";
		String unsigned_string = method + "&" + Utils.encode(endpoint) + "&" + Utils.encode(parameter_string);
		
		String signed_string = "";
		try {
			signed_string = Utils.computeSignature(unsigned_string, Utils.OAUTH_CONSUMER_SECRET + "&");
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace(); 
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		String header_authorization_string = "OAuth oauth_nonce=\""+nonce_string+"\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" +time_stamp+ "\",oauth_consumer_key=\""+Utils.OAUTH_CONSUMER_KEY+"\",oauth_signature=\""+Utils.encode(signed_string)+"\",oauth_verifier=\""+pin+"\",oauth_version=\"1.0\",oauth_token=\"" + Utils.encode(OAUTH_TOKEN) + "\"";
		
		String response = null;
		try {
			HttpsURLConnection con = (HttpsURLConnection) new URL(endpoint).openConnection();
			con.setRequestMethod(method);
			con.setRequestProperty("Authorization", header_authorization_string);
			con.connect();
			
			InputStreamReader isr = new InputStreamReader(con.getInputStream());
			BufferedReader bis = new BufferedReader(isr);
			response = bis.readLine();
			
			System.out.println(response);
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		
		parseAccessToken(response);
	}
	
	// Obtener claves, id de usuario y nombre de la respuesta
	void parseAccessToken(String response) {
		String[] response_parts = response.split("&");
		
		setACCESS_TOKEN(response_parts[0].split("oauth_token=")[1]);
		setACCESS_TOKEN_SECRET(response_parts[1].split("oauth_token_secret=")[1]);
		
		setUser_id(response_parts[2].split("user_id=")[1]);
		setUser_screen_name(response_parts[3].split("screen_name=")[1]);
	}

	public String getOAUTH_TOKEN() {
		return OAUTH_TOKEN;
	}

	public void setOAUTH_TOKEN(String oAUTH_TOKEN) {
		OAUTH_TOKEN = oAUTH_TOKEN;
	}

	public String getOAUTH_TOKEN_SECRET() {
		return OAUTH_TOKEN_SECRET;
	}

	public void setOAUTH_TOKEN_SECRET(String oAUTH_TOKEN_SECRET) {
		OAUTH_TOKEN_SECRET = oAUTH_TOKEN_SECRET;
	}

	public String getACCESS_TOKEN() {
		return ACCESS_TOKEN;
	}

	public void setACCESS_TOKEN(String aCCESS_TOKEN) {
		ACCESS_TOKEN = aCCESS_TOKEN;
	}

	public String getACCESS_TOKEN_SECRET() {
		return ACCESS_TOKEN_SECRET;
	}

	public void setACCESS_TOKEN_SECRET(String aCCESS_TOKEN_SECRET) {
		ACCESS_TOKEN_SECRET = aCCESS_TOKEN_SECRET;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_screen_name() {
		return user_screen_name;
	}

	public void setUser_screen_name(String user_screen_name) {
		this.user_screen_name = user_screen_name;
	}

	public String getShow_tweets_number() {
		return show_tweets_number;
	}

	public void setShow_tweets_number(String show_tweets_number) {
		this.show_tweets_number = show_tweets_number;
		saveUserConfig();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
