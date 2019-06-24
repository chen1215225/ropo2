package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.Machine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/16.
 */

public interface MachineRepository extends PagingAndSortingRepository<Machine, String> {

    @Query(value = "{ '_id' : { '$in' : ?0} }")
    List<Machine> ins(List<String> ids);

    @Query(value = "{ '_id' : ?0 }")
    Machine find(String id);

    @Query(value = "{ }")
    List<Machine> find();

    @Query(value = "{ }")
    List<Machine> query(Pageable pageable);
}
