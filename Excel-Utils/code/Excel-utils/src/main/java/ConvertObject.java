import org.apache.commons.beanutils.BeanUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * created by ths 2017/8/25
 * description:
 **/
public class ConvertObject {

        public static void main(String[]args)throws IntrospectionException, IllegalAccessException,
    InstantiationException, InvocationTargetException {
            Map map=new HashMap<String,Object>();
            map.put("id",1);
            map.put("name","jack");
            map.put("sex","female");
            map.put("age",18);
            map.put("address","安徽省");
            User user = new User();
            BeanUtils.populate(user,map);
            System.out.println(user);
        }

        //使用泛型
        public static final <T> T injectBean(Class<T> beanClass,Map parasMap) {
            T bean = null;
            try {
                //通过反射生成对象
                bean = beanClass.newInstance();
                //还可以用Class.forName生成对象
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //获取类的方法
            Method[] methods = beanClass.getMethods();
            int len = methods.length;
            for(int i = 0; i < len; ++i) {
                Method method = methods[i];
                String methodName = method.getName();
                //如果方法名是set开头的且名字长度大于3的
                if(methodName.startsWith("set") && methodName.length() > 3) {
                    //获取方法的参数类型
                    Class[] types = method.getParameterTypes();
                    //只有一个参数的方法才继续执行
                    if(types.length == 1) {
                        //取字段名且让其首字母小写
                        String attrName = firstCharToLowerCase(methodName.substring(3));
                        //map中是否有属性名
                        if(parasMap.containsKey(attrName)) {
                            Object value = parasMap.get(attrName);
                            try {
                                //通过反射的方式执行bean的mothod方法，在这里相当于执行set方法赋值
                                method.invoke(bean, new Object[]{value});
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            return bean;
        }

        //取字段名且让其首字母小写
        public static String firstCharToLowerCase(String substring) {
            if (substring!=null&& substring.charAt(0)>='A' && substring.charAt(0)<='Z'){
                char[] arr = substring.toCharArray();
                arr[0] = (char)(arr[0] + 32);
                return new String(arr);
            }else {
                return substring;
            }
        }
}
