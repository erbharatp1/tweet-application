package com.csipl.hrms.service.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.authoriztion.UserRole;
import com.csipl.hrms.model.common.User;
import com.csipl.hrms.service.authorization.repository.UserRoleRepository;
import com.csipl.hrms.service.employee.repository.UserRepository;
import com.csipl.hrms.service.users.UserService;
@Transactional
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserService userService;
	
 
	@Override
	public void save(UserRole userRole) {
		userRoleRepository.save(userRole);
	}

	@Override
	public List<UserRole> findAllUserRoles() {
		return userRoleRepository.findAllUserRoles();
	}

	@Override
	public void saveAll(List<UserRole> userRoleList) {
 		userRoleRepository.save(userRoleList);
	}

	@Override
	public List<UserRole> findUserRoles(Long employeeId) {
	 Long userId = userService.findUserRoles(employeeId);
		return userRoleRepository.findUserRoles(userId);
	}

	@Override
	public void delete(Long userRolesSrNo) {
		userRoleRepository.delete(userRolesSrNo);		
	}

	@Override
	public UserRole getUserRole(Long employeeId) {
		
		// TODO Auto-generated method stub
		 Long userId = userService.findUserRoles(employeeId);
		System.out.println("getUserId--------------------------------------"+userId);
		return userRoleRepository.getUserRoles(userId);
	}

	@Override
	public void updateById(List<UserRole> userRole) {
		for (UserRole userRole2 : userRole) {
 
			userRoleRepository.updateByIds( userRole2.getRoleMaster().getRoleId(),userRole2.getUserRolesSrNo());
			System.out.println("UserRoleServiceImpl.updateById()"+ userRole2.getRoleMaster().getRoleId()+""+userRole2.getUserRolesSrNo());
	}
		
	}
}
