package de.femodeling.e4.server.internal.transform;

import de.femodeling.e4.model.dto.UserDTO;
import de.femodeling.e4.model.dto.UserDTOImpl;
import de.femodeling.e4.server.internal.model.UserServerImpl;



public class UserTransformService {

	public static UserDTO transform(UserServerImpl userImpl) {
		if (userImpl == null)
			return null;
		UserDTO userDTO = new UserDTOImpl();

		userDTO.setForename(userImpl.getForename());
		userDTO.setGroups(userImpl.getGroups());
		userDTO.setId(userImpl.getId());
		userDTO.setLocation(userImpl.getLocation());
		userDTO.setPassword(userImpl.getPassword());
		userDTO.setPhonenumber(userImpl.getPhonenumber());
		userDTO.setRoles(userImpl.getRoles());
		userDTO.setSurname(userImpl.getSurname());
		userDTO.setParameter(userImpl.getParameter());
		

		return userDTO;

	}

	public static UserServerImpl transform(UserDTO userDTO) {
		if (userDTO == null)
			return null;
		UserServerImpl userImpl = new UserServerImpl();

		userImpl.setForename(userDTO.getForename());
		userImpl.setGroups(userDTO.getGroups());
		userImpl.setId(userDTO.getId());
		userImpl.setLocation(userDTO.getLocation());
		userImpl.setPassword(userDTO.getPassword());
		userImpl.setPhonenumber(userDTO.getPhonenumber());
		userImpl.setRoles(userDTO.getRoles());
		userImpl.setSurname(userDTO.getSurname());
		userImpl.setParameter(userDTO.getParameter());

		return userImpl;

	}

	

}
