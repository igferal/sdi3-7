package com.sdi.dto;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;
import com.sdi.model.User;

public class SolicitudesDto {

	private List<User> admitidos;

	private List<User> pendientes;

	private List<User> excluidos;

	private Long idViaje;

	private Date fechaSalida;

	private String departureCity;

	private String destinationCity;

	private int plazasLibres;
	
	private int maxPlazas;

	public SolicitudesDto(Trip trip) {

		this.idViaje = trip.getId();
		this.fechaSalida = trip.getDepartureDate();
		this.departureCity = trip.getDeparture().getCity();
		this.destinationCity = trip.getDestination().getCity();
		this.setPlazasLibres(trip.getAvailablePax());
		this.maxPlazas = trip.getMaxPax();
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public Long getIdViaje() {
		return idViaje;
	}

	public void setIdViaje(Long idViaje) {
		this.idViaje = idViaje;
	}

	public List<User> getAdmitidos() {
		return admitidos;
	}

	public void setAdmitidos(List<User> admitidos) {
		this.admitidos = admitidos;
	}

	public List<User> getPendientes() {
		return pendientes;
	}

	public void setPendientes(List<User> pendientes) {
		this.pendientes = pendientes;
	}

	public List<User> getExcluidos() {
		return excluidos;
	}

	public void setExcluidos(List<User> excluidos) {
		this.excluidos = excluidos;
	}

	public int getPlazasLibres() {
		return plazasLibres;
	}

	public void setPlazasLibres(int plazasLibres) {
		this.plazasLibres = plazasLibres;
	}

	public int getMaxPlazas() {
		return maxPlazas;
	}

	public void setMaxPlazas(int maxPlazas) {
		this.maxPlazas = maxPlazas;
	}

}
