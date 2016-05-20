package com.sdi.presentation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sdi.dto.DTOAssembler;
import com.sdi.dto.PasajeroInfoDto;
import com.sdi.dto.TripDto;
import com.sdi.infrastructure.Factories;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;

@ManagedBean(name = "tripSeats")
@ViewScoped
public class BeanTripSeats implements Serializable {

	private static final long serialVersionUID = 5188245089243636845L;

	private TripDto tripDto;

	private List<PasajeroInfoDto> acceptedPassengers = new ArrayList<>();
	private List<PasajeroInfoDto> pendingPassengers = new ArrayList<>();
	private List<PasajeroInfoDto> excludedPassengers = new ArrayList<>();

	@PostConstruct
	public void init() {
		
		Trip trip = Factories.services.getTripService().findTrip(
				(Long) FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get("tripSeatsParam"));
		setTripDto(DTOAssembler.generateTripDto(trip,
				(User) FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get("LOGGEDIN_USER")));

		putAcceptedPassengers();
		putPendingPassengers();
		putExcludedPassengers();
		
	}

	public String moveToAccepted(PasajeroInfoDto pas) {
		Factories.services.getSeatService().moveToAccepted(pas.getIdUsuario(),
				tripDto.getTrip().getId());

		return "tripSeats.xhtml?faces-redirect=true";
	}

	public String moveToPending(PasajeroInfoDto pas) {
		Factories.services.getSeatService().moveToPending(pas.getIdUsuario(),
				tripDto.getTrip().getId());

		return "tripSeats.xhtml?faces-redirect=true";
	}

	public String moveToExcluded(PasajeroInfoDto pas) {
		Factories.services.getSeatService().moveToExcluded(pas.getIdUsuario(),
				tripDto.getTrip().getId());

		return "tripSeats.xhtml?faces-redirect=true";
	}

	public TripDto getTripDto() {
		return tripDto;
	}

	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}

	public List<PasajeroInfoDto> getAcceptedPassengers() {
		return acceptedPassengers;
	}

	public void setAcceptedPassengers(List<PasajeroInfoDto> acceptedPassengers) {
		this.acceptedPassengers = acceptedPassengers;
	}

	public List<PasajeroInfoDto> getPendingPassengers() {
		return pendingPassengers;
	}

	public void setPendingPassengers(List<PasajeroInfoDto> pendingPassengers) {
		this.pendingPassengers = pendingPassengers;
	}

	public List<PasajeroInfoDto> getExcludedPassengers() {
		return excludedPassengers;
	}

	public void setExcludedPassengers(List<PasajeroInfoDto> excludedPassengers) {
		this.excludedPassengers = excludedPassengers;
	}

	private void putAcceptedPassengers() {
		for (PasajeroInfoDto dto : tripDto.getPasajeros())
			if (dto.getSeatStatus() != null
					&& dto.getSeatStatus().equals(SeatStatus.ACCEPTED))
				acceptedPassengers.add(dto);
	}

	private void putPendingPassengers() {
		for (PasajeroInfoDto dto : tripDto.getPasajeros())
			if (dto.getSeatStatus() == null)
				pendingPassengers.add(dto);
	}

	private void putExcludedPassengers() {
		for (PasajeroInfoDto dto : tripDto.getPasajeros())
			if (dto.getSeatStatus() != null
					&& dto.getSeatStatus().equals(SeatStatus.EXCLUDED))
				excludedPassengers.add(dto);
	}

	public String formattedDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}

	public boolean cancelledTrip() {
		return tripDto.getTrip().getStatus().equals(TripStatus.CANCELLED);
	}

	public boolean closedTrip() {
		return tripDto.getTrip().getStatus().equals(TripStatus.CLOSED);
	}

	public boolean openTrip() {
		return tripDto.getTrip().getStatus().equals(TripStatus.OPEN);
	}

	public boolean doneTrip() {
		return tripDto.getTrip().getStatus().equals(TripStatus.DONE);
	}
}
