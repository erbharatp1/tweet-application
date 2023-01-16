package com.csipl.hrms.service.report;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csipl.hrms.common.util.DateUtils;
import com.csipl.hrms.dto.employee.EmployeeEducationDTO;
import com.csipl.hrms.dto.employee.EmployeeFamilyDTO;
import com.csipl.hrms.dto.employee.EmployeeStatuaryDTO;
import com.csipl.hrms.dto.report.EmployeeReportDTO;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.employee.ProfessionalInformation;
import com.csipl.hrms.service.adaptor.EmployeeReportAdaptor;
import com.csipl.hrms.service.report.repository.EmployeeReportRepository;
import com.csipl.hrms.service.util.ConverterUtil;

@Service("employeeReportService")
public class EmployeeReportServiceImpl implements EmployeeReportService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeReportServiceImpl.class);

	@Autowired
	private EmployeeReportRepository employeeReportRepository;

	EmployeeReportAdaptor employeeReportAdaptor = new EmployeeReportAdaptor();

	@Override
	public EmployeeReportDTO countEMPIMPTODAYDATE(Long companyId, String value) {

		value = "";
		// TODO Auto-generated method stub

		List<Object[]> empReportList = employeeReportRepository.countEMPIMPTODAYDATE(companyId, value);

		EmployeeReportDTO empDto = new EmployeeReportDTO();

		for (Object[] report : empReportList) {

			empDto.setAnniversarydateCount(ConverterUtil.getString(report[0]));
			empDto.setBirthdaydateCount(ConverterUtil.getString(report[1]));
			empDto.setWorkanniversarydateCount(ConverterUtil.getString(report[2]));
			empDto.setEmpCount(ConverterUtil.getString(report[3]));
			empDto.setDeptCount(ConverterUtil.getString(report[4]));
			empDto.setHolidayCount(ConverterUtil.getString(report[5]));
			empDto.setInitCanCount(ConverterUtil.getString(report[6]));
			empDto.setPenCanCount(ConverterUtil.getString(report[7]));
			empDto.setEmpNoticePeriodCount(ConverterUtil.getString(report[8]));
			empDto.setEmpExitThisMonthCount(ConverterUtil.getString(report[9]));
			empDto.setEmpFinalSettlementCount(ConverterUtil.getString(report[10]));

		}

		return empDto;
	}

	@Override
	public EmployeeReportDTO empTicketStatus(Long companyId, Long userId, String roleName) {

		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.empTicketStatus(companyId, userId, roleName);

		EmployeeReportDTO empDto = new EmployeeReportDTO();

		for (Object[] report : empReportList) {

			empDto.setTicketLogged(ConverterUtil.getString(report[0]));
			empDto.setTicketResolved(ConverterUtil.getString(report[1]));
			empDto.setTicketPending(ConverterUtil.getString(report[2]));

		}

		return empDto;
	}

	@Override
	public EmployeeReportDTO empTicketStatuswithMonth(Long companyId, Long lastMonth) {

		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.empTicketStatuswithMonth(companyId, lastMonth);

		EmployeeReportDTO empDto = new EmployeeReportDTO();

		for (Object[] report : empReportList) {

			empDto.setTicketLogged(ConverterUtil.getString(report[0]));
			empDto.setTicketResolved(ConverterUtil.getString(report[1]));
			empDto.setTicketPending(ConverterUtil.getString(report[2]));

		}

		return empDto;
	}

	@Override
	public List<EmployeeReportDTO> fetchBirthDayEmpList(Long companyId, String value) {
		// TODO Auto-generated method stub data_birhtday

		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.fetchBirthDayEmpList(companyId, value);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpName(ConverterUtil.getString(report[0]));
			empDto.setEmpCode(ConverterUtil.getString(report[1]));
			empDto.setEmpDetp(ConverterUtil.getString(report[2]));
			empDto.setEmpDesignation(ConverterUtil.getString(report[3]));
			empDto.setBirthdaydate(ConverterUtil.getString(report[4]));
			empDto.setEmployeeLogoPath(ConverterUtil.getString(report[5]));
			String first = ConverterUtil.getString(report[6]);
			String last = ConverterUtil.getString(report[7]);

			empDto.setLastName(Character.toString(first.charAt(0)).toUpperCase());
			empDto.setFirstName(Character.toString(last.charAt(0)).toUpperCase());

			
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;

	}

	@Override
	public List<EmployeeReportDTO> fetchAnniversaryDayEmpList(Long companyId, String value) {
		List<Object[]> empReportList = employeeReportRepository.fetchAnniversaryDayEmpList(companyId, value);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpName(ConverterUtil.getString(report[0]));
			empDto.setEmpCode(ConverterUtil.getString(report[1]));
			empDto.setEmpDetp(ConverterUtil.getString(report[2]));
			empDto.setEmpDesignation(ConverterUtil.getString(report[3]));
			empDto.setAnniversarydate(ConverterUtil.getString(report[4]));
			empDto.setEmployeeLogoPath(ConverterUtil.getString(report[5]));
			String first = ConverterUtil.getString(report[6]);
			String last = ConverterUtil.getString(report[7]);

			empDto.setLastName(Character.toString(first.charAt(0)).toUpperCase());
			empDto.setFirstName(Character.toString(last.charAt(0)).toUpperCase());
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> fetchHolidayListByMonth(Long companyId, String value) throws ParseException {
		List<Object[]> empReportList = employeeReportRepository.fetchAnniversaryDayEmpList(companyId, value);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setHolidayName(ConverterUtil.getString(report[0]));
			empDto.setFromDateString(ConverterUtil.getString(report[1]));
			empDto.setToDateString(ConverterUtil.getString(report[2]));
			empDto.setDay(ConverterUtil.getLong(report[3]));

			empDto.setFromDate(ConverterUtil.getDate(empDto.getFromDateString()));
			empDto.setToDate(ConverterUtil.getDate(empDto.getToDateString()));
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
			String parameter;
			if (empDto.getDay() > 1) {
				String delim = "";
				parameter = "";

				Calendar c = Calendar.getInstance();
				c.setTime(empDto.getFromDate());
				Date date = empDto.getFromDate();

				for (int i = 0; i < empDto.getDay(); i++) {

					parameter = parameter.concat(delim);
					delim = ",";
					parameter = parameter.concat(simpleDateformat.format(date));

					c.add(Calendar.DATE, 1);
					date = c.getTime();
				}

			} else
				parameter = simpleDateformat.format(empDto.getFromDate());
			empDto.setDaysName(parameter);
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> fetchWorkAnniversaryDayEmpList(Long companyId, String value) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.fetchWorkAnniversaryDayEmpList(companyId, value);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpName(ConverterUtil.getString(report[0]));
			empDto.setEmpCode(ConverterUtil.getString(report[1]));
			empDto.setEmpDetp(ConverterUtil.getString(report[2]));
			empDto.setEmpDesignation(ConverterUtil.getString(report[3]));
			empDto.setWorkanniversarydate(ConverterUtil.getString(report[4]));
			empDto.setEmployeeLogoPath(ConverterUtil.getString(report[5]));
			String first = ConverterUtil.getString(report[6]);
			String last = ConverterUtil.getString(report[7]);

			empDto.setLastName(Character.toString(first.charAt(0)).toUpperCase());
			empDto.setFirstName(Character.toString(last.charAt(0)).toUpperCase());
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> fetchEmployeeDocumentConfirmation(Long companyId) {
		// TODO Auto-generated method stub Name UI UA ES BA AI MI
		List<Object[]> empReportList = employeeReportRepository.fetchEmployeeDocumentConfirmation(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		String fristName = null;
		String lastName = null;
		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			if (ConverterUtil.getString(report[0]) != null) {
				empDto.setEmpName(ConverterUtil.getString(report[0]));
			}

			if (ConverterUtil.getString(report[1]) != null) {
				empDto.setEmpCode(ConverterUtil.getString(report[1]));
			}

			if (ConverterUtil.getString(report[2]) != null) {
				empDto.setEmployeeLogoPath(ConverterUtil.getString(report[2]));
			}

			if (ConverterUtil.getString(report[3]) != null) {
				empDto.setEmpDesignation(ConverterUtil.getString(report[3]));
			}

			if (ConverterUtil.getString(report[4]) != null) {
				empDto.setEmpDetp(ConverterUtil.getString(report[4]));
			}

			if (ConverterUtil.getString(report[5]) != null) {
				empDto.setuId(ConverterUtil.getString(report[5]));
			}

			if (ConverterUtil.getString(report[6]) != null) {
				empDto.setuA(ConverterUtil.getString(report[6]));
			}

			if (ConverterUtil.getString(report[7]) != null) {
				empDto.seteS(ConverterUtil.getString(report[7]));
			}

			if (ConverterUtil.getString(report[8]) != null) {
				empDto.setbA(ConverterUtil.getString(report[8]));
			}

			if (ConverterUtil.getString(report[9]) != null) {
				empDto.setaI(ConverterUtil.getString(report[9]));
			}

			if (ConverterUtil.getString(report[10]) != null) {
				empDto.setmI(ConverterUtil.getString(report[10]));
			}
			if (ConverterUtil.getString(report[11]) != null) {
				fristName = ConverterUtil.getString(report[11]);
			}

			if (ConverterUtil.getString(report[12]) != null) {
				lastName = ConverterUtil.getString(report[12]);
			}
			/*
			 * if (ConverterUtil.getString(report[13]) != null) {
			 * empDto.setPan(ConverterUtil.getString(report[13])); }
			 */
			empDto.setFirstName(Character.toString(fristName.charAt(0)).toUpperCase());
			empDto.setLastName(Character.toString(lastName.charAt(0)).toUpperCase());
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> fetchDesignationWiseCTC(Long companyId, Long p_process_month) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.fetchDesignationWiseCTCList(companyId, p_process_month);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		List<String> colorDegiCount = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
				"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888", "#ffbe3c", "#375baf",
				"#ff6060", "#1a84cc", "#845cee", "#2095f2", "#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350",
				"#2095f2", "#888888", "#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2", "#ff6060",
				"#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888", "#ffbe3c", "#375baf", "#ff6060",
				"#1a84cc", "#845cee", "#2095f2", "#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2",
				"#888888");

		int j = 0;
		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpDesignation(ConverterUtil.getString(report[0]));
			empDto.setEmpCtc(ConverterUtil.getString(report[1]));
			empDto.setColor(colorDegiCount.get(j));
			j++;
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> fetchHeadCountByBankPay(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.fetchHeadCountByBankPay(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setBankPer(ConverterUtil.getString(report[0]));
			empDto.setEmpBankName(ConverterUtil.getString(report[1]));
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> empPayrollstatusbyMonth(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.empPayrollstatusbyMonth(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setProcessMonth(ConverterUtil.getString(report[0]));
			empDto.setEmpDetp(ConverterUtil.getString(report[1]));
			if (ConverterUtil.getString(report[2]) != null) {
				empDto.setGenStatus("Done");
			} else {
				empDto.setGenStatus("Pending");
			}
			if (ConverterUtil.getString(report[3]) != null) {
				empDto.setDisStatus("Done");
			} else {
				empDto.setDisStatus("Pending");
			}
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> fetchEmpPfContribution(Long companyId, Long p_process_month) {
		// TODO Auto-generated method stub fetchEmpPfContribution
		List<Object[]> empReportList = employeeReportRepository.fetchEmpPfContribution(companyId, p_process_month);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			if (ConverterUtil.getString(report[0]) != null) {
				empDto.setAmtEmployee(ConverterUtil.getString(report[0]));
			}
			if (ConverterUtil.getString(report[1]) != null) {
				empDto.setAmtEmployer(ConverterUtil.getString(report[1]));
			}
			if (ConverterUtil.getString(report[2]) != null) {
				empDto.setAmtPension(ConverterUtil.getString(report[2]));
			}

			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> fetchEmpESIContribution(Long companyId, Long p_process_month) {
		// TODO Auto-generated method stub fetchEmpPfContribution
		List<Object[]> empReportList = employeeReportRepository.fetchEmpESIContribution(companyId, p_process_month);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			if (ConverterUtil.getString(report[0]) != null) {
				empDto.setAmtEmployee(ConverterUtil.getString(report[0]));
				// empDto.setEmployeeAmt(BigDecimal.valueOf(empDto.getAmtEmployee()));
			}
			if (ConverterUtil.getString(report[1]) != null) {
				empDto.setAmtEmployer(ConverterUtil.getString(report[1]));
				// empDto.setEmployeerAmt(Long.valueOf(empDto.getAmtEmployer()));
			}

			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public EmployeeReportDTO countNotification(Long companyId) {
		// TODO Auto-generated method stub
		String value = " ";
		List<Object> empReportList = employeeReportRepository.countNotification(companyId, value);
		EmployeeReportDTO empDto = new EmployeeReportDTO();
		if (ConverterUtil.getString(empReportList.get(0)).equals("0")) {
			empDto.setHeadcountNotification("");
		} else {
			empDto.setHeadcountNotification(ConverterUtil.getString(empReportList.get(0)));

		}



		return empDto;

	}

	@Override
	public List<EmployeeReportDTO> employeeNotification(Long companyId, String value) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.employeeNotification(companyId, value);
		List<EmployeeReportDTO> employeeReportList = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto0 = new EmployeeReportDTO();
			EmployeeReportDTO empDto1 = new EmployeeReportDTO();

			empDto0.setNotification(ConverterUtil.getString(report[0]));
			empDto0.setCountNotification(ConverterUtil.getString(report[1]));
			empDto0.setUrl("/empMaster/ticketManagament-list");
			if (Integer.parseInt(ConverterUtil.getString(report[1])) > 0) {
				employeeReportList.add(empDto0);
			}

			empDto1.setNotification(ConverterUtil.getString(report[2]));
			empDto1.setCountNotification(ConverterUtil.getString(report[3]));
			empDto1.setUrl("/empMaster/resignationList");
			if (Integer.parseInt(ConverterUtil.getString(report[3])) > 0) {
				employeeReportList.add(empDto1);
			}




		}

		return employeeReportList;
	}

	@Override
	public List<EmployeeReportDTO> fetchEmployeeSeprationInfo(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.fetchEmployeeSeprationInfo(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			if (ConverterUtil.getString(report[0]) != null) {
				empDto.setEmpName(ConverterUtil.getString(report[0]));
			}

			if (ConverterUtil.getString(report[1]) != null) {
				empDto.setEmpCode(ConverterUtil.getString(report[1]));
			}

			if (ConverterUtil.getString(report[2]) != null) {
				empDto.setEmpDesignation(ConverterUtil.getString(report[2]));
			}

			if (ConverterUtil.getString(report[3]) != null) {
				empDto.setEmpDetp(ConverterUtil.getString(report[3]));
			}

			if (ConverterUtil.getString(report[4]) != null) {
				empDto.setDateCreated(ConverterUtil.getString(report[4]));
			}

			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> empGenderWiseRatio(Long companyId) {
		// TODO Auto-generated method stub

		List<Object[]> empReportList = employeeReportRepository.empGenderWiseRatio(companyId);

		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {

			EmployeeReportDTO empDto = new EmployeeReportDTO();

			empDto.setFemalePer(ConverterUtil.getString(report[0]));
			empDto.setMalePer(ConverterUtil.getString(report[1]));
			empDto.setOtherPer(ConverterUtil.getString(report[2]));
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;

	}

	@Override
	public List<EmployeeReportDTO> empAgeWiseRatio(Long companyId) {
		// TODO Auto-generated method stub

		List<Object[]> empReportList = employeeReportRepository.empAgeWiseRatio(companyId);

		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {

			EmployeeReportDTO e1 = new EmployeeReportDTO();
			EmployeeReportDTO e2 = new EmployeeReportDTO();
			EmployeeReportDTO e3 = new EmployeeReportDTO();
			EmployeeReportDTO e4 = new EmployeeReportDTO();
			EmployeeReportDTO e5 = new EmployeeReportDTO();

			e1.setEmpAge(ConverterUtil.getString(report[0]));
			e1.setEmpRange("Age: 15-20");
			e1.setColor("#28aaf8");
			e2.setEmpAge(ConverterUtil.getString(report[1]));
			e2.setEmpRange("Age: 20-30");
			e2.setColor("#4990fa");
			e3.setEmpAge(ConverterUtil.getString(report[2]));
			e3.setEmpRange("Age: 30-40");
			e3.setColor("#97d1f6");
			e4.setEmpAge(ConverterUtil.getString(report[3]));
			e4.setEmpRange("Age: 40-50");
			e4.setColor("#93d1f6");
			e5.setEmpAge(ConverterUtil.getString(report[4]));
			e5.setEmpRange("Age: 50-60");
			e5.setColor("#0278be");
			employeeReportDTO.add(e1);
			employeeReportDTO.add(e2);
			employeeReportDTO.add(e3);
			employeeReportDTO.add(e4);
			employeeReportDTO.add(e5);
		}

		return employeeReportDTO;

	}

	@Override
	public List<EmployeeReportDTO> departmentWiseRatio(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.departmentWiseRatio(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();

			empDto.setEmpDetp(ConverterUtil.getString(report[1]));
			empDto.setDeptCount(ConverterUtil.getString(report[2]));
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> EmpAttritionofResigned(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.EmpAttritionofResigned(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();

			empDto.setEmpCount(ConverterUtil.getString(report[0]));
			empDto.setProcessMonth(ConverterUtil.getString(report[1]));
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> EmpAttritionofJoined(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.EmpAttritionofJoined(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();

			empDto.setEmpCount(ConverterUtil.getString(report[0]));
			empDto.setProcessMonth(ConverterUtil.getString(report[1]));
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> empCompanyAnnouncement(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.empCompanyAnnouncement(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();

			empDto.setTitle(ConverterUtil.getString(report[0]));
			empDto.setDesc(ConverterUtil.getString(report[1]));
			empDto.setDepartmentName(ConverterUtil.getString(report[2]));
			empDto.setDateFrom(ConverterUtil.getString(report[3]));
			empDto.setDateTo(ConverterUtil.getString(report[4]));
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> empCountByDesignationWise(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.empCountByDesignationWise(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		List<String> colorDegiCount = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
				"#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#888888", "#2a9eee", "#f650f8", "#f89843", "#01cb83",
				"#ffbe3c", "#845cee", "#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2", "#a3a1fb",
				"#86b800", "#b9b9b9", "#f8b350", "#888888", "#2a9eee", "#f650f8", "#f89843", "#01cb83", "#ffbe3c",
				"#845cee", "#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2", "#a3a1fb", "#86b800",
				"#b9b9b9", "#f8b350", "#888888", "#2a9eee", "#f650f8", "#f89843", "#01cb83", "#ffbe3c", "#845cee");

		int j = 0;
		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();

			empDto.setEmpCount(ConverterUtil.getString(report[0]));
			empDto.setEmpDesignation(ConverterUtil.getString(report[1]));
			empDto.setColor(colorDegiCount.get(j));


			j++;
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> empCountByDepartmentWise(Long companyId) {
		// TODO Auto-generated method stub
		List<Object[]> empReportList = employeeReportRepository.empCountByDepartmentWise(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		List<String> colorDegiCount = Arrays.asList("#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2",
				"#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888", "#ffbe3c", "#375baf",
				"#ff6060", "#1a84cc", "#845cee", "#2095f2", "#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350",
				"#2095f2", "#888888", "#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2", "#ff6060",
				"#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888", "#ffbe3c", "#375baf", "#ff6060",
				"#1a84cc", "#845cee", "#2095f2", "#ff6060", "#a3a1fb", "#86b800", "#b9b9b9", "#f8b350", "#2095f2",
				"#888888", "#ffbe3c", "#375baf", "#ff6060", "#1a84cc", "#845cee", "#2095f2", "#ff6060", "#a3a1fb",
				"#86b800", "#b9b9b9", "#f8b350", "#2095f2", "#888888");

		int j = 0;

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpCount(ConverterUtil.getString(report[0]));
			empDto.setEmpDetp(ConverterUtil.getString(report[1]));
			empDto.setColor(colorDegiCount.get(j));

			j++;
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<Object[]> findEmployeesReportStatusBased(String status, Long companyId, List<Long> departmentList) {
		DateUtils dateUtils = new DateUtils();
		String currentDate = dateUtils.getCurrentDateTime();
		Timestamp ts = dateUtils.getCurrentDateWithTimestamp(currentDate);

		return employeeReportRepository.findEmployeesReportStatusBased11(companyId, status, departmentList);// ts
	}

	public List<Employee> findEmployeesReportDeptAndStatusBased(Long companyId, Date fromDate1, Date toDate1,
			Long departmentId, String status) {
		if (departmentId == 0) {
			if (status.equals("DE")) {
				return employeeReportRepository.findDeactivateEmployeesReportDurationBased(companyId, status, fromDate1,
						toDate1);
			} else {
				return employeeReportRepository.findEmployeesReportStatusAndDurationBased(companyId, status, fromDate1,
						toDate1);
			}
		} else {
			if (status.equals("DE")) {
				return employeeReportRepository.findDeactivateEmployeesReportDurationAndDeptBased(companyId,
						departmentId, status, fromDate1, toDate1);
			} else {
				return employeeReportRepository.findEmployeesReportDeptAndStatusBased(companyId, departmentId, status,
						fromDate1, toDate1);
			}
		}
	}

	@Override
	public List<EmployeeReportDTO> empAttendanceRatio(Long companyId, Long employeeId) {
		List<Object[]> empAttendanceReportList = employeeReportRepository.empAttendanceRatio(companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (Object[] report : empAttendanceReportList) {

			EmployeeReportDTO e1 = new EmployeeReportDTO();
			EmployeeReportDTO e2 = new EmployeeReportDTO();
			EmployeeReportDTO e3 = new EmployeeReportDTO();
			/*
			 * EmployeeReportDTO e4 = new EmployeeReportDTO(); EmployeeReportDTO e5 = new
			 * EmployeeReportDTO();
			 */

			e1.setEmpAttenance(ConverterUtil.getString(report[0]));
			e1.setEmpAttendanceeRange("presense");
			e2.setEmpAttenance(ConverterUtil.getString(report[1]));
			e2.setEmpAttendanceeRange("on leave");
			Long absent = Long.valueOf(maxDay) - (ConverterUtil.getLong(report[0]) + ConverterUtil.getLong(report[1]));
			e3.setEmpAttenance(ConverterUtil.getString(absent));
			e3.setEmpAttendanceeRange("absent");
			employeeReportDTO.add(e1);
			employeeReportDTO.add(e2);
			employeeReportDTO.add(e3);
			// employeeReportDTO.add(e3);
			// employeeReportDTO.add(e4);
			// employeeReportDTO.add(e5);
		}

		return employeeReportDTO;
	}

	@Override
	public List<Object[]> getUpcomingBDayList(Long companyId) {
		// TODO Auto-generated method stub
		return employeeReportRepository.getUpcomingBDayList(companyId);
	}

	@Override
	public List<Object[]> getAnniversaryList(Long companyId) {
		// TODO Auto-generated method stub
		return employeeReportRepository.getAnniversaryList(companyId);
	}

	@Override
	public List<Object[]> getWorkAnniversaryList(Long companyId) {
		return employeeReportRepository.getTodayWorkAnniversaryList(companyId);
	}

	@Override
	public List<Object[]> findFormerEmployeeReport(Long longcompanyId, Date fromDate1, Date toDate1,
			List<Long> departmentList) {
		DateUtils dateUtils = new DateUtils();
		String currentDate = dateUtils.getCurrentDateTime();
		Timestamp ts = dateUtils.getCurrentDateWithTimestamp(currentDate);

		return employeeReportRepository.findFormerEmployeeReport(longcompanyId, fromDate1, toDate1, departmentList);
	}

	@Override
	public List<EmployeeReportDTO> fetchLastTwelveMonthSalary(Long companyId, Long p_process_month) {
		List<Object[]> empReportList = employeeReportRepository.fetchLastTwelveMonthSalary(companyId, p_process_month);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();

		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpDesignation(ConverterUtil.getString(report[0]));
			empDto.setEmpCtc(ConverterUtil.getString(report[1]));
			employeeReportDTO.add(empDto);

		}

		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getMyTeamToday(String leaveFlagEnum, Long companyId, Long employeeId) {
		List<Object[]> empReportList = employeeReportRepository.getMyTeamToday(leaveFlagEnum, companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		/**
		 * e.employeeCode,e.firstName,e.lastName,degi.designationName,dept.departmentName
		 * ,e.employeeLogoPath, tl.fromDate,tl.toDate,tl.days
		 */
		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpCode(ConverterUtil.getString(report[0]));
			empDto.setFirstName(ConverterUtil.getString(report[1]));
			empDto.setLastName(ConverterUtil.getString(report[2]));
			empDto.setEmpName(empDto.getFirstName() + " " + empDto.getLastName());
			empDto.setEmpDesignation(ConverterUtil.getString(report[3]));
			empDto.setEmpDetp(ConverterUtil.getString(report[4]));
			empDto.setEmployeeLogoPath(ConverterUtil.getString(report[5]));
			empDto.setFromDateString(ConverterUtil.getString(report[6]));
			empDto.setToDateString(ConverterUtil.getString(report[7]));

			empDto.setFromDate(ConverterUtil.getDate(empDto.getFromDateString()));
			empDto.setToDate(ConverterUtil.getDate(empDto.getFromDateString()));

			empDto.setDay(ConverterUtil.getBigDecimal(report[8]).longValue());

			String first = ConverterUtil.getString(report[1]);
			String last = ConverterUtil.getString(report[2]);
			empDto.setFirstName(Character.toString(first.charAt(0)).toUpperCase());
			empDto.setLastName(Character.toString(last.charAt(0)).toUpperCase());
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
			String parameter;
			if (empDto.getDay() > 1) {
				String delim = "";
				parameter = "";

				Calendar c = Calendar.getInstance();
				c.setTime(empDto.getFromDate());
				Date date = empDto.getFromDate();

				for (int i = 0; i < empDto.getDay(); i++) {

					parameter = parameter.concat(delim);
					delim = ",";
					parameter = parameter.concat(simpleDateformat.format(date));

					c.add(Calendar.DATE, 1);
					date = c.getTime();
				}

			} else
				parameter = simpleDateformat.format(empDto.getFromDate());
			empDto.setDaysName(parameter);

			employeeReportDTO.add(empDto);
			logger.info("hello  " + empDto.toString());
		}
		return employeeReportDTO;

	}

	@Override
	public List<EmployeeReportDTO> getMyTeamMonthCount(String flag, Long companyId, Long employeeId) {
		List<Object[]> empReportList = employeeReportRepository.getMyTeamToday(flag, companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setTeamOnLeaveToday(ConverterUtil.getLong(report[0]));
			empDto.setTeamOnLeaveMonth(ConverterUtil.getLong(report[1]));
			employeeReportDTO.add(empDto);
		}
		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getCountNotificationLeave(Long companyId, Long employeeId) {
		List<Object[]> empReportList = employeeReportRepository.getCountNotificationLeave(companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : empReportList) {
			EmployeeReportDTO empLeave = new EmployeeReportDTO();

			if (ConverterUtil.getString(report[0]) != null) {
				empLeave.setLeaveCount(ConverterUtil.getString(report[0]));
				empLeave.setTotalCountNotify(ConverterUtil.getLong(report[0]) + ConverterUtil.getLong(report[1])
						+ ConverterUtil.getLong(report[2]) + ConverterUtil.getLong(report[3])
						+ ConverterUtil.getLong(report[4]));

			}
			if (ConverterUtil.getString(report[1]) != null) {
				empLeave.setSeprationCount(ConverterUtil.getString(report[1]));

			}
			if (ConverterUtil.getString(report[2]) != null) {
				empLeave.setCompOffCount(ConverterUtil.getString(report[2]));

			}
			if (ConverterUtil.getString(report[3]) != null) {
				empLeave.setArrequestCount(ConverterUtil.getString(report[3]));

			}
			if (ConverterUtil.getString(report[4]) != null) {
				empLeave.setHelpCount(ConverterUtil.getString(report[4]));

			}

			employeeReportDTO.add(empLeave);

		}

		/**
		 * leaveCount seprationCount compOffCount arrequestCount helpCount
		 */
		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getCountNotificationLeaveBy(Long companyId, Long employeeId) {
		List<Object[]> objects = employeeReportRepository.getCountNotificationLeaveBy(companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : objects) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setFirstName(ConverterUtil.getString(report[0]));
			empDto.setLastName(ConverterUtil.getString(report[1]));
			empDto.setEmployeeRemark(ConverterUtil.getString(report[2]));
			empDto.setDateCreated(ConverterUtil.getString(report[3]));
			empDto.setEmpName(empDto.getFirstName() + " " + empDto.getLastName());
			employeeReportDTO.add(empDto);
		}
		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getCountNotificationArRequest(Long companyId, Long employeeId) {
		List<Object[]> objects = employeeReportRepository.getCountNotificationArRequest(companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : objects) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setFirstName(ConverterUtil.getString(report[0]));
			empDto.setLastName(ConverterUtil.getString(report[1]));
			empDto.setEmployeeRemark(ConverterUtil.getString(report[2]));
			empDto.setDateCreated(ConverterUtil.getString(report[3]));
			empDto.setEmpName(empDto.getFirstName() + " " + empDto.getLastName());
			employeeReportDTO.add(empDto);
		}
		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getCountNotificationCompOff(Long companyId, Long employeeId) {
		List<Object[]> objects = employeeReportRepository.getCountNotificationCompOff(companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : objects) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setFirstName(ConverterUtil.getString(report[0]));
			empDto.setLastName(ConverterUtil.getString(report[1]));
			empDto.setEmployeeRemark(ConverterUtil.getString(report[2]));
			empDto.setDateCreated(ConverterUtil.getString(report[3]));
			empDto.setEmpName(empDto.getFirstName() + " " + empDto.getLastName());
			employeeReportDTO.add(empDto);
		}
		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getCountNotificationSepration(Long companyId, Long employeeId) {
		List<Object[]> objects = employeeReportRepository.getCountNotificationSepration(companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : objects) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setFirstName(ConverterUtil.getString(report[0]));
			empDto.setLastName(ConverterUtil.getString(report[1]));
			empDto.setEmployeeRemark(ConverterUtil.getString(report[2]));
			empDto.setDateCreated(ConverterUtil.getString(report[3]));
			empDto.setEmpName(empDto.getFirstName() + " " + empDto.getLastName());
			employeeReportDTO.add(empDto);
		}
		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getCountNotificationHelp(Long companyId, Long employeeId) {
		List<Object[]> objects = employeeReportRepository.getCountNotificationHelp(companyId, employeeId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : objects) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setFirstName(ConverterUtil.getString(report[0]));
			empDto.setLastName(ConverterUtil.getString(report[1]));
			empDto.setEmployeeRemark(ConverterUtil.getString(report[2]));
			empDto.setDateCreated(ConverterUtil.getString(report[3]));
			empDto.setEmpName(empDto.getFirstName() + " " + empDto.getLastName());
			employeeReportDTO.add(empDto);
		}
		return employeeReportDTO;
	}

	@Override
	public List<EmployeeReportDTO> getCountLeave(Long companyId) {
		List<Object[]> objects = employeeReportRepository.getCountLeave(companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		for (Object[] report : objects) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setAllLeaveCount(ConverterUtil.getBigDecimal(report[0]).longValue());
			employeeReportDTO.add(empDto);
		}
		return employeeReportDTO;
	}

	@Override
	public List<Object[]> getIdAddressProofsReport(Long companyId, Long employeeId, List<Long> departmentIds,
			String activeStatus) {

		if (employeeId != 0) {
			return employeeReportRepository.getEmployeeIdProofsDataEmployeeWise(companyId, employeeId);
		} else if (departmentIds.size() > 0) {
			return employeeReportRepository.getEmployeeIdProofsDataDepartmentWise(companyId, departmentIds,
					activeStatus);
		}
		return null;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@Override
	public List<Object[]> getAccidentalInsuranceReport(Long companyId, Long employeeId, List<Long> departmentIds,
			String activeStatus) {

		if (employeeId > 0) {
			return employeeReportRepository.getEmployeeAccidentalInsDataEmployeeWise(companyId, employeeId);
		} else if (departmentIds.size() > 0) {
			return employeeReportRepository.getEmployeeAccidentalInsDataDepartmentWise(companyId, departmentIds,
					activeStatus);
		}
		return null;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@Override
	public List<Object[]> getPfUANNumbersReport(Long companyId, Long employeeId, List<Long> departmentIds,
			String activeStatus) {
		if (employeeId != 0) {
			return employeeReportRepository.getEmployeePfDataEmployeeWise(companyId, employeeId);
		} else if (departmentIds.size() > 0) {
			return employeeReportRepository.getEmployeePfDataDepartmentWise(companyId, departmentIds, activeStatus);
		}
		return null;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@Override
	public Map<String, List<EmployeeFamilyDTO>> getFamilyDetailsReport(Long companyId, Long employeeId,
			List<Long> departmentIds, String activeStatus) {

		if (employeeId != 0L) {

			List<EmployeeFamilyDTO> employeeList = employeeReportAdaptor.objectListToFamilyDetailsReport(
					employeeReportRepository.getFamilyDetailsEmpWise(companyId, employeeId));
			Map<String, List<EmployeeFamilyDTO>> map = new HashMap<>();
			map.put("empCode", employeeList);
			return map;
		} else if (departmentIds.size() > 0) {

			List<Object[]> employee = employeeReportRepository.getFamilyDetailsDeptWise(companyId, departmentIds,
					activeStatus);

			List<EmployeeFamilyDTO> employeeFamilyDTOList = employeeReportAdaptor
					.objectListToFamilyDetailsReport(employee);

			Map<String, List<EmployeeFamilyDTO>> map = new HashMap<>(); // Put Into
			List<EmployeeFamilyDTO> employeeList = new ArrayList<EmployeeFamilyDTO>(); // Put Into

			for (EmployeeFamilyDTO value : employeeFamilyDTOList) {

				if (map.containsKey(value.getEmpCode())) { // when one emp Code has multiple set of data
					employeeList.add(value);
					map.put(value.getEmpCode(), employeeList);
				} else {
					employeeList = new ArrayList<>(); // when one emp Code has One set of data
					employeeList.add(value);
					map.put(value.getEmpCode(), employeeList);
				}
			}

			return map;
		}
		return null;
	}

	/**
	 * @author ${Nisha Parveen}
	 *
	 */
	@Override
	public Map<String, List<EmployeeEducationDTO>> getEducationalDetailsReport(Long companyId, Long employeeId,
			List<Long> departmentIds, String activeStatus) {

		if (employeeId != 0L) {

			List<EmployeeEducationDTO> empList = employeeReportAdaptor.objectListToEducationalDetailsReport(
					employeeReportRepository.getEducationalDetailsEmpWise(companyId, employeeId));

			Map<String, List<EmployeeEducationDTO>> map = new HashMap<>();

			map.put("empCode", empList);

			return map;
		} else if (departmentIds.size() > 0) {
			List<Object[]> employee = employeeReportRepository.getEducationalDetailsDeptWise(companyId, departmentIds,
					activeStatus);

			List<EmployeeEducationDTO> employeeEducationDTOList = employeeReportAdaptor
					.objectListToEducationalDetailsReport(employee);

			Map<String, List<EmployeeEducationDTO>> map = new HashMap<>();

			List<EmployeeEducationDTO> empList = new ArrayList<EmployeeEducationDTO>();

			for (EmployeeEducationDTO value : employeeEducationDTOList) {

				if (map.containsKey(value.getEmpCode())) {
					empList.add(value);
					map.put(value.getEmpCode(), empList);
				} else {
					empList = new ArrayList<EmployeeEducationDTO>();
					empList.add(value);
					map.put(value.getEmpCode(), empList);
				}
			}
			return map;
		}
		return null;

	}

	@Override
	public List<Object[]> getEsicReport(Long companyId, Long employeeId, List<Long> departmentList, String status) {

		if (employeeId != 0L)
			return employeeReportRepository.getEsicReportEmloyeeWise(companyId, employeeId);// 5
		else if (departmentList.size() > 0)
			return employeeReportRepository.getEsicReportDepartmentWise(companyId, status, departmentList);// 6
		return null;

	}

	@Override
	public List<Object[]> getMedicalInsuranceReport(Long companyId, Long employeeId, List<Long> departmentList,
			String status) {

		if (employeeId != 0L)
			return employeeReportRepository.getMedicalInsuranceReportEmloyeeWise(companyId, employeeId);
		else if (departmentList.size() > 0)
			return employeeReportRepository.getMedicalInsuranceReportDepartmentWise(companyId, status, departmentList);
		return null;

	}

	@Override
	public Map<String, List<ProfessionalInformation>> getProfessionalDetailsReport(Long companyId, Long employeeId,
			String status, List<Long> departmentIds) {

		if (employeeId != 0L) {

			List<ProfessionalInformation> professionalInformationDtoList = employeeReportAdaptor
					.objectListToDtoProfDetailsList(
							employeeReportRepository.getProfessionalDetailsReportEmloyeeWise(companyId, employeeId));

			Map<String, List<ProfessionalInformation>> map = new HashMap<>();
			map.put("empCode", professionalInformationDtoList);
			return map;

		} else if (departmentIds.size() > 0) {

			List<Object[]> objects = employeeReportRepository.getProfessionalDetailsReportDepartmentWise(companyId,
					status, departmentIds);

			List<ProfessionalInformation> professionalInformationDTOList = employeeReportAdaptor
					.objectListToDtoProfDetailsList(objects);

			Map<String, List<ProfessionalInformation>> map = new HashMap<>();
			List<ProfessionalInformation> professionalInformationList = new ArrayList<ProfessionalInformation>();

			for (ProfessionalInformation value : professionalInformationDTOList) {

				if (map.containsKey(value.getEmpCode())) {

					professionalInformationList.add(value);

					map.put(value.getEmpCode(), professionalInformationList);
				} else {

					professionalInformationList = new ArrayList<>();

					professionalInformationList.add(value);
					map.put(value.getEmpCode(), professionalInformationList);
				}
			}

			return map;
		}
		return null;
	}

	// Nominee Details
	@Override
	public Map<String, List<EmployeeStatuaryDTO>> getNomineeDetailsReport(Long companyId, Long employeeId,
			String status, List<Long> departmentList) {

		if (employeeId != 0L) {

			List<EmployeeStatuaryDTO> employeeStatuaryDTOList = employeeReportAdaptor.objectListToDtoNomineeDetailsList(
					employeeReportRepository.getNomineeDetailsReportEmloyeeWise(companyId, employeeId));

			Map<String, List<EmployeeStatuaryDTO>> map = new HashMap<>();
			map.put("empCode", employeeStatuaryDTOList);
			return map;

		} else if (departmentList.size() > 0) {

			List<Object[]> objectList = employeeReportRepository.getNomineeDetailsReportDepartmentWise(companyId,
					status, departmentList);

			List<EmployeeStatuaryDTO> employeeStatuaryDTOList = employeeReportAdaptor
					.objectListToDtoNomineeDetailsList(objectList);

			Map<String, List<EmployeeStatuaryDTO>> map = new HashMap<>();
			List<EmployeeStatuaryDTO> nomineeDetailsList = new ArrayList<EmployeeStatuaryDTO>();

			for (EmployeeStatuaryDTO value : employeeStatuaryDTOList) {

				if (map.containsKey(value.getEmpCode())) {

					nomineeDetailsList.add(value);

					map.put(value.getEmpCode(), nomineeDetailsList);
				} else {

					nomineeDetailsList = new ArrayList<>();

					nomineeDetailsList.add(value);
					map.put(value.getEmpCode(), nomineeDetailsList);
				}
			}

			return map;
		}

		return null;
	}

	@Override
	public List<Object[]> getEmployeesOnNoticePeriod(Long companyId, Date fDate, Date tDate) {
		// TODO Auto-generated method stub
		return employeeReportRepository.getEmployeesOnNoticePeriod(companyId, fDate, tDate);
	}

	@Override
	public List<Object[]> getLanguageKnownStatusReport(Long companyId, Long employeeId, String activeStatus,
			List<Long> departmentIds) {
		if (employeeId > 0) {

			return employeeReportRepository.getLanguageStatusEmpWise(companyId, employeeId);

		} else if (departmentIds.size() > 0) {

			return employeeReportRepository.getLanguageStatusDeptWise(companyId, activeStatus, departmentIds);
		}

		return null;
	}

	@Override
	public List<EmployeeReportDTO> allEmployeeLeaveToday(String leaveFlagEnum, Long companyId) {
		List<Object[]> empReportList = employeeReportRepository.allEmployeeLeaveToday(leaveFlagEnum, companyId);
		List<EmployeeReportDTO> employeeReportDTO = new ArrayList<EmployeeReportDTO>();
		/**
		 * e.employeeCode,e.firstName,e.lastName,degi.designationName,dept.departmentName
		 * ,e.employeeLogoPath, tl.fromDate,tl.toDate,tl.days
		 */
		for (Object[] report : empReportList) {
			EmployeeReportDTO empDto = new EmployeeReportDTO();
			empDto.setEmpCode(ConverterUtil.getString(report[0]));
			empDto.setFirstName(ConverterUtil.getString(report[1]));
			empDto.setLastName(ConverterUtil.getString(report[2]));
			empDto.setEmpName(empDto.getFirstName() + " " + empDto.getLastName());
			empDto.setEmpDesignation(ConverterUtil.getString(report[3]));
			empDto.setEmpDetp(ConverterUtil.getString(report[4]));
			empDto.setEmployeeLogoPath(ConverterUtil.getString(report[5]));
			empDto.setFromDateString(ConverterUtil.getString(report[6]));
			empDto.setToDateString(ConverterUtil.getString(report[7]));

			empDto.setFromDate(ConverterUtil.getDate(empDto.getFromDateString()));
			empDto.setToDate(ConverterUtil.getDate(empDto.getFromDateString()));

			empDto.setDay(ConverterUtil.getBigDecimal(report[8]).longValue());

			String first = ConverterUtil.getString(report[1]);
			String last = ConverterUtil.getString(report[2]);
			empDto.setFirstName(Character.toString(first.charAt(0)).toUpperCase());
			empDto.setLastName(Character.toString(last.charAt(0)).toUpperCase());
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
			String parameter;
			if (empDto.getDay() > 1) {
				String delim = "";
				parameter = "";

				Calendar c = Calendar.getInstance();
				c.setTime(empDto.getFromDate());
				Date date = empDto.getFromDate();

				for (int i = 0; i < empDto.getDay(); i++) {

					parameter = parameter.concat(delim);
					delim = ",";
					parameter = parameter.concat(simpleDateformat.format(date));

					c.add(Calendar.DATE, 1);
					date = c.getTime();
				}

			} else
				parameter = simpleDateformat.format(empDto.getFromDate());
			empDto.setDaysName(parameter);

			employeeReportDTO.add(empDto);

		}
		return employeeReportDTO;
	}

	@Override
	public List<Object[]> getSeparationReqSummary(Long companyId, Long employeeId, List<Long> departmentIds, Date fDate,
			Date tDate) {

		if (employeeId != 0) {
			return employeeReportRepository.findSeparationReqSumEmployeeWise(companyId, fDate, tDate, employeeId);
		} else if (departmentIds.size() > 0) {
			return employeeReportRepository.findSeparationReqSumDepartmentWise(companyId, fDate, tDate, departmentIds);
		}
		return null;
	}

}
