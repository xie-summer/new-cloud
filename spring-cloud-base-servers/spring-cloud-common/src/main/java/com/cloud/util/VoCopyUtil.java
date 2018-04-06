package com.cloud.util;

import com.cloud.api.vo.BaseVo;
import com.cloud.api.vo.ResultCode;
import com.cloud.api.vo.VoMap;
import com.cloud.dubbo.bytecode.Wrapper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.lang.ArrayUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * @author summer
 */
public class VoCopyUtil {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(VoCopyUtil.class);
	public static Map<String/*srcClass+dstClass*/, List<String>> copyPropsMap = new FastHashMap();
	public static <K, V> VoMap<K, V> toVoMap(Map<K, V> map){
		VoMap result = new VoMap(map.size());
		result.putAll(map);
		return result;
	}
	public static <K, V> List<VoMap<K, V>> toVoMapList(List<Map<K, V>> mapList){
		List<VoMap<K, V>> result = new ArrayList<>(mapList.size());
		for(Map<K, V> map: mapList){
			result.add(toVoMap(map));
		}
		return result;
	}

	public static <S extends BaseVo, T> ResultCode<List<S>> copyListProperties(Class<S> clazz, List<T> itemList) {
		List<S> voItemList = new ArrayList<S>();
		if (CollectionUtils.isEmpty(itemList)){
			return ResultCode.getSuccessReturn(voItemList);
		}
		try {
			for (T item : itemList) {
				if(item==null) {
                    continue;
                }
				Class src = item.getClass();
				Wrapper srcWrapper = Wrapper.getWrapper(src);
				Wrapper destWrapper = Wrapper.getWrapper(clazz);
				S itemVo = (S) destWrapper.gainNewInstance();
				List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), src, clazz);
				copyInternal(itemVo, item, srcWrapper, destWrapper, props);
				voItemList.add(itemVo);
			}
		}catch(Exception e){
			DB_LOGGER.warn(e, 10);
		}
		return ResultCode.getSuccessReturn(voItemList);
	}

	public static <S extends BaseVo, T> ResultCode<S> copyProperties(Class<S> clazz, T item) {
		if (item == null) {
            return ResultCode.getFailure(ResultCode.CODE_DATA_ERROR, "数据不存在！");
        }
		try {
			Wrapper srcWrapper = Wrapper.getWrapper(item.getClass());
			Wrapper destWrapper = Wrapper.getWrapper(clazz);
			S itemVo = (S) destWrapper.gainNewInstance();
			List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), item.getClass(), clazz);
			copyInternal(itemVo, item, srcWrapper, destWrapper, props);
			return ResultCode.getSuccessReturn(itemVo);
		} catch (Exception e) {
			DB_LOGGER.warn(e, 10);
			return ResultCode.getFailure(ResultCode.CODE_UNKNOWN_ERROR, "未知错误！");
		}
	}
	private static List<String> getJoinProperties(String[] srcProps, Class src, Class dest){
		String key = src.getCanonicalName() + "2" + dest.getCanonicalName();
		List<String> propList = copyPropsMap.get(key);
		if(propList == null){
			propList = new ArrayList<String>();
			synchronized(src){
				for (String name: srcProps) {
					try{
						PropertyDescriptor descriptor = new PropertyDescriptor(name, dest);
						if(PropertyUtils.getWriteMethod(descriptor)!=null){
							propList.add(name);
						}
					} catch (Exception e) {
						//ignore e.printStackTrace();
					}
				}
				copyPropsMap.put(key, UnmodifiableList.decorate(propList)/*不允许变更*/);
			}
		}
		return propList;
	}
	/**
	 * 两个Bean之前简单属性copy
	 * 限制：1）dst,src不能是Map，只能是POJO  2）无法级联
	 * @param dst
	 * @param src
	 */
	public static void copy(Object dst, Object src)  {
		Wrapper srcWrapper = Wrapper.getWrapper(src.getClass());
		Wrapper destWrapper = Wrapper.getWrapper(dst.getClass());
		List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), src.getClass(), dst.getClass());
		copyInternal(dst, src, srcWrapper, destWrapper, props);

	}

	public static void copy(Object dst, Object src, boolean allow, String... fields){
		if(ArrayUtils.isEmpty(fields)){
			copy(dst, src);
		}else{
			Wrapper srcWrapper = Wrapper.getWrapper(src.getClass());
			Wrapper destWrapper = Wrapper.getWrapper(dst.getClass());
			List<String> copyProps = Arrays.asList(fields);
			if(!allow){
				List<String> props = getJoinProperties(srcWrapper.getReadPropertyNames(), src.getClass(), dst.getClass());
				List<String> remove = copyProps;

				copyProps = new ArrayList<String>(props);
				copyProps.removeAll(remove);
			}
			copyInternal(dst, src, srcWrapper, destWrapper, copyProps);
		}
	}

	private static void copyInternal(Object dst, Object src, Wrapper srcWrapper, Wrapper destWrapper, List<String> props) {
		for(String name: props){
			try{
				destWrapper.setPropertyValue(dst, name, srcWrapper.getPropertyValue(src, name));
			}catch(Throwable e){
				DB_LOGGER.warn(name, e, 10);
			}
		}
	}

	/**
	 * 目前只支持简单对象copy</br>
	 * 参数beanmap，key是对象的属性，value是属性对应的值
	 * @param dst
	 * @param beanmap
	 * @author leo
	 * @addTime 2016年4月27日下午6:35:58
	 */
	public static void copy(Object dst, Map<String, Object> beanmap)  {
		Wrapper destWrapper = Wrapper.getWrapper(dst.getClass());
		copyInternal(dst, destWrapper, beanmap);
	}

	private static void copyInternal(Object dst, Wrapper destWrapper, Map<String, Object> beanmap) {
		for(Map.Entry<String, Object> entry : beanmap.entrySet()){
			try{
				destWrapper.setPropertyValue(dst, entry.getKey(), entry.getValue());
			}catch(Throwable e){
				DB_LOGGER.warn(entry.getKey(), e, 10);
			}
		}
	}
}
