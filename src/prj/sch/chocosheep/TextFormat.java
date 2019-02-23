package prj.sch.chocosheep;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class TextFormat {
    private String path;
    private float size;
    private Color color;

    private Font font;

    public TextFormat(String path, float size, Color color) {
        this.path = path;
        this.size = size;
        this.color = color;

        init();
    }

    private void init() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public int stringWidth(String string) {
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
        return (int) font.getStringBounds(string, fontRenderContext).getWidth();
    }

    public int stringHeight(String string) {
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
        return (int) font.getStringBounds(string, fontRenderContext).getHeight();
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return color;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
        init();
    }
}
