package com.csipl.hrms.model.payroll;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.csipl.hrms.model.common.Company;
import com.csipl.hrms.model.employee.Employee;
import com.csipl.hrms.model.payrollprocess.FinancialYear;


/**
 * The persistent class for the TdsDeduction database table.
 * 
 */
@Entity
@NamedQuery(name="TdsDeduction.findAll", query="SELECT t FROM TdsDeduction t")
public class TdsDeduction implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private Long tdsDeductionId;

        @Temporal(TemporalType.DATE)
        private Date dateCreated;

        @Temporal(TemporalType.DATE)
        private Date dateUpdate;

        private String remark;

        private BigDecimal taxDeductedMonthly;

        private BigDecimal taxTobeDeductedMonthly;

        private Long totalTax;

        private Long userId;

        private Long userIdUpdate;
        private String activeStatus;

        //bi-directional many-to-one association to Employee
        @ManyToOne
        @JoinColumn(name="employeeId")
        private Employee employee;

        //bi-directional many-to-one association to FinancialYear
        @ManyToOne
        @JoinColumn(name="financialYearId")
        private FinancialYear financialYear;

        //bi-directional many-to-one association to Company
        @ManyToOne
        @JoinColumn(name="companyId")
        private Company company;

        public TdsDeduction() {
        }

        public Long getTdsDeductionId() {
                return this.tdsDeductionId;
        }

        public void setTdsDeductionId(Long tdsDeductionId) {
                this.tdsDeductionId = tdsDeductionId;
        }

        public Date getDateCreated() {
                return this.dateCreated;
        }

        public void setDateCreated(Date dateCreated) {
                this.dateCreated = dateCreated;
        }

        public Date getDateUpdate() {
                return this.dateUpdate;
        }

        public void setDateUpdate(Date dateUpdate) {
                this.dateUpdate = dateUpdate;
        }

        public String getRemark() {
                return this.remark;
        }

        public void setRemark(String remark) {
                this.remark = remark;
        }

        public BigDecimal getTaxDeductedMonthly() {
                return this.taxDeductedMonthly;
        }

        public void setTaxDeductedMonthly(BigDecimal taxDeductedMonthly) {
                this.taxDeductedMonthly = taxDeductedMonthly;
        }

        public BigDecimal getTaxTobeDeductedMonthly() {
                return this.taxTobeDeductedMonthly;
        }

        public void setTaxTobeDeductedMonthly(BigDecimal taxTobeDeductedMonthly) {
                this.taxTobeDeductedMonthly = taxTobeDeductedMonthly;
        }

        public Long getTotalTax() {
                return this.totalTax;
        }

        public void setTotalTax(Long totalTax) {
                this.totalTax = totalTax;
        }

        public Long getUserId() {
                return this.userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }

        public Long getUserIdUpdate() {
                return this.userIdUpdate;
        }

        public void setUserIdUpdate(Long userIdUpdate) {
                this.userIdUpdate = userIdUpdate;
        }

        public Employee getEmployee() {
                return this.employee;
        }

        public void setEmployee(Employee employee) {
                this.employee = employee;
        }

        public FinancialYear getFinancialYear() {
                return this.financialYear;
        }

        public void setFinancialYear(FinancialYear financialYear) {
                this.financialYear = financialYear;
        }

        public Company getCompany() {
                return this.company;
        }

        public void setCompany(Company company) {
                this.company = company;
        }

		public String getActiveStatus() {
			return activeStatus;
		}

		public void setActiveStatus(String activeStatus) {
			this.activeStatus = activeStatus;
		}

}