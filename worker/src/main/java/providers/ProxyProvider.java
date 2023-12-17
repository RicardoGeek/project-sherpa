
package providers;

import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.auth.AuthType;

import java.net.InetSocketAddress;
import java.util.UUID;
@Slf4j
public class ProxyProvider {

    public BrowserMobProxyServer getProxy(
            final String host,
            final Integer port,
            final String username,
            final String password
    ) {
        BrowserMobProxyServer proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.setChainedProxy(new InetSocketAddress(host, port));

        proxy.chainedProxyAuthorization(username, password, AuthType.BASIC);
        proxy.start(0);

        return proxy;
    }

    public String getRandomSessionId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
