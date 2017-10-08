import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * created by ths 2017/9/27
 * description:
 **/
public class StringToArray {

    public static String[] stringToArray(String des){

        if(null == des || des.length() == 0){
            return null;
        }else{
            if(des.contains(",")){
                return des.split(",");
            }else{
                String[] array = new String[0];
                array[0] = des;
                return array;
            }
        }
    }

    public static List<Map<String,String>> objectsToMap(String[] keys,List<Object[]> objects){

        List<Map<String,String>> resultList = new ArrayList<Map<String, String>>();

//        if(null != keys && keys.length > 0 && objects != null && objects.size()>0)
        // 需要进行长度验证以及是否为空判断

        for(Object[] o : objects){
            Map<String,String> map = new LinkedHashMap<String, String>();
            for(int index =0; index < keys.length; index++){
                map.put(keys[index],o[index] == null ?"" : String.valueOf(o[index]) );
            }
            resultList.add(map);
        }
        return resultList;
    }
}
