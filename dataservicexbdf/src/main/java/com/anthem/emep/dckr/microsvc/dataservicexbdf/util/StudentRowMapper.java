package com.anthem.emep.dckr.microsvc.dataservicexbdf.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.BaseVO;
import com.anthem.emep.dckr.microsvc.dataservicexbdf.model.Student;

public class StudentRowMapper implements RowMapper<BaseVO> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

		Student student = new Student();
		student.setId(rs.getLong("id"));
		student.setName(rs.getString("name"));
		student.setCode(rs.getString("code"));
		student.setDob(rs.getDate("dob"));

		System.out.println(" Student is " + student);
		return student;
	}

}
