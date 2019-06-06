package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackRepositoryTest {

    @Autowired
    private TrackRepository trackRepository;
    private Track track;


    @Before
    public void setUp() throws Exception {

        track= new Track();
        track.setTrackId(10);
        track.setTrackName("Aishu");
        track.setTrackComments("good");

    }

    @After
    public void tearDown() throws Exception {
        trackRepository.deleteAll();
    }


    @Test
    public void testSaveTrack(){
        trackRepository.save(track);
        Track fetchTrack = trackRepository.findById(track.getTrackId()).get();
        Assert.assertEquals(10,fetchTrack.getTrackId());

    }

    @Test
    public void testSaveTrackFailure(){
        Track testTrack = new Track(101,"Aishu","good");
        trackRepository.save(track);
        Track fetchTrack = trackRepository.findById(track.getTrackId()).get();
        Assert.assertNotSame(testTrack,track);
    }

    @Test
    public void testGetAllTrack(){
        Track track1 = new Track(102,"DJ","nice");
        Track track2 = new Track(103,"lucifer","awesome");
        trackRepository.save(track1);
        trackRepository.save(track2);

        List<Track> list = trackRepository.findAll();
        Assert.assertEquals("DJ",list.get(0).getTrackName());
    }

    @Test
    public void testDeleteTrack(){
        Track track=new Track(2,"everynight","super");
        trackRepository.delete(track);
        boolean deletedTrack=trackRepository.existsById(2);
        Assert.assertEquals(false,deletedTrack);
    }

    @Test
    public void testDeleteTrackFailure(){
        Track track=new Track(2,"everynight","super");
        trackRepository.delete(track);
        boolean deletedTrack=trackRepository.existsById(2);
        Assert.assertNotSame(true,deletedTrack);
    }


    @Test
    public void testUpdateTrack(){
        Track track=new Track(3,"manam","nice");
        Track track1=new Track(4,"everynight","song");
        trackRepository.save(track);
        trackRepository.save(track1);
        trackRepository.findById(track.getTrackId()).get().setTrackComments("super");
        List<Track> list=trackRepository.findAll();
        Assert.assertEquals("super",list.get(0).getTrackComments());
    }

    @Test
    public void testUpdateTrackFailure(){
        Track track=new Track(3,"manam","nice");
        Track track1=new Track(4,"everynight","song");
        trackRepository.save(track);
        trackRepository.save(track1);
        trackRepository.findById(track.getTrackId()).get().setTrackComments("super");
        List<Track> list=trackRepository.findAll();
        Assert.assertNotSame("nice song",list.get(0).getTrackComments());
    }

}


