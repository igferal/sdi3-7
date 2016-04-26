package com.sdi.presentation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.AddressPoint;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.model.User;
import com.sdi.model.Waypoint;
import com.sdi.persistence.util.DateUtil;

@ManagedBean(name = "tripController")
@RequestScoped
public class BeanTripController {

	@ManagedProperty(value = "#{trip}")
	private BeanTrip trip;
	private AddressPoint departure;
	private AddressPoint destination;
	private Date dateSalida;
	private Date dateInscripcion;
	private Date dateLlegada;
	private String costeEstimado;
	private String plazasDisponibles;
	private String plazasMaximas;
	private String coordenadasOrigen;
	private String coordenadasDestino;
	private String provincias[];
	private List<String> autoCompletado;

	public List<String> getAutoCompletado() {
		return autoCompletado;
	}

	public void setAutoCompletado(List<String> autoCompletado) {
		this.autoCompletado = autoCompletado;
	}

	public String[] getProvincias() {
		return provincias;
	}

	public void setProvincias(String ciudades[]) {
		this.provincias = ciudades;
	}

	public String getCoordenadasOrigen() {
		return coordenadasOrigen;
	}

	public void setCoordenadasOrigen(String coordenadasOrigen) {
		this.coordenadasOrigen = coordenadasOrigen;
	}

	public String getCoordenadasDestino() {
		return coordenadasDestino;
	}

	public void setCoordenadasDestino(String coordenadasDestino) {
		this.coordenadasDestino = coordenadasDestino;
	}

	public String getCosteEstimado() {
		return costeEstimado;
	}

	public void setCosteEstimado(String costeEstimado) {
		this.costeEstimado = costeEstimado;
	}

	public String getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(String plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public String getPlazasMaximas() {
		return plazasMaximas;
	}

	public void setPlazasMaximas(String plazasMaximas) {
		this.plazasMaximas = plazasMaximas;
	}

	public Date getDateInscripcion() {
		return dateInscripcion;
	}

	public void setDateInscripcion(Date dateInscripcion) {
		this.dateInscripcion = dateInscripcion;
	}

	public Date getDateLlegada() {
		return dateLlegada;
	}

	public void setDateLlegada(Date dateLlegada) {
		this.dateLlegada = dateLlegada;
	}

	public BeanTrip getTrip() {
		return trip;
	}

	public void setTrip(BeanTrip trip) {
		this.trip = trip;
	}

	public AddressPoint getDeparture() {
		return departure;
	}

	public void setDeparture(AddressPoint departure) {
		this.departure = departure;
	}

	public Date getDateSalida() {
		return dateSalida;
	}

	public void setDateSalida(Date dateSalida) {
		this.dateSalida = dateSalida;
	}

	public AddressPoint getArrival() {
		return destination;
	}

	public void setArrival(AddressPoint arrival) {
		this.destination = arrival;
	}

	public List<String> completeList(String query) {

		List<String> filteredThemes = new ArrayList<String>();

		for (int i = 0; i < autoCompletado.size(); i++) {
			String state = autoCompletado.get(i);
			if (state.toLowerCase().startsWith(query.toLowerCase())) {
				filteredThemes.add(state);
			}
		}

		return filteredThemes;
	}

	@PostConstruct
	public void init() {

		departure = new AddressPoint("", "", "", "", "", new Waypoint(0D, 0D));
		setArrival(new AddressPoint("", "", "", "", "", new Waypoint(0D, 0D)));
		System.out.println("BeanTripController - PostConstruct");

		trip = (BeanTrip) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("trip"));
		if (trip == null) {
			System.out.println("BeanTrip - No existia");
			trip = new BeanTrip();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("trip", trip);
		}
		fetchProvincias();
	}

