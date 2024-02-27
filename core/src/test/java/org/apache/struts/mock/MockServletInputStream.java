package org.apache.struts.mock;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

public class MockServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream in;

    public MockServletInputStream(byte[] buf) {
        this.in = new ByteArrayInputStream(buf);
    }

    @Override
    public boolean isFinished() {
        return in.available() == 0;
    }

	@Override
	public boolean isReady() {
        return in.available() != 0;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public void close() throws IOException {
        super.close();
        in.close();
    }
}
