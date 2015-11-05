package com.buswe.dhtcrawler.db.mongodb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buswe.dhtcrawler.db.DbUtil;
import com.buswe.dhtcrawler.db.models.DhtInfoStateCode;
import com.buswe.dhtcrawler.db.models.DhtInfo_MongoDbPojo;
import com.buswe.dhtcrawler.db.models.PeerInfo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @author 耳东 (cgp@0731life.com)
 */
public class MongodbUtil {
	
	  protected Logger logger = LoggerFactory.getLogger(getClass());
	protected final DB db;

	public MongodbUtil(DB db) {
		this.db = db;

		// db.c
		// db.test.ensureIndex({"userid":1},{"unique":true})
	}

	/**
	 * 保存对象到db
	 * 
	 * @param object
	 */
	public void save(Object object) throws Exception {
		DBCollection collection = getDBCollection(object.getClass());
		DBObject dbobject = objectToDBObject(object);
		collection.insert(dbobject);
	}

	/**
	 * 获取DBCollection
	 * 
	 * @param clazz
	 * @return
	 */
	public DBCollection getDBCollection(Class<?> clazz) {
		DBCollection collection = db.getCollection(getDBCollectionName(clazz));
		return collection;
	}

	public static String getDBCollectionName(Class<?> clazz) {
		MongoCollection collectionAnno = clazz.getAnnotation(MongoCollection.class);
		String collectionName;
		if (collectionAnno != null && collectionAnno.value().trim().length() != 0) {
			collectionName = collectionAnno.value();
		} else {
			collectionName = clazz.getName().replace(".", "_");// mongodb不支持'.'的名字
		}
		return collectionName;
	}

	/**
	 * 根据fiele获取字段名称
	 * 
	 * @param f
	 * @return
	 */
	private String getAnnotationFieldName(Field f) {
		MongoField fieldAnno = f.getAnnotation(MongoField.class);
		if (fieldAnno == null || fieldAnno.value().trim().length() == 0) {
			return f.getName();
		}
		return fieldAnno.value().trim();
	}

