package org.javaee.bolao.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class UserSession extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserAuthorization;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idUser")
	private User user;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirateDate;

	@NotNull
	@Size(min = 36, max = 36)
	private String token;

	public Long getIdUserAuthorization() {
		return idUserAuthorization;
	}

	public void setIdUserAuthorization(Long idUserAuthorization) {
		this.idUserAuthorization = idUserAuthorization;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExpirateDate() {
		return expirateDate;
	}

	public void setExpirateDate(Date expirateDate) {
		this.expirateDate = expirateDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String authorization) {
		this.token = authorization;
	}

	@Override
	public Long getId() {
		return getIdUserAuthorization();
	}

	public boolean isExpired(Date date) {
		return getExpirateDate().after(date);
	}

}
