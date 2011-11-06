package de.schdef.slash.coding.support.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;

import de.schdef.slash.coding.support.io.DelegatingServletOutputStream;

public class HttpBufferedResponseWrapper 
    extends HttpServletResponseWrapper {

    private ByteArrayOutputStream out;

    public HttpBufferedResponseWrapper(HttpServletResponse response) {
        super(response);
        out = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new DelegatingServletOutputStream(new TeeOutputStream(
                super.getOutputStream(), out));
    }

    public String getContentAsString() {
        return new String(this.out.toByteArray());
    }

}