	public String save() {

		TripService tservice;
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");

		try {

			String respuesta = fechTripInfo(bundle);
			if (respuesta.equals("fallo"))
				return "fallo";

			tservice = Factories.services.createTripService();

			tservice.saveTrip(trip);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",

					bundle.getString("viajeCreado")));
		}

		catch (Exception e) {
			e.printStackTrace();
			return "fallo";
		}

		return "exito";

	}

	private void completeWaypoints() {

		if (!coordenadasOrigen.equals("")) {
			System.out.println("PASO");
			String[] coorOr = coordenadasOrigen.split("C");
			Double latOr = Double.parseDouble(coorOr[0]);
			Double lonOr = Double.parseDouble(coorOr[1]);
			Waypoint cO = new Waypoint(latOr, lonOr);
			departure.setWaypoint(cO);

		}
		if (!coordenadasDestino.equals("")) {
			System.out.println("PASO2");
			String[] coorDes = coordenadasOrigen.split("C");
			Double latDes = Double.parseDouble(coorDes[0]);
			Double lonDes = Double.parseDouble(coorDes[1]);
			Waypoint cD = new Waypoint(latDes, lonDes);
			destination.setWaypoint(cD);
		}
	}

	public String editTrip() {
		TripService tservice;
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");
		try {

			tservice = Factories.services.createTripService();
			String respuesta = fechTripInfo(bundle);
			if (respuesta.equals("fallo"))
				return "fallo";
			tservice.update(trip);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",

					bundle.getString("viajeEditado")));

		} catch (Exception e) {
			e.printStackTrace();
			return "fallo";
		}
		return "exito";
	}

	public String fillEditView(Trip tripToEdit) {

		try {
			// identificamos el viaje
			trip.setId(tripToEdit.getId());

			System.out.println(tripToEdit.toString());
			// colocamos la informacion del formulario
			setArrival(tripToEdit.getDestination());
			setCosteEstimado("" + tripToEdit.getEstimatedCost());
			setDateInscripcion(tripToEdit.getClosingDate());
			setDateSalida(tripToEdit.getDepartureDate());
			setDateLlegada(tripToEdit.getArrivalDate());
			setDeparture(tripToEdit.getDeparture());
			setPlazasDisponibles("" + tripToEdit.getAvailablePax());
			setPlazasMaximas("" + tripToEdit.getMaxPax());

		} catch (Exception e) {

			e.printStackTrace();
			return "fallo";
		}

		return "exito";
	}

	private String fechTripInfo(ResourceBundle bundle) {

		try {

			completeWaypoints();
			Double estimatedCost = Double.parseDouble(costeEstimado);
			Integer availablePax = Integer.parseInt(plazasDisponibles);
			Integer maxPax = Integer.parseInt(plazasMaximas);

			if (maxPax < availablePax) {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",

						bundle.getString("createTrip_wrongSeats")));
				return "fallo";

			}

			if (DateUtil.isAfter(dateInscripcion, dateSalida)
					|| DateUtil.isAfter(dateSalida, dateLlegada)) {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								bundle.getString("createTrip_wrongDates")));
				return "fallo";
			}

			Map<String, Object> session = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			User user = (User) session.get("LOGGEDIN_USER");
			trip.setArrivalDate(dateLlegada);
			trip.setAvailablePax(availablePax);
			trip.setClosingDate(dateInscripcion);
			trip.setDeparture(departure);
			trip.setDepartureDate(dateSalida);
			trip.setDestination(destination);
			trip.setEstimatedCost(estimatedCost);
			trip.setMaxPax(maxPax);
			trip.setStatus(TripStatus.OPEN);
			trip.setPromoterId(user.getId());
		}

		catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
							.getString("createTrip_wrongNumberInputs")));
			return "fallo";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "exito";
	}

	public void fetchProvincias() {

		provincias = new String[] { "Albacete", "Alicante", "Almería", "Alava",
				"Asturias", "Avila", "Badajoz", "Baleares", "Barcelona",
				"Vizcaya", "Burgos", "Caceres", "Cadiz", "Cantabria",
				"Castellon", "Ciudad Real", "Córdoba", "A Coruña", "Cuenca",
				"Gipuzcua", "Girona", "Granada", "Guadalajara", "Huelva",
				"Huesca", "Jaen", "Leon", "Lleida", "Lugo", "Madrid", "Malaga",
				"Murcia", "Navarra", "Ourense", "Palencia", "Las almas",
				"Pontevedra", "La Rioja", "Salamanca",
				"Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria",
				"Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid",
				"Zamora", "Zaragoza", "Ceuta", "Melilla" };

		autoCompletado = new ArrayList<String>();

		for (int i = 0; i < provincias.length; i++) {
			autoCompletado.add(provincias[i]);
		}

	}

	public void fillWithPredeterminatedTrip() {

		trip.fillTrip();
		setArrival(trip.getDestination());
		setCosteEstimado("" + trip.getEstimatedCost());
		setDateInscripcion(trip.getClosingDate());
		setDateSalida(trip.getDepartureDate());
		setDateLlegada(trip.getArrivalDate());
		setDeparture(trip.getDeparture());
		setPlazasDisponibles("" + trip.getAvailablePax());
		setPlazasMaximas("" + trip.getMaxPax());
		setCoordenadasDestino(trip.getDestination().getWaypoint().toString());
		setCoordenadasOrigen(trip.getDeparture().getWaypoint().toString());

	}

	@PreDestroy
	public void end() {
		System.out.println("BeanTripController - PreDestroy");
	}

}
