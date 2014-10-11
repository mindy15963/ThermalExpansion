package thermalexpansion.block.simple;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAirSignal extends BlockAirBase {

	public BlockAirSignal() {

		super(materialBarrier);
		disableStats();
		setBlockTextureName("glowstone");
		setBlockBounds(0, 0, 0, 0, 0, 0);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public boolean canProvidePower() {

		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {

		EntityFireworkSparkFX spark = new EntityFireworkSparkFX(world, x + 0.5, y + 0.5, z + 0.5,
				rand.nextGaussian() / 10, rand.nextDouble() / 6, rand.nextGaussian() / 10,
				Minecraft.getMinecraft().effectRenderer);
		spark.setColour(0xFF0000);
		spark.setFadeColour(0x660000);
		spark.setTwinkle(true);
		Minecraft.getMinecraft().effectRenderer.addEffect(spark);
	}

}
