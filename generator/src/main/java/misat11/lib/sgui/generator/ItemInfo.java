package misat11.lib.sgui.generator;

public class ItemInfo {
	private ItemInfo parent;
	private int position;
	private ItemStack item;
	private ItemData data;
	private boolean hasChilds;
	
	public ItemInfo(ItemInfo parent, ItemStack item, int position, ItemData data) {
		this.parent = parent;
		this.item = item;
		this.position = position;
		this.data = data;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	public ItemData getData() {
		return data;
	}
	
	public void setData(ItemData data) {
		this.data = data;
	}

	public ItemInfo getParent() {
		return parent;
	}

	public void setParent(ItemInfo parent) {
		this.parent = parent;
	}

	public boolean hasChilds() {
		return hasChilds;
	}

	public void setHasChilds(boolean hasChilds) {
		this.hasChilds = hasChilds;
	}
	
}
