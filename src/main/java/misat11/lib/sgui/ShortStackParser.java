package misat11.lib.sgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShortStackParser {
	public static final List<String> STRING_BEGIN_END = Arrays.asList("\"", "'");
	public static final List<String> ESCAPE_SYMBOLS = Arrays.asList("\\");
	public static final List<String> ARGUMENT_SEPARATORS = Arrays.asList(";");

	@SuppressWarnings("deprecation")
	public static ItemStack parseShortStack(String shortStack) {
		shortStack = shortStack.trim();
		if (shortStack.startsWith("(cast to ItemStack)")) {
			shortStack = shortStack.substring(19).trim();
		}
		
		char[] characters = shortStack.toCharArray();
		List<String> arguments = new ArrayList<String>();
		int lastIndexOfEscape = -2;
		boolean workOnString = false;
		String workOnStringWith = "";
		String build = "";
		for (int i = 0; i < characters.length; i++) {
			char character = characters[i];
			String c = String.valueOf(character);
			if (ESCAPE_SYMBOLS.contains(c) && lastIndexOfEscape != (i - 1)) {
				lastIndexOfEscape = i;
			} else if (workOnString) {
				if (lastIndexOfEscape != (i - 1) && c.equals(workOnStringWith)) {
					workOnString = false;
					arguments.add(build.trim());
					build = "";
				} else {
					build += c;
				}
			} else if (STRING_BEGIN_END.contains(c) && lastIndexOfEscape != (i - 1)) {
				workOnString = true;
				workOnStringWith = c;
			} else if (ARGUMENT_SEPARATORS.contains(c) && lastIndexOfEscape != (i - 1)) {
				arguments.add(build);
				build = "";
			} else {
				build += c;
			}
		}
		if (!build.equals("")) {
			arguments.add(build);
		}

		Material mat = Material.AIR;
		int damage = 0;
		int amount = 1;
		String displayName = null;
		List<String> lore = new ArrayList<String>();

		for (int i = 0; i < arguments.size(); i++) {
			String argument = arguments.get(i);
			if (i == 0) {
				String[] splitByColon = argument.split(":");
				mat = Material.matchMaterial(splitByColon[0]);
				if (mat == null) {
					// try legacy
					try {
						Material.matchMaterial(splitByColon[0], true);
					} catch (Throwable t) {
					}
				}
				if (mat == null) {
					// maybe it's id?
					try {
						int legacyId = Integer.parseInt(splitByColon[0]);
						
						for (Material mater : Material.values()) {
							try {
								if (mater.getId() == legacyId) {
									mat = mater;
								}
							} catch (Throwable t) {
								// This material is not legacy
							}
						}
					} catch (Throwable t) {
						// this is not id
					}
				}
				if (mat == null) {
					// this is nothing, let it be air
					mat = Material.AIR;
				}
				if (splitByColon.length > 1) {
					damage = Integer.parseInt(splitByColon[1]);
				}
			} else if (i == 1) {
				amount = Integer.parseInt(argument);
			} else if (i == 2) {
				displayName = argument;
			} else {
				lore.add(argument);
			}
		}

		ItemStack stack = new ItemStack(mat, amount, (short) damage);
		if (mat != Material.AIR) {
			ItemMeta meta = stack.getItemMeta();
			if (displayName != null && !displayName.equals("")) {
				meta.setDisplayName(displayName);
			}
			if (!lore.isEmpty()) {
				meta.setLore(lore);
			}
			stack.setItemMeta(meta);

		}
		return stack;
	}
}
