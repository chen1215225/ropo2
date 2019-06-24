package com.delai.bees.tops.service;

import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.document.Machine;
import com.delai.bees.tops.document.SignalConfig;
import com.delai.bees.tops.repository.AnalogSignalRepository;
import com.delai.bees.tops.repository.MachineRepository;
import com.delai.bees.tops.repository.SignalConfigRepository;
import com.delai.bees.tops.utils.MongoDateHelper;
import com.ipukr.elephant.utils.DateUtils;
//import com.mongodb.BasicDBList;
//import com.mongodb.BasicDBObject;
//import com.mongodb.QueryBuilder;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
@Service
public class AnalogSignalService {
    @Resource
    private AnalogSignalRepository mAnalogSignalRepository;
    @Resource
    private SignalConfigRepository mSignalConfigRepository;
    @Resource
    private MachineRepository mMachineRepository;
    @Resource
    private MongoOperations iMongoOperations;

    /**
     * @param signals
     * @param begin
     * @param end
     * @return
     */

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * 信号查询
     * @param signals
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    public List<AnalogSignal> ins(List<String> signals, Date begin, Date end) throws Exception {

        List<AnalogSignal> ass = mAnalogSignalRepository.ins4(signals, begin.getTime(), end.getTime());

        if (ass.size() > 0) {
            // Append SignalConfig
            List<String> scids = ass.stream().map(e->e.getSignalId()).collect(Collectors.toList());
            List<SignalConfig> scs = mSignalConfigRepository.ins(scids);
            for (AnalogSignal as: ass) {
                for (SignalConfig sc: scs ) {
                    if (as.getSignalId().equals(sc.getId())) {
                        as.setSignal(sc);
                    }
                }
            }
        }

        return ass;
    }
}
