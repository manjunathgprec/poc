package com.anthem.emep.dckr.microsvc.dataservicexbdf.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.Page;

/**
 * Pagination Helper class to execute db queries with pagination criteria and
 * return page data
 * 
 * @author Krothama
 *
 * @param <E>
 */
@Component
public class PaginationHelper<E> {
	
	public long fetchTotalRowsCount(final JdbcTemplate jt, String sqlCountRows, Object args[]) {
		long totalRows = jt.queryForObject(sqlCountRows, Long.class, args);
		return totalRows;		
	}

	public Page<E> fetchPage(final JdbcTemplate jt,final String sqlFetchRows,
			final Object args[], final int pageNo, final int pageSize, final RowMapper<E> rowMapper) {

		if (pageSize == 0) {
			return null;
		}

		Page<E> page = new Page<E>();

		final int startRow = (pageNo - 1) * pageSize;

		String selectSQL = sqlFetchRows + " limit " + startRow + "," + pageSize;

		jt.query(selectSQL, args, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				final List<E> pageItems = page.getPageItems();
				int currentRow = 0;
				while (rs.next()) {
					pageItems.add(rowMapper.mapRow(rs, currentRow++));
				}
				return page;
			}
		});
		return page;
	}

}
