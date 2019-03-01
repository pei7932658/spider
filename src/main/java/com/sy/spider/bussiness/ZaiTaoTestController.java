package com.sy.spider.bussiness;

import com.sy.spider.utils.Base64ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.MalformedURLException;
import java.util.*;

 /**
  * @Author peiliang
  * @Description : 模拟在逃库请求
  * @Date 2018/11/20 18:19
  * @Param
  * @Return
  */
@RestController
@RequestMapping("/carwl/test")
@Slf4j
public class ZaiTaoTestController {

    @Value("${dzh.simulate.photoPath}")
    private String simulatePhotoPath;

    //所有图片的集合
    private static List<File> imageList = new ArrayList<>();

    @PostConstruct
    private void init(){
        if(imageList.isEmpty()){
            File photoDir = new File(simulatePhotoPath);
            imageList = Arrays.asList(photoDir.listFiles());
        }
    }

    @PostMapping("/xzztryQueryResult")
    public Map xzztryQueryResult(String json,int page,int rows){

        log.info("test page:"+page);
        log.info("test rows:"+rows);
        Map<String,Object> resObj = new HashMap<>();
        List<Map<String,String>> rwosList = new ArrayList<>();
        resObj.put("total",imageList.size());
        int startVal = (page-1)*rows;
        for(int i=startVal;i<imageList.size()&&i<startVal+rows;i++){
            Map<String,String> map = new HashMap<String,String>();
            //将图片index作为编号
            map.put("ZTRYBH", Integer.toString(i));
            map.put("FILEPATH",imageList.get(i).getPath());
            rwosList.add(map);
        }

        resObj.put("rows",rwosList);

        return resObj;
    }

    @RequestMapping(value = "/ztryxxzt",method = RequestMethod.GET)
    public ModelAndView ztryxxzt(String ztrybh) throws MalformedURLException {
        ModelAndView mv=new ModelAndView();
        File photoFile = imageList.get(Integer.valueOf(ztrybh));
        mv.addObject("imageShow","data:image/png;base64,"+ Base64ImageUtil.getImageStr(photoFile));
        mv.addObject("imageSrc", photoFile.getPath());
        mv.addObject("name","&nbsp;"+photoFile.getName().substring(0,photoFile.getName() .lastIndexOf(".")));
        mv.addObject("sfzh","&nbsp;居民身份证：420602199505231023");
        mv.addObject("ztbh","&nbsp;"+ztrybh);
        mv.setViewName("carwltest");
        return mv;
    }

}
