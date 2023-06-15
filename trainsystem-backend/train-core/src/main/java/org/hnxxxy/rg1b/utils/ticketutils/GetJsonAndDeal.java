package org.hnxxxy.rg1b.utils.ticketutils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import org.hnxxxy.rg1b.domain.dto.Ticket;
import org.hnxxxy.rg1b.utils.BeanUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetJsonAndDeal {
    /*
     *分析返回的json得到以下信息
     * info.get(3);// 车次信息
     *  info.get(6); // 出发地代号
     *  info.get(7);// 目的地代号
     *  info.get(8);// 出发时间
     *  info.get(9); // 到达时间
     *  info.get(10);// 历时
     *  info.get(11); // Y
     * info.get(21); // 高级软卧
     * info.get(23); // 软卧
     * info.get(26); // 无座
     * info.get(28); // 硬卧
     * info.get(29); // 硬座
     * info.get(30); // 二等座
     * info.get(31);// 一等座
     *  info.get(32); // 商务，特等座
     */

    public static List<String> getTicketsInfo(String train_date, String from_station, String to_station, String purpose_codes) throws Exception {
        List<String> ticketsInfoList = new ArrayList<>();
        List<String> jsonLists = new ArrayList<>();//https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2023-03-05&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=SHH&purpose_codes=ADULT
        String urlStr = "https://kyfw.12306.cn/otn/leftTicket/query?"
                + "leftTicketDTO.train_date="
                + train_date
                + "&leftTicketDTO.from_station="
                + from_station
                + "&leftTicketDTO.to_station="
                + to_station
                + "&purpose_codes="
                + purpose_codes;
        String ticketsInfo_json = ReturnJson.returnJson(urlStr);  //调用class ReturnJson.returnJson(String);获取json
        JSONObject jsonObject = JSONObject.fromObject(ticketsInfo_json.trim());//将获取的json字符串转换为json对象
        JSONArray jsonArray_result = jsonObject.getJSONObject("data").getJSONArray("result");//取出result
        JSONObject jsonObject_map = jsonObject.getJSONObject("data").getJSONObject("map");//取map
        Pattern p = Pattern.compile("(^[^\\|]).*");//正则表达式，不匹配 分割符 `|`
        for (Object object : jsonArray_result) {
            Matcher m = p.matcher(object.toString());
            if (m.find()) {
                jsonLists.add(m.group(0));  //匹配后放到jsonList，每一条数据就是一组
            }
        }
        for (int i = 0; i < jsonLists.size(); i++) {     //遍历list
            String tempStr = jsonLists.get(i).toString();  //将每一条list转换为String
            String tempArr[] = tempStr.split("\\|");  //进行分割
            List<String> info = new ArrayList<>();
            for (String str : tempArr) {
                info.add(str);              //遍历，放入list
            }
            List<String> ticketsInfoListTemp = new ArrayList<>(); //临时的list，将info里的数据分别存到list中，组成一条我们需要的数据
            ticketsInfoListTemp.add(info.get(3));
            ticketsInfoListTemp.add((String) jsonObject_map.get(info.get(6)));
            ticketsInfoListTemp.add((String) jsonObject_map.get(info.get(7)));//得到车站名字
            ticketsInfoListTemp.add(info.get(8));
            ticketsInfoListTemp.add(info.get(9));
            ticketsInfoListTemp.add(info.get(10));
            ticketsInfoListTemp.add(info.get(21));
            ticketsInfoListTemp.add(info.get(23));
            ticketsInfoListTemp.add(info.get(26));
            ticketsInfoListTemp.add(info.get(28));
            ticketsInfoListTemp.add(info.get(29));
            ticketsInfoListTemp.add(info.get(30));
            ticketsInfoListTemp.add(info.get(31));
            ticketsInfoListTemp.add(info.get(32));
            ticketsInfoListTemp.add(info.get(2));
            ticketsInfoListTemp.add(info.get(16));
            ticketsInfoListTemp.add(info.get(17));
            ticketsInfoListTemp.add(info.get(35));
            ticketsInfoListTemp.add(info.get(13));

            ticketsInfoList.add(ticketsInfoListTemp.toString()); //最后放到list
        }
        return ticketsInfoList;
    }

    public static Map<String,String> getAllStation(){
        Map<String, String> map = new HashMap<>();
        //获取json写入字符串
        String urlStr = "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9030";
        String nameString = null;
        try {
            nameString = ReturnJson.returnJson(urlStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //第一次处理json字符串
        String[] nameArr = nameString.split("\\@");//获得json由@分割开
        Pattern p = Pattern.compile("[^\\@].*\\|.*");
        List<String> jsonLists = new ArrayList<String>();
        for (Object object : nameArr) {
            Matcher m = p.matcher(object.toString());
            if (m.find()) {
                jsonLists.add(m.group(0));
            }
        }
        //第二次处理json字符互串
        for (int i = 0; i < jsonLists.size(); i++) {
            String temp = jsonLists.get(i).toString();
            String jsonArr[] = temp.split("\\|");
            List<String> mapLists = new ArrayList<String>();
            for (Object object : jsonArr) {
                mapLists.add(object.toString());
            }
            map.put(mapLists.get(1), mapLists.get(2));
        }
        return map;
    }
    public static String getCodeByName(String stationName){

        //这里以后改成从数据库查询
        return getAllStation().get(stationName);
    }

    public static void getTicketPrice(Ticket ticket) throws Exception {

        String url = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no=" +
                //"5l00000G2410" +
                ticket.getTrainNo().trim() +
                "&from_station_no=" +
                //"01" +
                ticket.getFromStationNo().trim() +
                "&to_station_no=" +
                //"05" +
                ticket.getToStationNo().trim() +
                "&seat_types=" +
                //"9MO" +
                ticket.getSeatTypes().trim() +
                "&train_date=" +
                //"2023-03-06"
                new StringBuffer(ticket.getTrainDate().trim().replace("-","")).insert(4,"-").insert(7,"-");
        String result = ReturnJson.returnJson(url);

        JSONObject data = JSONObject.fromObject(result.trim()).getJSONObject("data");

        //对json进行处理并将票价封装到bean中
        if (!" ".equals(ticket.getGjrw())){//高级软卧
            if (data.get("A6") != null) {
                String a6 = (String) data.get("A6");
                ticket.setGjrwPrice(Double.parseDouble(a6.substring(1)));
            }
        }
        if (!" ".equals(ticket.getRw())){//软卧
            if (data.get("AI") != null){
                String ai = (String) data.get("AI");
                ticket.setRwPrice(Double.parseDouble(ai.substring(1)));
            }
            if (data.get("A4") != null) {
                String a4 = (String) data.get("A4");
                ticket.setRwPrice(Double.parseDouble(a4.substring(1)));
            }
        }
        if (!" ".equals(ticket.getWz())){//无座
            if (data.get("WZ") != null) {
                String wz = (String) data.get("WZ");
                ticket.setWzPrice(Double.parseDouble(wz.substring(1)));
            }
            if (data.get("O") != null) {
                String o = (String) data.get("O");
                ticket.setWzPrice(Double.parseDouble(o.substring(1)));
            }
        }
        if (!" ".equals(ticket.getYw())){//硬卧
            if (data.get("AJ") != null) {
                String aj = (String) data.get("AJ");
                ticket.setYwPrice(Double.parseDouble(aj.substring(1)));
            }
            if (data.get("A3") != null) {
                String a3 = (String) data.get("A3");
                ticket.setYwPrice(Double.parseDouble(a3.substring(1)));
            }
        }
        if (!" ".equals(ticket.getYz())){//硬座
            if (data.get("A1") != null) {
                String a1 = (String) data.get("A1");
                ticket.setYzPrice(Double.parseDouble(a1.substring(1)));
            }
            if (data.get("O") != null) {
                String o = (String) data.get("O");
                ticket.setYzPrice(Double.parseDouble(o.substring(1)));
            }
        }
        if (!" ".equals(ticket.getEd())){//二等座
            if (data.get("WZ") != null) {
                String wz = (String) data.get("WZ");
                ticket.setEdPrice(Double.parseDouble(wz.substring(1)));
            }
            if (data.get("O") != null) {
                String o = (String) data.get("O");
                ticket.setEdPrice(Double.parseDouble(o.substring(1)));
            }
        }
        if (!" ".equals(ticket.getYd())){//一等座
            if (data.get("M") != null) {
                String m = (String) data.get("M");
                ticket.setYdPrice(Double.parseDouble(m.substring(1)));
            }
        }
        if (!" ".equals(ticket.getTd())){//商务座，特等座
            if (data.get("A9") != null) {
                String a9 = (String) data.get("A9");
                ticket.setTdPrice(Double.parseDouble(a9.substring(1)));
            }
        }
    }

    //获取指定时间的全部列车信息
    public static List<Ticket> getDayAllTickets(String firstStr,String date) throws Exception {
        List<Ticket> list=new ArrayList<>();
        String url="https://search.12306.cn/search/v1/train/search?"+
                "keyword="+firstStr+ //firstStr表示车次的第一个字符 第一个字符有G-高铁/城际/复兴号，D-动车，Z-直达，T-特快，K-快速,其他(1-8)
                "&date="+date;//表示日期
        String result = ReturnJson.returnJsonNoSetCookie(url);
        JSONObject data = JSONObject.fromObject(result.trim());
        JSONArray jsonArrayResult = data.getJSONArray("data");//取出data

        for(Object obj:jsonArrayResult){
            AllTicket getAllTicket= JSON.parseObject(obj.toString(),AllTicket.class, JSONReader.Feature.SupportSmartMatch);
            Ticket ticket = new Ticket();
            ticket.setTrainDate(getAllTicket.getDate());
            ticket.setFromStationName(getAllTicket.getFromStation());
            ticket.setToStationName(getAllTicket.getToStation());
            ticket.setStationTrainCode(getAllTicket.getStationTrainCode());
            ticket.setTotalNum(Integer.parseInt(getAllTicket.getTotalNum()));
            ticket.setTrainNo(getAllTicket.getTrainNo());

            list.add(ticket);
        }
        return list;
    }

    public static List<Ticket> getTrainInfo(Ticket trainTrips) throws Exception {
        List<Ticket> list = new ArrayList<>();
        StringBuffer departDate = new StringBuffer(trainTrips.getTrainDate().trim().replace("-",""));
        departDate = departDate.insert(4,"-").insert(7,"-");
        String url = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?" +
                "train_no=" +
                trainTrips.getTrainNo() +
                "&from_station_telecode=" +
                trainTrips.getFromStationNo() +
                "&to_station_telecode=" +
                trainTrips.getToStationNo() +
                "&depart_date=" +
                departDate;
        String result = ReturnJson.returnJsonNoSetCookie(url);
        JSONObject data = JSONObject.fromObject(result.trim());
        JSONArray jsonArrayResult = data.getJSONObject("data").getJSONArray("data");//取出data

        for (Object obj:jsonArrayResult){
            JSONObject jsonObject = (JSONObject) obj;
            Ticket ticket = JSON.parseObject(obj.toString(), Ticket.class, JSONReader.Feature.SupportSmartMatch);
            Ticket resultTicket = new Ticket();
            BeanUtils.copyProperties(trainTrips,resultTicket);
            BeanUtils.copyProperties(ticket,resultTicket);
            resultTicket.setStationNo(jsonObject.getInt("station_no"));
            list.add(resultTicket);
        }
        return list;
    }


}


