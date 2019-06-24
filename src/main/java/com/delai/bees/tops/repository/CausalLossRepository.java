package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.CausalLoss;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/27.
 */
public interface CausalLossRepository extends PagingAndSortingRepository<CausalLoss, String> {
    /**
     *
     * @param from
     * @param to
     * @return
     */
    @Query(value = "{ 'On' : { '$gt' : ?0, '$lt' : ?1} }")
    List<CausalLoss> range(Date from, Date to);
}
