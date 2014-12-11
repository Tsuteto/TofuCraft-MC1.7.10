package tsuteto.tofu.asm.entry;

import cpw.mods.fml.relauncher.Side;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import tsuteto.tofu.asm.AsmPetitUtil;
import tsuteto.tofu.asm.ITransformerEntry;

import java.util.EnumSet;

public class TEntityDispenserBehaviorEmptyBucket implements ITransformerEntry, Opcodes
{

    @Override
    public String getTargetClass()
    {
        return "net.minecraft.init.Bootstrap$11";
    }

    @Override
    public String getTargetMethodDeobf()
    {
        return "dispenseStack";
    }

    @Override
    public String getTargetMethodObf()
    {
        return "func_82487_b";
    }

    @Override
    public String getTargetMethodDesc()
    {
        return "(Lnet/minecraft/dispenser/IBlockSource;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;";
    }

    @Override
    public void transform(MethodNode mnode, ClassNode cnode)
    {
        String iBlockSource = AsmPetitUtil.getActualClass("net/minecraft/dispenser/IBlockSource");
        String itemStack = AsmPetitUtil.getActualClass("net/minecraft/item/ItemStack");

        InsnList overrideList = new InsnList();

        overrideList.add(new VarInsnNode(ALOAD, 1));
        overrideList.add(new VarInsnNode(ALOAD, 2));

        overrideList.add(new MethodInsnNode(INVOKESTATIC,
                "tsuteto/tofu/eventhandler/DispenserBehaviorHook",
                "onDispenseEmptyBucket",
                "(L" + iBlockSource + ";L" + itemStack + ";)Z"));
        LabelNode l1 = new LabelNode();
        overrideList.add(new JumpInsnNode(IFEQ, l1));
        overrideList.add(new VarInsnNode(ALOAD, 2));
        overrideList.add(new InsnNode(ARETURN));
        overrideList.add(l1);

        mnode.instructions.insert(mnode.instructions.get(1), overrideList);

    }

    @Override
    public EnumSet<Side> getSide()
    {
        return EnumSet.of(Side.SERVER, Side.CLIENT);
    }
}
