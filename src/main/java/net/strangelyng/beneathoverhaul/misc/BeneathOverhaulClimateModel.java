package net.strangelyng.beneathoverhaul.misc;

import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.climate.ClimateModelType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec2;

public enum BeneathOverhaulClimateModel implements ClimateModel {
    INSTANCE;

    float lerp(float x, float y, float a) {
        return (1 - a) * x + y * a;
    }

    @Override
    public ClimateModelType<?> type() {
        return BeneathOverhaulClimateModels.BENEATH.get();
    }

    @Override
    public float getInstantTemperature(LevelReader level, BlockPos pos, long calendarTicks, int daysInMonth) {
        return lerp(100, 25, (float) pos.getY() / 128);
    }

    @Override
    public float getAverageTemperature(LevelReader levelReader, BlockPos blockPos) {
        return lerp(100, 25, (float) blockPos.getY() / 128);
    }

    @Override
    public float getAverageRainfall(LevelReader levelReader, BlockPos blockPos) {
        return lerp(0, 500, (float) blockPos.getY() / 128);
    }

    @Override
    public float getFog(LevelReader level, BlockPos pos) {
        return 0;
    }

    @Override
    public Vec2 getWind(Level level, BlockPos pos, long calendarTicks, int daysInMonth) {
        return new Vec2(0, 0);
    }
}
