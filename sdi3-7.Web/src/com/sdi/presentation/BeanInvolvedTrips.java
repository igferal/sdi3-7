package com.sdi.presentation;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.sdi.business.SeatService;
import com.sdi.dto.DTOAssembler;
import com.sdi.dto.ViajeImplicadoDto;
import com.sdi.infrastructure.Factories;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.persistence.util.DateUtil;

@ManagedBean(name = "involvedTrips")
@ViewScoped
public class BeanInvolvedTrips implements Serializable {
	private static final long serialVersionUID = -8662200441018725393L;

	private List<ViajeImplicadoDto> trips;
	private Trip selectedTrip;

	public boolean dateBefore(ViajeImplicadoDto trip) {

		if (trip != null) {

			if (trip.getSeatStatus() != null) {
				if (trip.getSeatStatus().equals(SeatStatus.EXCLUDED)) {
					return false;
				}
			}

			Date now = new Date();
			return DateUtil.isAfter(trip.getClosingDate(), now);
		}
		return false;
	}
	
	public boolean openTrip(Trip trip) {
		return trip.getStatus().equals(TripStatus.OPEN);	
	}

	public String load() {
		if (trips != null)
			return "exito";
		else
			return "fallo";
	}

	@PostConstruct
	public void init() {
		list();
	}

	public String list() {

		if (trips != null) {
			return "exito";
		}
		try {
			Map<String, Object> session = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			User user = (User) session.get("LOGGEDIN_USER");

			setTrips(DTOAssembler.getViajesImplicadosDto(user.getId()));

			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "fallo";
		}

	}

	public String getRole(ViajeImplicadoDto trip) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");

		if (DateUtil.isAfter(new Date(), trip.getClosingDate())
				&& trip.getSeatStatus() == null) {

			return bundle.getString("statusSinPlaza");

		}

		else if (trip.getSeatStatus() == null) {

			if (trip.getAvailablePax() == 0) {
				return bundle.getString("statusSinPlaza");
			} else {
				return bundle.getString("statusPendiente");
			}
		} else if (trip.getSeatStatus().equals(SeatStatus.ACCEPTED)) {
			return bundle.getString("statusAdmitido");
		} else {
			return bundle.getString("statusCancelado");
		}

	}

	public String cancelSeat(ViajeImplicadoDto trip) {

		try {

			Map<String, Object> session = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			User user = (User) session.get("LOGGEDIN_USER");

			SeatService sService = Factories.services.getSeatService();
			sService.moveToExcluded(user.getId(), trip.getId());
			trips.remove(trip);
			generateFeebackMessages();

		} catch (Exception e) {

			e.printStackTrace();
			return "fallo";
		}

		return "exito";
	}

	private void generateFeebackMessages() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "",

				bundle.getString("plazaCancelada")));
	}

	public List<ViajeImplicadoDto> getTrips() {
		return trips;
	}

	public void setTrips(List<ViajeImplicadoDto> trips) {
		this.trips = trips;
	}

	public String formattedDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}

	public Trip getSelectedTrip() {
		return selectedTrip;
	}

	public void setSelectedTrip(Trip selectedTrip) {
		this.selectedTrip = selectedTrip;
	}
	
	public void onRowSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("tripInfoParam", selectedTrip.getId());
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("tripInfo.xhtml");
		} catch (IOException e) {
		}
	}

}
