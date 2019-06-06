package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exceptions.TrackNotFoundException;
import com.stackroute.muzixservice.repository.TrackRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TrackServiceImpl  implements TrackService{

    TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        Track savedTrack=null;
        if (trackRepository.existsById(track.getTrackId())) {
            throw new TrackAlreadyExistsException("track already exists");
        } else {
            savedTrack = trackRepository.save(track);
            if (savedTrack == null) {
                throw new TrackAlreadyExistsException("track not empty");
            }
            return savedTrack;
        }
    }


    @Override
    public List<Track> getAllTrack() {
        return trackRepository.findAll();
    }

    @Override
    public boolean delete(Track track) throws TrackNotFoundException {
        if (trackRepository.existsById(track.getTrackId())) {
            trackRepository.deleteById(track.getTrackId());
            return true;
        } else {
            throw new TrackNotFoundException("Exception in deleting track");
        }
    }


    @Override
    public Track updateComments(Track track) throws TrackNotFoundException {
        Optional optional=trackRepository.findById(track.getTrackId());
        if (optional.isPresent()) {
            Track track1 = trackRepository.findById(track.getTrackId()).get();
            track1.setTrackComments(track.getTrackComments());
            trackRepository.save(track1);
            return track1;
        } else {
            throw new TrackNotFoundException("track not found");
        }
    }

    @Override
    public List<Track> getTrackByName(String trackName) throws TrackNotFoundException {
        List<Track> trackList=null;
        trackList=trackRepository.getTrackByName(trackName);
        if(trackList.isEmpty()){
            throw new TrackNotFoundException("track not found");
        }
        return trackList;
    }
}


