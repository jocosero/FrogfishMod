package com.jocosero.odd_water_mobs.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HydrothermalSmoke extends SimpleAnimatedParticle {

    protected HydrothermalSmoke(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet sprites) {
        super(world, x, y, z, sprites, -0.5f);
        this.xd = (float) motionX;
        this.yd = motionY + (double) (this.random.nextFloat() / 5.0F);
        this.zd = (float) motionZ;
        this.quadSize *= 0.5F + this.random.nextFloat() * 0.5F;
        this.lifetime = 15 + this.random.nextInt(20);
        this.gravity = -0.5f;
        this.scale(3.5F);
        this.setSprite(this.sprites.get(0, this.lifetime));
    }


    public void tick() {
        super.tick();
        int totalFrames = 12;
        int frameDuration = this.lifetime / totalFrames;
        int currentFrame = Math.min(this.age / frameDuration, totalFrames - 1);
        this.setSprite(this.sprites.get(currentFrame, totalFrames));
        if (this.age < this.lifetime / 2) {
            this.yd = 0;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            HydrothermalSmoke p = new HydrothermalSmoke(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return p;
        }
    }
}
