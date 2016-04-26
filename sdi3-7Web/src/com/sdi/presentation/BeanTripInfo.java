package com.sdi.presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sdi.dto.DTOAssembler;
import com.sdi.dto.PasajeroInfoDto;
import com.sdi.dto.TripDto;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.User;

@ManagedBean(name = "tripInfo")
@ViewScoped
public class BeanTripInfo {

	private TripDto tripDto;
	private boolean isTripClosed;

	@PostConstruct
	public void init() {

		System.out.println("Creando Bean info trip");
		Trip trip = Factories.services.createTripService().findTrip(
				(Long) FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get("tripInfoParam"));
		setTripDto(DTOAssembler.generateTripDto(trip,
				(User) FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get("LOGGEDIN_USER")));
	}

	public String solicitarPlaza() {
		Application app = new Application(((User) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("LOGGEDIN_USER")).getId(), tripDto.getTrip().getId());

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");
		if (Factories.services.createApplicationService().save(app))
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", bundle
							.getString("solicitarPlaza_result_exito")));
		else
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
							.getString("solicitarPlaza_result_fallo")));

		context.getExternalContext().getFlash().setKeepMessages(true);

		return "tripInfo.xhtml?faces-redirect=true";
	}

	public TripDto getTripDto() {
		return tripDto;
	}

	public void setTripDto(TripDto tripDto) {
		this.tripDto = tripDto;
	}

	public String formattedDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}

	public List<PasajeroInfoDto> getAcceptedPassengers() {
		List<PasajeroInfoDto> pasajeros = new ArrayList<PasajeroInfoDto>();

		for (PasajeroInfoDto pasajero : tripDto.getPasajeros())
			if (pasajero.getSeatStatus() != null
					&& pasajero.getSeatStatus().equals(SeatStatus.ACCEPTED))
				pasajeros.add(pasajero);

		return pasajeros;
	}

	public boolean isTripClosed() {
		isTripClosed = tripDto.getTrip().getClosingDate().before(new Date());
		return isTripClosed;
	}

	public void setTripClosed(boolean isTripClosed) {
		this.isTripClosed = isTripClosed;
	}

}
