package com.gic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gic.entity.Position;
import com.gic.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;
	
	public List<Position> getAllPositions() {
		return positionRepository.findAll();
	}

	public Optional<Position> getPosition(int id) {
		return positionRepository.findById(id);
	}

	public Position addPosition(Position newPosition) {
		return positionRepository.save(newPosition);		
	} 

	// FIXED: Setting the ID from the URL parameter onto the object before saving
		public Position updatePosition(Position positionDetails, int id) {
			return positionRepository.findById(id)
				.map(existingPosition -> {
					existingPosition.setPosition_name(positionDetails.getPosition_name());
					return positionRepository.save(existingPosition);
				})
				.orElseGet(() -> {
					// Optional: If ID doesn't exist, create it as a new record
					positionDetails.setPosition_id(id);
					return positionRepository.save(positionDetails);
				});
		}

	public void deleteAllPositions() {
		positionRepository.deleteAll();
	}

	public void deletePositionByID(int id) {
		positionRepository.deleteById(id);
	}

}
