package com.cdw.business.piece;

import org.springframework.data.repository.CrudRepository;

public interface PieceRepository extends CrudRepository<Piece,Integer> {

	Piece[] findAllByPublication(boolean pubOrWorkshop);  //publication = true -- workshop = false
}
