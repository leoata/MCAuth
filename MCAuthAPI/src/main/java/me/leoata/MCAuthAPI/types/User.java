package me.leoata.MCAuthAPI.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.leoata.MCAuthAPI.restservice.registration.attempt.UserRegisterAttempt;
import me.leoata.MCAuthAPI.util.TwoFAUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.bson.Document;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class User {

    @Getter private String uuid;
    @Getter @Setter private String ip;
    @Getter @Setter private long lastLogin;
    @Setter private String twoFaSecret;

    @Getter private List<String> servers;

    public User(UserRegisterAttempt attempt) {
        this.uuid = attempt.getUuid();
        this.ip = attempt.getIp();
        this.lastLogin = System.currentTimeMillis();
        this.twoFaSecret = TwoFAUtil.generateSecret();
        this.servers = Collections.singletonList(attempt.getName());
    }

    public static User fromBson(Document document) {
        if (document == null || document.getString("uuid") == null)
            return null;
        return new User(document.getString("uuid"), document.getString("ip"), document.getLong("lastLogin"), document.getString("twoFASecret"), (List<String>) document.getList("servers", String.class));
    }

    public Document toBson() {
        Document document = new Document();
        document.put("uuid", this.uuid);
        document.put("ip", this.ip);
        document.put("lastLogin", this.lastLogin);
        document.put("servers", getServers());
        document.put("twoFASecret", this.twoFaSecret);
        return document;
    }

    @JsonIgnore
    @JsonProperty(value = "twoFASecret")
    public String getTwoFaSecret() {
        return twoFaSecret;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", ip='" + ip + '\'' +
                ", lastLogin=" + lastLogin +
                ", twoFaSecret='" + twoFaSecret + '\'' +
                ", servers=" + StringUtils.join(servers) +
                '}';
    }
}
