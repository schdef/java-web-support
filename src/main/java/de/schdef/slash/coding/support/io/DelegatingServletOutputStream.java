package de.schdef.slash.coding.support.io;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import de.schdef.slash.coding.support.util.Assert;

public class DelegatingServletOutputStream 
    extends ServletOutputStream {

    private final OutputStream targetStream;

    public DelegatingServletOutputStream(OutputStream targetStream) {
	Assert.isNotNull(targetStream, "invalid targetStream: null");
	this.targetStream = targetStream;
    }

    public final OutputStream getTargetStream() {
	return this.targetStream;
    }

    public void write(int b) throws IOException {
	this.targetStream.write(b);
    }

    public void flush() throws IOException {
	super.flush();
	this.targetStream.flush();
    }

    public void close() throws IOException {
	super.close();
	this.targetStream.close();
    }

}
