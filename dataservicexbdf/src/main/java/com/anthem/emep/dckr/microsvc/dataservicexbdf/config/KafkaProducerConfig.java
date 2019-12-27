package com.anthem.emep.dckr.microsvc.dataservicexbdf.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.BaseVO;


@Configuration
@EnableKafka
public class KafkaProducerConfig {
	
	//@Autowired
	//private ApplicationPropertyConfig appProperties;
	
	@Bean
	public ProducerFactory<String, BaseVO> producerFactory() {
		Map<String, Object> props = new HashMap<String, Object>();
//		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, appProperties.getKafkaproducerpropmap().get("kafka.bootstrapServers"));
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);
//		props.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, appProperties.getKafkaproducerpropmap().get("kafka.securityProtocol"));
//		props.put(SaslConfigs.SASL_MECHANISM, appProperties.getKafkaproducerpropmap().get("kafka.saslMechanism"));
//		props.put(SaslConfigs.SASL_JAAS_CONFIG,appProperties.getKafkaproducerpropmap().get("kafka.saslJaas"));

		return new DefaultKafkaProducerFactory<String, BaseVO>(props);
	}
	
//	@ConditionalOnMissingBean(name = "kafkaListenerContainerFactoryConfigurer")


	@Bean
	public KafkaTemplate<String, BaseVO> kafkaTemplate() {
		return new KafkaTemplate<String, BaseVO>(producerFactory());
	}


}
