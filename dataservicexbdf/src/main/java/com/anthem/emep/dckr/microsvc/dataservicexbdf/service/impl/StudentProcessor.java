package com.anthem.emep.dckr.microsvc.dataservicexbdf.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.BaseVO;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.Page;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.service.IProcessor;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.service.kafka.KafkaPublishService;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.util.PaginationHelper;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.util.StudentRowMapper;

@Service("studentProcessor")
public class StudentProcessor implements IProcessor{

	@Autowired
	private PaginationHelper<BaseVO> paginationHelper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private KafkaPublishService kafkaPublishService;

	@Value("${PAGE_SIZE}")
	int pageSize;
	
	@Value("${SQL_COUNT_ROWS}")
	private String sqlCountRows;
	
	@Value("${SQL_FETCH_ROWS}")
	private String sqlFetchRows;
	
	@Value("${JOB_START_DATE}")
	private String jobStartDate;
	
	@Value("${JOB_END_DATE:#{null}}")
	private String jobEndDate;

	@Override
	public void process() {
		
		System.out.println(" End Date is " + jobEndDate);
		
		Object[] args = {jobStartDate, StringUtils.isEmpty(jobEndDate) ? null : jobEndDate};

		long totalRowsCount = paginationHelper.fetchTotalRowsCount(jdbcTemplate, sqlCountRows, args);
		int pageCount = Math.round(totalRowsCount / pageSize);
		
		if (totalRowsCount > pageCount * pageSize) {
			pageCount++;
		}
		
		Page<BaseVO> currentPage = null;

		for (int pageNo = 1; pageNo <= pageCount; pageNo++) {
			currentPage = paginationHelper.fetchPage(jdbcTemplate, sqlFetchRows, args, pageNo, pageSize,
					new StudentRowMapper());
			kafkaPublishService.sendMessage(currentPage);
		}

	}

}

