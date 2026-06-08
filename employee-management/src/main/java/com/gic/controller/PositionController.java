package com.gic.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gic.entity.Position;
import com.gic.service.PositionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/positions")
public class PositionController {

    private static final Logger logger = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;

    @GetMapping
    public List<Position> getAllPositions() {
        logger.info("Fetching all positions");
        return positionService.getAllPositions();
    }

    @GetMapping("/{id}")
    public Optional<Position> getPosition(@PathVariable int id) {
        logger.info("Fetching position with id: {}", id);
        return positionService.getPosition(id);
    }

    @PostMapping
    public Position addPosition(@RequestBody Position position) {
        logger.info("Adding new position: {}", position.getPosition_name());
        return positionService.addPosition(position);
    }

    @PutMapping("/{id}")
    public Position updatePosition(@RequestBody Position position, @PathVariable int id) {
        logger.info("Updating position with id: {}", id);
        return positionService.updatePosition(position, id);
    }

    @DeleteMapping
    public void deleteAllPositions() {
        logger.warn("Deleting all positions");
        positionService.deleteAllPositions();
    }

    @DeleteMapping("/{id}")
    public void deletePositionByID(@PathVariable int id) {
        logger.info("Deleting position with id: {}", id);
        positionService.deletePositionByID(id);
    }
}
