package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.ModeCategorisation;
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

public interface ModeCategorisationRepository extends PagingAndSortingRepository<ModeCategorisation, String> {


    @Query(value = "{ '_id' : { '$exists' : true } }")
    List<ModeCategorisation> query();

    @Query(value = "{ '_id' : { '$exists' : true } }")
    List<ModeCategorisation> query(Pageable pageable);
}
