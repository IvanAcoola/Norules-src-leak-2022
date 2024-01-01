package net.minecraftforge.font;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LOD;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MIN_LOD;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.TextFormatting;

/**
 * FontRendering by KamiBlue team. Ported and Recoded - sprayD
 * 
 */
public class FontRenderer {

	private static Tessellator tessellator;
	private static BufferBuilder buffer;

	private static FontGlyphs[] glyphArray;
	private static FontGlyphs currentVariant;
	private static ColorHolder currentColor;

	private static String[] fallbackFonts;
	private float size = 1f;
	private float gap = 0f;

	public final FontGlyphs[] getGlyphArray() {
		return glyphArray;
	}

	public final void reloadFonts(String name) {
		int i = 0;

		for (int i2 = glyphArray.length; i < i2; ++i) {
			glyphArray[i].destroy();
			glyphArray[i] = this.loadFont(name, i);
		}

	}

	private final FontGlyphs loadFont(String fontName, int index) {
		TextProperties.Style style = TextProperties.Style.values()[index];

		Font font;
		try {
			font = new Font(fontName, style.getStyleConst(), 32);
			;
		} catch (Exception e) {
			e.printStackTrace();
			font = this.getSansSerifFont(style.getStyleConst());
		}

		Font fallbackFont;
		try {
			fallbackFont = new Font(this.getFallbackFont(), style.getStyleConst(), 32);
		} catch (Exception e) {
			e.printStackTrace();
			fallbackFont = this.getSansSerifFont(style.getStyleConst());
		}

		return new FontGlyphs(style, font, fallbackFont);
	}

	private final String getFallbackFont() {
		int index = 0;

		String fallbackFont;
		while (true) {
			if (index >= fallbackFonts.length) {
				fallbackFont = null;
				break;
			}

			fallbackFont = fallbackFonts[index];

			++index;
		}

		return fallbackFont;
	}

	private final Font getSansSerifFont(int style) {
		return new Font("SansSerif", style, 32);
	}

	public void drawString(String text, double x, double y, boolean shadow, int color, float scale) {
		drawString(text, (float) x, (float) y, shadow, new ColorHolder(new Color(color)), scale);
	}

	public void drawString(String text, double x, double y, int color, float scale) {
		drawString(text, x, y, false, color, scale);
	}

	public void drawString(String text, double x, double y, int color) {
		drawString(text, x, y, false, color, 1f);
	}

	public void drawCenteredString(String text, double x, double y, int color, float scale) {
		drawString(text, x - getStringWidth(text, scale) / 2, y, color, scale);
	}

	public void drawStringWithShadow(String text, double x, double y, int color, float scale) {
		drawString(text, x, y, true, color, scale);
	}

	public void drawCenteredString(String text, double x, double y, int color) {
		drawString(text, x - getStringWidth(text) / 2, y, color, 1f);
	}

	public void drawStringWithShadow(String text, double x, double y, int color) {
		drawString(text, x, y, true, color, 1f);
	}

