package com.csipl.hrms.dto.payroll.dashboard;

import java.util.Comparator;

import com.csipl.hrms.model.employee.PayStructureHd;

public class ObjectSorter implements Comparator<PayStructureHd> {

	@Override
	public int compare(PayStructureHd o1, PayStructureHd o2) {
		return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
	}
}