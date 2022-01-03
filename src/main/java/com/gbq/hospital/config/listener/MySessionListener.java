package com.gbq.hospital.config.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/** 
* 创建时间：2019年4月18日09:39:24
* @author  gbq
* @version  
* 
*/
@Component
public class MySessionListener implements SessionListener {
	
	private static final Logger S_LOGGER = LoggerFactory.getLogger(MySessionListener.class);
	
	private final AtomicInteger sessionCount = new AtomicInteger(0);
	
	public int getSessionCount() {
        return sessionCount.get();
    }
	@Override
	public void onStart(Session session) {
		sessionCount.incrementAndGet();
		S_LOGGER.info("登录+1=="+sessionCount.get());
		
	}

	@Override
	public void onStop(Session session) {
		sessionCount.decrementAndGet();
		S_LOGGER.info("登录退出-1=="+sessionCount.get());
		
	}

	@Override
	public void onExpiration(Session session) {
		sessionCount.decrementAndGet();
		S_LOGGER.info("登录过期-1=="+sessionCount.get());
		
	}

}
