package com.csipl.hrms.common.util;

import java.math.BigDecimal;

public class CustomEmpBank {
	
  private String bankName;
  private Integer count;
  private BigDecimal netPayableAmount;
  private int totalCount;
  private BigDecimal totalAmount;
  
  CustomEmpBank(){
	  
  }

public String getBankName() {
	return bankName;
}

public void setBankName(String bankName) {
	this.bankName = bankName;
}

public Integer getCount() {
	return count;
}

public void setCount(Integer count) {
	this.count = count;
}

public BigDecimal getNetPayableAmount() {
	return netPayableAmount;
}

public void setNetPayableAmount(BigDecimal netPayableAmount) {
	this.netPayableAmount = netPayableAmount;
}

public int getTotalCount() {
	return totalCount;
}

public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
}

public BigDecimal getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
}
  
  

}
