package me.nrules.clickgui.settings;

import me.nrules.util.BlocksUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class BlockListSetting extends Setting1 {
    private final ArrayList<String> blockNames = new ArrayList<>();

    private final String[] defaultNames;

    public BlockListSetting(String description, Block... blocks) {
        super(description);

        Arrays.stream(blocks).parallel().map(BlocksUtils::getName).distinct().sorted().forEachOrdered(this.blockNames::add);
        this.defaultNames = this.blockNames.toArray(new String[0]);
    }

    public BlockListSetting(Block... blocks) {
        this(null, blocks);
    }

    public List<String> getBlockNames() {
        return Collections.unmodifiableList(this.blockNames);
    }
}