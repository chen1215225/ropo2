package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.DigitalSignal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/16.
 */

public interface DigitalSignalRepository extends PagingAndSortingRepository<DigitalSignal, String> {

//    @Query(value = "{ 'On':{ '$gt': ?0, '$lt':?1}, 'Off': {'$gt':?0, '$lt':?1}}")
    @Query(value = "{ 'On':{ '$gt': ?0, '$lt': ?1}}")
    public List<DigitalSignal> range(Date from, Date to);

    @Query(value = "{ 'SignalId': { '$in': ?0 }, 'Off': { '$gt': ?1, '$lt': ?2 }, 'On': { '$gt': ?1, '$lt': ?2 } }")
    public List<DigitalSignal> ins(List<String> sis, Date from, Date to);

    @Query(value = "{ }")
    public List<DigitalSignal> query();

    @Query(value = "{ }")
    public List<DigitalSignal> query(Pageable pageable);



}
