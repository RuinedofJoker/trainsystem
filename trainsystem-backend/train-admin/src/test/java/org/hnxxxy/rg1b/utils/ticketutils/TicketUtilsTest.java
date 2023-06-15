package org.hnxxxy.rg1b.utils.ticketutils;

import org.hnxxxy.rg1b.domain.TrainTrips;
import org.hnxxxy.rg1b.domain.dto.Ticket;
import org.hnxxxy.rg1b.utils.BeanUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TicketUtilsTest {

    //演示查询列车
    //@Test
    public void test01(){
        List<Ticket> tickets = TicketUtilsApi.getTickets("2023-03-15", "上海", "北京", PurposeCode.PurposeCode_ADULT);
        for (Ticket ticket:tickets){
            ticket = TicketUtilsApi.getPrice(ticket);
            System.out.println(ticket);
        }
    }


    //演示获取所有城市
    //@Test
    public void test02(){
        Map<String, String> allStation = TicketUtilsApi.getAllStation();
        for (String value:allStation.keySet()){
            System.out.println(allStation.get(value)+":"+value);
        }
    }

    //演示获取指定时间的全部列车信息
    //@Test
    public void test03(){
        try {
            List<Ticket> g = TicketUtilsApi.getDayAllTickets("G", "2023-03-15");
            for (Ticket ticket:g){
                System.out.println(ticket);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //演示查询车次详情
    //@Test
    public void test04(){
        try {
            //List<Ticket> g = TicketUtilsApi.getDayAllTickets("G", "2023-03-15");
            //Ticket ticket = g.get(0);
            List<Ticket> trainInfo = TicketUtilsApi.getTrainInfo("0h00000G4809", "VNP", "DFT", "2023-03-15");
            System.out.println(trainInfo);
            TrainTrips trainTrips = new TrainTrips();
            BeanUtils.copyProperties(trainInfo.get(0),trainTrips);
            System.out.println(trainTrips);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
