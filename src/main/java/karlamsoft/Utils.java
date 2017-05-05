package karlamsoft;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;

public class Utils {

	public static String OAUTH_CONSUMER_KEY = "";
	public static String OAUTH_CONSUMER_SECRET = "";
	
	// Codifica string a formato URL
	public static String encode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) { }
		
        StringBuilder buf = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') buf.append("%2A");
			else if (focus == '+') buf.append("%20");
			else if (focus == '%' && (i + 1) < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else buf.append(focus);
        }
		
        return buf.toString();
    }
	
	// Firmar
	public static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException {
	    SecretKey secretKey = null;

	    byte[] keyBytes = keyString.getBytes();
	    secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(secretKey);

	    byte[] text = baseString.getBytes();

	    return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
	}
	
	public static AppUser loadUserConfig() {
		File userConfigFile = new File("user.conf");
		ObjectInputStream ois = null;
		if(userConfigFile.exists() && userConfigFile.isFile()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(userConfigFile));
				AppUser user = (AppUser) ois.readObject();
				return user;
			} catch (IOException e) { 
				e.printStackTrace();
				return null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					ois.close();
				} catch (IOException e) { e.printStackTrace(); }
			}
		} else return null;
	}
	
	// Obtiene una imagen de una url
	public static Image getWebImage(String url) {
		Image image = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.connect();
			
			ImageInputStream iis = ImageIO.createImageInputStream((conn.getInputStream()));
			image = ImageIO.read(iis);
		} catch (IOException e) { e.printStackTrace(); }
		return image;
	}
	
	// Obtener un formato manejable de la fecha contenida en "created_at"
	public static String tweetDateFormat(String utcDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
		Calendar cal = sdf.getCalendar();
		
		String[] enUSMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dec"};
		String[] esESMonths = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
		
		return cal.get(Calendar.DAY_OF_MONTH)+" "+enUSMonths[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
	}
	
}
