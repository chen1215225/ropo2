package com.delai.bees.tops.controller;

import com.delai.bees.tops.BootServerTest;
import com.ipukr.elephant.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
public class TopsControllerTest extends BaseControllerTest {

    @Test
    public void testTop200() throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/top200")
                        .param("begin", "2018-03-29 09:05:00")
                        .param("end", "2018-03-29 09:15:00")
        ).andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    public void testTops() throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/tops")
                        .param("begin", "2018-03-29 09:05:00")
                        .param("end", "2018-03-29 09:15:00")
        ).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
