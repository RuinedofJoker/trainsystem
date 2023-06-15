package org.hnxxxy.rg1b.utils.ticketutils;

import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import org.hnxxxy.rg1b.domain.dto.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryTickets {

    public static List<Ticket> queryTickets(String trainDate, String fromStation, String toStation, String purposeCodes) throws Exception {
        List<String> list = GetJsonAndDeal.getTicketsInfo(trainDate, fromStation,toStation, purposeCodes);
        Pattern p = Pattern.compile("[^\\[].*[^\\]]");
        Matcher m ;
        Ticket bean;
        List<Ticket> beanList = new ArrayList<>();
        for(int i =0 ; i<list.size();i++){
            m = p.matcher(list.get(i));
            if(m.find())
                m.group(0);
            JSONArray jsonArray = JSONArray.fromObject(m.group(0).split(","));
            bean = new Ticket();
            BeanUtils.setProperty(bean, "stationTrainCode",jsonArray.get(0).toString());
            BeanUtils.setProperty(bean, "fromStationName",jsonArray.get(1).toString());
            BeanUtils.setProperty(bean, "toStationName",jsonArray.get(2).toString());
            BeanUtils.setProperty(bean, "startTime",jsonArray.get(3).toString());
            BeanUtils.setProperty(bean, "arriveTime",jsonArray.get(4).toString());
            BeanUtils.setProperty(bean, "lishi",jsonArray.get(5).toString());
            BeanUtils.setProperty(bean, "gjrw",jsonArray.get(6).toString());
            BeanUtils.setProperty(bean, "rw",jsonArray.get(7).toString());
            BeanUtils.setProperty(bean, "wz",jsonArray.get(8).toString());
            BeanUtils.setProperty(bean, "yw",jsonArray.get(9).toString());
            BeanUtils.setProperty(bean, "yz",jsonArray.get(10).toString());
            BeanUtils.setProperty(bean, "ed",jsonArray.get(11).toString());
            BeanUtils.setProperty(bean, "yd",jsonArray.get(12).toString());
            BeanUtils.setProperty(bean, "td",jsonArray.get(13).toString());
            BeanUtils.setProperty(bean, "trainNo",jsonArray.get(14).toString());
            BeanUtils.setProperty(bean, "fromStationNo",jsonArray.get(15).toString());
            BeanUtils.setProperty(bean, "toStationNo",jsonArray.get(16).toString());
            BeanUtils.setProperty(bean, "seatTypes",jsonArray.get(17).toString());
            BeanUtils.setProperty(bean, "trainDate",jsonArray.get(18).toString());

            beanList.add(bean);
        }
        return beanList;
    }
}

