package me.nrules.module.modules.render;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.BlocksUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class XrayBypass extends Module {
    public XrayBypass() {
        super(XrayBypass.piska2() + XrayBypass.piska3() + XrayBypass.piska4() + XrayBypass.piska5(), Category.MISC);
        Main.settingsManager.rSetting(new Setting("XZPos", this, 20, 5, 80, true));
        Main.settingsManager.rSetting(new Setting("YPos", this, 6, 2, 27, true));
        Main.settingsManager.rSetting(new Setting("SpeedClick", this, 4, 1, 5, true));
        Main.settingsManager.rSetting(new Setting("Diamonds", this, true));
        Main.settingsManager.rSetting(new Setting("Emeralds", this, false));
        Main.settingsManager.rSetting(new Setting("Golds", this, false));
        Main.settingsManager.rSetting(new Setting("Irons", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Xra");
    }

    public static String piska3() {
        return piska2.replace("GHO", "yBy");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "pas");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "s");
    }

    ArrayList<BlockPos> ores = new ArrayList<>();
    ArrayList<BlockPos> toCheck = new ArrayList<>();
    public static int done;
    public static int all;

    @Override
    public void onEnable() {
        ores.clear();
        toCheck.clear();

        int radXZ = (int) Main.settingsManager.getSettingByName("XZPos").getValDouble();
        int radY = (int) Main.settingsManager.getSettingByName("YPos").getValDouble();
        ArrayList<BlockPos> blockPositions = getBlocks(radXZ, radY, radXZ);

        for (BlockPos pos : blockPositions) {
            IBlockState state = BlocksUtils.getState(pos);
            if (isCheckableOre(Block.getIdFromBlock(state.getBlock()))) {
                toCheck.add(pos);
            }
        }

        all = toCheck.size();
        done = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.renderGlobal.loadRenderers();
        super.onDisable();
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent e) {
        if (toCheck.size() > 0) {
            if (done % 200 == 0) {

            }

            double spd = Main.settingsManager.getSettingByName("SpeedClick").getValDouble();
            for (int i = 0; i < (int) spd; i++) {
                if (toCheck.size() < 1)
                    return;

                BlockPos pos = toCheck.remove(0);
                done++;
                mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
            }
        }
    }

    private boolean isCheckableOre(int id) {
        int check = 0;
        int check1 = 0;
        int check2 = 0;
        int check3 = 0;
        if (Main.settingsManager.getSettingByName("Diamonds").getValBoolean() && id != 0) {
            check = 56;
        }
        if (Main.settingsManager.getSettingByName("Golds").getValBoolean() && id != 0) {
            check1 = 14;
        }
        if (Main.settingsManager.getSettingByName("Irons").getValBoolean() && id != 0) {
            check2 = 15;
        }
        if (Main.settingsManager.getSettingByName("Emeralds").getValBoolean() && id != 0) {
            check3 = 129;
        }
        if (id == 0) {
            return false;
        }
        return id == check || id == check1 || id == check2 || id == check3;
    }

    private boolean isEnabledOre(int id) {
        int check = 0;
        int check1 = 0;
        int check2 = 0;
        int check3 = 0;
        if (Main.settingsManager.getSettingByName("Diamonds").getValBoolean() && id != 0) {
            check = 56;
        }
        if (Main.settingsManager.getSettingByName("Golds").getValBoolean() && id != 0) {
            check1 = 14;
        }
        if (Main.settingsManager.getSettingByName("Irons").getValBoolean() && id != 0) {
            check2 = 15;
        }
        if (Main.settingsManager.getSettingByName("Emeralds").getValBoolean() && id != 0) {
            check3 = 129;
        }
        if (id == 0) {
            return false;
        }
        return id == check || id == check1 || id == check2 || id == check3;
    }

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (packet instanceof SPacketBlockChange) {
            SPacketBlockChange p = (SPacketBlockChange) packet;

            if (isEnabledOre(Block.getIdFromBlock(p.getBlockState().getBlock()))) {
                ores.add(p.getBlockPosition());

            }
        } else if (packet instanceof SPacketMultiBlockChange) {
            SPacketMultiBlockChange p = (SPacketMultiBlockChange) packet;

            for (SPacketMultiBlockChange.BlockUpdateData dat : p.getChangedBlocks()) {
                if (isEnabledOre(Block.getIdFromBlock(dat.getBlockState().getBlock()))) {
                    ores.add(dat.getPos());

                }
                return true;
            }
        }
        return true;
    }

    private ArrayList<BlockPos> getBlocks(int x, int y, int z) {
        BlockPos min = new BlockPos(mc.player.posX - x, mc.player.posY - y, mc.player.posZ - z);
        BlockPos max = new BlockPos(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
        return BlocksUtils.getAllInBox(min, max);
    }

}
