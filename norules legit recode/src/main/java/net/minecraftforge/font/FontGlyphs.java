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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;

public class FontGlyphs {

	private final HashMap<Integer, GlyphChunk> chunkMap;
	private float fontHeight;

	private final TextProperties.Style style;
	private final Font font;
	private final Font fallbackFont;
	public static final int TEXTURE_WIDTH = 1024;
	public static final double TEXTURE_WIDTH_DOUBLE = 1024.0;
	public static final int MAX_TEXTURE_HEIGHT = 1024;

	public final float getFontHeight() {
		return this.fontHeight;
	}

	public final CharInfo getCharInfo(char var1) {
		int chunk = var1 >> 8;
		int chunkStart = chunk << 8;
		return this.getChunk(chunk).getCharInfoArray()[var1 - chunkStart];
	}

	public final GlyphChunk getChunk(char var1) {
		return this.getChunk(var1 >> 8);
	}

	public final GlyphChunk getChunk(int chunk) {
		Map map = this.chunkMap;
		Object value = map.get(chunk);
		Object var8;
		if (value == null) {
			GlyphChunk var10000 = this.loadGlyphChunk(chunk);
			if (var10000 == null) {
				var8 = this.chunkMap.get(0);

				return (GlyphChunk) var8;
			}

			Object answer$iv = var10000;
			map.put(chunk, answer$iv);
			var8 = answer$iv;
		} else {
			var8 = value;
		}

		return (GlyphChunk) var8;
	}

	public final void destroy() {
		Iterator var2 = this.chunkMap.values().iterator();

		while (var2.hasNext()) {
			GlyphChunk chunk = (GlyphChunk) var2.next();
			chunk.getDynamicTexture().deleteGlTexture();
		}

		this.chunkMap.clear();
	}

	public static int ceilToPOT(int valueIn) {
		int i = valueIn;
		i = (--i | i >> 1);
		i |= i >> 2;
		i |= i >> 4;
		i |= i >> 8;
		i |= i >> 16;
		return ++i;
	}

	private final GlyphChunk loadGlyphChunk(int chunk) {
		GlyphChunk var2;
		try {
			int chunkStart = chunk << 8;
			BufferedImage bufferedImage = new BufferedImage(1024, 1024, 2);
			Graphics var10000 = bufferedImage.getGraphics();
			if (var10000 == null) {
				throw new IllegalStateException("null cannot be cast to non-null type java.awt.Graphics2D");
			}

			Graphics2D graphics2D = (Graphics2D) var10000;
			graphics2D.setBackground(new Color(0, 0, 0, 0));
			int rowHeight = 0;
			int positionX = 1;
			int positionY = 1;
			short var9 = 256;
			CharInfoBuilder[] var10 = new CharInfoBuilder[var9];

			for (int var11 = 0; var11 < var9; ++var11) {
				char var14 = (char) (chunkStart + var11);
				BufferedImage charImage = this.getCharImage(var14);
				if (positionX + charImage.getWidth() >= 1024) {
					positionX = 1;
					positionY += rowHeight;
					rowHeight = 0;
				}

				CharInfoBuilder builder = new CharInfoBuilder(positionX, positionY,
						charImage.getWidth(), charImage.getHeight());
				int var17 = charImage.getHeight();
				boolean var20 = false;
				int var21 = Math.max(var17, rowHeight);
				rowHeight = var21;
				graphics2D.drawImage((Image) charImage, positionX, positionY, (ImageObserver) null);
				positionX += charImage.getWidth() + 2;
				var10[var11] = builder;
			}

			int var30 = ceilToPOT(positionY + rowHeight);
			short var32 = 1024;
			boolean var12 = false;
			int textureHeight = Math.min(var30, var32);
			BufferedImage textureImage = new BufferedImage(1024, textureHeight, 2);
			var10000 = textureImage.getGraphics();
			if (var10000 == null) {
				throw new IllegalStateException("null cannot be cast to non-null type java.awt.Graphics2D");
			}

			((Graphics2D) var10000).drawImage((Image) bufferedImage, 0, 0, (ImageObserver) null);
			DynamicTexture var38 = this.createTexture(textureImage);

			DynamicTexture dynamicTexture = var38;
			Collection destination$iv$iv = (Collection) (new ArrayList(var10.length));
			CharInfoBuilder[] var18 = var10;
			int var19 = var10.length;

			for (int var41 = 0; var41 < var19; ++var41) {
				Object item$iv$iv = var18[var41];
				CharInfo var25 = ((CharInfoBuilder) item$iv$iv).build((double) textureHeight);
				destination$iv$iv.add(var25);
			}

			Collection $this$toTypedArray$iv = (Collection) ((List) destination$iv$iv);
			Object[] var39 = $this$toTypedArray$iv.toArray(new CharInfo[0]);
			if (var39 == null) {
				throw new IllegalStateException("null cannot be cast to non-null type kotlin.Array<T>");
			}

			CharInfo[] charInfoArray = (CharInfo[]) var39;
			var2 = new GlyphChunk(chunk, dynamicTexture.getGlTextureId(), dynamicTexture, charInfoArray);
		} catch (Exception var27) {

			var27.printStackTrace();
			var2 = null;
		}

		return var2;
	}

