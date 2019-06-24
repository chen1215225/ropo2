package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.Mode;
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

public interface ModeRepository extends PagingAndSortingRepository<Mode, String> {

    @Query(value = "{ '_id' : { '$in' : ?0 } }")
    List<Mode> ins(List<String> ids);

    @Query(value = "{ '$and' : [ { 'On' : { '$gt' : ?1}}, { 'Off' : { '$lt' : ?2}} ]}")
    public List<Mode> query(Date from, Date to);

    @Query(value = "{ '$and' : [{'MachineId' : ?0}, { 'On' : { '$gt' : ?1}}, { 'Off' : { '$lt' : ?2}} ]}")
    public List<Mode> query(String machineId, Date from, Date to);

    @Query(value = "{}")
    public List<Mode> query();

    @Query(value = "{}")
    public List<Mode> query(Pageable pageable);
}
