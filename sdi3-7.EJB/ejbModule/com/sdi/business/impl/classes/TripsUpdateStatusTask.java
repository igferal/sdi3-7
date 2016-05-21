package com.sdi.business.impl.classes;

import java.util.Timer;
import java.util.TimerTask;

public class TripsUpdateStatusTask {
	public static final int interval = 3000;
	
	private Timer timer = new Timer();
	
	class Task extends TimerTask {

		@Override
		public void run() {
//			TripDao dao = Factories.persistence.newTripDao();
//			List<Trip> trips = dao.findTravelsOpenAndClosed();
//			Date today = DateUtil.today();
//			
//			boolean modified = false;
//			for (Trip trip:trips) {
//				if (!trip.getStatus().equals(TripStatus.CANCELLED)) {
//					if (trip.getArrivalDate().before(today)
//							|| today.getTime() >= trip.getArrivalDate().getTime()) {
//						trip.setStatus(TripStatus.DONE);
//						modified = true;
//					}
//					
//					if (trip.getClosingDate().before(today)
//							|| today.getTime() >= trip.getClosingDate().getTime()) {
//						trip.setStatus(TripStatus.CLOSED);
//						modified = true;
//					}
//				}
//				
//				if (modified)
//					dao.update(trip);
//				
//				modified = false;
//			}

		}
		
	}
	
	public TripsUpdateStatusTask() {
		timer.schedule(new Task(), 0, interval);
	}
	
}
