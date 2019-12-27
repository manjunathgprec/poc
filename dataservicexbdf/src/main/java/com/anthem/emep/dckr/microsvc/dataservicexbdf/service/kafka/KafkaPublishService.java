package com.anthem.emep.dckr.microsvc.dataservicexbdf.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.BaseVO;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.Page;

@Service
public class KafkaPublishService {
	
	@Value("${topic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, BaseVO> kafkaTemplate;

	public void sendMessage(Page<BaseVO> page) {

		for (BaseVO item : page.getPageItems()) {
			System.out.println("Message sent successfully" + item);
			kafkaTemplate.send(topicName, item);
		}

	}

}
