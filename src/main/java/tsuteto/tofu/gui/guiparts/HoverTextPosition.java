package tsuteto.tofu.gui.guiparts;

import java.awt.*;

public interface HoverTextPosition
{
    public static final HoverTextPosition MC_STANDARD = new HoverTextPosition()
    {
        @Override
        public void applyOffset(Point pos, int fw, int fh)
        {
            pos.translate(12, -12);
        }
    };
    public static final HoverTextPosition UPPER_CENTER = new HoverTextPosition()
    {
        @Override
        public void applyOffset(Point pos, int fw, int fh)
        {
            pos.translate(-fw / 2, -fh - 8);
        }
    };
    public static final HoverTextPosition LOWER_CENTER = new HoverTextPosition()
    {
        @Override
        public void applyOffset(Point pos, int fw, int fh)
        {
            pos.translate(-fw / 2, 12);
        }
    };

    public void applyOffset(Point pos, int fw, int fh);
}
