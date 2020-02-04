package com.anthem.emep.dckr.microsvc.dataservicexbdf;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.domain.Page;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.domain.SearchCriteria;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.BaseEntity;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.PersonProxyRecords;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.rowmapper.PersonProxyRecordsRowMapper;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.service.impl.EhubDataStoreProcessor;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.service.kafka.KafkaPublishService;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.util.PaginationHelper;

public class EhubDataStoreProcessorTest {
	@Mock
	private PaginationHelper<BaseEntity> paginationHelper;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Mock
	private KafkaPublishService kafkaPublishService;

	@InjectMocks
	private EhubDataStoreProcessor ehubDataStoreProcessor;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testProcessPPIDWithPageResultsWithinPageSize() {

		SearchCriteria searchCriteria = prepareSearchCriteria(20, "20190706", "20201010",
				"ehub_cogs_prsn_proxy_rltnshp_bt", "sqlcount", "sqldata",
				new PersonProxyRecordsRowMapper("EHUBPERSONMSG"));

		PersonProxyRecords records = new PersonProxyRecords();
		records.setCsPersonId("cspersonId");

		Page<BaseEntity> currentPage = new Page<>();
		currentPage.setPageItems(Arrays.asList(records));

		Object[] args = { searchCriteria.getStartDate(),
				StringUtils.isEmpty(searchCriteria.getEndDate()) ? null : searchCriteria.getEndDate() };
		when(paginationHelper.fetchTotalRowsCount(Mockito.any(JdbcTemplate.class), Mockito.any(String.class),
				Mockito.any(Object[].class))).thenReturn(11l);
		when(paginationHelper.fetchPage(jdbcTemplate, searchCriteria.getSqlFetchRows(), args, 1,
				searchCriteria.getPageSize(), searchCriteria.getMapper())).thenReturn(currentPage);

		Mockito.doNothing().when(kafkaPublishService).sendMessage(currentPage, searchCriteria.getTableName());

		ehubDataStoreProcessor.process(searchCriteria);
		
		verify(kafkaPublishService, times(1)).sendMessage(any(Page.class), Mockito.anyString());
	}
	
	@Test
	public void testProcessPPIDWithPageResultsMoreThanPageSize() {

		SearchCriteria searchCriteria = prepareSearchCriteria(20, "20190706", "20201010",
				"ehub_cogs_prsn_proxy_rltnshp_bt", "sqlcount", "sqldata",
				new PersonProxyRecordsRowMapper("EHUBPERSONMSG"));

		PersonProxyRecords records = new PersonProxyRecords();
		records.setCsPersonId("cspersonId");

		Page<BaseEntity> currentPage = new Page<>();
		currentPage.setPageItems(Arrays.asList(records));

		when(paginationHelper.fetchTotalRowsCount(Mockito.any(JdbcTemplate.class), Mockito.any(String.class),
				Mockito.any(Object[].class))).thenReturn(40l);
		when(paginationHelper.fetchPage(Mockito.any(JdbcTemplate.class), Mockito.any(String.class), Mockito.any(Object[].class), Mockito.any(Integer.class),
				Mockito.any(Integer.class), Mockito.any(RowMapper.class))).thenReturn(currentPage);

		Mockito.doNothing().when(kafkaPublishService).sendMessage(currentPage, searchCriteria.getTableName());

		ehubDataStoreProcessor.process(searchCriteria);
		
		verify(kafkaPublishService, times(2)).sendMessage(any(Page.class), Mockito.anyString());
	}
	
	@Test
	public void testProcessPPIDWithPageResultsEmpty() {

		SearchCriteria searchCriteria = prepareSearchCriteria(20, "20190706", "20201010",
				"ehub_cogs_prsn_proxy_rltnshp_bt", "sqlcount", "sqldata",
				new PersonProxyRecordsRowMapper("EHUBPERSONMSG"));

		PersonProxyRecords records = new PersonProxyRecords();
		records.setCsPersonId("cspersonId");

		Page<BaseEntity> currentPage = new Page<>();
		currentPage.setPageItems(Arrays.asList(records));

		Object[] args = { searchCriteria.getStartDate(),
				StringUtils.isEmpty(searchCriteria.getEndDate()) ? null : searchCriteria.getEndDate() };
		when(paginationHelper.fetchTotalRowsCount(Mockito.any(JdbcTemplate.class), Mockito.any(String.class),
				Mockito.any(Object[].class))).thenReturn(0l);
		when(paginationHelper.fetchPage(jdbcTemplate, searchCriteria.getSqlFetchRows(), args, 1,
				searchCriteria.getPageSize(), searchCriteria.getMapper())).thenReturn(currentPage);

		ehubDataStoreProcessor.process(searchCriteria);
		
		verify(kafkaPublishService, times(0)).sendMessage(any(Page.class), Mockito.anyString());
	}


	public SearchCriteria prepareSearchCriteria(int pageSize, String startDate, String endDate, String tblName,
			String countQuery, String dataQuery, RowMapper mapper) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setPageSize(pageSize);
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);
		criteria.setTableName(tblName);
		criteria.setSqlCountRows(countQuery);
		criteria.setSqlFetchRows(dataQuery);
		criteria.setMapper(mapper);
		return criteria;
	}

}
