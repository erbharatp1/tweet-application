package com.csipl.hrms.service.customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.csipl.hrms.common.util.ErrorHandling;
import com.csipl.hrms.dto.customer.CustomerSubscriptionDTO;
import com.csipl.hrms.dto.organisation.UserDTO;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	Environment environment;
	
	@Override
	public CustomerSubscriptionDTO getCustomerSubscriptionDetails() {
		DriverManagerDataSource db = new DriverManagerDataSource();
		db.setDriverClassName(environment.getProperty("db.driver"));
		db.setUrl(environment.getProperty("db.url"));
		db.setUsername(environment.getProperty("db.username"));
		db.setPassword(environment.getProperty("db.password"));
		
		CustomerSubscriptionDTO customerSubscriptionDTO = new CustomerSubscriptionDTO();

		Connection c = null;

		String tenantId = null;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attr != null) {
			tenantId = attr.getRequest().getParameter("tenantId"); // find parameter from request
			if (tenantId == null)
				tenantId = attr.getRequest().getHeader("tenantId");
		}
			
		try {
			c = db.getConnection();
			PreparedStatement st = null;
			 
//			SELECT customer.customerId, customer.organizationName,plan.planName, sc.dateSubscribed,sc.dateUnsubsribed,sc.activeStatus , GROUP_CONCAT( DISTINCT st.noOfSubscriberTo ORDER BY st.noOfSubscriberTo DESC) AS NoOfSubscriberTo ,sc.subscriptionId FROM Subscription sc left  JOIN CustomerRegistration customer ON customer.customerId = sc.customerId     left JOIN Plan plan on  plan.planId = sc.currentPlanId  left Join SubscriptionTransaction st on st.subscriptionId=sc.subscriptionId WHERE sc.activeStatus='AC'  and customer.isDeployed='Y'
//					AND now()<>(SELECT  MAX(sci.dateUnsubsribed)  FROM Subscription sci   JOIN CustomerRegistration customer ON customer.customerId = sci.customerId   WHERE sci.activeStatus='AC'   and customer.isDeployed='Y'
//					 GROUP BY customer.customerId  HAVING   MAX(sci.dateUnsubsribed)=sc.dateUnsubsribed ORDER BY sci.subscriptionId DESC LIMIT 0,1) and customer.domainName='computronics.in'
//					GROUP BY customer.customerId  ORDER BY sc.subscriptionId DESC
			
			
				st = c.prepareStatement(" SELECT  customer.organizationName, sc.dateSubscribed,sc.dateUnsubsribed,"
				+ " GROUP_CONCAT( DISTINCT st.noOfSubscriberTo ORDER BY st.noOfSubscriberTo DESC) AS NoOfSubscriberTo "
				+ " FROM super_db.Subscription sc LEFT JOIN super_db.CustomerRegistration customer ON customer.customerId = sc.customerId  Left JOIN super_db.Plan plan on "
				+ " plan.planId = sc.currentPlanId LEFT JOIN super_db.SubscriptionTransaction st ON st.subscriptionId=sc.subscriptionId"
				+ "  WHERE sc.activeStatus='AC' and now() BETWEEN sc.dateSubscribed AND sc.dateUnsubsribed AND customer.isDeployed='Y'"
				+ " and customer.domainName='"+tenantId+"' GROUP BY customer.customerId ORDER BY sc.subscriptionId DESC");
				 
				
//			st = c.prepareStatement("SELECT  customer.organizationName, sc.dateSubscribed,sc.dateUnsubsribed,  \r\n"
//					+ "				    GROUP_CONCAT( DISTINCT st.noOfSubscriberTo ORDER BY st.noOfSubscriberTo DESC) AS NoOfSubscriberTo ,"
//					+ " ( SELECT sum(tus.totalSubsctription) FROM TopUpSubscription tus where tus.subscriptionId=sc.subscriptionId and tus.customerId=sc.customerId"
//					+ " GROUP BY sc.customerId) AS NoOfSubscriberTos  \r\n"
//					+ "				    FROM super_db.Subscription sc LEFT JOIN super_db.CustomerRegistration customer ON customer.customerId = sc.customerId"
//					+ " LEFT Join TopUpSubscription  ts ON ts.subscriptionId=sc.subscriptionId and ts.customerId=sc.customerId Left JOIN super_db.Plan plan on   \r\n"
//					+ "				    plan.planId = sc.currentPlanId LEFT JOIN super_db.SubscriptionTransaction st ON st.subscriptionId=sc.subscriptionId  \r\n"
//					+ "				     WHERE sc.activeStatus='AC' and now() BETWEEN sc.dateSubscribed AND sc.dateUnsubsribed AND customer.isDeployed='Y'  \r\n"
//					+ "				    and customer.domainName='"+tenantId+"' GROUP BY customer.customerId ORDER BY sc.subscriptionId DESC");
				
				
				System.out.println("customerSubscriptionDTO  data ");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String organizationName = rs.getString("organizationName");
				Date dateSubscribed = rs.getDate("dateSubscribed");
				Date dateUnsubsribed = rs.getDate("dateUnsubsribed");
				String NoOfSubscriberTo = rs.getString("NoOfSubscriberTo");
//				Long NoOfSubscriberTos = rs.getLong("NoOfSubscriberTos");
				String nos[] = NoOfSubscriberTo.split(",");
				customerSubscriptionDTO.setOrganizationName(organizationName);
				customerSubscriptionDTO.setDateSubscribed(dateSubscribed);
				customerSubscriptionDTO.setDateUnsubsribed(dateUnsubsribed);
//				if(NoOfSubscriberTos!=null) {
//					customerSubscriptionDTO.setNoOfSubscriberTo(Long.valueOf(nos[0])+NoOfSubscriberTos);
//				}
//				else {
//					customerSubscriptionDTO.setNoOfSubscriberTo(Long.valueOf(nos[0]));
//				}
				customerSubscriptionDTO.setNoOfSubscriberTo(Long.valueOf(nos[0]));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return customerSubscriptionDTO;
	}

}
