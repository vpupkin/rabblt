package rabbit.proxy;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.logging.Logger;

import rabbit.cache.Cache;
import rabbit.cache.CacheEntry;
import rabbit.cache.CacheException;
import rabbit.cache.FileHandler;
import rabbit.cache.NCache;
import rabbit.http.HttpHeader;
import rabbit.util.SProperties;

/** 
 * <b>Description:TODO</b>
 * @author      vipup<br>
 * <br>
 * <b>Copyright:</b>     Copyright (c) 2006-2008 Monster AG <br>
 * <b>Company:</b>       Monster AG  <br>
 * 
 * Creation:  07.12.2011::22:33:27<br> 
 */
public class NcacheWrapper<K, V> implements Cache<K, V> {
    private long maxSize;
	private long cacheTime;
	private int cleanLoopTime;
	
	

	public NcacheWrapper(net.sf.jsr107cache.Cache cacheTmp) {
		this.cache = cacheTmp;
	}

	/** Get the maximum size for this cache.
     * @return the maximum size in bytes this cache.
     */
    public long getMaxSize () {
	return maxSize;
    }

    /** Set the maximum size for this cache.
     * @param newMaxSize the new maximum size for the cache.
     */
    public void setMaxSize (long newMaxSize) {
	maxSize = newMaxSize;
    }

    /** Get the number of miliseconds the cache stores things usually.
     *  This is the standard expiretime for objects, but you can set it for
     *  CacheEntries individially if you want to.
     *  NOTE 1: dont trust that an object will be in the cache this long.
     *  NOTE 2: dont trust that an object will be removed from the cache
     *          when it expires.
     * @return the number of miliseconds objects are stored normally.
     */
    public long getCacheTime () {
	return cacheTime;
    }

    /** Set the standard expiry-time for CacheEntries
     * @param newCacheTime the number of miliseconds to keep objects normally.
     */
    public void setCacheTime (long newCacheTime) {
	cacheTime = newCacheTime;
    }

    /** Get how long time the cleaner sleeps between cleanups.
     * @return the number of millis between cleanups
     */
    public int getCleanLoopTime () {
	return cleanLoopTime;
    }

    /** Set how long time the cleaner sleeps between cleanups.
     * @param newCleanLoopTime the number of miliseconds to sleep.
     */
    public void setCleanLoopTime (int newCleanLoopTime) {
	cleanLoopTime = newCleanLoopTime;
    }

    net.sf.jsr107cache.Cache cache;
    /** Get the current size of the cache
     * @return the current size of the cache in bytes.
     */
    public long getCurrentSize () {
    	return cache.size();
    }    
    
	@Override
	public long getNumberOfEntries() {
		return cache.size();
	}

	@Override
	public URL getCacheDir() {
		return null;
	}

	@Override
	public CacheEntry<K, V> getEntry(K k) throws CacheException {
		// net.sf.jsr107cache.CacheEntry o = cache.getCacheEntry( k );
		Object o = cache.get( k );
		CacheEntry<K, V> retval =o!=null? new JSR170Entry(o):null;
		return retval ;
	}

	@Override
	public String getEntryName(long id, boolean real, String extension) { 
		// have to be calculated by entry itself
		return null;
		 
	}

	@Override
	public FileHandler<K> getKeyFileHandler() {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		return null;
		}
	}

	@Override
	public FileHandler<V> getHookFileHandler() {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		return null;
		}
	}

	@Override
	public CacheEntry<K, V> newEntry(K k) {
		
		{
		CacheEntry<K, V> retval = new JSR170Entry<K, V>(k);
		return retval ;
		}
	}

	@Override
	public void addEntry(CacheEntry<K, V> ent) throws CacheException {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		}
	}

	@Override
	public void entryChanged(CacheEntry<K, V> ent, K newKey, V newValue)
			throws CacheException {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		}
	}

	@Override
	public void remove(K k) throws CacheException {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		}
	}

	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		}
	}

	@Override
	public Collection<? extends CacheEntry<K, V>> getEntries() {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		return null;
		}
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		}
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		if (1==1)throw new RuntimeException("not yet implemented since 07.12.2011");
		else {
		return null;
		}
	}

 

}


 