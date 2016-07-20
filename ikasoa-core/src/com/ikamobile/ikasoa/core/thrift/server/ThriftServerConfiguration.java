package com.ikamobile.ikasoa.core.thrift.server;

import java.util.concurrent.ExecutorService;

import org.apache.thrift.server.TServerEventHandler;
import com.ikamobile.ikasoa.core.thrift.AbstractThriftConfiguration;

/**
 * Thrift服务器配置
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.1
 */
public class ThriftServerConfiguration extends AbstractThriftConfiguration {

	private ServerAspect serverAspect;

	/**
	 * Thrift服务事件处理器,用于扩展其它功能
	 */
	private TServerEventHandler serverEventHandler;

	/**
	 * Thrift服务使用的线程池(为空则默认)
	 * <p>
	 * 自定义线程池实现属于高级功能,一般情况下不推荐设置该值.
	 */
	private ExecutorService executorService;

	public ThriftServerConfiguration() {
	}

	public ServerAspect getServerAspect() {
		return serverAspect;
	}

	public void setServerAspect(ServerAspect serverAspect) {
		this.serverAspect = serverAspect;
	}

	public TServerEventHandler getServerEventHandler() {
		return serverEventHandler;
	}

	public void setServerEventHandler(TServerEventHandler serverEventHandler) {
		this.serverEventHandler = serverEventHandler;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

}