	/**
	 * 获取扄1�7有的字段
	 * 
	 * @param clazz
	 * @return
	 */
	private List<Field> getAllFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		list.addAll(Arrays.asList(clazz.getDeclaredFields()));
		// System.out.println(clazz);
		return list;
	}

	/**
	 * 将�1�7�转成db支持的类垄1�7
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object valueSerial(Object object) throws Exception {
		if (object.getClass().getAnnotation(MongoCollection.class) != null) {
			DBObject dbObject = objectToDBObject(object);
			return dbObject;
		}
		if (checkValidType(object.getClass())) {
			return object;
		}
		if (object instanceof List) {
			List<Object> list = (List<Object>) object;
			BasicDBList basicDBList = new BasicDBList();
			for (Object object2 : list) {
				Object dbobject = valueSerial(object2);
				basicDBList.add(dbobject);
			}
			return basicDBList;
		}
		return null;
	}

	/**
	 * 将对象转成DbObject
	 * 
	 * @param object
	 * @return
	 */
	public DBObject objectToDBObject(Object object) throws Exception {
		Class<? extends Object> clazz = object.getClass();
		List<Field> fields = getAllFields(clazz);
		DBObject dbobject = new BasicDBObject();
		for (Field f : fields) {
			Method get = DbUtil.getFieldGetMethod(clazz, f);
			Object fieldobj = get.invoke(object);
			if (fieldobj == null) {
				continue;
			}
			fieldobj = valueSerial(fieldobj);
			if (fieldobj == null) {
				continue;
			}
			String name = getAnnotationFieldName(f);
			dbobject.put(name, fieldobj);
		}
		return dbobject;
	}

	private boolean checkValidType(Class<?> clazz) {
		if (clazz.equals(Integer.class) || clazz.equals(Double.class) || //
				clazz.equals(Float.class) || clazz.equals(Boolean.class) || //
				clazz.equals(String.class) || clazz.equals(Date.class) || //
				clazz.equals(Long.class)) {
			return true;
		}
		return false;
	}

	// db.classes.update({"count":{$gt:20}},{$set:{"name":"c3"}})
	// $set 注意 DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
	// 加上set 至修改添加的部分，其他数据不变，
	/**
	 * 跟新
	 * 
	 * @param clazz
	 * @param q
	 *            跟新条件
	 * @param v
	 *            设置新�1�7�1�7
	 * @param isOverWrite
	 *            ture 覆盖全部值， false 值修改设定的部分，之前的不变
	 */
	public void update(Class<?> clazz, DBObject q, DBObject v, boolean isOverWrite) {
		DBCollection collection = getDBCollection(clazz);
		if (isOverWrite) {
			collection.update(q, v);
		} else {
			DBObject updateSetValue = new BasicDBObject("$set", v);
			collection.update(q, updateSetValue);
		}
	}

	public void update(DhtInfo_MongoDbPojo dhtInfo) throws Exception {
		DBObject q = new BasicDBObject("info_hash", dhtInfo.getInfo_hash());
		DBObject v = objectToDBObject(dhtInfo);
		update(dhtInfo.getClass(), q, v, false);
	}

	// ----- load -----
	public <T> List<T> loadAll(Class<T> clazz) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		DBCollection collection = getDBCollection(clazz);
		List<T> objList = new ArrayList<T>();
		DBCursor curr = collection.find();
		while (curr.hasNext()) {
			// new object of the clazz
			// DBObject dbo = curr.next();
			// ObjectId id = (ObjectId) dbo.get("_id");
			T o = null;

			// if (depickled.containsKey(id)) {
			// o = (T) depickled.get(id);
			// } else {
			// o = (T) loadOne(clazz, dbo);
			// }
			objList.add(o);
		}
		return objList;
	}

	public <T> List<T> findAll(Class<T> clazz) throws Exception {

		return findByLimit(clazz, 0);
	}

	public <T> List<T> findByLimit(Class<T> clazz, int limit) throws Exception {
		return find(clazz, new BasicDBObject(), limit);
	}

	public <T> List<T> find(Class<T> clazz, BasicDBObject where, int limit) throws Exception {
		List<T> objList = new ArrayList<T>();
		DBCursor curr = findDBCursor(clazz, where, limit);
		while (curr.hasNext()) {
			DBObject dbo = curr.next();
			T o = loadOne(clazz, dbo);
			objList.add(o);
		}

		System.out.println(objList.size());
		return objList;
	}

	public <T> DBCursor findDBCursor(Class<T> clazz, BasicDBObject where, int limit) {
		DBCollection collection = getDBCollection(clazz);
		DBCursor curr = collection.find(where).limit(limit);
		// collection.u
		return curr;
	}


	/**
	 * 查找并更新数据
	 * 
	 * @param clazz
	 * @param where
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findAndUpdate(Class<T> clazz, BasicDBObject where, int limit) throws Exception {
		List<T> objList = new ArrayList<T>();
		DBCursor curr = findDBCursor(clazz, where, limit);

		System.out.println(curr.count() + "：项");
		BasicDBList whereList = new BasicDBList();
		while (curr.hasNext()) {
			DBObject dbo = curr.next();
			T o = loadOne(clazz, dbo);
			objList.add(o);
			whereList.add(dbo);
		}
		BasicDBObject update = new BasicDBObject();
		updateOr(clazz, whereList, update);
		whereList.clear();
		whereList = null;
		System.out.println(objList.size());
		return objList;
	}

	/**
	 * 更新 or 操作
	 * 
	 * @param clazz
	 *            要操作的类
	 * @param whereList
	 *            条件 集合
	 * @param update
	 *            要改成什么值
	 */
	private <T> void updateOr(Class<T> clazz, BasicDBList whereList, BasicDBObject update) {
		DBCollection collection = getDBCollection(clazz);
		BasicDBObject queryCondition = new BasicDBObject();
		queryCondition.put("$or", whereList);
		DBObject updateSetValue = new BasicDBObject("$set", update);
		collection.update(queryCondition, updateSetValue, false, true);
	}

	public <T> DBCursor findDBCursor(Class<T> clazz) {
		return findDBCursor(clazz, new BasicDBObject(), 0);
	}

	public <T> T loadOne(Class<T> clazz, DBObject dbo) throws Exception {
		T o = clazz.newInstance();
		List<Field> fields = getAllFields(o.getClass());
		for (Field f : fields) {
			String name = getAnnotationFieldName(f);// key
			Object value = dbo.get(name);//
			if (value != null) {
				Type type = f.getGenericType();
				value = valueDeserial(type, value);// 解析得到真实倄1�7
			}
			Method set = DbUtil.getFieldSetMethod(clazz, f);
			set.invoke(o, value);
		}
		return o;
	}

	/**
	 * 将�1�7�翻译成为对豄1�7
	 * 
	 * @param type
	 *            对象的类垄1�7
	 * @param object
	 *            mongodb的对豄1�7
	 * 
	 * @return 返回转换后的对象
	 * @throws Exception
	 */
	private Object valueDeserial(Type type, Object object) throws Exception {
		Class<?> clazz = object.getClass();

		if (clazz == BasicDBList.class) {
			BasicDBList basicDBList = (BasicDBList) object;
			ArrayList<Object> arrayList = new ArrayList<Object>();
			Type type1 = ((ParameterizedType) type).getActualTypeArguments()[0];
			for (Object objectInList : basicDBList) {
				Class<?> objectInListTypeOfClass = Class.forName(type1.toString().replace("class ", "").trim());
				arrayList.add(valueDeserial(objectInListTypeOfClass, objectInList));
			}
			return arrayList;
		}
		if (checkValidType(clazz)) {// 如果是基本类型，直接返回
			return object;
		}
		if (clazz == BasicDBObject.class) {
			DBObject basicDBObject = (BasicDBObject) object;
			// System.out.println(basicDBObject);
			Class<?> clazzq = Class.forName(type.toString().replace("class ", "").trim());
			return loadOne(clazzq, basicDBObject);
		}
		return object;
	}

	// ----------------------------------
	public List<DhtInfo_MongoDbPojo> getNoAnalyticDhtInfos(int limit) throws Exception {
		BasicDBObject where = new BasicDBObject("analysised", DhtInfoStateCode.NO_DOWNLOAD);

		return find(DhtInfo_MongoDbPojo.class, where, limit);
	}

	public List<DhtInfo_MongoDbPojo> getHaveAnalyticedDhtInfos() throws Exception {
		BasicDBObject where = new BasicDBObject("analysised", DhtInfoStateCode.DOWNLOADSUCCESS_AND_PARSING_SUCCESS);

		return find(DhtInfo_MongoDbPojo.class, where, 0);
	}

	public DBCursor getHaveAnalyticedDhtInfosOfDBCursor() throws Exception {
		BasicDBObject where = new BasicDBObject("analysised", DhtInfoStateCode.DOWNLOADSUCCESS_AND_PARSING_SUCCESS);

		return findDBCursor(DhtInfo_MongoDbPojo.class, where, 0);
	}

	// -----------------------------------------
	public List<PeerInfo> getPeerInfos(BasicDBObject where, int limit) {
		List<PeerInfo> PeerInfos = null;
		try {
			PeerInfos = findAndUpdate(PeerInfo.class, where, limit);
			return PeerInfos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
