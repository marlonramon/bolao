package org.javaee.bolao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class UserRole extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserRole;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idUser")
	private User user;

	@NotNull
	private String role;

	public Long getIdUserRole() {
		return idUserRole;
	}

	public void setIdUserRole(Long idUserAuthorization) {
		this.idUserRole = idUserAuthorization;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String userRole) {
		this.role = userRole;
	}

	@Override
	public Long getId() {
		return getIdUserRole();
	}

}
