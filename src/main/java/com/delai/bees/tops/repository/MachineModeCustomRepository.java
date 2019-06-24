package com.delai.bees.tops.repository;

import com.delai.bees.tops.document.MachineMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/16.
 */
@Slf4j
@Repository
public class MachineModeCustomRepository {
    @Resource
    private MongoTemplate iMongoTemplate;

    /**
     * @param ids
     * @param ts
     * @return
     */
    public List<MachineMode> fetchAccordingMachineIdsAndTs(List<String> ids, Date ts) {
        MatchOperation condition = Aggregation.match(
                new Criteria().andOperator(
                        new Criteria("MachineId").in(ids),
                        new Criteria("On").lt(ts),
                        new Criteria("Off").gt(ts)
                )
        );

        Aggregation aggr = Aggregation.newAggregation(condition,
                Aggregation.unwind("$Mode"),
                Aggregation.lookup("Modes", "ModeId", "_id", "Mode"),
                Aggregation.unwind("$MachineProduct"),
                Aggregation.lookup("Machines", "MachineId", "_id", "MachineProduct")
        );

        AggregationResults<MachineMode> rst = iMongoTemplate.aggregate(aggr, "MachineModes", MachineMode.class);
        return rst.getMappedResults();
    }


    /**
     * @param ids
     * @param begin
     * @param end
     * @return
     */
    public List<MachineMode> fetchProducingAccordingMachineIdsAndTimeRange(List<String> ids, Date begin, Date end) {

//        MatchOperation condition = Aggregation.match(
//                new Criteria().andOperator(
//                        new Criteria("MachineId").in(ids),
//                        new Criteria("On").gte(begin),
//                        new Criteria("Off").lte(end),
//                        new Criteria("ModeId").in(Arrays.asList("44196d22-ae6d-48dd-8492-517ae55dd0fd"))
//                )
//        );
//        Aggregation aggr = Aggregation.newAggregation(condition,
//                Aggregation.lookup("Modes", "ModeId", "_id", "mode"),
//                Aggregation.unwind("$mode"),
//                Aggregation.match(new Criteria().andOperator(
//                        new Criteria("mode.ModeType").in(2)
//                ))
//        );

        Query query = new Query(
                new Criteria().andOperator(
                        new Criteria("MachineId").in(ids),
                        new Criteria("Off").gte(begin),
                        new Criteria("On").lte(end),
                        new Criteria("ModeId").in(Arrays.asList("44196d22-ae6d-48dd-8492-517ae55dd0fd"))
                )
        );
        return iMongoTemplate.find(query, MachineMode.class);

//        AggregationResults<MachineMode> rst = iMongoTemplate.find(condition, "MachineModes", MachineMode.class);
//        return rst.getMappedResults();
    }

}
