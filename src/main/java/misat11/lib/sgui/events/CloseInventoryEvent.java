package misat11.lib.sgui.events;

import misat11.lib.sgui.GuiHolder;
import misat11.lib.sgui.SimpleGuiFormat;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class CloseInventoryEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private SimpleGuiFormat format;
    private GuiHolder guiHolder;
    private Inventory inventory;
    private boolean isCanceled = false;

    public CloseInventoryEvent(Player player, SimpleGuiFormat format, GuiHolder guiHolder, Inventory inventory) {
        this.player = player;
        this.format = format;
        this.guiHolder = guiHolder;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public SimpleGuiFormat getFormat() {
        return format;
    }

    public GuiHolder getGuiHolder() {
        return guiHolder;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public static HandlerList getHandlerList() {
        return CloseInventoryEvent.handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return CloseInventoryEvent.handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.isCanceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCanceled = cancel;
    }
}
