package com.sdi.presentation;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

@ManagedBean(name = "activeTrips")
@ViewScoped
public class BeanActiveTrips implements Serializable {
	private static final long serialVersionUID = -8662200441018725390L;

	private List<Trip> trips = null;
	private List<Trip> filteredTrips = null;

	private Trip selectedTrip;

	@PostConstruct
	public void init() {
		list();
	}

	public String load() {

		if (trips != null)
			return "exito";
		else
			return "fallo";
	}

	public String listMyTrips() {

		try {
			TripService tservice = Factories.services.createTripService();
			setTrips(tservice.listActiveTrips(new Date()));
			return "exito";

		} catch (Exception e) {
			return "fallo";
		}

	}

	public String list() {

		try {
			TripService tservice = Factories.services.createTripService();
			setTrips(tservice.listActiveTrips(new Date()));
			return "exito";

		} catch (Exception e) {
			return "fallo";
		}

	}

	/*
	 * public boolean filterByCost(Object value, Object filter, Locale locale) {
	 * String filterText = (filter == null) ? null : filter.toString().trim();
	 * if(filterText == null||filterText.equals("")) { return true; }
	 * 
	 * if(value == null) { return false; }
	 * 
	 * return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0; }
	 */

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	public List<Trip> getFilteredTrips() {
		return filteredTrips;
	}

	public void setFilteredTrips(List<Trip> filteredTrips) {
		this.filteredTrips = filteredTrips;
	}

	public String formattedDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
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

	public Trip getSelectedTrip() {
		return selectedTrip;
	}

	public void setSelectedTrip(Trip selectedTrip) {
		this.selectedTrip = selectedTrip;
	}
}
