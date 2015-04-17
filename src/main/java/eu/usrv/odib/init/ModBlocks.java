package eu.usrv.odib.init;

import cpw.mods.fml.common.registry.GameRegistry;
import eu.usrv.odib.blocks.*;
import eu.usrv.odib.help.RegisterHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {

//	public static Block _SteelLeafBlock = new BlockBase("blockSteelLeaf");
	private static BlocksEnabled _enabledBlocks = null;
	
    public static void init(BlocksEnabled pEnabledBlocks)
    {
    	_enabledBlocks = pEnabledBlocks;
    	InitBlocks();
    	RegisterBlocks();
    }
    
    private static void InitBlocks()
    {
/*    	RegisterHelper.registerBlock(_AdamantiumBlock); */

    }
    //public static Block Blocks_A = new Blocks_A();
    
    private static void RegisterBlocks()
    {
    	//GameRegistry.registerBlock(Blocks_A, "Block of ");
    	
    	
    	
    	//OreDictionary.registerOre("blockAdamantium", new ItemStack(_AdamantiumBlock, 1, 1));
    	
    }
}
