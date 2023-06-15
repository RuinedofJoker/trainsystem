package org.hnxxxy.rg1b.task;

import org.hnxxxy.rg1b.TrainApplication;
import org.hnxxxy.rg1b.common.utils.uuid.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TrainApplication.class})
public class TrainInfoInjectTaskTest {

    @Test
    public void tripsInjectBeforeDaysTest(){
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void insertSeatTypesTest(){

    }
}
