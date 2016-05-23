package com.sdi.model;

public enum TripStatus {
	OPEN,
	CLOSED,
	CANCELLED,
	DONE;
	
	@Override
	public String toString() {
		switch (this) {
			case OPEN: return "OPEN";
			case CLOSED: return "CLOSED";
			case CANCELLED: return "CANCELLED";
			case DONE: return "DONE";
			default: return null;
		}
	}

}
