package org.hnxxxy.rg1b.utils.ticketutils;

import org.hnxxxy.rg1b.domain.dto.Ticket;

import java.text.SimpleDateFormat;
import java.util.*;

public class TicketUtilsApi {

    //查询列车
    public static List<Ticket> getTickets(Date trainDate, String fromStation, String toStation, PurposeCode purposeCodes){

        String purpose = purposeCodes.toString();

        List<Ticket> ticketList;
        try {
            ticketList = QueryTickets.queryTickets(new SimpleDateFormat("yyyy-MM-dd").format(trainDate).toString(), GetJsonAndDeal.getCodeByName(fromStation), GetJsonAndDeal.getCodeByName(toStation), purpose);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //直接查询票价（太慢了，删了）
//        for (Ticket ticket:ticketList){
//            try {
//                GetJsonAndDeal.getTicketPrice(ticket);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }

        return ticketList;
    }

    public static List<Ticket> getTickets(String trainDate, String fromStation, String toStation, PurposeCode purposeCodes){

        String purpose = purposeCodes.toString();

        List<Ticket> ticketList;
        try {
            ticketList = QueryTickets.queryTickets(trainDate, fromStation, toStation, purpose);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //直接查询票价（太慢了，删了）
        /*for (Ticket ticket:ticketList){
            try {
                GetJsonAndDeal.getTicketPrice(ticket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }*/

        return ticketList;
    }

    //根据ticket获取票价
    public static Ticket getPrice(Ticket ticket){

        try {
            GetJsonAndDeal.getTicketPrice(ticket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ticket;
    }

    //查询所有站点
    public static Map<String,String> getAllStation(){

        return GetJsonAndDeal.getAllStation();
    }

    //获取指定时间的全部列车信息
    //firstStr表示车次的第一个字符 第一个字符有G-高铁/城际/复兴号，D-动车，Z-直达，T-特快，K-快速,其他(1-8)
    public static List<Ticket> getDayAllTickets(String firstStr, Date date){

        List<Ticket> dayAllTickets;
        try {
            dayAllTickets = GetJsonAndDeal.getDayAllTickets(firstStr, new SimpleDateFormat("yyyy-MM-dd").format(date).toString().replace("-",""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dayAllTickets;
    }

    //获取指定时间的全部列车信息
    //firstStr表示车次的第一个字符 第一个字符有G-高铁/城际/复兴号，D-动车，Z-直达，T-特快，K-快速,其他(1-8)
    public static List<Ticket> getDayAllTickets(String firstStr, String date){

        List<Ticket> dayAllTickets;
        try {
            dayAllTickets = GetJsonAndDeal.getDayAllTickets(firstStr, date.replace("-",""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dayAllTickets;
    }

    public static List<Ticket> getTrainInfo(String trainNo,String fromStationNo,String toStationNo,String trainDate){
        Ticket ticket = new Ticket();
        ticket.setTrainNo(trainNo);
        ticket.setFromStationNo(fromStationNo);
        ticket.setToStationNo(toStationNo);
        ticket.setTrainDate(trainDate);
        List<Ticket> trainInfo;
        try {
            trainInfo = GetJsonAndDeal.getTrainInfo(ticket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return trainInfo;
    }
}
