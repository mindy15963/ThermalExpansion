package thermalexpansion.gui.element;

import cofh.gui.GuiBase;
import cofh.gui.element.ElementBase;
import cofh.render.RenderHelper;

import thermalexpansion.core.TEProps;

public class ElementSlotOverlay extends ElementBase {

	public static boolean enableBorders;
	public static boolean colorBlind;

	public int slotColor;
	public int slotType;
	public int slotRender;

	public ElementSlotOverlay(GuiBase gui, int posX, int posY) {

		super(gui, posX, posY);
		this.texture = TEProps.textureGuiCommon;
	}

	public ElementSlotOverlay setSlotInfo(int color, int type, int render) {

		slotColor = color;
		slotType = type;
		slotRender = render;
		return this;
	}

	public ElementSlotOverlay setSlotColor(int color) {

		slotColor = color;
		return this;
	}

	public ElementSlotOverlay setSlotRender(int render) {

		slotRender = render;
		return this;
	}

	@Override
	public void draw() {

		if (!visible) {
			return;
		}
		RenderHelper.bindTexture(texture);
		if (enableBorders) {
			drawSlotWithBorder(posX, posY);
		} else {
			drawSlotNoBorder(posX, posY);
		}
	}

	@Override
	public boolean intersectsWith(int mouseX, int mouseY) {

		return false;
	}

	protected void drawSlotNoBorder(int x, int y) {

		sizeX = 0;
		sizeY = 0;
		int offsetX = slotColor / 3 * 128;
		int offsetY = slotColor % 3 * 32;

		switch (slotType) {
		case 0:
			sizeX = 16;
			sizeY = 16;
			offsetX += 8;
			offsetY += 8;
			break;
		case 1:
			sizeX = 24;
			sizeY = 24;
			offsetX += 36;
			offsetY += 4;
			break;
		case 2:
			sizeX = 42;
			sizeY = 24;
			offsetX += 75;
			offsetY += 4;
			break;
		case 3:
			sizeX = 16;
			sizeY = 60;
			offsetX = slotColor * 32 + 8;
			offsetY = 98;
			break;
		case 4:
			sizeX = 34;
			sizeY = 160;
			offsetX += 8;
			offsetY += 164;
			break;
		}

		switch (slotRender) {
		case 0:
			sizeY /= 2;
			break;
		case 1:
			sizeY /= 2;
			y += sizeY;
			offsetY += sizeY;
			break;
		case 2:
			break;
		}
		gui.drawTexturedModalRect(x, y, offsetX, offsetY, sizeX, sizeY);
	}

	protected void drawSlotWithBorder(int x, int y) {

		int sizeX = 32;
		int sizeY = 32;
		int offsetX = slotColor / 3 * 128;
		int offsetY = slotColor % 3 * 32;

		offsetX += slotType * 32;

		switch (slotType) {
		case 0:
			x -= 8;
			y -= 8;
			break;
		case 1:
			x -= 4;
			y -= 4;
			break;
		case 2:
			sizeX = 64;
			x -= 11;
			y -= 4;
			break;
		case 3:
			sizeX = 32;
			sizeY = 64;
			offsetX = slotColor * 32;
			offsetY = 96;
			x -= 8;
			y -= 2;
			break;
		case 4:
			sizeX = 164;
			sizeY = 38;
			offsetX = slotColor / 3 * 128 + 6;
			offsetY += 162;
			x -= 2;
			y -= 2;
			break;
		}

		switch (slotRender) {
		case 0:
			sizeY /= 2;
			break;
		case 1:
			sizeY /= 2;
			y += sizeY;
			offsetY += sizeY;
			break;
		case 2:
			break;
		}
		gui.drawTexturedModalRect(x, y, offsetX, offsetY, sizeX, sizeY);
	}

}
