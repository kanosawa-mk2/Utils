package my.bat.base.common.db;

import java.beans.PropertyDescriptor;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Beanにマッピングするときに、アンダーバーを除去してマッピングを行う。<br>
 * アンダーバーを除去した場合に、同じ名前が存在すると使えないので注意
 */
public class RemoveUnderScoreNameMappingProcessor extends FixedBeanProcessor {

	public static Map<String,String> cache = new HashMap<String,String>();

	@Override
	protected int[] mapColumnsToProperties(ResultSetMetaData rsmd,
			PropertyDescriptor[] props) throws SQLException {
		int cols = rsmd.getColumnCount();
		int columnToProperty[] = new int[cols + 1];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);
		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnName(col);
			for (int i = 0; i < props.length; i++) {
				// NOTE: 元のコードを
				// if (columnName.equalsIgnoreCase(props[i].getName())) {
				// NOTE: 次のように変更
				if (equalsColumnProperty(columnName, props[i].getName())) {
					columnToProperty[col] = i;
					break;
				}
			}
		}
		return columnToProperty;
	}

	private boolean equalsColumnProperty(String colName, String propName) {
		synchronized(cache) {
			String val = cache.get(colName);
			if(val != null) {
				return val.equalsIgnoreCase(propName);
			}else {
				val = colName.replaceAll("_", "");
				cache.put(colName, val);
				return val.equalsIgnoreCase(propName);
			}
		}
	}
}