	private final BufferedImage getCharImage(char var1) {
		Font font = this.font.canDisplay(var1) ? this.font
				: (this.fallbackFont.canDisplay(var1) ? this.fallbackFont : this.font);
		Graphics var10000 = (new BufferedImage(1, 1, 2)).getGraphics();
		if (var10000 == null) {
			throw new IllegalStateException("null cannot be cast to non-null type java.awt.Graphics2D");
		} else {
			Graphics2D tempGraphics2D = (Graphics2D) var10000;
			tempGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			tempGraphics2D.setFont(font);
			FontMetrics fontMetrics = tempGraphics2D.getFontMetrics();
			tempGraphics2D.dispose();
			int charWidth = fontMetrics.charWidth(var1) > 0 ? fontMetrics.charWidth(var1) : 8;
			int charHeight = fontMetrics.getHeight() > 0 ? fontMetrics.getHeight() : font.getSize();
			BufferedImage charImage = new BufferedImage(charWidth, charHeight, 2);
			var10000 = charImage.getGraphics();
			if (var10000 == null) {
				throw new IllegalStateException("null cannot be cast to non-null type java.awt.Graphics2D");
			} else {
				Graphics2D graphics2D = (Graphics2D) var10000;
				graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics2D.setFont(font);
				graphics2D.setColor(Color.WHITE);
				graphics2D.drawString(String.valueOf(var1), 0, fontMetrics.getAscent());
				return charImage;
			}
		}
	}

