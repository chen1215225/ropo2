package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.AnalogSignal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 *
 */

public interface AnalogSignalRepository extends PagingAndSortingRepository<AnalogSignal, String> {

    @Query(value = "{ 'Ts': { '$gt': ?0, '$lt': ?1}}")
    List<AnalogSignal> query(Date from, Date to);
    /**
     * 根据时间范围，信号获取
     * @param signalId 信号
     * @param from
     * @param to
     * @return
     */
    @Query(value = "{ 'SignalId': ?0, 'Ts': { '$gt': ?1, '$lt': ?2}}")
    List<AnalogSignal> query(String signalId, Date from, Date to);

    /**
     * @param sis 信号列表
     * @param from 开始时间
     * @param to 结束时间
     * @return
     */
    @Query(value = "{ 'SignalId': { '$in': ?0 }, 'Ts': { '$gt': ?1, '$lt': ?2}}")
    List<AnalogSignal> ins(List<String> sis, Date from, Date to);


    @Query(value = "{ '$and' : [{ 'SignalId': { '$in': ?#{[0]} }}, { 'Ts' : { '$gt': ?#{[1]}}}, { 'Ts': { '$lt': ?#{[2]}}}]}")
    List<AnalogSignal> ins2(List<String> sis, Date from, Date to);


    @Query(value = "{ '$and' : [{ 'SignalId': { '$in': ?#{[0]} }}, { 'Ts' : { '$gt': {'$date': ?#{[1]} }}}, { 'Ts': { '$lt': {'$date': ?#{[2]}}}}]}")
    List<AnalogSignal> ins3(List<String> sis, Long from, Long to);


    @Query(value = "{ 'SignalId': { '$in': ?#{[0]} }, 'Ts': { '$gt': {'$date': ?#{[1]} }, '$lt': {'$date': ?#{[2]} } } }")
    List<AnalogSignal> ins4(List<String> sis, Long from, Long to);


    @Query(value = "{ }")
    List<AnalogSignal> query();

    @Query(value = "{ }")
    List<AnalogSignal> query(Pageable pageable);

    /**
     * @param signalId
     * @param sort
     * @return
     */
    @Query(value = "{ 'SignalId': ?0 }")
    List<AnalogSignal> newest(String signalId, Sort sort);



}
