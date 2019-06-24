package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.domain.AnalogSignalGRP;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/20.
 */
@Repository
public class AnalogSignalCustomRepository {
    @Resource
    private MongoTemplate iMongoTemplate;

    /**
     * 获取首条信号记录
     * @param ids 信号范围
     * @param begin 开始时间
     * @param end 结束时间
     * @param direction 排序规则
     * @return
     */
    public List<AnalogSignal> fetchFirstRecordAccordingSignalsIdsAndTsRange(List<String> ids, Date begin, Date end, Sort.Direction direction) {
        MatchOperation match = Aggregation.match(
                new Criteria().andOperator(new Criteria("SignalId").in(ids), new Criteria("Ts").gt(begin).lt(end), new Criteria("Val").gt(0))
        );
        GroupOperation group = Aggregation.group("SignalId")
                .first("$SignalId").as("SignalId")
                .first("$Ts").as("Ts")
                .first("$Name").as("Name")
                .first("$Val").as("Val");
//        LookupOperation lookup = Aggregation.lookup("SignalConfig", "SignalId", "_id", "Config");
//        UnwindOperation unwin = Aggregation.unwind("$Config");

        SortOperation sorted = Aggregation.sort(direction, "Ts");

        Aggregation aggregation = Aggregation.newAggregation(
                match,
                group,
//                lookup,
//                unwin,
                sorted);
        AggregationResults<AnalogSignal> results = iMongoTemplate.aggregate(aggregation, "AnalogSignals", AnalogSignal.class);
        return  results.getMappedResults();
    }


    /**
     * @param ids
     * @param begin
     * @param end
     * @return
     */
    public List<AnalogSignalGRP> fetchMaxAndMinValAccordingSignalsIdsAndTsRange(List<String> ids, Date begin, Date end){
        MatchOperation match = Aggregation.match(
                new Criteria().andOperator(
                        new Criteria("SignalId").in(ids),
                        new Criteria("Ts").gte(begin).lte(end)
                )
        );
        GroupOperation group = Aggregation.group("SignalId")
                .first("$SignalId").as("SignalId")
                .max("$Val").as("Max")
                .min("$Val").as("Min");
        Aggregation aggregation = Aggregation.newAggregation(
                match,
                group);
        AggregationResults<AnalogSignalGRP> results = iMongoTemplate.aggregate(aggregation, "AnalogSignals", AnalogSignalGRP.class);
        return  results.getMappedResults();
    }

}
