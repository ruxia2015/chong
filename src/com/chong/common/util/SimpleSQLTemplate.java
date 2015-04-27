package com.chong.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.chong.bean.ResourceTypeBean;
import com.chong.common.annotation.SQLAnnotation;

public class SimpleSQLTemplate<T> {

	public String generatorAddSQL(String tableName, T t, Class clazz) {

		Map<String, Object> map = getObjectValue(t, clazz);

		StringBuffer sb = new StringBuffer();
		String columns = "";
		String values = "";
		for (String key : map.keySet()) {
			SQLAnnotation annotation = (SQLAnnotation)getFieldAnnotation(clazz,key,SQLAnnotation.class);
			if(annotation!=null && annotation.is_Ingore()){
				continue;
			}
			String val = (String) map.get(key);
			if (!StringTools.isEmptyOrNone(val)) {
				columns = columns + key + ",";
				values = values + "'" + val + "'" + ",";
			}
		}

		if (StringTools.isEmptyOrNone(columns)) {
			return null;
		} else {
			columns = columns.substring(0, columns.length() - 1);
			values = values.substring(0, values.length() - 1);
			sb.append("insert into " + tableName);
			sb.append(" (" + columns + " )");
			sb.append(" values(" + values + ")");
		}
		System.out.println("insert SQL ==> " + sb.toString());
		return sb.toString();
	}

	public String generatorDeleteSQL(String tableName, T t, Class clazz) {
		Map<String, Object> map = getObjectValue(t, clazz);
		StringBuffer sb = new StringBuffer();
		String where = "";
		for (String key : map.keySet()) {
			String val = (String) map.get(key);
			if (!StringTools.isEmptyOrNone(val)) {
				val = "'" + val + "'";
				where = where + " AND " + key + " = " + val;
			}
		}

		if (StringTools.isEmptyOrNone(where)) {
			return null;
		} else {
			sb.append("delete FROM  " + tableName);
			sb.append(" WHERE 1 = 1 " + where);
		}
		System.out.println("delete SQL ==> " + sb.toString());
		return sb.toString();

	}

	public String generatorQuerySQL(String tableName, T t, Class clazz) {
		Map<String, Object> map = getObjectValue(t, clazz);
		Map<String, Field> fMap = getFieldMap(clazz);

		StringBuffer sb = new StringBuffer();
		String where = "";
		String columns = "";
		for (String key : map.keySet()) {
			Field f = fMap.get(key);
			f.setAccessible(true);
			SQLAnnotation annotation = (SQLAnnotation) f
					.getAnnotation(SQLAnnotation.class);

			columns = columns + key + ",";
			String val = (String) map.get(key);
			if (!StringTools.isEmptyOrNone(val)) {
				where = where + getWhere(val, annotation, key);
			}
		}

		if (StringTools.isEmptyOrNone(columns)) {
			return null;
		} else {
			columns = columns.substring(0, columns.length() - 1);
			sb.append("select * from " + tableName);
			sb.append(" WHERE 1 = 1 " + where);
		}

		System.out.println("生成的select语句 ==> " + sb.toString());
		return sb.toString();
	}

	/**
	 * 生成更新语句 <功能详细描述>
	 * 
	 * @param tableName
	 * @param t
	 * @param clazz
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String generatorUpdateSQL(String tableName, T t, Class clazz) {
		Map<String, Object> map = getObjectValue(t, clazz);
		StringBuffer sb = new StringBuffer();
		String where = "";
		String updateColumns = "";
		for (String key : map.keySet()) {
			String val = (String) map.get(key);
			if (StringTools.isEmptyOrNone(val)) {
				continue;
			}

			SQLAnnotation annotation = (SQLAnnotation)getFieldAnnotation(clazz,key,SQLAnnotation.class);
			
			if(annotation==null){
				updateColumns = updateColumns + key + " ='" + val + "',";
			}else  if(annotation.is_Ingore()){
				continue;
			}
			else if (annotation.is_column() && !annotation.update_is_where()) {
				// 作为更新的字段
				updateColumns = updateColumns + key + " ='" + val + "',";
			} else {
				String col = key;
				where = where + getWhere(val, annotation, col);
			}

		}

		if (StringTools.isEmptyOrNone(where)
				|| StringTools.isEmptyOrNone(updateColumns)) {
			return null;
		} else {
			updateColumns = updateColumns.substring(0,
					updateColumns.length() - 1);
			sb.append("update " + tableName);
			sb.append(" set " + updateColumns);
			sb.append(" WHERE 1 = 1 " + where);
		}
		System.out.println("update SQL ==> " + sb.toString());
		return sb.toString();

	}

	private String getWhere(String val, SQLAnnotation annotation, String col) {
		if (annotation == null) {
			if (StringTools.isEmptyOrNone(val)) {
				return " AND " + col + "IS NULL ";
			} else {
				return " AND " + col + "= " + val;
			}
		}
		if (annotation.is_Ingore()) {
			return "";
		}
		if (!StringTools.isEmptyOrNone(annotation.where_column())) {
			col = annotation.where_column();
		}
		String tempVal = val;
		if (StringTools.isEmptyOrNone(val)) {
			return " AND " + col + "IS NULL ";
		} else if ("like".equalsIgnoreCase(annotation.where_oper())) {
			tempVal = "'%" + val + "%'";
		} else if ("in".equalsIgnoreCase(annotation.where_oper())) {
			String[] vals = val.split(",");
			String val2 = "";
			for (String str : vals) {
				val2 = val2 + ",'" + str + "'";
			}
			val2 = val2.replaceFirst(",", "");
			tempVal = " (" + val2 + ") ";
		} else {
			tempVal = "'" + tempVal + "'";
		}
		return " AND  " + col + " " + annotation.where_oper() + tempVal;
	}
	
	
	public Annotation getFieldAnnotation(Class cls,String fieldName, Class annotation){
		Field f;
		try {
			f = cls.getDeclaredField(fieldName);
			if(f!=null){
				
				f.setAccessible(true);
				return  f.getAnnotation(annotation);
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public Map<String, Field> getFieldMap(Class clazz) {
		Map<String, Field> value = new HashMap<String, Field>();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			value.put(field.getName(), field);
		}

		return value;
	}

	public Map<String, Object> getObjectValue(T t, Class clazz) {
		Map<String, Object> value = new HashMap<String, Object>();

		Field[] fields = clazz.getDeclaredFields();

		try {
			if (t == null) {
				t = (T) clazz.newInstance();
			}

			for (Field field : fields) {
				field.setAccessible(true);
				Object obj = field.get(t);

				if (obj != null) {
					value.put(field.getName(), obj.toString());

				} else {
					value.put(field.getName(), null);
				}

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;

	}
	
	
	

	public static void main(String[] args) {

		try {
			Class cl = ResourceTypeBean.class;
			Field f = cl.getDeclaredField("id");
			System.out.println(" " + f.getName());
			Annotation[] ans = f.getAnnotations();

			for (Annotation annotation : ans) {
				System.out.println("==" + annotation.annotationType());

			}

		} catch (Exception e) {

		}

	}

}
