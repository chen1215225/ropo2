package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.SignalConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/16.
 */

public interface SignalConfigRepository extends PagingAndSortingRepository<SignalConfig, String> {


    /**
     * 获取所有信号
     * @return
     */
    @Query(value = "{ }")
    public List<SignalConfig> find();

    /**
     * 根据Key值获取
     * @param signalId
     * @return
     */
    @Query(value = "{ '_id' : ?0 }")
    SignalConfig find(String signalId);

    /**
     * @param addresses
     * @return
     */
    @Query(value = "{ 'Address' : { '$in' : ?0 } }")
    List<SignalConfig> findByAddresses(Set<String> addresses);

    /**
     * 根据Keys获取
     * @param sids
     * @return
     */
    @Query(value = "{ '_id' : { '$in' : ?0 } }")
    public List<SignalConfig> ins(List<String> sids);

    /**
     * 匹配查询/分页
     * @param pageable
     * @return
     */
    @Query(value = "{ }")
    public List<SignalConfig> query(Pageable pageable);
}
