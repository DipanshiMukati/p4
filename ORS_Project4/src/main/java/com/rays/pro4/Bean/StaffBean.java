package com.rays.pro4.Bean;

import java.sql.Date;

public class StaffBean extends BaseBean {

	public String fullName;
	public Date joiningDate;
	public String division;
	public String previousEmployer;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPreviousEmployer() {
		return previousEmployer;
	}
	public void setPreviousEmployer(String previousEmployer) {
		this.previousEmployer = previousEmployer;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
