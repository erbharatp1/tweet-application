package com.csipl.tms.leave.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import com.csipl.tms.dto.leave.SandwitchIssueResolver;

import java.time.LocalDate;


public class LeaveValidationResult {
       
	    private List<Date> applyHolidays =  new ArrayList<>();
        private List<Date> appliedSandwhichDates =  new ArrayList<>();
        private List<String> appliedLeaveEnteriesDates= new ArrayList<>();
        private BigDecimal weekllyOffCount = BigDecimal.ZERO;
        private BigDecimal holidaysOffCount = BigDecimal.ZERO;
        private BigDecimal weekholidaysOffCount = BigDecimal.ZERO;
        private BigDecimal actulappliedDaysCount = BigDecimal.ZERO;
        private BigDecimal sandwhichDaysCount = BigDecimal.ZERO;
        private BigDecimal totalLeaveApplyDays = BigDecimal.ZERO;
        private String leaveNature;
        private String sandwitchNature;
        private BigDecimal leaveAsAbsentDaysCount = BigDecimal.ZERO;
        
        TreeSet<Date> holidayset = new TreeSet<>();
        TreeSet<Date> allHolidayset = new TreeSet<>();
        TreeSet<String> weeklydayset = new TreeSet<>();
		TreeSet<Date> leaveEnterieSet = new TreeSet<>();
		TreeSet<Date> weeklyOffSet = new TreeSet<>();
		TreeSet<Date> sandwichDatesFromSet = new TreeSet<>();
		TreeSet<Date> sandwichDatesToSet = new TreeSet<>();
		TreeSet<Date> sandwichDates = new TreeSet<>();
		TreeSet<Date> leaveAsAbsentDates = new TreeSet<>();
		TreeSet<SandwitchIssueResolver> sandwichDatesFromSetObj = new TreeSet<>();
		TreeSet<SandwitchIssueResolver> sandwichDatesToSetObj = new TreeSet<>();
		public TreeSet<SandwitchIssueResolver> getSandwichDatesFromSetObj() {
			return sandwichDatesFromSetObj;
		}
		public void setSandwichDatesFromSetObj(TreeSet<SandwitchIssueResolver> sandwichDatesFromSetObj) {
			this.sandwichDatesFromSetObj = sandwichDatesFromSetObj;
		}
		public TreeSet<SandwitchIssueResolver> getSandwichDatesToSetObj() {
			return sandwichDatesToSetObj;
		}
		public void setSandwichDatesToSetObj(TreeSet<SandwitchIssueResolver> sandwichDatesToSetObj) {
			this.sandwichDatesToSetObj = sandwichDatesToSetObj;
		}
		TreeSet<Date> finalSandwichDates= new  TreeSet<>();
		
        
        
        public BigDecimal getSandwhichDaysCount() {
			return sandwhichDaysCount;
		}
		public void setSandwhichDaysCount(BigDecimal sandwhichDaysCount) {
			this.sandwhichDaysCount = sandwhichDaysCount;
		}
		
		
		
