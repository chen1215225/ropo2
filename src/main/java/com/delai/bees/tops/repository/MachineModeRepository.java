package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.MachineMode;
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

public interface MachineModeRepository extends PagingAndSortingRepository<MachineMode, String> {


//    @Query(value = "{ '$and': [ {'On' : { '$lt' : ?1 }}, {'$or':[{ 'Off': null}, { 'Off': { '$gt': '$1'}} ]} ]}")
    @Query(value = "{ '$and': [ {'On' : { '$lt' : ?1 }}, { 'Off': { '$gt': ?0}} ]}")
    public List<MachineMode> cross(Date from, Date to);

    @Query(value = "{'MachineId' : ?0, 'Off' : { '$gt': ?1, '$lt' : ?2}, 'On' : { '$gt' : ?1, '$lt' : ?2 }}")
    public List<MachineMode> query(String machineId, Date from, Date to);

    @Query(value = "{ '_id' : { '$exists' : true } }")
    public List<MachineMode> query();

    @Query(value = "{ '_id' : { '$exists' : true } }")
    public List<MachineMode> query(Pageable pageable);

}