	public final void drawString(String text, float posXIn, float posYIn, boolean drawShadow, ColorHolder colorIn,
			float scale) {
		double posX = 0.0D;
		double posY = 0.0D;
		GlStateManager.disableOutlineMode();
		GlStateManager.enableTexture2D();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GL11.glPushMatrix();
		GL11.glTranslatef(posXIn, posYIn, 0.0F);
		GL11.glScalef(size * scale, size * scale, 1.0F);
		GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		this.resetStyle();
		CharSequence chr = (CharSequence) text;

		for (int index = 0; index < chr.length(); ++index) {
			char var12 = chr.charAt(index);
			if (!this.checkStyleCode(text, index)) {
				FontGlyphs.GlyphChunk chunk = currentVariant.getChunk(var12);
				FontGlyphs.CharInfo charInfo = currentVariant.getCharInfo(var12);
				ColorHolder color = currentColor.equals(new ColorHolder(255, 255, 255)) ? colorIn : currentColor;
				GlStateManager.bindTexture(chunk.getTextureId());
				GL11.glTexParameteri(3553, 10242, 33071);
				GL11.glTexParameteri(3553, 10243, 33071);
				GL11.glTexParameteri(3553, 10240, 9728);
				GL11.glTexParameteri(3553, 10241, 9987);
				GL11.glTexParameterf(3553, 34049, 2.0f * 0.5f - 1.25f);
				if (var12 == '\n') {
					posY += (double) (currentVariant.getFontHeight() * 0);
					posX = 0.0D;
				} else {
					if (drawShadow) {
						this.getShadowColor(color).setGLColor();
						this.drawQuad(posX + 0.7D, posY + 0.7D, charInfo);
					}

					color.setGLColor();
					this.drawQuad(posX, posY, charInfo);
					posX += charInfo.getWidth() + gap;
				}
			}
		}

		this.resetStyle();
		GL11.glPopMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.bindTexture(0);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 1000);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LOD, 1000);
		GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_LOD, -1000);
		GlStateManager.color(1f, 1f, 1f, 1f);
	}

	private ColorHolder getShadowColor(ColorHolder color) {
		return new ColorHolder((int) ((float) color.getR() * 0.2F), (int) ((float) color.getG() * 0.2F),
				(int) ((float) color.getB() * 0.2F), (int) ((float) color.getA() * 0.9F));
	}

	private void drawQuad(double posX, double posY, FontGlyphs.CharInfo charInfo) {
		buffer.begin(5, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(posX, posY, 0.0D).tex(charInfo.getU1(), charInfo.getV1()).endVertex();
		buffer.pos(posX, posY + charInfo.getHeight(), 0.0D).tex(charInfo.getU1(), charInfo.getV2()).endVertex();
		buffer.pos(posX + charInfo.getWidth(), posY, 0.0D).tex(charInfo.getU2(), charInfo.getV1()).endVertex();
		buffer.pos(posX + charInfo.getWidth(), charInfo.getHeight(), 0.0D).tex(charInfo.getU2(), charInfo.getV2())
				.endVertex();
		tessellator.draw();
	}

	public float getFontHeight(float scale) {
		return glyphArray[0].getFontHeight() * size * scale;
	}

	public float getFontHeight() {
		return getFontHeight(1f);
	}

	public float getStringWidth(String text, float scale) {
		double width = 0.0D;
		this.resetStyle();
		CharSequence chr = (CharSequence) text;

		for (int index = 0; index < chr.length(); ++index) {
			if (!this.checkStyleCode(text, index)) {
				width += currentVariant.getCharInfo(chr.charAt(index)).getWidth() + gap;
			}
		}
		this.resetStyle();
		return (float) (width * size * scale);
	}

	public float getStringWidth(String text) {
		return getStringWidth(text, 1f);
	}

	public String trimStringToWidth(String text, int width) {
		return this.trimStringToWidth(text, width, false);
	}

	public String trimStringToWidth(String text, int width, boolean reverse) {
		StringBuilder stringbuilder = new StringBuilder();

		float f = 0.0F;
		int i = reverse ? text.length() - 1 : 0;
		int j = reverse ? -1 : 1;

		boolean flag = false;
		boolean flag1 = false;

		for (int k = i; k >= 0 && k < text.length() && f < (float) width; k += j) {
			char c0 = text.charAt(k);
			float f1 = getStringWidth(Character.toString(c0));

			if (flag) {
				flag = false;

				if (c0 != 'l' && c0 != 'L') {
					if (c0 == 'r' || c0 == 'R') {
						flag1 = false;
					}
				} else {
					flag1 = true;
				}
			} else if (f1 < 0.0F) {
				flag = true;
			} else {
				f += f1;

				if (flag1) {
					++f;
				}
			}

			if (f > width) {
				break;
			}

			if (reverse) {
				stringbuilder.insert(0, c0);
			} else {
				stringbuilder.append(c0);
			}
		}

		return stringbuilder.toString();
	}

	private final void resetStyle() {
		currentVariant = glyphArray[0];
		currentColor = new ColorHolder(255, 255, 255);
	}

	private boolean checkStyleCode(String text, int index) {
		Character character = getOrNull((CharSequence) text, index - 1);
		if (character != null) {
			if (character == 167) {
				return true;
			}
		}

		character = getOrNull((CharSequence) text, index);
		if (character != null) {
			if (character == 167) {
				Character var3;
				char chr;
				label182: {
					var3 = getOrNull((CharSequence) text, index + 1);
					chr = TextProperties.Style.REGULAR.getCodeChar();
					if (var3 != null) {
						if (var3 == chr) {
							currentVariant = glyphArray[0];
							break label182;
						}
					}

					chr = TextProperties.Style.BOLD.getCodeChar();
					if (var3 != null) {
						if (var3 == chr) {
							currentVariant = glyphArray[1];
							break label182;
						}
					}

					chr = TextProperties.Style.ITALIC.getCodeChar();
					if (var3 != null) {
						if (var3 == chr) {
							currentVariant = glyphArray[2];
						}
					}
				}

				ColorHolder color;
				label183: {
					var3 = getOrNull((CharSequence) text, index + 1);
					chr = TextFormatting.BLACK.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(0, 0, 0);
							break label183;
						}
					}

					chr = TextFormatting.DARK_BLUE.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(0, 0, 170);
							break label183;
						}
					}

					chr = TextFormatting.DARK_GREEN.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(0, 170, 0);
							break label183;
						}
					}

					chr = TextFormatting.DARK_AQUA.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(0, 170, 170);
							break label183;
						}
					}

					chr = TextFormatting.DARK_RED.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(170, 0, 0);
							break label183;
						}
					}

					chr = TextFormatting.DARK_PURPLE.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(170, 0, 170);
							break label183;
						}
					}

					chr = TextFormatting.GOLD.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(250, 170, 0);
							break label183;
						}
					}

					chr = TextFormatting.GRAY.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(170, 170, 170);
							break label183;
						}
					}

					chr = TextFormatting.DARK_GRAY.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(85, 85, 85);
							break label183;
						}
					}

					chr = TextFormatting.BLUE.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(85, 85, 255);
							break label183;
						}
					}

					chr = TextFormatting.GREEN.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(85, 255, 85);
							break label183;
						}
					}

					chr = TextFormatting.AQUA.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(85, 255, 255);
							break label183;
						}
					}

					chr = TextFormatting.RED.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(255, 85, 85);
							break label183;
						}
					}

					chr = TextFormatting.LIGHT_PURPLE.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(255, 85, 255);
							break label183;
						}
					}

					chr = TextFormatting.YELLOW.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(255, 255, 85);
							break label183;
						}
					}

					chr = TextFormatting.WHITE.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(254, 255, 255);
							break label183;
						}
					}

					chr = TextFormatting.RESET.toString().charAt(1);
					if (var3 != null) {
						if (var3 == chr) {
							color = new ColorHolder(255, 255, 255);
							break label183;
						}
					}

					color = currentColor;
				}

				currentColor = color;
				return true;
			}
		}

		return false;
	}

	public static final Character getOrNull(CharSequence chr, int index) {
		return (index >= 0 && index <= chr.length() - 1) ? Character.valueOf(chr.charAt(index)) : null;
	}

	public FontRenderer(String fontName, float size, float gap) {
		tessellator = Tessellator.getInstance();
		buffer = tessellator.getBuffer();tessellator = Tessellator.getInstance();
		buffer = tessellator.getBuffer();
		currentColor = new ColorHolder(255, 255, 255);
		this.size = size;
		this.gap = gap;

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] font = graphicsEnvironment.getAllFonts();
		Collection destination = (Collection) (new ArrayList(font.length));

		for (int i = 0; i < font.length; ++i) {
			destination.add(font[i].getName());
		}

		fallbackFonts = new String[] { "Noto Sans JP", "Noto Sans CJK JP", "Noto Sans CJK JP", "Noto Sans CJK KR",
				"Noto Sans CJK SC", "Noto Sans CJK TC", "Source Han Sans", "Source Han Sans HC", "Source Han Sans SC",
				"Source Han Sans TC", "Source Han Sans K", "MS Gothic", "Meiryo", "Yu Gothic", "Hiragino Sans GB W3",
				"Hiragino Kaku Gothic Pro W3", "Hiragino Kaku Gothic ProN W3", "Osaka", "TakaoPGothic", "IPAPGothic" };
		byte index = 3;
		FontGlyphs[] fontGlyphs = new FontGlyphs[index];

		for (int i = 0; i < index; ++i) {
			fontGlyphs[i] = loadFont(fontName, i);
		}

		glyphArray = fontGlyphs;
		currentVariant = glyphArray[0];
	}

	public float getSize() {
		return size;
	}

}
