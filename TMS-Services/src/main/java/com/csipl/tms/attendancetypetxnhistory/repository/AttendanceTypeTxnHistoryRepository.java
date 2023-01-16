package com.csipl.tms.attendancetypetxnhistory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csipl.tms.model.attendancetypetransaction.AttendanceTypeTxnHistory;


@Repository 
public interface AttendanceTypeTxnHistoryRepository  extends CrudRepository< AttendanceTypeTxnHistory, Long>{

}
