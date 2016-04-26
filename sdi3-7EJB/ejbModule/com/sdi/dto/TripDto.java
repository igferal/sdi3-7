package com.sdi.dto;

import java.util.List;

import com.sdi.model.Trip;
import com.sdi.model.User;

public class TripDto {

	private Trip trip;
	private List<PasajeroInfoDto> pasajeros;
	private long idPromotor;
	private String promotorLogin;
	private String promotorName;
	private String promotorSurname;
	private boolean isInTrip;
	private long idUser;

	public TripDto(Trip trip, User user) {
		this.setTrip(trip);
		if (user == null)
			this.setIdUser(-1);
		else
			this.setIdUser(user.getId());
	}

	public boolean checkInTrip(long idUser) {
		if (idPromotor == idUser)
			return true;
		
		for (PasajeroInfoDto pasajero:pasajeros)
			if (idUser == pasajero.getIdUsuario())
				return true;
		
		return false;
	}

	public List<PasajeroInfoDto> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(List<PasajeroInfoDto> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Long getIdPromotor() {
		return idPromotor;
	}

	public void setIdPromotor(Long idPromotor) {
		this.idPromotor = idPromotor;
	}

	public boolean isIsInTrip() {
		return isInTrip;
	}
	
	public void setIsInTrip(boolean isInTrip) {
		this.isInTrip = isInTrip;
	}

	public String getPromotorName() {
		return promotorName;
	}

	public void setPromotorName(String promotorName) {
		this.promotorName = promotorName;
	}

	public String getPromotorSurname() {
		return promotorSurname;
	}

	public void setPromotorSurname(String promotorSurname) {
		this.promotorSurname = promotorSurname;
	}

	public String getPromotorLogin() {
		return promotorLogin;
	}

	public void setPromotorLogin(String promotorLogin) {
		this.promotorLogin = promotorLogin;
	}
	
	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

}
