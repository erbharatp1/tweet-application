package com.csipl.hrms.common.enums;

public enum StandardEarningEnum {
	
	BasicSalary ( 1 ),
	AdvanceBonus ( 2 ),
	HouseRentAllowance ( 3 ),
	LeaveTravelAllowance ( 5 ),
	ConveyanceAllowance ( 135 ),
	SpecialAllowance ( 134 ), 
//	UniformAllowance ( 6 ),
	MedicalAllowance ( 133 ),
	PerformanceBonus ( 143 ),
//	CompanyBenefits ( 11 ),
//	OtherAllowance(12),
	Arrears(114),
	OverTime(130),
	DearnessAllowance ( 136 ),
	Incentive(142)
	;
	
	
	
	long standardEarning;
	StandardEarningEnum( long standardEarning ){
		this.standardEarning = standardEarning;
	}
	
	public long getStandardEarning() {
		return standardEarning;
	}
	

}
