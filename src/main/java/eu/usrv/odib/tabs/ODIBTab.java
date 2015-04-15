package eu.usrv.odib.tabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ODIBTab extends CreativeTabs {
	String name;
	
	public ODIBTab(int par1, String par2str)
	{
		super(par1, par2str);
		this.name = par2str;
	}
	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return Items.iron_ingot;
	}
}
