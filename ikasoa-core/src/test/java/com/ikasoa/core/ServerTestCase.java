package com.ikasoa.core;

import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;

import junit.framework.TestCase;
import lombok.SneakyThrows;

@Ignore
public class ServerTestCase extends TestCase {

	@SneakyThrows
	protected String getSslUrlFileString(String fileName) {
		URL url = this.getClass().getResource(fileName);
		return url == null ? null : URLDecoder.decode(url.getFile(), "UTF-8");
	}

	@SneakyThrows
	protected void waiting() {
		TimeUnit.SECONDS.sleep(1);
	}

}
