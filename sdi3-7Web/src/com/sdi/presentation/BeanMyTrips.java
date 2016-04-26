package com.sdi.presentation;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import alb.util.log.Log;

import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.persistence.util.DateUtil;

@ManagedBean(name = "myTrips")
@ViewScoped
public class BeanMyTrips implements Serializable {
	private static final long serialVersionUID = -8662200441018725393L;

	private List<Trip> trips = null;
	private Map<Long, Boolean> selectedIds = new HashMap<Long, Boolean>();
	private List<Trip> tripsToDelete = null;
	private Trip selectedTrip;

	public String load() {
		if (trips != null)
			return "exito";
		else
			return "fallo";

	}

	public boolean openTrip(Trip trip) {
		return trip.getStatus().equals(TripStatus.OPEN);	
	}
	
	public boolean dateBefore(Date date) {

		if (date != null) {
			Date now = new Date();
			return DateUtil.isAfter(date, now);
		}
		return true;

	}

	@PostConstruct
	public void init() {
		listMyTrips();
	}

	public String cancel() {

		try {

			TripService tservice = Factories.services.createTripService();

			tripsToDelete = new ArrayList<Trip>();

			boolean cancelThisTrip = false;

			for (Trip trip : trips) {

				if (selectedIds.get(trip.getId()) != null)
					cancelThisTrip = selectedIds.get(trip.getId());

				if (cancelThisTrip) {

					trip.setStatus(TripStatus.CANCELLED);
					Log.debug("El viaje con destino  [%s] ha sido cancelado",
							trip.getDeparture().getCity());

					tservice.update(trip);
					tripsToDelete.add(trip);

				}

			}

			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(
					context, "msgs");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",

					bundle.getString("viajesCancelados")));

		} catch (NumberFormatException e) {
			return "fallo";
		} catch (Exception e) {
			return "fallo";
		}
		return "exito";
	}

	public String listMyTrips() {

		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		User user = (User) session.get("LOGGEDIN_USER");

		try {
			TripService tservice = Factories.services.createTripService();
			setTrips(tservice.travelsPromoter(user.getId()));
			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "fallo";
		}

	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
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

	public Map<Long, Boolean> getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(Map<Long, Boolean> selectedIds) {
		this.selectedIds = selectedIds;
	}

	public List<Trip> getTripsToDelete() {
		return tripsToDelete;
	}

	public void setTripsToDelete(List<Trip> tripsToDelete) {
		this.tripsToDelete = tripsToDelete;
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
