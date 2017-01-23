package com.lpmas.tpp.console.channel.offline.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.mq.activemq.ActiveMQReceiver;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.oms.client.bean.response.DeliveryInfoSyncResponseBean;
import com.lpmas.tpp.console.channel.offline.handler.OfflineOrderInfoHandler;

public class OfflineOrderDeliveryInfoMqReceiver extends ActiveMQReceiver {
	private static Logger log = LoggerFactory.getLogger(OfflineOrderDeliveryInfoMqReceiver.class);

	public OfflineOrderDeliveryInfoMqReceiver(String brokerId) {
		super(brokerId);
	}

	@Override
	public void process(String message) {
		log.info("收到消息:" + message);
		DeliveryInfoSyncResponseBean responseBean = JsonKit.toBean(message, DeliveryInfoSyncResponseBean.class);
		if (responseBean != null) {
			OfflineOrderInfoHandler orderInfoHandler = new OfflineOrderInfoHandler();
			try {
				orderInfoHandler.syncDeliveryInfoResponse(responseBean);
			} catch (Exception e) {
				log.error("更新订单信息失败!");
			}
		} else {
			log.error("消息反序列化失败!");
		}
	}

}
