package com.princeli.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

public class MongoDB4CRUDTest {

	private Mongo mg = null;
	private DB db;
	private DBCollection customers;

	@Before
	public void init() {
		try {
			mg = new Mongo();
			// mg = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		// 获取temp DB；如果默认没有创建，mongodb会自动创建
		db = mg.getDB("my");
		// 获取customers DBCollection；如果默认没有创建，mongodb会自动创建
		customers = db.getCollection("customer");
	}

	@After
	public void destory() {
		if (mg != null)
			mg.close();
		mg = null;
		db = null;
		customers = null;
		System.gc();
	}

	public void print(Object o) {
		System.out.println(o);
	}

	/**
	 * <b>function:</b> 查询所有数据
	 * 
	 * @author hoojo
	 * @createDate 2011-6-2 下午03:22:40
	 */
	private void queryAll() {
		print("查询customers的所有数据：");
		// db游标
		DBCursor cur = customers.find();
		while (cur.hasNext()) {
			print(cur.next());
		}
	}

	@Test
	public void add() {
		// 先查询所有数据
		queryAll();
		print("count: " + customers.count());

		DBObject customer = new BasicDBObject();
		customer.put("name", "hoojo");
		customer.put("age", 24);
		// customers.save(customer)保存，getN()获取影响行数
		// print(customers.save(customer).getN());

		// 扩展字段，随意添加字段，不影响现有数据
		customer.put("sex", "男");
		print(customers.save(customer).getN());

		// 添加多条数据，传递Array对象
		print(customers.insert(customer, new BasicDBObject("name", "tom")).getN());

		// 添加List集合
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(customer);
		DBObject customer2 = new BasicDBObject("name", "lucy");
		customer.put("age", 22);
		list.add(customer2);
		// 添加List集合
		print(customers.insert(list).getN());

		// 查询下数据，看看是否添加成功
		print("count: " + customers.count());
		queryAll();
	}

	@Test
	public void remove() {
		queryAll();
		print("删除id = 4de73f7acd812d61b4626a77："
				+ customers.remove(
						new BasicDBObject("_id", new ObjectId(
								"4de73f7acd812d61b4626a77"))).getN());
		print("remove age >= 24: "
				+ customers.remove(
						new BasicDBObject("age", new BasicDBObject("$gte", 24)))
						.getN());
	}

	@Test
	public void modify() {
		print("修改："
				+ customers.update(
						new BasicDBObject("_id", new ObjectId(
								"4dde25d06be7c53ffbd70906")),
						new BasicDBObject("age", 99)).getN());
		print("修改："
				+ customers.update(
						new BasicDBObject("_id", new ObjectId(
								"4dde2b06feb038463ff09042")),
						new BasicDBObject("age", 121), true,// 如果数据库不存在，是否添加
						false// 多条修改
				).getN());
		print("修改："
				+ customers.update(new BasicDBObject("name", "haha"),
						new BasicDBObject("name", "dingding"), true,// 如果数据库不存在，是否添加
						true// false只修改第一天，true如果有多条就不修改
				).getN());

		// 当数据库不存在就不修改、不添加数据，当多条数据就不修改
		// print("修改多条：" + coll.updateMulti(new BasicDBObject("_id", new
		// ObjectId("4dde23616be7c19df07db42c")), new BasicDBObject("name",
		// "199")));
	}

	@Test
	public void query() {
		// 查询所有
		// queryAll();

		// 查询id = 4de73f7acd812d61b4626a77
		print("find id = 4de73f7acd812d61b4626a77: "
				+ customers.find(
						new BasicDBObject("_id", new ObjectId(
								"4de73f7acd812d61b4626a77"))).toArray());

		// 查询age = 24
		print("find age = 24: "
				+ customers.find(new BasicDBObject("age", 24)).toArray());

		// 查询age >= 24
		print("find age >= 24: "
				+ customers.find(
						new BasicDBObject("age", new BasicDBObject("$gte", 24)))
						.toArray());
		print("find age <= 24: "
				+ customers.find(
						new BasicDBObject("age", new BasicDBObject("$lte", 24)))
						.toArray());

		print("查询age!=25："
				+ customers.find(
						new BasicDBObject("age", new BasicDBObject("$ne", 25)))
						.toArray());
		print("查询age in 25/26/27："
				+ customers.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.IN, new int[] { 25, 26, 27 })))
						.toArray());
		print("查询age not in 25/26/27："
				+ customers.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.NIN, new int[] { 25, 26, 27 })))
						.toArray());
		print("查询age exists 排序："
				+ customers.find(
						new BasicDBObject("age", new BasicDBObject(
								QueryOperators.EXISTS, true))).toArray());

		print("只查询age属性："
				+ customers.find(null, new BasicDBObject("age", true)).toArray());
		print("只查属性："
				+ customers.find(null, new BasicDBObject("age", true), 0, 2)
						.toArray());
		print("只查属性："
				+ customers.find(null, new BasicDBObject("age", true), 0, 2,
						Bytes.QUERYOPTION_NOTIMEOUT).toArray());

		// 只查询一条数据，多条去第一条
		print("findOne: " + customers.findOne());
		print("findOne: " + customers.findOne(new BasicDBObject("age", 26)));
		print("findOne: "
				+ customers.findOne(new BasicDBObject("age", 26),
						new BasicDBObject("name", true)));

		// 查询修改、删除
		print("findAndRemove 查询age=25的数据，并且删除: "
				+ customers.findAndRemove(new BasicDBObject("age", 25)));

		// 查询age=26的数据，并且修改name的值为Abc
		print("findAndModify: "
				+ customers.findAndModify(new BasicDBObject("age", 26),
						new BasicDBObject("name", "Abc")));
		print("findAndModify: "
				+ customers.findAndModify(new BasicDBObject("age", 28), // 查询age=28的数据
						new BasicDBObject("name", true), // 查询name属性
						new BasicDBObject("age", true), // 按照age排序
						false, // 是否删除，true表示删除
						new BasicDBObject("name", "Abc"), // 修改的值，将name修改成Abc
						true, true));

		queryAll();
	}

	public void testOthers() {
		DBObject customer = new BasicDBObject();
		customer.put("name", "hoojo");
		customer.put("age", 24);

		// JSON 对象转换
		print("serialize: " + JSON.serialize(customer));
		// 反序列化
		print("parse: " + JSON.parse("{ \"name\" : \"hoojo\" , \"age\" : 24}"));

		print("判断temp Collection是否存在: " + db.collectionExists("temp"));

		// 如果不存在就创建
		if (!db.collectionExists("temp")) {
			DBObject options = new BasicDBObject();
			options.put("size", 20);
			options.put("capped", 20);
			options.put("max", 20);
			print(db.createCollection("account", options));
		}

		// 设置db为只读
		db.setReadOnly(true);

		// 只读不能写入数据
		db.getCollection("test").save(customer);
	}

}
