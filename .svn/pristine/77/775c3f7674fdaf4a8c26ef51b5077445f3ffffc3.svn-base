package com.nec.asia.nic.comp.trans.dto;

import java.util.Comparator;
import java.util.Date;

public class EstDateOfCollectionAndPriority implements Comparator<EstDateOfCollectionAndPriority> {

	private Date estDateOfCollection;
	private Integer priority;

	public EstDateOfCollectionAndPriority() {
		super();
	}

	public EstDateOfCollectionAndPriority(Date estDateOfCollection, Integer priority) {
		super();
		this.estDateOfCollection = estDateOfCollection;
		this.priority = priority;
	}

	public int compare(EstDateOfCollectionAndPriority This, EstDateOfCollectionAndPriority other) {

		if (This.getEstDateOfCollection() == null || other.getEstDateOfCollection() == null) {
			return 0;
		}

		int compare = This.getEstDateOfCollection().compareTo(other.getEstDateOfCollection());
		if (compare != 0) {
			return compare;
		}

		if (This.getPriority() == null || other.getPriority() == null) {
			return 0;
		}

		This.setPriority(This.getPriority() * -1);
		other.setPriority(other.getPriority() * -1);
		return This.getPriority().compareTo(other.getPriority());
	}

	public Date getEstDateOfCollection() {
		return estDateOfCollection;
	}

	public void setEstDateOfCollection(Date estDateOfCollection) {
		this.estDateOfCollection = estDateOfCollection;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
