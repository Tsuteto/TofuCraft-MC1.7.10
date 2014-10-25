package tsuteto.tofu.asm;

import cpw.mods.fml.relauncher.Side;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.EnumSet;

public interface ITransformerEntry
{
    String getTargetClass();
    String getTargetMethodDeobf();
    String getTargetMethodObf();
    String getTargetMethodDesc();
    void transform(MethodNode mnode, ClassNode cnode);
    EnumSet<Side> getSide();
}
