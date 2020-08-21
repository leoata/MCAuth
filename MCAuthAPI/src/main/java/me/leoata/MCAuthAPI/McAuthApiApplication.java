package me.leoata.MCAuthAPI;

import lombok.Getter;
import me.leoata.MCAuthAPI.storage.MongoImpl;
import me.leoata.MCAuthAPI.util.TwoFAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class McAuthApiApplication {
	@Getter private static McAuthApiApplication instance;
	@Getter private Logger logger;
	@Getter private Authenticator authenticator;
	@Getter private MongoImpl mongo;

	public static void main(String[] args) {
		instance = new McAuthApiApplication();
		SpringApplication.run(McAuthApiApplication.class, args);

	}

	public McAuthApiApplication(){
		instance = this;
		authenticator = new Authenticator();
		logger = LoggerFactory.getLogger(McAuthApiApplication.class);
		logger.info("Hooked into SpringBoot's logger");
		logger.info("Listening on " + getIp());
		mongo = new MongoImpl();
	}

	private String getIp() {
		try {

			StringBuilder result = new StringBuilder();
			URL url = new URL("https://api.ipify.org");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result.toString();

		} catch (Exception ex) {
			logger.error(String.join(" ", Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())));
		}
		return null;
	}
}
