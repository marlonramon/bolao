package org.javaee.bolao.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "User")
@XmlRootElement
public class User extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	@NotNull
	@Size(min = 1, max = 80)
	private String login;
	@NotNull
	@Size(min = 1, max = 80)
	private String password;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInsert;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastAccess;
	@NotNull
	@Size(min = 1, max = 80)
	private String name;
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "E-mail inválido")
	// if the field contains email address consider using this annotation to
	// enforce field validation
	@Size(max = 100)
	private String email;

	@OneToMany(mappedBy = "user")
	private Set<UserRole> roles;

	public User() {
	}

	public User(Long id) {
		this.idUser = id;
	}

	public User(Long id, String login, String password, Date dateinsert, String name) {
		this.idUser = id;
		this.login = login;
		this.password = password;
		this.dateInsert = dateinsert;
		this.name = name;
	}
	
	@PrePersist
	private void prePersist(){
		setDateInsert(new Date());
		setDateLastAccess(getDateInsert());
	}
	
	@Override
	public Long getId() {
		return idUser;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long id) {
		this.idUser = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateInsert() {
		return dateInsert;
	}

	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}

	public Date getDateLastAccess() {
		return dateLastAccess;
	}

	public void setDateLastAccess(Date dateLastAccess) {
		this.dateLastAccess = dateLastAccess;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

}