		public TreeSet<Date> getFinalSandwichDates() {
			return finalSandwichDates;
		}
		public void setFinalSandwichDates(TreeSet<Date> finalSandwichDates) {
			this.finalSandwichDates = finalSandwichDates;
		}
		public List<String> getAppliedLeaveEnteriesDates() {
			return appliedLeaveEnteriesDates;
		}
		public void setAppliedLeaveEnteriesDates(List<String> appliedLeaveEnteriesDates) {
			this.appliedLeaveEnteriesDates = appliedLeaveEnteriesDates;
		}
		public List<Date> getAppliedSandwhichDates() {
			return appliedSandwhichDates;
		}
		public void setAppliedSandwhichDates(List<Date> appliedSandwhichDates) {
			this.appliedSandwhichDates = appliedSandwhichDates;
		}
		public List<Date> getApplyHolidays() {
                return applyHolidays;
        }
        public void setApplyHolidays(List<Date> applyHolidays) {
                this.applyHolidays = applyHolidays;
        }
        public BigDecimal getWeekllyOffCount() {
                return weekllyOffCount;
        }
        public void setWeekllyOffCount(BigDecimal weekllyOffCount) {
                this.weekllyOffCount = weekllyOffCount;
        }
        public BigDecimal getHolidaysOffCount() {
                return holidaysOffCount;
        }
        public void setHolidaysOffCount(BigDecimal holidaysOffCount) {
                this.holidaysOffCount = holidaysOffCount;
        }
        public BigDecimal getWeekholidaysOffCount() {
                return weekholidaysOffCount;
        }
        public void setWeekholidaysOffCount(BigDecimal weekholidaysOffCount) {
                this.weekholidaysOffCount = weekholidaysOffCount;
        }
        
        
        public BigDecimal getActulappliedDaysCount() {
			return actulappliedDaysCount;
		}
		public void setActulappliedDaysCount(BigDecimal actulappliedDaysCount) {
			this.actulappliedDaysCount = actulappliedDaysCount;
		}
		public BigDecimal getTotalLeaveApplyDays() {
                return totalLeaveApplyDays;
        }
        public void setTotalLeaveApplyDays(BigDecimal totalLeaveApplyDays) {
                this.totalLeaveApplyDays = totalLeaveApplyDays;
        }
		public TreeSet<Date> getHolidayset() {
			return holidayset;
		}
		public void setHolidayset(TreeSet<Date> holidayset) {
			this.holidayset = holidayset;
		}
		public TreeSet<Date> getLeaveEnterieSet() {
			return leaveEnterieSet;
		}
		public void setLeaveEnterieSet(TreeSet<Date> leaveEnterieSet) {
			this.leaveEnterieSet = leaveEnterieSet;
		}
		public TreeSet<Date> getWeeklyOffSet() {
			return weeklyOffSet;
		}
		public void setWeeklyOffSet(TreeSet<Date> weeklyOffSet) {
			this.weeklyOffSet = weeklyOffSet;
		}
		public TreeSet<Date> getSandwichDatesFromSet() {
			return sandwichDatesFromSet;
		}
		public void setSandwichDatesFromSet(TreeSet<Date> sandwichDatesFromSet) {
			this.sandwichDatesFromSet = sandwichDatesFromSet;
		}
		public TreeSet<Date> getSandwichDatesToSet() {
			return sandwichDatesToSet;
		}
		public void setSandwichDatesToSet(TreeSet<Date> sandwichDatesToSet) {
			this.sandwichDatesToSet = sandwichDatesToSet;
		}
		public TreeSet<Date> getSandwichDates() {
			return sandwichDates;
		}
		public void setSandwichDates(TreeSet<Date> sandwichDates) {
			this.sandwichDates = sandwichDates;
		}
		public TreeSet<String> getWeeklydayset() {
			return weeklydayset;
		}
		public void setWeeklydayset(TreeSet<String> weeklydayset) {
			this.weeklydayset = weeklydayset;
		}
		public TreeSet<Date> getAllHolidayset() {
			return allHolidayset;
		}
		public void setAllHolidayset(TreeSet<Date> allHolidayset) {
			this.allHolidayset = allHolidayset;
		}
		public String getLeaveNature() {
			return leaveNature;
		}
		public void setLeaveNature(String leaveNature) {
			this.leaveNature = leaveNature;
		}
		public String getSandwitchNature() {
			return sandwitchNature;
		}
		public void setSandwitchNature(String sandwitchNature) {
			this.sandwitchNature = sandwitchNature;
		}
		public TreeSet<Date> getLeaveAsAbsentDates() {
			return leaveAsAbsentDates;
		}
		public void setLeaveAsAbsentDates(TreeSet<Date> leaveAsAbsentDates) {
			this.leaveAsAbsentDates = leaveAsAbsentDates;
		}
		public BigDecimal getLeaveAsAbsentDaysCount() {
			return leaveAsAbsentDaysCount;
		}
		public void setLeaveAsAbsentDaysCount(BigDecimal leaveAsAbsentDaysCount) {
			this.leaveAsAbsentDaysCount = leaveAsAbsentDaysCount;
		}
		
        
        
        
        
}
