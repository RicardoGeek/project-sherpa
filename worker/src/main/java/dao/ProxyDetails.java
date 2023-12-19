package dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProxyDetails {
    int id;
    String host;
    int port;
    String username;
    String password;
    String quality;
    String provider;
}
