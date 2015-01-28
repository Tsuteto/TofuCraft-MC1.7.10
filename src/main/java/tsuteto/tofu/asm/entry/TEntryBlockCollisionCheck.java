package tsuteto.tofu.asm.entry;

import cpw.mods.fml.relauncher.Side;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import tsuteto.tofu.asm.ITransformerEntry;

import java.util.EnumSet;

public class TEntryBlockCollisionCheck implements ITransformerEntry, Opcodes
{
    @Override
    public String getTargetClass()
    {
        return "net.minecraft.world.World";
    }

    @Override
    public String getTargetMethodDeobf()
    {
        return "checkBlockCollision";
    }

    @Override
    public String getTargetMethodObf()
    {
        return "func_72829_c";
    }

    @Override
    public String getTargetMethodDesc()
    {
        return "(Lnet/minecraft/util/AxisAlignedBB;)Z";
    }

    @Override
    public void transform(MethodNode mnode, ClassNode cnode)
    {
        FieldNode fnode = cnode.fields.get(0);

        InsnList overrideList = new InsnList();

        overrideList.add(new VarInsnNode(ALOAD, 0));
        overrideList.add(new VarInsnNode(ALOAD, 1));
        overrideList.add(new MethodInsnNode(INVOKESTATIC,
                "tsuteto/tofu/eventhandler/BlockCollisionCheckHook",
                "onBlockCollisionCheck",
                "(L" + cnode.name + ";Lnet/minecraft/util/AxisAlignedBB;)Z",
                false));
        LabelNode l1 = new LabelNode();
        overrideList.add(new JumpInsnNode(IFEQ, l1));
        overrideList.add(new InsnNode(ICONST_1));
        overrideList.add(new InsnNode(IRETURN));
        overrideList.add(l1);

        mnode.instructions.insert(mnode.instructions.get(1), overrideList);

    }

    @Override
    public EnumSet<Side> getSide()
    {
        return EnumSet.of(Side.CLIENT, Side.SERVER);
    }
}
