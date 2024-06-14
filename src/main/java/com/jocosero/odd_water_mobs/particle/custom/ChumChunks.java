package com.jocosero.odd_water_mobs.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChumChunks extends SimpleAnimatedParticle {

    protected ChumChunks(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet sprites) {
        super(world, x, y, z, sprites, 1.0F);
        this.xd = motionX * 0.03;
        this.yd = motionY * 1;
        this.zd = motionX * 0.03;
        this.quadSize *= 1.2F + this.random.nextFloat() * 0.8F;
        this.lifetime = 50;
        this.gravity = 3F;
        this.hasPhysics = true;

        this.setSprite(this.sprites.get(0, this.lifetime));
    }


    public void tick() {
        super.tick();
        int totalFrames = 13;
        int frameDuration = this.lifetime / totalFrames;
        int currentFrame = Math.min(this.age / frameDuration, totalFrames - 1);
        this.setSprite(this.sprites.get(currentFrame, totalFrames));
        if (this.age < this.lifetime) {
            this.yd = 0;
        }
        if (this.onGround) {
            this.setSprite(this.sprites.get(12, 12));
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
            ChumChunks p = new ChumChunks(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return p;
        }
    }
}
