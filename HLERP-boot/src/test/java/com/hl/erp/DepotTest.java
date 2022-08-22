package com.hl.erp;

import com.alibaba.fastjson.JSON;
import com.hl.erp.service.depot.DepotService;
import com.hl.erp.datasource.entities.Depot;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepotTest {

    @Resource
    private DepotService depotService;

    @Test
    public void getDepot() throws Exception{
        Depot result = depotService.getDepot(1l);
        log.info("depot result:" + JSON.toJSONString(result));
    }

    @Test
    public void getAllList() throws Exception{
        List<Depot> depotList =depotService.getAllList();
        log.info("depot result:" + JSON.toJSONString(depotList));
    }


}
