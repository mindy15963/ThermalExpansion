package thermalexpansion.block.simple;

import buildcraft.api.tools.IToolWrench;

import cofh.api.block.IDismantleable;
import cofh.api.core.IInitializer;
import cofh.render.IconRegistry;
import cofh.util.ServerHelper;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import thermalexpansion.ThermalExpansion;
import thermalexpansion.util.Utils;

public class BlockGlass extends Block implements IDismantleable, IInitializer {

	public BlockGlass() {

		super(Material.glass);
		setHardness(3.0F);
		setResistance(200.0F);
		setStepSound(soundTypeGlass);
		setCreativeTab(ThermalExpansion.tabBlocks);
		setBlockName("thermalexpansion.glass");
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {

		for (int i = 0; i < 1; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitSide, float hitX, float hitY, float hitZ) {

		Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;

		if (player.isSneaking()) {
			if (Utils.isHoldingUsableWrench(player, x, y, z)) {
				if (ServerHelper.isServerWorld(world)) {
					dismantleBlock(player, world, x, y, z, false);
					((IToolWrench) equipped).wrenchUsed(player, x, y, z);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int quantityDropped(Random random) {

		return 0;
	}

	@Override
	protected boolean canSilkHarvest() {

		return true;
	}

	@Override
	public int damageDropped(int i) {

		return i;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {

		return false;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {

		return IconRegistry.getIcon("GlassHardened");
	}

	@Override
	public int getRenderBlockPass() {

		return 1;
	}

	@Override
	public boolean isOpaqueCube() {

		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {

		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {

		Block block = world.getBlock(x, y, z);
		return block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {

		IconRegistry.addIcon("GlassHardened", "thermalexpansion:glass/Glass_Hardened", ir);
	}

	/* IDismantleable */
	@Override
	public ItemStack dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnBlock) {

		int metadata = world.getBlockMetadata(x, y, z);
		ItemStack dropBlock = new ItemStack(this, 1, metadata);
		world.setBlockToAir(x, y, z);

		if (dropBlock != null && !returnBlock) {
			float f = 0.3F;
			double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entity = new EntityItem(world, x + x2, y + y2, z + z2, dropBlock);
			entity.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entity);

			Utils.dismantleLog(player.getDisplayName(), this, metadata, x, y, z);
		}
		return dropBlock;
	}

	@Override
	public boolean canDismantle(EntityPlayer player, World world, int i, int j, int k) {

		return true;
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		return true;
	}

	@Override
	public boolean initialize() {

		glassHardened = new ItemStack(this);

		OreDictionary.registerOre("glassHardened", glassHardened);
		GameRegistry.registerCustomItemStack("glassHardened", glassHardened);
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", glassHardened);

		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	public static ItemStack glassHardened;

}
