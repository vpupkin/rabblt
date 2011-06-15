package rabbit.httpio;

import java.net.InetAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import org.khelekore.rnio.NioHandler;

import com.btr.proxy.search.ProxySearch;
import com.btr.proxy.search.ProxySearch.Strategy;
import com.btr.proxy.util.PlatformUtil;
import com.btr.proxy.util.PlatformUtil.Platform;

import rabbit.dns.DNSHandler;
import rabbit.io.ProxyChain;
import rabbit.io.Resolver;

/** A default implementation of a ProxyChain that always return 
 *  the same SimpleResolver.
 *
 * @author <a href="mailto:robo@khelekore.org">Robert Olofsson</a>
 */
public class VoleProxyChain implements ProxyChain {
     

    private NioHandler nioHandler;
	private DNSHandler dnsHandler;

	/** Create a new Proxy chain that always uses direct connections.
     * @param nio the NioHandler to use for running background tasks
     * @param dnsHandler the DNSHandler to use for DNS lookups
     */
    public VoleProxyChain (NioHandler nio, DNSHandler dnsHandler) {
    	this.nioHandler =  nio;
    	this.dnsHandler  =  dnsHandler;
    }

	public Resolver getResolver(String url) {
		URI uri;
		List<Proxy> list = null;
		Resolver resolver = null;
		try {
			ProxySearch proxySearch = new ProxySearch();
			
			if (PlatformUtil.getCurrentPlattform() == Platform.WIN) {
				proxySearch.addStrategy(Strategy.IE);
				proxySearch.addStrategy(Strategy.FIREFOX);
				proxySearch.addStrategy(Strategy.JAVA);
			} else 
			if (PlatformUtil.getCurrentPlattform() == Platform.LINUX) {
				proxySearch.addStrategy(Strategy.GNOME);
				proxySearch.addStrategy(Strategy.KDE);
				proxySearch.addStrategy(Strategy.FIREFOX);
			} else {
				proxySearch.addStrategy(Strategy.OS_DEFAULT);
			}			
			ProxySelector myProxySelector = proxySearch.getProxySelector();	
			ProxySelector.setDefault(myProxySelector);
			// TODO refactor workaround for SSL
			if (url.indexOf(":443") > 0 && url.indexOf("http://") == -1)
				uri = new URI("http:/"+"/".trim()+url.substring(0, url.indexOf(":"))); 
			else
				uri = new URI("".trim()+url);
			ProxySelector default1 = ProxySelector.getDefault();
			try{
				list = default1.select(uri);
			}catch (NullPointerException e) {
				uri =  new URI("  ".trim()+url.substring(0, url.indexOf(":")));
				list = default1.select(uri);
			}

			if (list != null && list.size()>0 && list.get(0).address()!=null){
				String nameAndPort = list.get(0).address().toString();
				InetAddress proxy =   InetAddress.getByName( nameAndPort.substring(0, nameAndPort.indexOf(":")));
				int port = Integer.parseInt(nameAndPort.substring( nameAndPort.indexOf(":")+1));
				String authTmp = "QWxhZGRpbjpvcGVuIHNlc2FtZQ==";
				resolver = new ProxyResolver(proxy , port, authTmp  );
			}else{
				resolver = new SimpleResolver(nioHandler, dnsHandler); 
			}
		} catch (java.lang.StringIndexOutOfBoundsException e){
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return resolver;
	}
}
