import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * created by ths 2017/9/27
 * description:
 **/
public class Dom4jTest {



    @Test
    public void TestConvert(){
        // 模拟从数据库中查询出来的数据
        List<Object[]> list = new ArrayList<Object[]>();
        for(int i = 0;i<100;i++){
            Object[] objects = new Object[3];
            objects[0] = "0"+i* new Random().nextInt(100);
            objects[1] = "1";
            objects[2] = i;
            list.add(objects);
        }
        // 拿到xml 文件中配置的key
        String resultStr = readFromXml("d:/test.xml");
        String[] arrays = StringToArray.stringToArray(resultStr);

        // 将数据转换成map 返回
        List<Map<String,String>> maps = StringToArray.objectsToMap(arrays,list);
        for(Map<String,String> map : maps){
            for(Map.Entry<String,String> m : map.entrySet()){
                System.out.println("key="+m.getKey()+"---------------- value="+m.getValue());
            }
        }
    }

    @Test
    public void Test(){
        try{
            readFromXml("d:/test.xml");
        }catch (Exception e){
            e.getMessage();
        }
    }

    public static String readFromXml(String filePath){
        try {
            SAXReader saxReader = new SAXReader();

            // 获得文件
            Document document = saxReader.read(filePath);
            // 获得根节点
            Element rootElement = document.getRootElement();
            // 获得子节点
            Element childEle = rootElement.element("child");
            return childEle.getText();

//            String childStr = childEle.getText();
//            String[] arrays = StringToArray.stringToArray(childStr);
//            for(String s : arrays){
//                System.out.println(s);
//            }
//            System.out.println(arrays.length);
//            System.out.println(childStr);
//            return childStr;

        }catch (DocumentException e){
            e.printStackTrace();
            return null;
        }
    }

}
