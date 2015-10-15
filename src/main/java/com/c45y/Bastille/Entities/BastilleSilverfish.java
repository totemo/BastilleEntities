/*
 * The MIT License
 *
 * Copyright 2015 c45y.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.c45y.Bastille.Entities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

/**
 *
 * @author c45y
 */
public class BastilleSilverfish extends EntitySilverfish {
 
    private List<DamageSource> ignoreDamageTypes = new ArrayList<DamageSource>();
    
    public BastilleSilverfish(World world) {
        super(world);
    }

    public BastilleSilverfish(org.bukkit.World world) {
        super(((CraftWorld)world).getHandle());
    }
    
    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.ignoreDamageTypes.contains(damagesource)) {
            return false;
        }
        return super.damageEntity(damagesource, f);
    }
    
    public BastilleSilverfish ignoreDamageSource(DamageSource damagesource) {
        this.ignoreDamageTypes.add(damagesource);
        return this;
    }
    
    
    public BastilleSilverfish speed(float speed) {
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
        return this;
    }
    
    public BastilleSilverfish sprinting(boolean sprinting) {
        this.setSprinting(sprinting);
        return this;
    }

    public BastilleSilverfish health(float h) {
        this.setHealth(h);
        return this;
    }
    
    public BastilleSilverfish maxhealth(double max) {
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(max);
        return this;
    }
    
    public BastilleSilverfish damage(double damage) {
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(damage);
        return this;
    }
    
    public BastilleSilverfish emtpyGoals() {
        this.goalSelector = new PathfinderGoalSelector(world != null && world.methodProfiler != null ? world.methodProfiler : null);
        return this;
    }
    
    public BastilleSilverfish addGoal(int index, PathfinderGoal goal) {
        this.goalSelector.a(index, goal);
        return this;
    }
    
    public BastilleSilverfish emtpyTargets() {
        this.targetSelector = new PathfinderGoalSelector(world != null && world.methodProfiler != null ? world.methodProfiler : null);
        return this;
    }
    
    public BastilleSilverfish addTarget(int index, PathfinderGoal goal) {
        this.targetSelector.a(index, goal);
        return this;
    }
    
    private static Object getPrivateStatic(Class clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }

    public BastilleSilverfish spawn(Location loc) {
        this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.world.addEntity(this);
        return this;
    }
    
    public static void patch() {
        try {
            ((Map) getPrivateStatic(EntityTypes.class, "c")).put("Silverfish", BastilleSilverfish.class);
            ((Map) getPrivateStatic(EntityTypes.class, "d")).put(BastilleSilverfish.class, "Silverfish");
            ((Map) getPrivateStatic(EntityTypes.class, "e")).put(60, BastilleSilverfish.class);
            ((Map) getPrivateStatic(EntityTypes.class, "f")).put(BastilleSilverfish.class, 60);
            ((Map) getPrivateStatic(EntityTypes.class, "g")).put("Silverfish", 60);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
