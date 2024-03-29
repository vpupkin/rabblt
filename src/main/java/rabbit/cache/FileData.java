package rabbit.cache;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.khelekore.rnio.impl.Closer;

/** A class to store cache data to a file.
 *
 * @param <T> the data type stored on disk
 *
 * @author <a href="mailto:robo@khelekore.org">Robert Olofsson</a>
 */
class FileData<T> implements Serializable {
    private static final long serialVersionUID = 1;
    private long fileSize;

    public long getFileSize () {
	return fileSize;
    }
    
    /** Read the data from disk. 
     * @param name the name of the file to read the data from
     * @param fh the FileHandler that will do the data convesion
     * @param logger the logger to use
     * @throws IOException if file reading fails
     * @return the object read
     */
    protected T readData (String name, FileHandler<T> fh, Logger logger)
	throws IOException {
	File f = new File (name);
	if (!f.exists())
	    return null;
	FileInputStream fis = new FileInputStream (f);
	try {
	    InputStream is = new GZIPInputStream (fis);
	    try {
		return fh.read (is);
	    } finally {
		Closer.close (is, logger);
	    }
	} finally {
	    Closer.close (fis, logger);
	}
    }

    protected long writeData (String name, FileHandler<T> fh, T data,
			      Logger logger) throws IOException {
	File f = new File (name);
	FileOutputStream fos = new FileOutputStream (f);
	try {
	    OutputStream os = new GZIPOutputStream (fos);
	    try {
		fh.write (os, data);
	    } finally {
		Closer.close (os, logger);
	    }
	} finally {
	    Closer.close (fos, logger);
	}
	fileSize = f.length ();
	return fileSize;
    }    
}
