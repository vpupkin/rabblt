package rabbit.filter;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.MulticastDefinition;
import org.apache.camel.model.RouteDefinition;

import rabbit.http.HttpHeader;
import rabbit.proxy.Connection;
import rabbit.proxy.HttpProxy;
import rabbit.util.SProperties;

/**
 * <b>Description:TODO</b>
 * 
 * @author vipup<br>
 * <br>
 *         <b>Copyright:</b> Copyright (c) 2006-2008 Monster AG <br>
 *         <b>Company:</b> Monster AG <br>
 * 
 *         Creation: 15.06.2011::22:38:56<br>
 */
public class CamelFilter implements HttpFilter {

	// public static void main(String args[]) throws Exception {
	// File inboxDirectory = new File("data/inbox");
	// File outboxDirectory = new File("data/outbox");
	// outboxDirectory.mkdir();
	// File[] files = inboxDirectory.listFiles();
	// for (File source : files) {
	// File dest = new File(outboxDirectory.getPath() + File.separator
	// + source.getName());
	// copyFile(source, dest);
	// }
	// }
	public static void main(String args[]) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() { 
			public void configure() {
				
				String search0Tmp = "http://www.fiducia.de";
				String search1Tmp = "http://www.google.de/search?client=opera&rls=de&q=camel&sourceid=opera&ie=utf-8&oe=utf-8&channel=suggest";
				String search2Tmp = "http://www.bing.com/search?q=camel&form=OPRTSD&pc=OPER";
				RouteDefinition o = from( search0Tmp )
				//.multicast()// #1
						//.to(search1Tmp, search2Tmp)
						.to("file://d:/CaMeL/aaa"); // #1
				System.out.println(o);
				 
			}
//			
//			public void process(Exchange e) {
//				System.out.println("Received exchange: " + e.getIn());
//				System.out.println("Sended exchange: " + e.getOut());
//			}
		});
		context.setTracing(true);
		context.start();
		
		Thread.sleep(10000);
		
		
		context.stop();
	}

	private static void copyFile(File source, File dest) throws IOException {
		OutputStream out = new FileOutputStream(dest);
		byte[] buffer = new byte[(int) source.length()];
		FileInputStream in = new FileInputStream(source);
		in.read(buffer);
		try {
			out.write(buffer);
		} finally {
			out.close();
			in.close();
		}
	}
	private String prefix = null;
	private java.util.List<String> forwardTo = new ArrayList<String>();
	private Pattern deny = null;
	private boolean allowMeta = false;

	public HttpHeader doHttpInFiltering(SocketChannel socket,
			HttpHeader header, Connection con) {
		String s = header.getRequestURI();
		if (deny != null) {
			Matcher m = deny.matcher(s);
			if (m.matches() && allowMeta) {
				String metaStart = "http://"
						+ con.getProxy().getHost().getHostName() + ":"
						+ con.getProxy().getPort() + "/";
				if (!s.startsWith(metaStart)) {
					return con.getHttpGenerator().get403();
				}
			}
		}
		if (prefix != null && forwardTo  != null && s != null && s.length() > 0
				&& s.charAt(0) == '/') {
			//String newRequest = s.replaceAll(prefix, replacer);
			//header.setRequestURI(newRequest);
		}
		return null;
	}

	public HttpHeader doHttpOutFiltering(SocketChannel socket,
			HttpHeader header, Connection con) {
		return null;
	}

	public HttpHeader doConnectFiltering(SocketChannel socket,
			HttpHeader header, Connection con) {
		return null;
	}

	/**
	 * Setup this class with the given properties.
	 * 
	 * @param properties
	 *            the new configuration of this class.
	 */
	public void setup(SProperties properties, HttpProxy proxy) {
		prefix = properties.getProperty("prefix", "");
		for (int i=0;i<100;i++){
			String fNext = properties.getProperty("transformTo" );
			if(fNext !=null){
				forwardTo .add(fNext); 
			}
		}
		String denyString = properties.getProperty("deny");
		if (denyString != null)
			deny = Pattern.compile(denyString);
		allowMeta = properties.getProperty("allowMeta", "true")
				.equalsIgnoreCase("true");
	}

}
