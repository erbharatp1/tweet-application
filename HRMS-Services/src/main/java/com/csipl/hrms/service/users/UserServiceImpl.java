package com.csipl.hrms.service.users;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csipl.hrms.model.common.User;
import com.csipl.hrms.service.employee.repository.UserRepository;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserDetailsService,UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> findAllUsers() {

		return userRepository.findAllUsers();
	}

	@Override
	public User findUser(String userId, String password) {

		return userRepository.findUser(userId, password);
	}

	@Override
	public User findUser(String nameOfUser) {

		return userRepository.findUser(nameOfUser);
	}

	@Override
	public Long findUserRoles(Long employeeId) {

		return userRepository.findUserRoles(employeeId);
	}

	@Override
	public void userAttemptsUpdate(Long userAttempts, String nameOfUser) {

		 userRepository.userAttemptsUpdate(userAttempts, nameOfUser);
	}

	@Override
	public User save(User userBean) {
 		return userRepository.save(userBean);
	}

	@Override
	public String getUserRole(Long userId) {
		 
		return userRepository.getUserRole(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("======Before userName..==="+username+"password..");
		User user = userRepository.findByLoginName(username);
		System.out.println("======userName..===="+user.getLoginName()+"====password..======"+ user.getUserPassword());
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getLoginName(), user.getUserPassword(),
				getAuthority());
	}
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public int loginActiveDeactiveStatus(String activeDeactiveStatus, String employeeCode) {
		return userRepository.loginActiveDeactiveStatus(activeDeactiveStatus, employeeCode);
	}

}
