package org.javaee.bolao.entidades;



public abstract class AbstractEntity implements IEntity {
	private static final long serialVersionUID = 1L;

	public AbstractEntity() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		IEntity other = (IEntity) obj;

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
