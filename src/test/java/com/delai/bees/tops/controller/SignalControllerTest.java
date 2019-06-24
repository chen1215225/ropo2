package com.delai.bees.tops.controller;

import com.ipukr.elephant.utils.DateUtils;
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
public class SignalControllerTest extends BaseControllerTest {
    @Test
    public void testQuery() throws Exception {

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/signal/search")
                .param("key","y")
        ).andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    public void testDelete() throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.delete("/signal/39")
        ).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdate() throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.patch("/signal/38")
                .param("key","DB122.DBD128")
                .param("parentName","DB122.DBW92")
                .param("val","7")
        ).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
