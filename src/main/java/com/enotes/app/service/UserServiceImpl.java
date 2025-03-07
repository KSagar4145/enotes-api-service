package com.enotes.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.app.dto.UserDto;
import com.enotes.app.entity.Role;
import com.enotes.app.entity.User;
import com.enotes.app.repo.IRoleRepo;
import com.enotes.app.repo.IUserRepo;

import jakarta.persistence.EntityManager;



@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepo userRepo;

	@Autowired
	private IRoleRepo roleRepo;

//	@Autowired
//	private Validation validation;

	@Autowired
	private ModelMapper mapper;
	
	
	

	@Override
	public Boolean register(UserDto userDto) {

//		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
//		roleRepo.flush();
		roleRepo.save(new Role(1, "ADMIN"));
		roleRepo.save(new Role(2, "USER"));
		
		
//		 for (Role role : user.getRoles()) {
//	            if (role.getId() != null) {
//	                // Fetch the role from the database to ensure it's managed by Hibernate
//	            	role = roleRepo.findById(role.getId())
//	            			.orElseThrow(() -> new RuntimeException("Role not found in database"));
//	            } else {
//	                // If it's a new role (id is null), save it. Otherwise, do nothing.
//	            	roleRepo.save(role);
//	            }
//	        }
		
		
		
		

		setRole(userDto, user);

		User saveUser = userRepo.save(user);
		if (!ObjectUtils.isEmpty(saveUser)) {
			return true;
		}
		return false;
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}

}
