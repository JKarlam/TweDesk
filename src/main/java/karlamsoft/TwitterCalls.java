package karlamsoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

public class TwitterCalls {
	
	// Obtener información de la cuenta de twitter usando el screen_name
	public static String getUserInfo(String screen_name) {
		String response = "";
		String method = "GET";
		
		String host = "api.twitter.com";
		String path = "/1.1/users/show.json";
		String endpoint = "https://" + host + path;
		
		String nonce_string = UUID.randomUUID().toString().replaceAll("-", "");
		String time_stamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
		
		String parameter_string = "oauth_consumer_key="+Utils.OAUTH_CONSUMER_KEY+"&oauth_nonce="+nonce_string+"&oauth_signature_method=HMAC-SHA1"+"&oauth_timestamp="+time_stamp+"&oauth_token="+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"&oauth_version=1.0&screen_name="+Utils.encode(screen_name);
		String unsigned_string = method + "&" + Utils.encode(endpoint) + "&" + Utils.encode(parameter_string);
		
		String signed_string = "";
		try {
			signed_string = Utils.computeSignature(unsigned_string, Utils.OAUTH_CONSUMER_SECRET + "&" + Utils.encode(TweDesk.appUser.getACCESS_TOKEN_SECRET()));
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace(); 
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		String header_authorization_string = "OAuth oauth_consumer_key=\""+Utils.OAUTH_CONSUMER_KEY+"\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\""+time_stamp+"\",oauth_nonce=\""+nonce_string+"\",oauth_version=\"1.0\",oauth_signature=\""+Utils.encode(signed_string)+"\",oauth_token=\""+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"\"";
		InputStreamReader isr = null;
		try {
			HttpsURLConnection con = (HttpsURLConnection) new URL(endpoint+"?screen_name="+screen_name).openConnection();
			con.setRequestMethod(method);
			con.setRequestProperty("Authorization", header_authorization_string);
			con.connect();
			
			isr = new InputStreamReader(con.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			response = br.readLine();
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			try {
				if(isr != null) isr.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		return response;
	}

	// Obtener tweets y retweets recientes de un usuario mediante el nombre
	// Número máximo: 200
	// Si no se especifica numero devuelve 1
	public static String getUserTweets(String screen_name, int n) {
		String response = "";
		if(n > 200) response= "The maximum number of tweets is 200";
			else {
			String method = "GET";
			
			String host = "api.twitter.com";
			String path = "/1.1/statuses/user_timeline.json";
			String endpoint = "https://" + host + path;
			
			String nonce_string = UUID.randomUUID().toString().replaceAll("-", "");
			String time_stamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
			
			String parameter_string = "count="+n+"&oauth_consumer_key="+Utils.OAUTH_CONSUMER_KEY+"&oauth_nonce="+nonce_string+"&oauth_signature_method=HMAC-SHA1"+"&oauth_timestamp="+time_stamp+"&oauth_token="+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"&oauth_version=1.0&screen_name="+Utils.encode(screen_name);
			String unsigned_string = method + "&" + Utils.encode(endpoint) + "&" + Utils.encode(parameter_string);
			
			String signed_string = "";
			try {
				signed_string = Utils.computeSignature(unsigned_string, Utils.OAUTH_CONSUMER_SECRET + "&" + Utils.encode(TweDesk.appUser.getACCESS_TOKEN_SECRET()));
			} catch (UnsupportedEncodingException e) { 
				e.printStackTrace(); 
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			
			String header_authorization_string = "OAuth oauth_consumer_key=\""+Utils.OAUTH_CONSUMER_KEY+"\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\""+time_stamp+"\",oauth_nonce=\""+nonce_string+"\",oauth_version=\"1.0\",oauth_signature=\""+Utils.encode(signed_string)+"\",oauth_token=\""+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"\"";
			InputStreamReader isr = null;
			try {
				HttpsURLConnection con = (HttpsURLConnection) new URL(endpoint+"?count="+n+"&screen_name="+screen_name).openConnection();
				con.setRequestMethod(method);
				con.setRequestProperty("Authorization", header_authorization_string);
				con.connect();
				
				isr = new InputStreamReader(con.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				response = br.readLine();
			} catch (IOException e) { 
				e.printStackTrace(); 
			} finally {
				try {
					if(isr != null) isr.close();
				} catch (IOException e) { e.printStackTrace(); }
			}
		}
		
		return response;
	}
	
	static String getUserTweets(String screen_name) {
		return getUserTweets(screen_name, 1);
	}
	
	// Obtener tweets y retweets recientes del usuario autenticado (no sirve para cualquier usuario) y los usuarios a los que sigue
	// Número máximo: 200
	// Si no se especifica numero devuelve 1
	public static String getHomeTweets(int n) {
		String response = "";
		if(n > 200) response= "The maximum number of tweets is 200";
			else {
			String method = "GET";
			
			String host = "api.twitter.com";
			String path = "/1.1/statuses/home_timeline.json";
			String endpoint = "https://" + host + path;
			
			String nonce_string = UUID.randomUUID().toString().replaceAll("-", "");
			String time_stamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
			
			String parameter_string = "count="+n+"&oauth_consumer_key="+Utils.OAUTH_CONSUMER_KEY+"&oauth_nonce="+nonce_string+"&oauth_signature_method=HMAC-SHA1"+"&oauth_timestamp="+time_stamp+"&oauth_token="+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"&oauth_version=1.0";
			String unsigned_string = method + "&" + Utils.encode(endpoint) + "&" + Utils.encode(parameter_string);
			
			String signed_string = "";
			try {
				signed_string = Utils.computeSignature(unsigned_string, Utils.OAUTH_CONSUMER_SECRET + "&" + Utils.encode(TweDesk.appUser.getACCESS_TOKEN_SECRET()));
			} catch (UnsupportedEncodingException | GeneralSecurityException e) { e.printStackTrace(); }
			
			String header_authorization_string = "OAuth oauth_consumer_key=\""+Utils.OAUTH_CONSUMER_KEY+"\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\""+time_stamp+"\",oauth_nonce=\""+nonce_string+"\",oauth_version=\"1.0\",oauth_signature=\""+Utils.encode(signed_string)+"\",oauth_token=\""+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"\"";
			InputStreamReader isr = null;
			try {
				HttpsURLConnection con = (HttpsURLConnection) new URL(endpoint+"?count="+n).openConnection();
				con.setRequestMethod(method);
				con.setRequestProperty("Authorization", header_authorization_string);
				con.connect();
				
				isr = new InputStreamReader(con.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				response = br.readLine();
			} catch (IOException e) { 
				e.printStackTrace(); 
			} finally {
				try {
					if(isr != null) isr.close();
				} catch (IOException e) { e.printStackTrace(); }
			}
		}
		
		return response;
	}
	
	// Obtiene un tweet mediante id del tweet
	static String getSingleTweet(String tweet_id) {
		String method = "GET";
		
		String host = "api.twitter.com";
		String path = "/1.1/statuses/show.json";
		String endpoint = "https://" + host + path;
		
		String nonce_string = UUID.randomUUID().toString().replaceAll("-", "");
		String time_stamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);
		
		String parameter_string = "id="+tweet_id+"&oauth_consumer_key="+Utils.OAUTH_CONSUMER_KEY+"&oauth_nonce="+nonce_string+"&oauth_signature_method=HMAC-SHA1"+"&oauth_timestamp="+time_stamp+"&oauth_token="+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"&oauth_version=1.0";
		String unsigned_string = method + "&" + Utils.encode(endpoint) + "&" + Utils.encode(parameter_string);
		
		String signed_string = "";
		try {
			signed_string = Utils.computeSignature(unsigned_string, Utils.OAUTH_CONSUMER_SECRET + "&" + Utils.encode(TweDesk.appUser.getACCESS_TOKEN_SECRET()));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) { e.printStackTrace(); }
		
		String header_authorization_string = "OAuth oauth_consumer_key=\""+Utils.OAUTH_CONSUMER_KEY+"\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\""+time_stamp+"\",oauth_nonce=\""+nonce_string+"\",oauth_version=\"1.0\",oauth_signature=\""+Utils.encode(signed_string)+"\",oauth_token=\""+Utils.encode(TweDesk.appUser.getACCESS_TOKEN())+"\"";
		String response = "";
		InputStreamReader isr = null;
		try {
			HttpsURLConnection con = (HttpsURLConnection) new URL(endpoint+"?id="+tweet_id).openConnection();
			con.setRequestMethod(method);
			con.setRequestProperty("Authorization", header_authorization_string);
			con.connect();
			
			isr = new InputStreamReader(con.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			response = br.readLine();
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			try {
				if(isr != null) isr.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		return response;
	}
	
}
