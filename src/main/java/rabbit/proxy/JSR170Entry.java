package rabbit.proxy;

import rabbit.cache.Cache;
import rabbit.cache.CacheEntry;
import rabbit.cache.CacheException;

/** 
 * <b>Description:TODO</b>
 * @author      vipup<br>
 * <br>
 * <b>Copyright:</b>     Copyright (c) 2006-2008 Monster AG <br>
 * <b>Company:</b>       Monster AG  <br>
 * 
 * Creation:  07.12.2011::22:42:49<br> 
 */
public class JSR170Entry<K, V> implements CacheEntry<K, V> {

	private Object o;
	private V dataHook;
	private K key;

	public JSR170Entry(net.sf.jsr107cache.CacheEntry o){
		this.o=o;
	}
	
	public JSR170Entry(Object o){
		this.o=o;
	}	
	
	@Override
	public long getId() {
		return o.hashCode();
	}

	@Override
	public K getKey() throws CacheException {
		return (K) this.key;
	}

	@Override
	public long getCacheTime() {
		return 0;
	}

	@Override
	public long getSize() {
		return 1;
	}

	@Override
	public long getKeySize() {
		return 1;
	}

	@Override
	public long getHookSize() {
		return 1;
	}

	@Override
	public void setSize(long size) {
		
	}

	@Override
	public long getExpires() {
		return 1;
	}

	@Override
	public void setExpires(long d) {
		 
	}

	@Override
	public V getDataHook(Cache<K, V> cache) throws CacheException { 
		return this.dataHook;
		 
	}

	@Override
	public void setDataHook(V o) {
		this.	dataHook = o;
	}

}


 