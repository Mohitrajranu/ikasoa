package com.ikasoa.core.thrift.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;

import com.ikasoa.core.TestConstants;
import com.ikasoa.core.thrift.Factory;
import com.ikasoa.core.thrift.GeneralFactory;
import com.ikasoa.core.thrift.client.CompactThriftClientConfiguration;
import com.ikasoa.core.thrift.client.ThriftClient;
import com.ikasoa.core.thrift.client.ThriftClientConfiguration;
import com.ikasoa.core.thrift.client.TupleThriftClientConfiguration;
import com.ikasoa.core.thrift.protocol.DESCompactProtocolFactory;
import com.ikasoa.core.thrift.protocol.RC4CompactProtocolFactory;
import com.ikasoa.core.thrift.server.impl.ServletThriftServerImpl;
import com.ikasoa.core.thrift.server.impl.SimpleThriftServerImpl;
import com.ikasoa.core.thrift.server.impl.ThreadPoolThriftServerImpl;
import com.ikasoa.core.utils.ServerUtil;
import com.ikasoa.core.thrift.server.ThriftSimpleService;
import com.ikasoa.core.thrift.server.ThriftSimpleService.Iface;
import com.ikasoa.core.thrift.server.ThriftSimpleService.Processor;

import junit.framework.TestCase;
import lombok.SneakyThrows;

/**
 * Thrift服务端单元测试
 */
public class ServerTest extends TestCase {

	private static String serverName = "TestThriftServer";

	private static ThriftServerConfiguration configuration = new ThriftServerConfiguration();

	private static Factory factory = new GeneralFactory(configuration);

	private Processor<Iface> processor = new ThriftSimpleService.Processor<Iface>(new ThriftSimpleServiceImpl()) {
		@Override
		public boolean process(TProtocol in, TProtocol out) throws TException {
			try {
				return super.process(in, out);
			} catch (TTransportException e) {
				return Boolean.FALSE;
			}
		}
	};

