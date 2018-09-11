package com.blocklings.models;

import com.blocklings.entities.EntityBlockling;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlockling extends ModelBase {
	ModelRenderer body;
	ModelRenderer rightLeg;
	ModelRenderer leftLeg;
	ModelRenderer rightArm;
	ModelRenderer leftArm;
	ModelRenderer rightEye;
	ModelRenderer leftEye;
	float bodyBaseX = 0.0872665F;
	float rightLegBaseX = -0.0872665F;
	float leftLegBaseX = -0.0872665F;
	float rightArmBaseX = 0.7853982F - this.bodyBaseX;
	float leftArmBaseX = 0.7853982F - this.bodyBaseX;
	float rightEyeBaseX = 0.0F;
	float leftEyeBaseX = 0.0F;

	private int[] attackAnimation = { 0, 18, 39, 62, 85, 70, 58, 47, 37, 28, 20, 14, 8, 3, 0 };

	public ModelBlockling() {
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.body = new ModelRenderer(this, 20, 0);
		this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
		this.body.setRotationPoint(0.0F, 13.0F, 0.0F);
		this.body.setTextureSize(128, 64);
		this.body.showModel = true;
		setRotation(this.body, 0.0872665F, 0.0F, 0.0F);
		this.rightLeg = new ModelRenderer(this, 24, 32);
		this.rightLeg.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7);
		this.rightLeg.setRotationPoint(-4.0F, 4.0F, 0.5F);
		this.rightLeg.setTextureSize(128, 64);
		this.rightLeg.showModel = true;
		setRotation(this.rightLeg, -0.0872665F, 0.0F, 0.0F);
		this.leftLeg = new ModelRenderer(this, 52, 32);
		this.leftLeg.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7);
		this.leftLeg.setRotationPoint(4.0F, 4.0F, 0.5F);
		this.leftLeg.setTextureSize(128, 64);
		this.leftLeg.showModel = true;
		setRotation(this.leftLeg, -0.0872665F, 0.0F, 0.0F);
		this.rightArm = new ModelRenderer(this, 84, 18);
		this.rightArm.addBox(-3.0F, 0.0F, -7.0F, 3, 7, 7);
		this.rightArm.setRotationPoint(-8.0F, -3.0F, 0.0F);
		this.rightArm.setTextureSize(128, 64);
		this.rightArm.showModel = true;
		setRotation(this.rightArm, 0.6981317F, 0.0F, 0.0F);
		this.leftArm = new ModelRenderer(this, 0, 18);
		this.leftArm.addBox(0.0F, 0.0F, -7.0F, 3, 7, 7);
		this.leftArm.setRotationPoint(8.0F, -3.0F, 0.0F);
		this.leftArm.setTextureSize(128, 64);
		this.leftArm.showModel = true;
		setRotation(this.leftArm, 0.6981317F, 0.0F, 0.0F);
		this.rightEye = new ModelRenderer(this, 30, 12);
		this.rightEye.addBox(-1.0F, -1.5F, -0.5F, 2, 3, 1);
		this.rightEye.setRotationPoint(-2.0F, 3.0F, -8.0F);
		this.rightEye.setTextureSize(128, 64);
		this.rightEye.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		this.leftEye = new ModelRenderer(this, 68, 12);
		this.leftEye.addBox(-1.0F, -1.5F, -0.5F, 2, 3, 1);
		this.leftEye.setRotationPoint(2.0F, 3.0F, -8.0F);
		this.leftEye.setTextureSize(128, 64);
		this.leftEye.showModel = true;
		setRotation(this.leftEye, 0.0F, 0.0F, 0.0F);

		this.body.addChild(this.rightArm);
		this.body.addChild(this.leftArm);
		this.body.addChild(this.rightLeg);
		this.body.addChild(this.leftLeg);
		this.body.addChild(this.rightEye);
		this.body.addChild(this.leftEye);
	}

	public void render(Entity entity, float time, float speed, float age, float yaw, float pitch, float scale) {
		super.render(entity, time, speed, age, yaw, pitch, scale);

		this.body.render(scale);
	}

	public void setRotationAngles(float time, float speed, float age, float yaw, float pitch, float scale,
			Entity entity) {
		super.setRotationAngles(time, speed, age, yaw, pitch, scale, entity);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	private float degToRad(float degrees) {
		return (float) (degrees * 3.141592653589793D / 180.0D);
	}
}