package cn.hutool.db;

import java.sql.SQLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cn.hutool.core.lang.Console;

/**
 * SQL Server操作单元测试
 *
 * @author looly
 *
 */
public class SqlServerTest {

	@Test
	@Disabled
	public void createTableTest() throws SQLException {
		Db.use("sqlserver").execute("create table T_USER(ID bigint, name varchar(255))");
	}

	@Test
	@Disabled
	public void insertTest() throws SQLException {
		for (int id = 100; id < 200; id++) {
			Db.use("sqlserver").insert(Entity.create("T_USER")//
					.set("ID", id)//
					.set("name", "测试用户" + id)//
			);
		}
	}

	@Test
	@Disabled
	public void pageTest() throws SQLException {
		PageResult<Entity> result = Db.use("sqlserver").page(Entity.create("T_USER"), new Page(2, 10));
		for (Entity entity : result) {
			Console.log(entity.get("ID"));
		}
	}

}
