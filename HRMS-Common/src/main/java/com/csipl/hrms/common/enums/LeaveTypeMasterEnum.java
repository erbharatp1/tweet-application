package com.csipl.hrms.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum LeaveTypeMasterEnum {
	
	Casualleave ( 1 ),
	SickLeave ( 2 ),
	Paidleave ( 3 ),
	Maternityleave( 4),
	CarryForward ( 5 ), 
	LOP ( 6);
	
	 private static final Map<Long, LeaveTypeMasterEnum> MY_MAP = new HashMap<Long, LeaveTypeMasterEnum>();
	  static {
	    for (LeaveTypeMasterEnum myEnum : values()) {
	      MY_MAP.put(myEnum.getLeaveTypeId(), myEnum);
	    }
	  }
	
	
	long leaveTypeId;

	LeaveTypeMasterEnum( long leaveTypeId){
		this.leaveTypeId = leaveTypeId;
		
	}
	
	public long getLeaveTypeId() {
		return leaveTypeId;
	}
	
	public static LeaveTypeMasterEnum getByValue(long value) {
	    return MY_MAP.get(value);
	  }

	
	 public String toString() {
		    return name() ;
		  }

}
