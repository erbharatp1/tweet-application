package com.csipl.hrms.service.payroll;

import java.util.List;

import com.csipl.hrms.common.exception.PayRollProcessException;
import com.csipl.hrms.dto.payroll.EpfDTO;
import com.csipl.hrms.model.payroll.Epf;

public interface EpfService {
	public Epf save(Epf epf );
	public Epf saveEpfs(Epf existingEpfDE , Epf newEpf );
// 	public List<Epf> findAllEpfs();
 	public Epf  getEPF( Long companyId );
	public Epf getEPFByPayrollPsMonth(String payPsMonth, Long companyId) throws PayRollProcessException;
	public List<EpfDTO> findAllEpfsDescByDate(Long companyId);
}
