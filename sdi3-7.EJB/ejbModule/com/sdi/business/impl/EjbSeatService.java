package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.sdi.business.impl.classes.SeatFind;
import com.sdi.business.impl.classes.SeatMoveToAccepted;
import com.sdi.business.impl.classes.SeatMoveToExcluded;
import com.sdi.business.impl.classes.SeatMoveToPending;
import com.sdi.model.Seat;

@Stateless
@WebService(name="SeatService")
public class EjbSeatService implements LocalSeatService, RemoteSeatService {

	@Override
	public void moveToAccepted(Long idUser, Long idTrip) {
		new SeatMoveToAccepted().accept(idUser, idTrip);
	}

	@Override
	public void moveToPending(Long idUser, Long idTrip) {
		new SeatMoveToPending().move(idUser, idTrip);
	}

	@Override
	public void moveToExcluded(Long idUser, Long idTrip) {
		new SeatMoveToExcluded().exclude(idUser, idTrip);
	}

	@Override
	public Seat find(Long[] id) {
		return new SeatFind(id).find();	
	}

	@Override
	public List<Seat> findByUserAndNotExcludedAndOpenTrip(Long id) {
		return new SeatFind().findByUserAndNotExcluded(id);
	}

	@Override
	public void moveSeatToExcluded(Seat seat) {
		new SeatMoveToExcluded().exclude(seat);
	}

}
