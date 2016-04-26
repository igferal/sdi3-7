package com.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.sdi.model.AddressPoint;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.model.Waypoint;
import com.sdi.persistence.util.DateUtil;

@ManagedBean(name = "trip")
@SessionScoped
public class BeanTrip extends Trip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6283148030775995714L;

	public BeanTrip() {

	}

	public void fillTrip() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");

		setArrivalDate(DateUtil.addDays(new Date(), 21));
		setAvailablePax(2);
		setClosingDate(DateUtil.addDays(new Date(), 19));
		setComments(bundle.getString("ctcomment"));
		setDeparture(new AddressPoint(bundle.getString("ctCalO"),
				bundle.getString("ctCiuO"), bundle.getString("ctProO"),
				bundle.getString("ctPaiO"), bundle.getString("ctCPO"),
				new Waypoint(23.4D, 43.45D)));
		setDepartureDate(DateUtil.addDays(new Date(), 20));
		setDestination(new AddressPoint(bundle.getString("ctCalD"),
				bundle.getString("ctCiuD"), bundle.getString("ctProD"),
				bundle.getString("ctPaiD"), bundle.getString("ctCPD"),
				new Waypoint(23.4D, 43.45D)));
		setEstimatedCost(70D);
		setMaxPax(2);
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		User user = (User) session.get("LOGGEDIN_USER");
		setPromoterId(user.getId());
		setStatus(TripStatus.OPEN);

	}

}