	private final DynamicTexture createTexture(BufferedImage bufferedImage) {
		DynamicTexture dynamicTexture;
		try {
			dynamicTexture = new DynamicTexture(bufferedImage);
			dynamicTexture.loadTexture(Minecraft.getMinecraft().getResourceManager());
			int textureId = dynamicTexture.getGlTextureId();
			GL11.glTexParameteri(3553, 10242, 33071);
			GL11.glTexParameteri(3553, 10243, 33071);
			GL11.glTexParameteri(3553, 10240, 9728);
			GL11.glTexParameteri(3553, 10241, 9729);
			GL11.glHint(33170, 4354);
			GL11.glTexParameteri(3553, 33082, 0);
			GL11.glTexParameteri(3553, 33083, 3);
			GL11.glTexParameteri(3553, 33084, 0);
			GL11.glTexParameteri(3553, 33085, 3);
			GL11.glTexParameterf(3553, 34049, 0.0F);
			GlStateManager.bindTexture(textureId);
			int mipmapLevel = 0;

			for (byte var5 = 3; mipmapLevel <= var5; ++mipmapLevel) {
				GL11.glTexImage2D(3553, mipmapLevel, 6406, bufferedImage.getWidth() >> mipmapLevel,
						bufferedImage.getHeight() >> mipmapLevel, 0, 6406, 5121, (ByteBuffer) null);
			}

			GL11.glTexParameteri(3553, 33169, 1);
			TextureUtil.uploadTextureImageSub(textureId, bufferedImage, 0, 0, true, true);
			GlStateManager.bindTexture(0);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 1000);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LOD, 1000);
			GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_LOD, -1000);
		} catch (Exception var6) {
			var6.printStackTrace();
			dynamicTexture = null;
		}

		return dynamicTexture;
	}

	public final TextProperties.Style getStyle() {
		return this.style;
	}

	public FontGlyphs(TextProperties.Style style, Font font, Font fallbackFont) {
		super();
		this.style = style;
		this.font = font;
		this.fallbackFont = fallbackFont;
		this.chunkMap = new HashMap();
		FontGlyphs var10000 = this;
		GlyphChunk var10001 = this.loadGlyphChunk(0);
		float var26;
		if (var10001 != null) {
			GlyphChunk var4 = var10001;
			((Map) this.chunkMap).put(0, var4);
			CharInfo[] $this$maxBy$iv = var4.getCharInfoArray();

			CharInfo var25;
			if ($this$maxBy$iv.length == 0) {
				var25 = null;
			} else {
				CharInfo maxElem$iv = $this$maxBy$iv[0];
				int lastIndex$iv = $this$maxBy$iv.length - 1;
				if (lastIndex$iv == 0) {
					var25 = maxElem$iv;
				} else {
					double maxValue$iv = maxElem$iv.getHeight();
					int i$iv = 1;
					int var16 = lastIndex$iv;
					if (i$iv <= lastIndex$iv) {
						while (true) {
							CharInfo e$iv = $this$maxBy$iv[i$iv];
							double v$iv = e$iv.getHeight();
							if (Double.compare(maxValue$iv, v$iv) < 0) {
								maxElem$iv = e$iv;
								maxValue$iv = v$iv;
							}

							if (i$iv == var16) {
								break;
							}

							++i$iv;
						}
					}

					var25 = maxElem$iv;
				}
			}

			float var22 = var25 != null ? (float) var25.getHeight() : 32.0F;
			var10000 = this;
			var26 = var22;
		} else {
			var26 = 32.0F;
		}

		var10000.fontHeight = var26;
	}

	public static final class CharInfoBuilder {
		private final int posX;
		private final int posY;
		private final int width;
		private final int height;

		public final CharInfo build(double textureHeight) {
			return new CharInfo((double) this.posX, (double) this.posY, (double) this.width,
					(double) this.height, (double) this.posX / 1024.0D, (double) this.posY / textureHeight,
					(double) (this.posX + this.width) / 1024.0D, (double) (this.posY + this.height) / textureHeight);
		}

		public final int getPosX() {
			return this.posX;
		}

		public final int getPosY() {
			return this.posY;
		}

		public final int getWidth() {
			return this.width;
		}

		public final int getHeight() {
			return this.height;
		}

		public CharInfoBuilder(int posX, int posY, int width, int height) {
			this.posX = posX;
			this.posY = posY;
			this.width = width;
			this.height = height;
		}
	}

	public static final class CharInfo {
		private final double posX1;
		private final double posY1;
		private final double width;
		private final double height;
		private final double u1;
		private final double v1;
		private final double u2;
		private final double v2;

		public final double getPosX1() {
			return this.posX1;
		}

		public final double getPosY1() {
			return this.posY1;
		}

		public final double getWidth() {
			return this.width;
		}

		public final double getHeight() {
			return this.height;
		}

		public final double getU1() {
			return this.u1;
		}

		public final double getV1() {
			return this.v1;
		}

		public final double getU2() {
			return this.u2;
		}

		public final double getV2() {
			return this.v2;
		}

		public CharInfo(double posX1, double posY1, double width, double height, double u1, double v1, double u2,
				double v2) {
			this.posX1 = posX1;
			this.posY1 = posY1;
			this.width = width;
			this.height = height;
			this.u1 = u1;
			this.v1 = v1;
			this.u2 = u2;
			this.v2 = v2;
		}

		public final double component1() {
			return this.posX1;
		}

		public final double component2() {
			return this.posY1;
		}

		public final double component3() {
			return this.width;
		}

		public final double component4() {
			return this.height;
		}

		public final double component5() {
			return this.u1;
		}

		public final double component6() {
			return this.v1;
		}

		public final double component7() {
			return this.u2;
		}

		public final double component8() {
			return this.v2;
		}

		public final CharInfo copy(double posX1, double posY1, double width, double height, double u1,
				double v1, double u2, double v2) {
			return new CharInfo(posX1, posY1, width, height, u1, v1, u2, v2);
		}

		public String toString() {
			return "CharInfo(posX1=" + this.posX1 + ", posY1=" + this.posY1 + ", width=" + this.width + ", height="
					+ this.height + ", u1=" + this.u1 + ", v1=" + this.v1 + ", u2=" + this.u2 + ", v2=" + this.v2 + ")";
		}

		public int hashCode() {
			return ((((((Double.hashCode(this.posX1) * 31 + Double.hashCode(this.posY1)) * 31
					+ Double.hashCode(this.width)) * 31 + Double.hashCode(this.height)) * 31 + Double.hashCode(this.u1))
					* 31 + Double.hashCode(this.v1)) * 31 + Double.hashCode(this.u2)) * 31 + Double.hashCode(this.v2);
		}

		public boolean equals(@Nullable Object var1) {
			if (this != var1) {
				if (var1 instanceof CharInfo) {
					CharInfo var2 = (CharInfo) var1;
					if (Double.compare(this.posX1, var2.posX1) == 0 && Double.compare(this.posY1, var2.posY1) == 0
							&& Double.compare(this.width, var2.width) == 0
							&& Double.compare(this.height, var2.height) == 0 && Double.compare(this.u1, var2.u1) == 0
							&& Double.compare(this.v1, var2.v1) == 0 && Double.compare(this.u2, var2.u2) == 0
							&& Double.compare(this.v2, var2.v2) == 0) {
						return true;
					}
				}

				return false;
			} else {
				return true;
			}
		}
	}

	public static final class GlyphChunk {
		private final int chunk;
		private final int textureId;

		private final DynamicTexture dynamicTexture;

		private final CharInfo[] charInfoArray;

		public boolean equals(@Nullable Object other) {
			if ((GlyphChunk) this == other) {
				return true;
			} else if (!(other instanceof GlyphChunk)) {
				return false;
			} else if (this.chunk != ((GlyphChunk) other).chunk) {
				return false;
			} else if (this.textureId != ((GlyphChunk) other).textureId) {
				return false;
			} else {
				CharInfo[] var2 = this.charInfoArray;
				CharInfo[] var3 = ((GlyphChunk) other).charInfoArray;
				boolean var4 = false;
				return Arrays.equals(var2, var3);
			}
		}

		public int hashCode() {
			int result = this.chunk;
			result = 31 * result + this.textureId;
			int var10000 = 31 * result;
			CharInfo[] var2 = this.charInfoArray;
			int var4 = var10000;
			boolean var3 = false;
			int var5 = Arrays.hashCode(var2);
			result = var4 + var5;
			return result;
		}

		public final int getChunk() {
			return this.chunk;
		}

		public final int getTextureId() {
			return this.textureId;
		}

		public final DynamicTexture getDynamicTexture() {
			return this.dynamicTexture;
		}

		public final CharInfo[] getCharInfoArray() {
			return this.charInfoArray;
		}

		public GlyphChunk(int chunk, int textureId, DynamicTexture dynamicTexture,
				CharInfo[] charInfoArray) {
			super();
			this.chunk = chunk;
			this.textureId = textureId;
			this.dynamicTexture = dynamicTexture;
			this.charInfoArray = charInfoArray;
		}

		public final int component1() {
			return this.chunk;
		}

		public final int component2() {
			return this.textureId;
		}

		public final DynamicTexture component3() {
			return this.dynamicTexture;
		}

		public final CharInfo[] component4() {
			return this.charInfoArray;
		}

		public final GlyphChunk copy(int chunk, int textureId, DynamicTexture dynamicTexture,
				CharInfo[] charInfoArray) {
			return new GlyphChunk(chunk, textureId, dynamicTexture, charInfoArray);
		}

		public String toString() {
			return "GlyphChunk(chunk=" + this.chunk + ", textureId=" + this.textureId + ", dynamicTexture="
					+ this.dynamicTexture + ", charInfoArray=" + Arrays.toString(this.charInfoArray) + ")";
		}
	}
}
