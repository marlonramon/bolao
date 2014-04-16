package org.javaee.bolao.entities;


import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public AbstractEntity() {
	}

	public abstract Long getId();

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		AbstractEntity other = (AbstractEntity) obj;

		Long myId = getId();
		Long otherId = other.getId();

		if (myId == null || otherId == null) {
			return false;
		}

		return myId.equals(otherId);
	}

	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString() + "[ID = " + getId() + "]";
	}
	
	public boolean hasId(){
		return getId() != null;
	}

}
