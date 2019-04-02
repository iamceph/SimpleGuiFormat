package misat11.lib.sgui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SimpleGuiFormat {

	public static final int ITEMS_ON_PAGE = 36;

	public static final int ITEMS_ON_ROW = 9;

	private final List<Map<String, Object>> data;
	private final List<ItemInfo> generatedData = new ArrayList<ItemInfo>();

	private int lastpos = 0;

	public SimpleGuiFormat(List<Map<String, Object>> data) {
		this.data = data;
	}

	public List<Map<String, Object>> getData() {
		return this.data;
	}

	public List<ItemInfo> getPreparedData() {
		return this.generatedData;
	}

	public void generateData() {
		for (Map<String, Object> object : data) {
			lastpos = generateItem(null, object, lastpos);
		}
	}

	private int generateItem(ItemInfo parent, Map<String, Object> object, int lastpos) {
		ItemStack stack = object.containsKey("stack") ? (ItemStack) object.get("stack") : new ItemStack(Material.AIR);
		int positionC = lastpos;
		int linebreakC = 0;
		int pagebreakC = 0;
		if (object.containsKey("linebreak")) {
			String lnBreak = (String) object.get("linebreak");
			if ("before".equalsIgnoreCase(lnBreak)) {
				linebreakC = 1;
			} else if ("after".equalsIgnoreCase(lnBreak)) {
				linebreakC = 2;
			} else if ("both".equalsIgnoreCase(lnBreak)) {
				linebreakC = 3;
			}
		}
		if (object.containsKey("pagebreak")) {
			String pgBreak = (String) object.get("pagebreak");
			if ("before".equalsIgnoreCase(pgBreak)) {
				pagebreakC = 1;
			} else if ("after".equalsIgnoreCase(pgBreak)) {
				pagebreakC = 2;
			} else if ("both".equalsIgnoreCase(pgBreak)) {
				pagebreakC = 3;
			}
		}
		if (pagebreakC == 1 || pagebreakC == 3) {
			positionC += (ITEMS_ON_PAGE - (positionC % ITEMS_ON_PAGE));
		}
		if (object.containsKey("row")) {
			positionC = positionC - (positionC % ITEMS_ON_PAGE) + (((int) object.get("row") - 1) * 9) + (positionC % 9);
		}
		if (object.containsKey("column")) {
			Object cl = object.get("column");
			int column = 0;
			if ("left".equals(cl) || "first".equals(cl)) {
				column = 0;
			} else if ("middle".equals(cl) || "center".equals(cl)) {
				column = 4;
			} else if ("right".equals(cl) || "last".equals(cl)) {
				column = 8;
			} else {
				column = (int) cl;
			}

			positionC = (positionC - (positionC % 9)) + column;
		}
		if (linebreakC == 1 || linebreakC == 3) {
			positionC += (9 - (positionC % 9));
		}
		if (object.containsKey("skip")) {
			positionC += (int) object.get("skip");
		}
		String id = object.containsKey("id") ? (String) object.get("id") : null;
		List<Property> properties = new ArrayList<Property>();
		if (object.containsKey("properties")) {
			Object prop = object.get("properties");
			if (properties instanceof List) {
				List<Object> propertiesList = (List<Object>) prop;
				for (Object obj : propertiesList) {
					if (obj instanceof Map) {
						Map<String, Object> propertyMap = (Map<String, Object>) obj;
						Property pr = new Property(propertyMap.containsKey("name") ? (String) propertyMap.get("name") : null, propertyMap);
						properties.add(pr);
					}
				}
			}
		}
		ItemData iData = new ItemData(id, properties, object);
		ItemInfo info = new ItemInfo(parent, stack, positionC, iData);
		if (object.containsKey("items")) {

			List<Map<String, Object>> items = (List<Map<String, Object>>) object.get("items");
			for (Map<String, Object> itemObject : items) {
				iData.lastpos = generateItem(info, itemObject, iData.lastpos);
			}
		}
		generatedData.add(info);
		int nextPosition = positionC;
		if (pagebreakC >= 2) {
			nextPosition += (ITEMS_ON_PAGE - (nextPosition % ITEMS_ON_PAGE));
		}
		if (linebreakC >= 2) {
			nextPosition += (9 - (nextPosition % 9));
		}
		if (pagebreakC < 2 && linebreakC < 2) {
			nextPosition++;
		}
		lastpos = nextPosition;
		return lastpos;
	}
}