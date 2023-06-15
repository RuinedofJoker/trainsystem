package org.hnxxxy.rg1b.service;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hnxxxy.rg1b.TrainApplication;
import org.hnxxxy.rg1b.domain.dto.IntelCity;
import org.hnxxxy.rg1b.domain.RoutePath;
import org.hnxxxy.rg1b.domain.vo.OptimalRouteVo;
import org.hnxxxy.rg1b.domain.vo.TrainIntelVo;
import org.hnxxxy.rg1b.exception.IllegalTrainIntelException;
import org.hnxxxy.rg1b.mapper.TrainCityMapper;
import org.hnxxxy.rg1b.service.train.ITrainIntelService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TrainApplication.class})
public class TrainIntelServiceTest {

    @Autowired
    ITrainIntelService trainIntelService;
    @Autowired
    TrainCityMapper trainCityMapper;

    //@Test
    public void generateOptimalRouteTest(){
        SimpleDateFormat timeFormat = new  SimpleDateFormat("HH:mm");
        TrainIntelVo trainIntelVo = new TrainIntelVo();
        trainIntelVo.setFromCity(new IntelCity(trainCityMapper.selectNoByName("上海").getCityNo(),"20230328"));
        trainIntelVo.setToCity(new IntelCity(trainCityMapper.selectNoByName("北京").getCityNo(),"20230329"));
        List<IntelCity> list = new ArrayList<>();
        list.add(new IntelCity(trainCityMapper.selectNoByName("武汉").getCityNo(),"20230328"));
        //trainIntelVo.setThroughCity(list);
        List<OptimalRouteVo> optimalRouteVos;
        try {
            optimalRouteVos = trainIntelService.generateOptimalRoute(trainIntelVo, 111L);
        } catch (IllegalTrainIntelException e) {
            throw new RuntimeException(e);
        }

        try {
            XSSFWorkbook sheets = new XSSFWorkbook();

            for (OptimalRouteVo curOptimalRouteVo : optimalRouteVos){
                XSSFSheet sheet = sheets.createSheet(curOptimalRouteVo.getDescription());
                XSSFRow firstRow = sheet.createRow(0);
                firstRow.createCell(0).setCellValue("trainDate");
                firstRow.createCell(1).setCellValue("fromCityName");
                firstRow.createCell(2).setCellValue("fromTime");
                firstRow.createCell(3).setCellValue("toCityName");
                firstRow.createCell(4).setCellValue("toTime");
                firstRow.createCell(5).setCellValue("orderNum");
                firstRow.createCell(6).setCellValue("stationTrainId");
                firstRow.createCell(7).setCellValue("pathId");
                firstRow.createCell(8).setCellValue("routeId");
                for (int i = 0; i < curOptimalRouteVo.getRoutePaths().size(); i++){
                    XSSFRow row = sheet.createRow(i+1);
                    RoutePath currRoutePath = curOptimalRouteVo.getRoutePaths().get(i);
                    row.createCell(0).setCellValue(currRoutePath.getTrainDate());
                    row.createCell(1).setCellValue(currRoutePath.getFromCityName());
                    row.createCell(2).setCellValue(timeFormat.format(currRoutePath.getFromTime()));
                    row.createCell(3).setCellValue(currRoutePath.getToCityName());
                    row.createCell(4).setCellValue(timeFormat.format(currRoutePath.getToTime()));
                    row.createCell(5).setCellValue(currRoutePath.getOrderNum());
                    row.createCell(6).setCellValue(currRoutePath.getStationTrainId());
                    row.createCell(7).setCellValue(currRoutePath.getPathId());
                    row.createCell(8).setCellValue(currRoutePath.getRouteId());
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\61640\\Desktop\\OptimalRoute.xlsx");
            sheets.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