	@Test
	public void testThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		ThriftServer thriftServer = factory.getThriftServer(serverName, serverPort, processor);
		assertEquals(thriftServer.getServerName(), serverName);
		assertEquals(thriftServer.getServerPort(), serverPort);
		assertEquals(thriftServer.getServerConfiguration(), configuration);
		thriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport)).get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	@Test
	public void testNonblockingThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		ThriftServer nioThriftServer = factory.getNonblockingThriftServer(serverName, serverPort, processor);
		assertEquals(nioThriftServer.getServerName(), serverName);
		assertEquals(nioThriftServer.getServerPort(), serverPort);
		assertEquals(nioThriftServer.getServerConfiguration(), configuration);
		nioThriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport)).get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			nioThriftServer.stop();
		}
	}

	@Test
	public void testSimpleThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		ThriftServer simpleThriftServer = new SimpleThriftServerImpl(serverName, serverPort, configuration, processor);
		assertEquals(simpleThriftServer.getServerName(), serverName);
		assertEquals(simpleThriftServer.getServerPort(), serverPort);
		assertEquals(simpleThriftServer.getServerConfiguration(), configuration);
		simpleThriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport)).get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			fail();
		} finally {
			simpleThriftServer.stop();
		}
	}

	@Test
	public void testAysncThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		ThriftServerConfiguration thriftServerConfiguration = new ThriftServerConfiguration();
		thriftServerConfiguration.setProtocolFactory(new TCompactProtocol.Factory());
		thriftServerConfiguration.setProcessorFactory(new TProcessorFactory(processor));
		thriftServerConfiguration.setServerArgsAspect(new ServerArgsAspect() {
			@Override
			public TThreadPoolServer.Args tThreadPoolServerArgsAspect(TThreadPoolServer.Args args) {
				args.stopTimeoutVal = 1;
				return args;
			}
		});
		Factory factory = new GeneralFactory(thriftServerConfiguration);
		ThriftServer thriftServer = factory.getThriftServer(serverName, serverPort, processor);
		thriftServer.run();
		try {
			ThriftSimpleService.AsyncClient thriftClient = new ThriftSimpleService.AsyncClient(
					new TCompactProtocol.Factory(), new TAsyncClientManager(),
					new TNonblockingSocket(TestConstants.LOCAL_HOST, serverPort));
			Thread.sleep(500);
			thriftClient.get(TestConstants.TEST_STRING, new TestCallback());
			Thread.sleep(1000);
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	@Test
	public void testMultiplexedThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		Map<String, TProcessor> processorMap = new HashMap<>();
		processorMap.put("testServer", processor);
		MultiplexedProcessor processor = new MultiplexedProcessor(processorMap);
		ThriftServer thriftServer = new ThreadPoolThriftServerImpl(serverName, serverPort, configuration, processor);
		assertEquals(thriftServer.getServerName(), serverName);
		assertEquals(thriftServer.getServerPort(), serverPort);
		assertEquals(thriftServer.getServerConfiguration(), configuration);
		thriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport, "testServer"))
							.get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	@Test
	public void testCompactThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		Factory factory = new GeneralFactory(new CompactThriftServerConfiguration(),
				new CompactThriftClientConfiguration());
		ThriftServer thriftServer = factory.getThriftServer(serverName, serverPort, processor);
		thriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport)).get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	@Test
	public void testTupleThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		Factory factory = new GeneralFactory(new TupleThriftServerConfiguration(),
				new TupleThriftClientConfiguration());
		ThriftServer thriftServer = factory.getThriftServer(serverName, serverPort, processor);
		thriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport)).get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	@Test
	public void testDESCompactThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		String key = TestConstants.TEST_KEY8;
		ThriftServerConfiguration serverConfiguration = new ThriftServerConfiguration();
		serverConfiguration.setProtocolFactory(new DESCompactProtocolFactory(key));
		ThriftClientConfiguration clientConfiguration = new ThriftClientConfiguration();
		clientConfiguration.setProtocolFactory(new DESCompactProtocolFactory(key));
		Factory factory = new GeneralFactory(serverConfiguration, clientConfiguration);
		ThriftServer thriftServer = factory.getThriftServer(serverName, serverPort, processor);
		thriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport)).get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	@Test
	public void testRC4CompactThriftServerImpl() {
		int serverPort = ServerUtil.getNewPort();
		String key = TestConstants.TEST_KEY; // RC4的key可以超过8位
		ThriftServerConfiguration serverConfiguration = new ThriftServerConfiguration();
		serverConfiguration.setProtocolFactory(new RC4CompactProtocolFactory(key));
		ThriftClientConfiguration clientConfiguration = new ThriftClientConfiguration();
		clientConfiguration.setProtocolFactory(new RC4CompactProtocolFactory(key));
		Factory factory = new GeneralFactory(serverConfiguration, clientConfiguration);
		ThriftServer thriftServer = factory.getThriftServer(serverName, serverPort, processor);
		thriftServer.run();
		try (ThriftClient thriftClient = factory.getThriftClient(TestConstants.LOCAL_HOST, serverPort);
				TTransport transport = thriftClient.getTransport()) {
			Thread.sleep(500);
			transport.open();
			assertEquals(TestConstants.TEST_STRING,
					new ThriftSimpleService.Client(thriftClient.getProtocol(transport)).get(TestConstants.TEST_STRING));
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	@Test
	public void testServletThriftServerImpl() {
		ThriftServer servletThriftServer = new ServletThriftServerImpl(serverName, configuration, null);
		assertEquals(servletThriftServer.getServerName(), serverName);
		assertEquals(servletThriftServer.getServerConfiguration(), configuration);
	}

	@Test
	public void testServerAspect() {
		int serverPort = ServerUtil.getNewPort("testServerAspect");
		ThriftServerConfiguration configuration = new ThriftServerConfiguration();
		configuration.setServerAspect(new TestServerAspectImpl());
		ThriftServer thriftServer = new GeneralFactory(configuration).getThriftServer(serverName, serverPort,
				processor);
		try {
			thriftServer.run();
		} catch (Exception e) {
			fail();
		} finally {
			thriftServer.stop();
		}
	}

	private class TestCallback implements AsyncMethodCallback<String> {

		@Override
		public void onComplete(String arg) {
			assertEquals(arg, TestConstants.TEST_STRING);
		}

		@Override
		public void onError(Exception exception) {
			fail();
		}
	}

	private class TestServerAspectImpl extends TestCase implements ServerAspect {

		@Test
		@Override
		public void beforeStart(String serverName, int serverPort, ServerConfiguration configuration,
				TProcessor processor, ThriftServer server) {
			assertEquals(ServerTest.serverName, serverName);
			assertEquals(ServerUtil.getNewPort("testServerAspect"), serverPort);
			assertFalse(server.isServing());
		}

		@SneakyThrows
		@Test
		@Override
		public void afterStart(String serverName, int serverPort, ServerConfiguration configuration,
				TProcessor processor, ThriftServer server) {
			assertEquals(ServerTest.serverName, serverName);
			assertEquals(ServerUtil.getNewPort("testServerAspect"), serverPort);
			Thread.sleep(500);
			assertTrue(server.isServing());
		}

		@Test
		@Override
		public void beforeStop(String serverName, int serverPort, ServerConfiguration configuration,
				TProcessor processor, ThriftServer server) {
			assertEquals(ServerTest.serverName, serverName);
			assertEquals(ServerUtil.getNewPort("testServerAspect"), serverPort);
			assertTrue(server.isServing());
		}

		@SneakyThrows
		@Test
		@Override
		public void afterStop(String serverName, int serverPort, ServerConfiguration configuration,
				TProcessor processor, ThriftServer server) {
			assertEquals(ServerTest.serverName, serverName);
			assertEquals(ServerUtil.getNewPort("testServerAspect"), serverPort);
			Thread.sleep(500);
			assertFalse(server.isServing());
		}

	}

}
