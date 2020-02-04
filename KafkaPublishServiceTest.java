package com.anthem.emep.dckr.microsvc.dataservicexbdf;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.config.ApplicationPropertyConfig;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.domain.Page;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.BaseEntity;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.PersonProxyRecords;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.repository.PersonProxyRecordsRepository;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.service.kafka.KafkaPublishService;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.util.ApplicationConstants;

public class KafkaPublishServiceTest {
	
	@InjectMocks
	private KafkaPublishService kafkaPublishService;
	
	@Mock
	private ApplicationPropertyConfig config;

	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Mock
	private PersonProxyRecordsRepository ehubPrsnProxyRltnshpRepository;
	
	@Mock
	private SendResult<String, String> sendResult;
	
	@Mock
	private ListenableFuture<SendResult<String, String>> responseFuture;
	
	@Mock
	private ProducerRecord<String, String> record; 
	
	@Mock
	private Throwable throwable;

	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void publishSingleMessageToKafkaOnSuccess() {
        String topic = "topic123";
        long offset = 1;
        int partition = 0;

        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition(topic, partition), offset, 0L, 0L, 0L, 0, 0);

        given(sendResult.getRecordMetadata()).willReturn(recordMetadata);
        
        Map<String, String> appConfigProps = new HashMap<>();
        appConfigProps.put(ApplicationConstants.KAFKA_TOPIC_NAME, "SampleTopic");
        
		when(config.getKafkaproducerpropmap()).thenReturn(appConfigProps);
		
        when(kafkaTemplate.send(any(ProducerRecord.class))).thenReturn(responseFuture);
        doAnswer(invocationOnMock -> {
            ListenableFutureCallback listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onSuccess(sendResult);
            assertEquals(sendResult.getRecordMetadata().offset(), offset);
            assertEquals(sendResult.getRecordMetadata().partition(), partition);
            return null;
        }).when(responseFuture).addCallback(any(ListenableFutureCallback.class));

		Page<BaseEntity> currentPage = new Page<>();
		currentPage.setPageItems(Arrays.asList(preparePersonProxyRecords()));
		
        kafkaPublishService.sendMessage(currentPage, "PPID");
        
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
    }
	
	@Test
	public void publishMultipleMessagesToKafkaOnSuccess() {
        String topic = "topic123";
        long offset = 1;
        int partition = 0;

        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition(topic, partition), offset, 0L, 0L, 0L, 0, 0);

        given(sendResult.getRecordMetadata()).willReturn(recordMetadata);
        
        Map<String, String> appConfigProps = new HashMap<>();
        appConfigProps.put(ApplicationConstants.KAFKA_TOPIC_NAME, "SampleTopic");
        
		when(config.getKafkaproducerpropmap()).thenReturn(appConfigProps);
		
        when(kafkaTemplate.send(any(ProducerRecord.class))).thenReturn(responseFuture);
        doAnswer(invocationOnMock -> {
            ListenableFutureCallback listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onFailure(throwable);
            return null;
        }).when(responseFuture).addCallback(any(ListenableFutureCallback.class));

		Page<BaseEntity> currentPage = new Page<>();
		currentPage.setPageItems(Arrays.asList(preparePersonProxyRecords(), preparePersonProxyRecords()));
		
        kafkaPublishService.sendMessage(currentPage, "PPID");
        
        verify(kafkaTemplate, times(2)).send(any(ProducerRecord.class));
    }
	
	@Test
	public void publishMessageToKafkaOnFailure() {
        String topic = "topic123";
        long offset = 1;
        int partition = 0;

        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition(topic, partition), offset, 0L, 0L, 0L, 0, 0);

        given(sendResult.getRecordMetadata()).willReturn(recordMetadata);
        
        Map<String, String> appConfigProps = new HashMap<>();
        appConfigProps.put(ApplicationConstants.KAFKA_TOPIC_NAME, "SampleTopic");
        
		when(config.getKafkaproducerpropmap()).thenReturn(appConfigProps);
		
        when(kafkaTemplate.send(any(ProducerRecord.class))).thenReturn(responseFuture);
        doAnswer(invocationOnMock -> {
            ListenableFutureCallback listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onFailure(throwable);
            return null;
        }).when(responseFuture).addCallback(any(ListenableFutureCallback.class));

		Page<BaseEntity> currentPage = new Page<>();
		currentPage.setPageItems(Arrays.asList(preparePersonProxyRecords()));
		
        kafkaPublishService.sendMessage(currentPage, "PPID");  
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
    }
	
	public PersonProxyRecords preparePersonProxyRecords() {
		PersonProxyRecords records = new PersonProxyRecords();
		records.setCsPersonId("cspersonId");
		records.setMetaMsgType("PPIDMSGTYPE");
		records.setMetaMsgCreateDtm(new Date());
		records.setPkeyCsPersonId("Pkey");
		records.setPrsnRecLstUpdtDtm(new Date());
		records.setMetaRecLtstUpdtGuid("GUID");
		return records;		
	}
	
	//@Test
	public void testSendMessage() {
		
		PersonProxyRecords records = new PersonProxyRecords();
		records.setCsPersonId("cspersonId");

		Page<BaseEntity> currentPage = new Page<>();
		currentPage.setPageItems(Arrays.asList(records));
		//when(kafkaTemplate.send(Mockito.any(ProducerRecord.class))).thenReturn(arg0)
		
		kafkaPublishService.sendMessage(currentPage, "PPID");
		
	}

}

