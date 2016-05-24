package com.sdi.business.impl.classes;

//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.ejb.LocalBean;
//import javax.ejb.Singleton;
//import javax.ejb.Startup;
//import javax.ejb.Timeout;
//import javax.ejb.Timer;
//import javax.ejb.TimerService;
//
//import com.sdi.infrastructure.Factories;
//import com.sdi.model.Trip;
//import com.sdi.model.TripStatus;
//import com.sdi.persistence.TripDao;
//import com.sdi.persistence.util.DateUtil;

//@Singleton
//@LocalBean
//@Startup
public class TripsUpdateStatusTask {
//	public static final int interval = 3000;
//
//	@Resource
//	private TimerService timerService;
//
//	@PostConstruct
//	private void init() {
//		timerService.createTimer(0, interval, "TaskTrips");
//	}
//
//	@Timeout
//	public void execute(Timer timer) {
//		TripDao dao = Factories.persistence.newTripDao();
//		List<Trip> trips = dao.findTravelsOpenAndClosed();
//		Date today = DateUtil.today();
//
//		boolean modified = false;
//		for (Trip trip : trips) {
//			if (!trip.getStatus().equals(TripStatus.CANCELLED)) {
//				if (trip.getArrivalDate().before(today)
//						|| today.getTime() >= trip.getArrivalDate().getTime()) {
//					trip.setStatus(TripStatus.DONE);
//					modified = true;
//				}
//
//				if (trip.getClosingDate().before(today)
//						|| today.getTime() >= trip.getClosingDate().getTime()) {
//					trip.setStatus(TripStatus.CLOSED);
//					modified = true;
//				}
//			}
//
//			if (modified)
//				dao.update(trip);
//
//			modified = false;
//		}
//
//	}

}
