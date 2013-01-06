package de.femodeling.e4.client.service.internal.transform;

import java.util.HashSet;
import java.util.Set;

import de.femodeling.e4.client.model.UserClientImpl;
import de.femodeling.e4.model.dto.UserDTO;
import de.femodeling.e4.model.dto.UserDTOImpl;

public class UserTransformService {

	

	public static UserDTO transformClient(UserClientImpl ent) {

		if (ent == null)
			return null;

		UserDTO userDTO = new UserDTOImpl();

		userDTO.setForename(ent.getForename());
		userDTO.setGroups(ent.getGroups());
		userDTO.setId(ent.getId());
		userDTO.setLocation(ent.getLocation());
		userDTO.setPassword(ent.getPassword());
		userDTO.setPhonenumber(ent.getPhonenumber());
		userDTO.setRoles(ent.getRoles());
		userDTO.setSurname(ent.getSurname());

		return userDTO;

	}

	public static UserClientImpl transformClient(UserDTO ent) {
		if (ent == null)
			return null;
		UserClientImpl user = new UserClientImpl();

		user.setForename(ent.getForename());
		user.setGroups(ent.getGroups());
		user.setId(ent.getId());
		user.setLocation(ent.getLocation());
		user.setPassword(ent.getPassword());
		user.setPhonenumber(ent.getPhonenumber());
		user.setRoles(ent.getRoles());
		user.setSurname(ent.getSurname());

		return user;

	}

	public static Set<UserClientImpl> transformClient(Set<UserDTO> uDTOSet) {

		Set<UserClientImpl> u_Set = new HashSet<UserClientImpl>();

		for (UserDTO user : uDTOSet) {
			u_Set.add(transformClient(user));
		}

		return u_Set;

	}
	
	public static void copyData(UserClientImpl input,UserClientImpl target) {

		if (input == null)
			return ;


		target.setForename(input.getForename());
		target.setGroups(input.getGroups());
		target.setId(input.getId());
		target.setLocation(input.getLocation());
		target.setPassword(input.getPassword());
		target.setPhonenumber(input.getPhonenumber());
		target.setRoles(input.getRoles());
		target.setSurname(input.getSurname());

	}

}
