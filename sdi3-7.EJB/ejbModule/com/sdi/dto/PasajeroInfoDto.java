package com.sdi.dto;

import com.sdi.model.SeatStatus;

public class PasajeroInfoDto {

	private Long idUsuario;
	private String login;
	private String nombre;
	private String apellidos;
	private SeatStatus seatStatus;

	public PasajeroInfoDto(Long idUsuario, String login, String nombre,
			String apellidos, SeatStatus seatStatus) {
		super();
		this.idUsuario = idUsuario;
		this.login = login;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.seatStatus = seatStatus;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

}
