package misat11.lib.sgui.events;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import misat11.lib.sgui.ItemInfo;
import misat11.lib.sgui.PlayerItemInfo;
import misat11.lib.sgui.Property;
import misat11.lib.sgui.SimpleGuiFormat;

public class ShopTransactionEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Player player = null;
	private SimpleGuiFormat format = null;
	private int price = 0;
	private String type = null;
	private ItemStack stack = null;
	private PlayerItemInfo item = null;
	private boolean cancel = false;

	public ShopTransactionEvent(Player player, SimpleGuiFormat format, PlayerItemInfo item, int price, String type) {
		this.player = player;
		this.format = format;
		this.item = item;
		this.stack = item.getOriginal().getItem().clone();
		this.price = price;
		this.type = type;
	}

	public static HandlerList getHandlerList() {
		return ShopTransactionEvent.handlers;
	}

	public Player getPlayer() {
		return this.player;
	}
	
	public SimpleGuiFormat getFormat() {
		return this.format;
	}
	
	public PlayerItemInfo getItem() {
		return this.item;
	}
	
	public ItemInfo getOriginalItem() {
		return this.item.getOriginal();
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public String getType() {
		return this.type;
	}
	
	public ItemStack getStack() {
		return this.stack;
	}
	
	public boolean hasPlayerInInventory() {
		return hasPlayerInInventory(this.stack);
	}
	
	public boolean hasPlayerInInventory(ItemStack stack) {
		return this.player.getInventory().containsAtLeast(stack, stack.getAmount());
	}
	
	public void sellStack() {
		sellStack(this.stack);
	}
	
	public void sellStack(ItemStack stack) {
		this.player.getInventory().removeItem(stack);
	}
	
	public void buyStack() {
		buyStack(this.stack);
	}
	
	public void buyStack(ItemStack stack) {
		this.player.getInventory().addItem(stack);
	}
	
	public List<Property> getProperties() {
		return this.item.getProperties();
	}
	
	public boolean hasProperties() {
		return this.item.hasProperties();
	}

	@Override
	public HandlerList getHandlers() {
		return ShopTransactionEvent.handlers;
	}

	@Override
	public boolean isCancelled() {
		return this.cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
