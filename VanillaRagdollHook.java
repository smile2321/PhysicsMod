package net.diebuddies.physics.ragdoll;

import java.util.Iterator;
import java.util.List;

import net.diebuddies.physics.PhysicsEntity;
import net.diebuddies.physics.ragdoll.RagdollMapper.Counter;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.AllayModel;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.FrogModel;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.GoatModel;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.PufferfishBigModel;
import net.minecraft.client.model.PufferfishMidModel;
import net.minecraft.client.model.PufferfishSmallModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.RavagerModel;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.Deadmau5EarsLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Vindicator;

public class VanillaRagdollHook implements RagdollHook {

	@Override
	public void map(Ragdoll ragdoll, Entity entity, EntityModel model) {
		if (model instanceof PiglinModel) {
			AgeableListModel animal = (AgeableListModel) model;
			PiglinModel piglin = (PiglinModel) model;
			// basic ragdoll for humans/zombies
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter, true);
			}
			
			int headOffset = 0;
			int bodyOffset = counter.count;
			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int rightArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			int leftArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			int rightLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			int leftLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);

			ragdoll.addConnection(4, headOffset);
			ragdoll.addConnection(5, headOffset);
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightArmOffset, bodyOffset).stopCollision = true;
			ragdoll.addConnection(leftArmOffset, bodyOffset).stopCollision = true;
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);

			int hatOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			
			int leftPantsOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			int rightPantsOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			int leftSleeveOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			int rightSleeveOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			int jacketOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);

			if (piglin.hat.visible) {
				ragdoll.addConnection(hatOffset, headOffset, true, true);
			}
			
			if (piglin.leftPants.visible) {
				ragdoll.addConnection(leftPantsOffset, leftLegOffset, true, true);
			}

			if (piglin.rightPants.visible) {
				ragdoll.addConnection(rightPantsOffset, rightLegOffset, true, true);
			}

			if (piglin.leftSleeve.visible) {
				ragdoll.addConnection(leftSleeveOffset, leftArmOffset, true, true);
			}

			if (piglin.rightSleeve.visible) {
				ragdoll.addConnection(rightSleeveOffset, rightArmOffset, true, true);
			}

			if (piglin.jacket.visible) {
				ragdoll.addConnection(jacketOffset, bodyOffset, true, true);
			}

			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter, true);
			}
		} else if (model instanceof VexModel) {
			AgeableListModel animal = (AgeableListModel) model;
			// basic ragdoll for humans/zombies
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			}
			
			int headOffset = 0;
			int bodyOffset = counter.count;
			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int rightArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int hatOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightWingOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftWingOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightArmOffset, bodyOffset);
			ragdoll.addConnection(leftArmOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(rightWingOffset, bodyOffset);
			ragdoll.addConnection(leftWingOffset, bodyOffset);
		} else if (model instanceof HumanoidModel) {
			AgeableListModel animal = (AgeableListModel) model;
			// basic ragdoll for humans/zombies
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			}
			
			int headOffset = 0;
			int bodyOffset = counter.count;
			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int rightArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightArmOffset, bodyOffset);
			ragdoll.addConnection(leftArmOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);

			int hatOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			if (((HumanoidModel) model).hat.visible) {
				ragdoll.addConnection(hatOffset, headOffset, true, true);
			}
			
			if (model instanceof PlayerModel) {
				PlayerModel playerModel = (PlayerModel) model;
				
				try {
					int leftPantsOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					int rightPantsOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					int leftSleeveOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					int rightSleeveOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					int jacketOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					
					if (playerModel.leftPants.visible) {
						ragdoll.addConnection(leftPantsOffset, leftLegOffset, true, true);
					}

					if (playerModel.rightPants.visible) {
						ragdoll.addConnection(rightPantsOffset, rightLegOffset, true, true);
					}

					if (playerModel.leftSleeve.visible) {
						ragdoll.addConnection(leftSleeveOffset, leftArmOffset, true, true);
					}

					if (playerModel.rightSleeve.visible) {
						ragdoll.addConnection(rightSleeveOffset, rightArmOffset, true, true);
					}

					if (playerModel.jacket.visible) {
						ragdoll.addConnection(jacketOffset, bodyOffset, true, true);
					}
				} catch (Exception e) {}
			} else if (model instanceof ArmorStandModel) {
				try {
					int rightBodyStickOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					int leftBodyStickOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					int shoulderStickOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					int basePlateOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
					ragdoll.addConnection(rightBodyStickOffset, bodyOffset, true);
					ragdoll.addConnection(leftBodyStickOffset, bodyOffset, true);
					ragdoll.addConnection(shoulderStickOffset, bodyOffset, true);
				} catch (Exception e) {}
			} else if (model instanceof SkeletonModel) {
				boolean hasBow = false;
				
				// attach bow
				for (int i = 0; i < ragdoll.bodies.size(); i++) {
					PhysicsEntity b = ragdoll.bodies.get(i);
					
					if (b.feature instanceof ItemInHandLayer) {
						ragdoll.addConnection(i, rightArmOffset, true);
						hasBow = true;
					}
				}
				
				// this adds support for strays
				int count = RagdollMapper.countModelParts(entity, model);
				
				if (count + (hasBow ? 1 : 0) < ragdoll.bodies.size())
					ragdoll.addOverlayConnections(true, count * 2, hasBow ? 1 : 0);
			} else if (model instanceof DrownedModel) {
				int count = RagdollMapper.countModelParts(entity, model);

				if (ragdoll.bodies.size() > count * 2) {
					// with trident there gets something rendered before the overlay
					// so offset the indices by 1
					ragdoll.addOverlayConnections(true, 14, 5);
					
					// trident ragdoll
					int base = 7;
					int spike1 = 8;
					int spike2 = 9;
					int spike3 = 10;
					int base2 = 11;
					ragdoll.addConnection(base2, base, true);
					ragdoll.addConnection(spike1, base, true);
					ragdoll.addConnection(spike2, base, true);
					ragdoll.addConnection(spike3, base, true);
				} else {
					if (count < ragdoll.bodies.size())
						ragdoll.addOverlayConnections(true);
				}
			} else if (model instanceof EndermanModel) {
				ragdoll.addOverlayConnections(true);
			} else if (model instanceof ZombieVillagerModel) {
				int count = RagdollMapper.countModelParts(entity, model);

				if (count < ragdoll.bodies.size()) {
					// offsets for invisible head and hat
					int nbodyOffset = 0;
					int nrightArmOffset = 2;
					int nleftArmOffset = 3;
					int nrightLegOffset = 4;
					int nleftLegOffset = 5;
					int overlays = (int) Math.ceil(ragdoll.bodies.size() / (double) count);

					boolean hasHat = ragdoll.bodies.size() % count != 0;
					
					for (int i = 1; i < overlays; i++) {
						int offset = count * i + ((i != 1) ? (hasHat ? -4 : 0) : 0);
						
						if (i == 1 && hasHat) {
							ragdoll.addConnection(nbodyOffset + offset, bodyOffset, true, true);
							ragdoll.addConnection(nbodyOffset + 1 + offset, bodyOffset, true, true);
							ragdoll.addConnection(nleftArmOffset + offset, leftArmOffset, true, true);
							ragdoll.addConnection(nrightArmOffset + offset, rightArmOffset, true, true);
							ragdoll.addConnection(nrightLegOffset + offset, rightLegOffset, true, true);
							ragdoll.addConnection(nleftLegOffset + offset, leftLegOffset, true, true);
						} else {
							ragdoll.addConnection(headOffset + offset, headOffset, true, true);
							// nose
							ragdoll.addConnection(headOffset + 1 + offset, headOffset, true, true);
							
							ragdoll.addConnection(bodyOffset + offset, bodyOffset, true, true);
							ragdoll.addConnection(bodyOffset + 1 + offset, bodyOffset, true, true);
							
							ragdoll.addConnection(leftArmOffset + offset, leftArmOffset, true, true);
							ragdoll.addConnection(rightArmOffset + offset, rightArmOffset, true, true);
							ragdoll.addConnection(rightLegOffset + offset, rightLegOffset, true, true);
							ragdoll.addConnection(leftLegOffset + offset, leftLegOffset, true, true);
							
							ragdoll.addConnection(hatOffset + offset, headOffset, true, true);
							// hat rim
							ragdoll.addConnection(hatOffset + 1 + offset, headOffset, true, true);
						}
					}
				}
			}
			
			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
		} else if (model instanceof QuadrupedModel) {
			AgeableListModel animal = (AgeableListModel) model;
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();

			int headOffset = 0;
			
			if (model instanceof GoatModel) {
				counter.count = 6;
				headOffset = 3;
				ragdoll.addConnection(0, headOffset, true);
				ragdoll.addConnection(1, headOffset, true);
				ragdoll.addConnection(2, headOffset, true);
				ragdoll.addConnection(4, headOffset, true);
				ragdoll.addConnection(5, headOffset, true);
			} else {
				while (head.hasNext()) {
					RagdollMapper.getCuboids(ragdoll, head.next(), counter);
				}
			}
			
			int bodyOffset = counter.count;
			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int rightArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightArmOffset, bodyOffset);
			ragdoll.addConnection(leftArmOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			
			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
		} else if (model instanceof ChickenModel) { 
			AgeableListModel animal = (AgeableListModel) model;
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			int headOffset = 0;
			int beakOffset = RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			int wattleOffset = RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			}

			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int bodyOffset = counter.count;
			int rightLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightWingOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftWingOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			ragdoll.addConnection(beakOffset, headOffset, true);
			ragdoll.addConnection(wattleOffset, headOffset, true);
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			ragdoll.addConnection(rightWingOffset, bodyOffset);
			ragdoll.addConnection(leftWingOffset, bodyOffset);
			
			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
		} else if (model instanceof WolfModel) { 
			AgeableListModel animal = (AgeableListModel) model;
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			int headOffset = 0;
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			}

			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int bodyOffset = counter.count;
			int rightFrontLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftFrontLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightHindLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftHindLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int tailOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int neckOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			ragdoll.addConnection(headOffset, neckOffset);
			ragdoll.addConnection(rightFrontLegOffset, bodyOffset);
			ragdoll.addConnection(leftFrontLegOffset, bodyOffset);
			ragdoll.addConnection(leftHindLegOffset, bodyOffset);
			ragdoll.addConnection(rightHindLegOffset, bodyOffset);
			ragdoll.addConnection(tailOffset, bodyOffset);
			ragdoll.addConnection(neckOffset, bodyOffset);
			
			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
		} else if (model instanceof SquidModel) {
			ragdoll.addConnection(0, 4);
			ragdoll.addConnection(1, 4);
			ragdoll.addConnection(2, 4);
			ragdoll.addConnection(3, 4);
			ragdoll.addConnection(5, 4);
			ragdoll.addConnection(6, 4);
			ragdoll.addConnection(7, 4);
			ragdoll.addConnection(8, 4);
			
			RagdollMapper.getCuboids(ragdoll, ((HierarchicalModel) model).root(), new Counter());
		} else if (model instanceof CreeperModel) {
			int headOffset = 0;
			int rightArmOffset = 1;
			int rightLegOffset = 2;
			int leftLegOffset = 3;
			int bodyOffset = 4;
			int leftArmOffset = 5;

			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightArmOffset, bodyOffset);
			ragdoll.addConnection(leftArmOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			
			RagdollMapper.getCuboids(ragdoll, ((HierarchicalModel) model).root(), new Counter());
		} else if (model instanceof DolphinModel) {
			int bodyOffset = 0;
			int headOffset = 1;
			int noseOffset = 2;
			int leftFinOffset = 3;
			int rightFinOffset = 4;
			int tailOffset = 5;
			int tailFinOffset = 6;
			int backFinOffset = 7;
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(noseOffset, headOffset, true);
			ragdoll.addConnection(leftFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightFinOffset, bodyOffset, true);
			ragdoll.addConnection(backFinOffset, bodyOffset, true);
			ragdoll.addConnection(tailOffset, bodyOffset);
			ragdoll.addConnection(tailFinOffset, tailOffset);
			
			RagdollMapper.getCuboids(ragdoll, ((HierarchicalModel) model).root(), new Counter());
		} else if (model instanceof GhastModel) {
			ragdoll.addConnection(0, 5);
			ragdoll.addConnection(1, 5);
			ragdoll.addConnection(2, 5);
			ragdoll.addConnection(3, 5);
			ragdoll.addConnection(4, 5);
			ragdoll.addConnection(6, 5);
			ragdoll.addConnection(7, 5);
			ragdoll.addConnection(8, 5);
			ragdoll.addConnection(9, 5);
		} else if (model instanceof IronGolemModel) {
			int headOffset = 0;
			int noseOffset = 1;
			int rightArmOffset = 2;
			int leftLegOffset = 3;
			int leftArmOffset = 4;
			int rightLegOffset = 5;
			int bodyOffset = 6;
			int lowerBodyOffset = 7;
			
			ragdoll.addConnection(headOffset, lowerBodyOffset);
			ragdoll.addConnection(noseOffset, headOffset, true);
			ragdoll.addConnection(leftArmOffset, lowerBodyOffset);
			ragdoll.addConnection(rightArmOffset, lowerBodyOffset);
			ragdoll.addConnection(leftLegOffset, lowerBodyOffset);
			ragdoll.addConnection(rightLegOffset, lowerBodyOffset);
			ragdoll.addConnection(bodyOffset, lowerBodyOffset, true);
			
//			ragdoll.addConnection(headOffset, bodyOffset);
//			ragdoll.addConnection(noseOffset, headOffset, true);
//			ragdoll.addConnection(leftArmOffset, bodyOffset);
//			ragdoll.addConnection(rightArmOffset, bodyOffset);
//			ragdoll.addConnection(leftLegOffset, lowerBodyOffset);
//			ragdoll.addConnection(rightLegOffset, lowerBodyOffset);
//			ragdoll.addConnection(lowerBodyOffset, bodyOffset);
		} else if (model instanceof SpiderModel) {
			int headOffset = 0;
			int rightFrontLegOffset = 1;
			int rightHindLegOffset = 2;
			int leftMiddleFrontLegOffset = 3;
			int body0Offset = 4;
			int body1Offset = 5;
			int leftHindLegOffset = 6;
			int rightMiddleHindLegOffset = 7;
			int rightMiddleFrontLegOffset = 8;
			int leftMiddleHindLegOffset = 9;
			int leftFrontLegOffset = 10;
			
			ragdoll.addConnection(headOffset, body0Offset);
			ragdoll.addConnection(body1Offset, body0Offset);
			ragdoll.addConnection(rightFrontLegOffset, body0Offset).stopCollision = true;
			ragdoll.addConnection(rightHindLegOffset, body0Offset).stopCollision = true;
			ragdoll.addConnection(leftMiddleFrontLegOffset, body0Offset).stopCollision = true;
			ragdoll.addConnection(leftHindLegOffset, body0Offset).stopCollision = true;
			ragdoll.addConnection(rightMiddleHindLegOffset, body0Offset).stopCollision = true;
			ragdoll.addConnection(rightMiddleFrontLegOffset, body0Offset).stopCollision = true;
			ragdoll.addConnection(leftMiddleHindLegOffset, body0Offset).stopCollision = true;
			ragdoll.addConnection(leftFrontLegOffset, body0Offset).stopCollision = true;
		} else if (model instanceof SnowGolemModel) {
			int headOffset = 0;
			int rightArmOffset = 1;
			int upperBodyOffset = 2;
			int leftArmOffset = 3;
			int lowerBodyOffset = 4;
			int pumpkinOffset = 5;

			ragdoll.addConnection(headOffset, upperBodyOffset);
			ragdoll.addConnection(rightArmOffset, upperBodyOffset);
			ragdoll.addConnection(leftArmOffset, upperBodyOffset);
			ragdoll.addConnection(upperBodyOffset, lowerBodyOffset);
			
			if (ragdoll.bodies.size() == 6) ragdoll.addConnection(pumpkinOffset, headOffset, true);
		} else if (model instanceof GuardianModel) {
			int headOffset = 0;
			int spike0 = 21;
			int spike1 = 5;
			int spike2 = 6;
			int spike3 = 7;
			int spike4 = 8;
			int spike5 = 9;
			int spike6 = 10;
			int spike7 = 16;
			int spike8 = 17;
			int spike9 = 18;
			int spike10 = 19;
			int spike11 = 20;
			int eye = 11;
			int tail0 = 12;
			int tail1 = 13;
			int tail2 = 14;
			int tail3 = 15;

			// stretches the tail too much
//			ragdoll.addConnection(tail0, headOffset);
//			ragdoll.addConnection(tail1, tail0);
//			ragdoll.addConnection(tail2, tail1);
//			ragdoll.addConnection(tail3, tail2);
			ragdoll.addConnection(tail0, headOffset, true);
			ragdoll.addConnection(tail1, headOffset, true);
			ragdoll.addConnection(tail2, headOffset, true);
			ragdoll.addConnection(tail3, headOffset, true);
			
			ragdoll.addConnection(spike0, headOffset, true);
			ragdoll.addConnection(spike1, headOffset, true);
			ragdoll.addConnection(spike2, headOffset, true);
			ragdoll.addConnection(spike3, headOffset, true);
			ragdoll.addConnection(spike4, headOffset, true);
			ragdoll.addConnection(spike5, headOffset, true);
			ragdoll.addConnection(spike6, headOffset, true);
			ragdoll.addConnection(spike7, headOffset, true);
			ragdoll.addConnection(spike8, headOffset, true);
			ragdoll.addConnection(spike9, headOffset, true);
			ragdoll.addConnection(spike10, headOffset, true);
			ragdoll.addConnection(spike11, headOffset, true);
			ragdoll.addConnection(headOffset + 1, headOffset, true);
			ragdoll.addConnection(headOffset + 2, headOffset, true);
			ragdoll.addConnection(headOffset + 3, headOffset, true);
			ragdoll.addConnection(headOffset + 4, headOffset, true);
			ragdoll.addConnection(eye, headOffset, true);
		} else if (model instanceof WitchModel) {
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: head: 0
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: nose: 1
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: mole: 2
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: hat: 3
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: hat_rim: 4
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: hat2: 5
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: hat3: 6
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: hat4: 7
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: left_leg: 8
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: right_leg: 9
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: arms: 10
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: body: 13
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: jacket: 14
//			[12:14:20] [Render thread/INFO] (Minecraft) [STDOUT]: class net.minecraft.client.render.entity.model.WitchEntityModel
			int headOffset = 0;
			int noseOffset = 1;
			int moleOffset = 2;
			int hatOffset = 3;
			int hatRimOffset = 4;
			int hat2Offset = 5;
			int hat3Offset = 6;
			int hat4Offset = 7;
			int leftLegOffset = 8;
			int rightLegOffset = 9;
			int armsOffset = 10;
			int bodyOffset = 13;
			int jacketOffset = 14;

			ragdoll.addConnection(moleOffset, headOffset, true);
			ragdoll.addConnection(hat2Offset, headOffset, true);
			ragdoll.addConnection(hat3Offset, headOffset, true);
			ragdoll.addConnection(hat4Offset, headOffset, true);
			ragdoll.addConnection(hatOffset, headOffset, true);
			ragdoll.addConnection(hatRimOffset, headOffset, true);
			ragdoll.addConnection(noseOffset, headOffset, true);
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(jacketOffset, bodyOffset, true);
			ragdoll.addConnection(armsOffset, bodyOffset, true);
			ragdoll.addConnection(armsOffset + 1, bodyOffset, true);
			ragdoll.addConnection(armsOffset + 2, bodyOffset, true);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			ragdoll.addConnection(armsOffset, bodyOffset);
		} else if (model instanceof VillagerModel) {
			int headOffset = 0;
			int noseOffset = 1;
			int hatOffset = 2;
			int hatRimOffset = 3;
			int leftLegOffset = 4;
			int rightLegOffset = 5;
			int armsOffset = 6;
			int bodyOffset = 9;
			int jacketOffset = 10;

			ragdoll.addConnection(hatOffset, headOffset, true);
			ragdoll.addConnection(hatRimOffset, headOffset, true);
			ragdoll.addConnection(noseOffset, headOffset, true);
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(jacketOffset, bodyOffset, true);
			ragdoll.addConnection(armsOffset, bodyOffset, true);
			ragdoll.addConnection(armsOffset + 1, bodyOffset, true);
			ragdoll.addConnection(armsOffset + 2, bodyOffset, true);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			ragdoll.addConnection(armsOffset, bodyOffset);
			
			int count = RagdollMapper.countModelParts(entity, model);
			
			if (count < ragdoll.bodies.size()) {
				// offsets for invisible head and hat
				int nleftLegOffset = 0;
				int nrightLegOffset = 1;
				int narmsOffset = 2;
				int nbodyOffset = 5;
				int njacketOffset = 6;
				int overlays = (int) Math.ceil(ragdoll.bodies.size() / (double) count);

				boolean hasHat = ragdoll.bodies.size() % count != 0;
				
				for (int i = 1; i < overlays; i++) {
					int offset = count * i + ((i != 1) ? (hasHat ? -4 : 0) : 0);
					
					if (i == 1 && hasHat) {
						ragdoll.addConnection(nbodyOffset + offset, bodyOffset, true, true);
						ragdoll.addConnection(njacketOffset + offset, bodyOffset, true, true);
						ragdoll.addConnection(narmsOffset + offset, bodyOffset, true, true);
						ragdoll.addConnection(narmsOffset + 1 + offset, bodyOffset, true, true);
						ragdoll.addConnection(narmsOffset + 2 + offset, bodyOffset, true, true);
						ragdoll.addConnection(nrightLegOffset + offset, rightLegOffset, true, true);
						ragdoll.addConnection(nleftLegOffset + offset, leftLegOffset, true, true);
					} else {
						ragdoll.addConnection(hatOffset + offset, headOffset, true, true);
						ragdoll.addConnection(hatRimOffset + offset, headOffset, true, true);
						ragdoll.addConnection(noseOffset + offset, headOffset, true, true);
						ragdoll.addConnection(headOffset + offset, headOffset, true, true);
						
						ragdoll.addConnection(bodyOffset + offset, bodyOffset, true, true);
						ragdoll.addConnection(jacketOffset + offset, bodyOffset, true, true);
						ragdoll.addConnection(armsOffset + offset, bodyOffset, true, true);
						ragdoll.addConnection(armsOffset + 1 + offset, bodyOffset, true, true);
						ragdoll.addConnection(armsOffset + 2 + offset, bodyOffset, true, true);
						ragdoll.addConnection(rightLegOffset + offset, rightLegOffset, true, true);
						ragdoll.addConnection(leftLegOffset + offset, leftLegOffset, true, true);
					}
				}
			}
		} else if (model instanceof IllagerModel) {
			if (entity instanceof Illusioner) {
				int headOffset = 0;
				int noseOffset = 1;
				int hatOffset = 2;
				int leftLegOffset = 3;
				int rightLegOffset = 4;
				int arms1Offset = 5;
				int arms2Offset = 6;
				int leftShoulderOffset = 7;
				int body1Offset = 8;
				int body2Offset = 9;

				ragdoll.addConnection(hatOffset, headOffset, true);
				ragdoll.addConnection(noseOffset, headOffset, true);
				ragdoll.addConnection(headOffset, body1Offset);
				ragdoll.addConnection(leftLegOffset, body1Offset);
				ragdoll.addConnection(arms1Offset, body1Offset, true);
				ragdoll.addConnection(rightLegOffset, body1Offset);
				ragdoll.addConnection(arms2Offset, body1Offset, true);
				ragdoll.addConnection(leftShoulderOffset, body1Offset, true);
				ragdoll.addConnection(body2Offset, body1Offset, true);
			} else if (entity instanceof Evoker || entity instanceof Vindicator) {
				int headOffset = 0;
				int noseOffset = 1;
				int leftLegOffset = 2;
				int rightLegOffset = 3;
				int arms1Offset = 4;
				int arms2Offset = 5;
				int leftShoulderOffset = 6;
				int body1Offset = 7;
				int body2Offset = 8;
	
				ragdoll.addConnection(noseOffset, headOffset, true);
				ragdoll.addConnection(headOffset, body1Offset);
				ragdoll.addConnection(leftLegOffset, body1Offset);
				ragdoll.addConnection(arms1Offset, body1Offset, true);
				ragdoll.addConnection(rightLegOffset, body1Offset);
				ragdoll.addConnection(arms2Offset, body1Offset, true);
				ragdoll.addConnection(leftShoulderOffset, body1Offset, true);
				ragdoll.addConnection(body2Offset, body1Offset, true);
			} else {
				int headOffset = 0;
				int noseOffset = 1;
				int leftLegOffset = 2;
				int rightArmOffset = 3;
				int rightLegOffset = 4;
				int leftArmOffset = 5;
				int bodyOffset = 6;
	
				ragdoll.addConnection(noseOffset, headOffset, true);
				ragdoll.addConnection(headOffset, bodyOffset);
				ragdoll.addConnection(leftLegOffset, bodyOffset);
				ragdoll.addConnection(rightArmOffset, bodyOffset);
				ragdoll.addConnection(rightLegOffset, bodyOffset);
				ragdoll.addConnection(leftArmOffset, bodyOffset);
				ragdoll.addConnection(bodyOffset + 1, bodyOffset, true);
			}
			
			if (RagdollMapper.countModelParts(entity, model) < ragdoll.bodies.size())
				ragdoll.addOverlayConnections(true);
		} else if (model instanceof StriderModel) {
			int leftLegOffset = 0;
			int rightLegOffset = 1;
			int bodyOffset = 2;

			ragdoll.addConnection(leftLegOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			ragdoll.addConnection(3, bodyOffset, true);
			ragdoll.addConnection(4, bodyOffset, true);
			ragdoll.addConnection(5, bodyOffset, true);
			ragdoll.addConnection(6, bodyOffset, true);
			ragdoll.addConnection(7, bodyOffset, true);
			ragdoll.addConnection(8, bodyOffset, true);

			ragdoll.bodies.get(bodyOffset).backfaceCulling = false;
		} else if (model instanceof RavagerModel) {
			int rightFrontLegOffset = 0;
			int rightHindLegOffset = 1;
			int leftHindLegOffset = 2;
			int neckOffset = 3;
			int headOffset = 4;
			int headChildOffset = 5;
			int rightHornOffset = 6;
			int mouthOffset = 7;
			int leftHornOffset = 8;
			int bodyOffset = 9;
			int bodyChildOffset = 10;
			int leftFrontLegOffset = 11;

			ragdoll.addConnection(bodyChildOffset, bodyOffset, true);
			ragdoll.addConnection(rightFrontLegOffset, bodyOffset);
			ragdoll.addConnection(leftFrontLegOffset, bodyOffset);
			ragdoll.addConnection(rightHindLegOffset, bodyOffset);
			ragdoll.addConnection(leftHindLegOffset, bodyOffset);
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(neckOffset, headOffset, true);
			ragdoll.addConnection(mouthOffset, headOffset, true);
			ragdoll.addConnection(rightHornOffset, headOffset, true);
			ragdoll.addConnection(leftHornOffset, headOffset, true);
			ragdoll.addConnection(headChildOffset, headOffset, true);
		} else if (model instanceof BatModel) {
			int headOffset = 0;
			int rightEarOffset = 1;
			int leftEarOffset = 2;
			int bodyOffset = 3;
			int bodyChildOffset = 4;
			int rightWingOffset = 5;
			int rightWingTipOffset = 6;
			int leftWingOffset = 7;
			int leftWingTipOffset = 8;

			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(bodyChildOffset, bodyOffset, true);
			ragdoll.addConnection(rightEarOffset, headOffset, true);
			ragdoll.addConnection(leftEarOffset, headOffset, true);
			
			ragdoll.addConnection(rightWingTipOffset, rightWingOffset, true);
			ragdoll.addConnection(rightWingOffset, bodyOffset);
			ragdoll.addConnection(leftWingTipOffset, leftWingOffset, true);
			ragdoll.addConnection(leftWingOffset, bodyOffset);
		} else if (model instanceof BeeModel) {
			int frontLegsOffset = 0;
			int rightWingOffset = 1;
			int leftWingOffset = 2;
			int middleLegsOffset = 3;
			int leftAntennaOffset = 4;
			int rightAntennaOffset = 5;
			int stingerOffset = 6;
			int bodyOffset = 7;
			int backLegsOffset = 8;

			ragdoll.addConnection(frontLegsOffset, bodyOffset, true);
			ragdoll.addConnection(rightWingOffset, bodyOffset, true);
			ragdoll.addConnection(leftWingOffset, bodyOffset, true);
			ragdoll.addConnection(middleLegsOffset, bodyOffset, true);
			ragdoll.addConnection(rightAntennaOffset, bodyOffset, true);
			ragdoll.addConnection(stingerOffset, bodyOffset, true);
			ragdoll.addConnection(leftAntennaOffset, bodyOffset, true);
			ragdoll.addConnection(backLegsOffset, bodyOffset, true);
		} else if (model instanceof RabbitModel) {
			int headOffset;
			int noseOffset;
			int rightFrontLegOffset;
			int rightHindFootOffset;
			int tailOffset;
			int leftHaunchOffset;
			int rightHaunchOffset;
			int bodyOffset;
			int rightEarOffset;
			int leftFrontLegOffset;
			int leftHindFootOffset;
			int leftEarOffset;
			
			if (model.young) {
				headOffset = 0;
				leftEarOffset = 1;
				rightEarOffset = 2;
				noseOffset = 3;
				leftHindFootOffset = 4;
				rightHindFootOffset = 5;
				leftHaunchOffset = 6;
				rightHaunchOffset = 7;
				bodyOffset = 8;
				leftFrontLegOffset = 9;
				rightFrontLegOffset = 10;
				tailOffset = 11;
			} else {
				leftHindFootOffset = 0;
				rightHindFootOffset = 1;
				leftHaunchOffset = 2;
				rightHaunchOffset = 3;
				bodyOffset = 4;
				leftFrontLegOffset = 5;
				rightFrontLegOffset = 6;
				headOffset = 7;
				rightEarOffset = 8;
				leftEarOffset = 9;
				tailOffset = 10;
				noseOffset = 11;
			}

			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(leftEarOffset, headOffset, true);
			ragdoll.addConnection(rightEarOffset, headOffset, true);
			ragdoll.addConnection(noseOffset, headOffset, true);
			ragdoll.addConnection(rightFrontLegOffset, bodyOffset);
			ragdoll.addConnection(leftFrontLegOffset, bodyOffset);
			ragdoll.addConnection(tailOffset, bodyOffset, true);
			ragdoll.addConnection(rightHaunchOffset, bodyOffset);
			ragdoll.addConnection(leftHaunchOffset, bodyOffset);
			ragdoll.addConnection(rightHindFootOffset, rightHaunchOffset, true);
			ragdoll.addConnection(leftHindFootOffset, leftHaunchOffset, true);
		} else if (model instanceof WitherBossModel) {
			int shouldersOffset = 0;
			int ribcageOffset = 1;
			int tailOffset = 5;
			int leftHeadOffset = 6;
			int rightHeadOffset = 7;
			int centerHeadOffset = 8;

			ragdoll.addConnection(tailOffset, ribcageOffset);
			ragdoll.addConnection(shouldersOffset, ribcageOffset, true);
			ragdoll.addConnection(leftHeadOffset, ribcageOffset);
			ragdoll.addConnection(rightHeadOffset, ribcageOffset);
			ragdoll.addConnection(centerHeadOffset, ribcageOffset);
			
			RagdollMapper.getCuboids(ragdoll, ((HierarchicalModel) model).root(), new Counter());
		} else if (model instanceof OcelotModel) {
			AgeableListModel animal = (AgeableListModel) model;
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			}
			
			int headOffset = 0;
			int bodyOffset = counter.count;
			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int rightArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int upperTailOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int lowerTailOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightArmOffset, bodyOffset);
			ragdoll.addConnection(leftArmOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			ragdoll.addConnection(upperTailOffset, bodyOffset);
			ragdoll.addConnection(lowerTailOffset, upperTailOffset);
			
			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
		} else if (model instanceof FoxModel) {
			int headOffset = 0;
			int noseOffset = 1;
			int rightEarOffset = 2;
			int leftEarOffset = 3;
			int bodyOffset = 4;
			int tailOffset = 5;
			int leg1Offset = 6;
			int leg2Offset = 7;
			int leg3Offset = 8;
			int leg4Offset = 9;

			ragdoll.addConnection(noseOffset, headOffset, true);
			ragdoll.addConnection(rightEarOffset, headOffset, true);
			ragdoll.addConnection(leftEarOffset, headOffset, true);
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(tailOffset, bodyOffset);
			ragdoll.addConnection(leg1Offset, bodyOffset);
			ragdoll.addConnection(leg2Offset, bodyOffset);
			ragdoll.addConnection(leg3Offset, bodyOffset);
			ragdoll.addConnection(leg4Offset, bodyOffset);
		} else if (model instanceof SilverfishModel) {
			int segment2Offset = 0;
			int segment1Offset = 1;
			int segment0Offset = 2;
			// big one
			int layer0Offset = 3;
			// small one
			int layer1Offset = 4;
			// medium one
			int layer2Offset = 5;
			int segment6Offset = 6;
			int segment5Offset = 7;
			int segment4Offset = 8;
			int segment3Offset = 9;

			ragdoll.addConnection(segment0Offset, segment1Offset);
			ragdoll.addConnection(segment1Offset, segment2Offset);
			ragdoll.addConnection(segment2Offset, segment3Offset);
			ragdoll.addConnection(segment3Offset, segment4Offset);
			ragdoll.addConnection(segment4Offset, segment5Offset);
			ragdoll.addConnection(segment5Offset, segment6Offset);

			ragdoll.addConnection(layer0Offset, segment2Offset, true);
			ragdoll.addConnection(layer1Offset, segment4Offset, true);
			ragdoll.addConnection(layer2Offset, segment1Offset, true);
		} else if (model instanceof EndermiteModel) {
			int segment2Offset = 0;
			int segment1Offset = 1;
			int segment0Offset = 2;
			int segment3Offset = 3;

			ragdoll.addConnection(segment0Offset, segment1Offset);
			ragdoll.addConnection(segment1Offset, segment2Offset);
			ragdoll.addConnection(segment2Offset, segment3Offset);
		} else if (model instanceof ParrotModel) {
			int headOffset = 0;
			int beak1Offset = 1;
			int beak2Offset = 2;
			int featherOffset = 3;
			int head2Offset = 4;
			int leftLegOffset = 5;
			int rightWingOffset = 6;
			int rightLegOffset = 7;
			int tailOffset = 8;
			int leftWingOffset = 9;
			int bodyOffset = 10;

			ragdoll.addConnection(beak1Offset, headOffset, true);
			ragdoll.addConnection(beak2Offset, headOffset, true);
			ragdoll.addConnection(featherOffset, headOffset, true);
			ragdoll.addConnection(head2Offset, headOffset, true);
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(rightWingOffset, bodyOffset);
			ragdoll.addConnection(leftWingOffset, bodyOffset);
			ragdoll.addConnection(tailOffset, bodyOffset);
		} else if (model instanceof HorseModel) {
			AgeableListModel animal = (AgeableListModel) model;
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			}
			
			boolean hasSaddle = ((ModelPart) animal.headParts().iterator().next()).getChild("left_saddle_mouth").visible;
			int neckOffset = 0;
			int bodyOffset = counter.count;
			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int rightHindLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftHindLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightFrontLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftFrontLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			if (model.young) {
				rightHindLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
				leftHindLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
				rightFrontLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
				leftFrontLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
			
			ragdoll.addConnection(neckOffset, bodyOffset);
			ragdoll.addConnection(rightHindLegOffset, bodyOffset);
			ragdoll.addConnection(leftHindLegOffset, bodyOffset);
			ragdoll.addConnection(rightFrontLegOffset, bodyOffset);
			ragdoll.addConnection(leftFrontLegOffset, bodyOffset);
			
			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
			
			int leftEarOffset = 2;
			int rightEarOffset = 3;
			int headOffset = 1;
			
			int mane = 4;
			int upperMouth = 5;
			int tail = 7;
			
			if (hasSaddle) {
				int leftSaddleMouthOffset = 4;
				int mouthSaddleWrapOffset = 5;
				// right saddle line and left saddle line are never used
				int rightSaddleLineOffset = 6;
				int rightSaddleMouthOffset = 7;
				int leftSaddleLineOffset = 8;
				int saddleOffset = 11;
				int headSaddleOffset = 9;
				upperMouth = 8;
				mane = 6;
				tail = 12;
				
				if (model instanceof ChestedHorseModel) {
					boolean hasChests = ((AbstractChestedHorse) entity).hasChest();
					
					if (hasChests) {
						saddleOffset = 12;
						tail = 13;
					}
					
					// just ignore chest positions to make them pop off the model
//					int leftChest = 14;
//					int rightChest = 11;
				}

				ragdoll.addConnection(leftSaddleMouthOffset, neckOffset, true);
				ragdoll.addConnection(mouthSaddleWrapOffset, neckOffset, true);
				ragdoll.addConnection(rightSaddleMouthOffset, neckOffset, true);
				ragdoll.addConnection(headSaddleOffset, neckOffset, true);
				ragdoll.addConnection(saddleOffset, bodyOffset, true);
			} else {
				if (model instanceof ChestedHorseModel) {
					boolean hasChests = ((AbstractChestedHorse) entity).hasChest();
					
					if (hasChests) {
						tail = 8;
					}
				}
			}
			
			ragdoll.addConnection(headOffset, neckOffset, true);
			ragdoll.addConnection(leftEarOffset, neckOffset, true);
			ragdoll.addConnection(rightEarOffset, neckOffset, true);
			
			ragdoll.addConnection(tail, bodyOffset);
			ragdoll.addConnection(mane, neckOffset, true);
			ragdoll.addConnection(upperMouth, neckOffset, true);
			
			if (RagdollMapper.countModelParts(entity, model) < ragdoll.bodies.size())
				ragdoll.addOverlayConnections(true);
		} else if (model instanceof LlamaModel) {
			int headOffset = 0;
			int neckOffset = 1;
			int earLeftOffset = 2;
			int earRightOffset = 3;
			int bodyOffset = 4;
			int rightFrontLegOffset = 5;
			int rightHindLegOffset = 6;
			int leftHindLegOffset = 7;
			int leftFrontLegOffset = 8;
			
			// don't use chest positions to let them pop off the model
			int rightChestOffset = 9;
			int leftChestOffset = 10;
			
			ragdoll.addConnection(headOffset, neckOffset, true);
			ragdoll.addConnection(earLeftOffset, neckOffset, true);
			ragdoll.addConnection(earRightOffset, neckOffset, true);
			
			ragdoll.addConnection(neckOffset, bodyOffset);
			ragdoll.addConnection(rightFrontLegOffset, bodyOffset);
			ragdoll.addConnection(rightHindLegOffset, bodyOffset);
			ragdoll.addConnection(leftHindLegOffset, bodyOffset);
			ragdoll.addConnection(leftFrontLegOffset, bodyOffset);
			
			if (RagdollMapper.countModelParts(entity, model) < ragdoll.bodies.size())
				ragdoll.addOverlayConnections(true);
		} else if (model instanceof HoglinModel) {
			AgeableListModel animal = (AgeableListModel) model;
			Iterator<ModelPart> head = animal.headParts().iterator();
			Counter counter = new Counter();
			
			while (head.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, head.next(), counter);
			}
			
			int headOffset = 0;
			int bodyOffset = counter.count;
			Iterator<ModelPart> body = animal.bodyParts().iterator();
			int rightArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftArmOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int rightLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			int leftLegOffset = RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightArmOffset, bodyOffset);
			ragdoll.addConnection(leftArmOffset, bodyOffset);
			ragdoll.addConnection(rightLegOffset, bodyOffset);
			ragdoll.addConnection(leftLegOffset, bodyOffset);
			
			int rightHornOffset = 1;
			int leftHornOffset = 2;
			int rightEarOffset = 3;
			int leftEarOffset = 4;
			int maneOffset = 6;
			
			ragdoll.addConnection(rightHornOffset, headOffset, true);
			ragdoll.addConnection(leftHornOffset, headOffset, true);
			ragdoll.addConnection(rightEarOffset, headOffset, true);
			ragdoll.addConnection(leftEarOffset, headOffset, true);
			ragdoll.addConnection(maneOffset, bodyOffset, true);
			
			while (body.hasNext()) {
				RagdollMapper.getCuboids(ragdoll, body.next(), counter);
			}
		} else if (model instanceof SalmonModel) {
			int headOffset = 0;
			int leftFinOffset = 1;
			int bodyBackOffset = 2;
			int topBackFinOffset = 3;
			int backFinOffset = 4;
			int rightFinOffset = 5;
			int bodyFrontOffset = 6;
			int topFrontFinOffset = 7;
			
			ragdoll.addConnection(headOffset, bodyFrontOffset);
			ragdoll.addConnection(bodyBackOffset, bodyFrontOffset);
			
			ragdoll.addConnection(topFrontFinOffset, bodyFrontOffset, true);
			ragdoll.addConnection(leftFinOffset, bodyFrontOffset, true);
			ragdoll.addConnection(rightFinOffset, bodyFrontOffset, true);
			ragdoll.addConnection(topBackFinOffset, bodyBackOffset, true);
			ragdoll.addConnection(backFinOffset, bodyBackOffset, true);
		} else if (model instanceof AxolotlModel) {
			int bodyOffset = 0;
			int bodyGillsOffset = 1;
			int headOffset = 2;
			int topGillsOffset = 3;
			int leftGillsOffset = 4;
			int rightGillsOffset = 5;
			int rightFrontLegOffset = 6;
			int rightHindLegffset = 7;
			int tailOffset = 8;
			int leftHindLegOffset = 9;
			int leftFrontLegOffset = 10;
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(bodyGillsOffset, bodyOffset, true);
			ragdoll.addConnection(topGillsOffset, bodyOffset, true);
			ragdoll.addConnection(leftGillsOffset, bodyOffset, true);
			ragdoll.addConnection(rightGillsOffset, bodyOffset, true);
			
			ragdoll.addConnection(rightFrontLegOffset, bodyOffset, true);
			ragdoll.addConnection(rightHindLegffset, bodyOffset, true);
			ragdoll.addConnection(leftHindLegOffset, bodyOffset, true);
			ragdoll.addConnection(leftFrontLegOffset, bodyOffset, true);
			ragdoll.addConnection(tailOffset, bodyOffset, true);
		} else if (model instanceof PhantomModel) {
			int bodyOffset = 0;
			int headOffset = 1;
			int rightWingBaseOffset = 2;
			int rightWingTipOffset = 3;
			int tailBaseOffset = 4;
			int tailTipOffset = 5;
			int leftWingBaseOffset = 6;
			int leftWingTipOffset = 7;
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(rightWingBaseOffset, bodyOffset);
			ragdoll.addConnection(tailBaseOffset, bodyOffset);
			ragdoll.addConnection(leftWingBaseOffset, bodyOffset);
			
			ragdoll.addConnection(rightWingTipOffset, rightWingBaseOffset, true);
			ragdoll.addConnection(tailTipOffset, tailBaseOffset, true);
			ragdoll.addConnection(leftWingTipOffset, leftWingBaseOffset, true);
		} else if (model instanceof CodModel) {
			int headOffset = 0;
			int noseOffset = 1;
			int leftFinOffset = 2;
			int topFinOffset = 3;
			int rightFinOffset = 4;
			int bodyOffset = 5;
			int tailFinOffset = 6;
			
			ragdoll.addConnection(headOffset, bodyOffset);
			ragdoll.addConnection(noseOffset, headOffset, true);
			ragdoll.addConnection(tailFinOffset, bodyOffset, true);
			ragdoll.addConnection(leftFinOffset, bodyOffset, true);
			ragdoll.addConnection(topFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightFinOffset, bodyOffset, true);
		} else if (model instanceof PufferfishSmallModel) {
			int rightEyeOffset = 0;
			int leftFinOffset = 1;
			int rightFinOffset = 2;
			int leftEyeOffset = 3;
			int bodyOffset = 4;
			int backFinOffset = 5;
			
			ragdoll.addConnection(rightEyeOffset, bodyOffset, true);
			ragdoll.addConnection(leftEyeOffset, bodyOffset, true);
			ragdoll.addConnection(leftFinOffset, bodyOffset, true);
			ragdoll.addConnection(backFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightFinOffset, bodyOffset, true);
		} else if (model instanceof PufferfishMidModel) {
			int leftBlueFinOffset = 0;
			int topBackFinOffset = 1;
			int leftBackFinOffset = 2;
			int leftFrontFinOffset = 3;
			int bottomFrontFinOffset = 4;
			int rightFrontFinOffset = 5;
			int rightBackFinOffset = 6;
			int bodyOffset = 7;
			int topFrontFinOffset = 8;
			int bottomBackFinOffset = 9;
			int rightBlueFinOffset = 10;
			
			ragdoll.addConnection(leftBlueFinOffset, bodyOffset, true);
			ragdoll.addConnection(topBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(leftBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(leftFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(bottomFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(topFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(bottomBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightBlueFinOffset, bodyOffset, true);
		} else if (model instanceof PufferfishBigModel) {
			int leftBlueFinOffset = 0;
			int topBackFinOffset = 1;
			int leftBackFinOffset = 2;
			int leftFrontFinOffset = 3;
			int bottomFrontFinOffset = 4;
			int bodyOffset = 5;
			int rightFrontFinOffset = 6;
			int rightBackFinOffset = 7;
			int topFrontFinOffset = 8;
			int bottomBackFinOffset = 9;
			int rightBlueFinOffset = 10;
			int rightBlueBackFinOffset = 11;
			int topBlueFrontFinOffset = 12;
			
			ragdoll.addConnection(leftBlueFinOffset, bodyOffset, true);
			ragdoll.addConnection(topBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(leftBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(leftFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(bottomFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(topFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(bottomBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightBlueBackFinOffset, bodyOffset, true);
			ragdoll.addConnection(topBlueFrontFinOffset, bodyOffset, true);
			ragdoll.addConnection(rightBlueFinOffset, bodyOffset, true);
		} else if (model instanceof TropicalFishModelB) {
			int leftFinOffset = 0;
			int topFinOffset = 1;
			int tailOffset = 2;
			int rightFinOffset = 3;
			int bodyOffset = 4;
			int bottomFinOffset = 5;
			
			ragdoll.addConnection(leftFinOffset, bodyOffset, true, true);
			ragdoll.addConnection(topFinOffset, bodyOffset, true, true);
			ragdoll.addConnection(bottomFinOffset, bodyOffset, true, true);
			ragdoll.addConnection(tailOffset, bodyOffset, true, true);
			ragdoll.addConnection(rightFinOffset, bodyOffset, true, true);
			
			ragdoll.addOverlayConnections(true);
		} else if (model instanceof TropicalFishModelA) {
			int leftFinOffset = 0;
			int topFinOffset = 1;
			int tailOffset = 2;
			int rightFinOffset = 3;
			int bodyOffset = 4;
			
			ragdoll.addConnection(leftFinOffset, bodyOffset, true, true);
			ragdoll.addConnection(topFinOffset, bodyOffset, true, true);
			ragdoll.addConnection(tailOffset, bodyOffset, true, true);
			ragdoll.addConnection(rightFinOffset, bodyOffset, true, true);

			ragdoll.addOverlayConnections(true);
		} else if (model instanceof TadpoleModel) {
			int bodyOffset = 1;
			int tailOffset = 2;
			
			ragdoll.addConnection(tailOffset, bodyOffset, true, true);
		} else if (model instanceof FrogModel<?> frog) {
			int leftLeg = 0;
			int leftFoot = 1;
			int rightLeg = 2;
			int rightFoot = 3;
			int body = 4;
			int body2 = 5;
			int head = 6;
			int head2 = 7;
			int rightEye = 8;
			int leftEye = 9;
			int rightArm = 10;
			int rightHand = 11;
			int tongue = 12;
			int leftArm = 13;
			int leftHand = 14;
			
			ragdoll.addConnection(leftFoot, leftLeg, true, true);
			ragdoll.addConnection(rightFoot, rightLeg, true, true);
			ragdoll.addConnection(leftLeg, body);
			ragdoll.addConnection(rightLeg, body);
			ragdoll.addConnection(head2, body, true);
			ragdoll.addConnection(body2, body, true);
			ragdoll.addConnection(rightEye, body, true);
			ragdoll.addConnection(leftEye, body, true);
			ragdoll.addConnection(rightArm, body);
			ragdoll.addConnection(leftArm, body);
			ragdoll.addConnection(rightHand, rightArm, true, true);
			ragdoll.addConnection(leftHand, leftArm, true, true);
			ragdoll.addConnection(tongue, body);
			ragdoll.addConnection(head, body, true, true);
			
			if (ragdoll.bodies.size() > 15) {
				int croakingBody = 15;
				ragdoll.addConnection(croakingBody, body, true);
			}
			
			RagdollMapper.getCuboids(ragdoll, frog.root(), new Counter());
		} else if (model instanceof AllayModel allay) {
			int head = 0;
			int body = 1;
			int body2 = 2;
			int rightArm = 3;
			int leftArm = 4;
			int rightWing = 5;
			int leftWing = 6;
			
			ragdoll.addConnection(head, body);
			ragdoll.addConnection(rightArm, body);
			ragdoll.addConnection(leftArm, body);
			ragdoll.addConnection(body2, body);
			ragdoll.addConnection(rightWing, body, true, true);
			ragdoll.addConnection(leftWing, body, true, true);
			
			RagdollMapper.getCuboids(ragdoll, allay.root(), new Counter());
		} else if (model instanceof WardenModel<?> warden) {
			int bone = 0;
			int leftLeg = 0;
			int rightLeg = 1;
			int body = 2;
			int head = 3;
			int rightTendril = 4;
			int leftTendril = 5;
			int rightArm = 6;
			int leftArm = 7;
			int leftRibcage = 8;
			int rightRibcage = 9;
			
			ragdoll.addConnection(rightTendril, head, true, true);
			ragdoll.addConnection(leftTendril, head, true, true);
			ragdoll.addConnection(head, body);
			ragdoll.addConnection(rightArm, body);
			ragdoll.addConnection(leftArm, body);
			ragdoll.addConnection(rightLeg, body);
			ragdoll.addConnection(leftLeg, body);
			ragdoll.addConnection(leftRibcage, body, true, true);
			ragdoll.addConnection(rightRibcage, body, true, true);

			int leftLegBL = 10;
			int rightLegBL = 11;
			int headBL = 12;
			int rightArmBL = 13;
			int leftArmBL = 14;

			int leftLegP1 = 15;
			int rightLegP1 = 16;
			int bodyP1 = 17;
			int headP1 = 18;
			int rightArmP1 = 19;
			int leftArmP1 = 20;

			int leftLegP2 = 21;
			int rightLegP2 = 22;
			int bodyP2 = 23;
			int headP2 = 24;
			int rightArmP2 = 25;
			int leftArmP2 = 26;

			int rightTendrilT = 27;
			int leftTendrilT = 28;
			
			int bodyH = 29;

			ragdoll.addConnection(headBL, head, true, true);
			ragdoll.addConnection(leftArmBL, leftArm, true, true);
			ragdoll.addConnection(rightArmBL, rightArm, true, true);
			ragdoll.addConnection(leftLegBL, leftLeg, true, true);
			ragdoll.addConnection(rightLegBL, rightLeg, true, true);
			
			ragdoll.addConnection(bodyP1, body, true, true);
			ragdoll.addConnection(headP1, head, true, true);
			ragdoll.addConnection(leftArmP1, leftArm, true, true);
			ragdoll.addConnection(rightArmP1, rightArm, true, true);
			ragdoll.addConnection(leftLegP1, leftLeg, true, true);
			ragdoll.addConnection(rightLegP1, rightLeg, true, true);
			
			ragdoll.addConnection(bodyP2, body, true, true);
			ragdoll.addConnection(headP2, head, true, true);
			ragdoll.addConnection(leftArmP2, leftArm, true, true);
			ragdoll.addConnection(rightArmP2, rightArm, true, true);
			ragdoll.addConnection(leftLegP2, leftLeg, true, true);
			ragdoll.addConnection(rightLegP2, rightLeg, true, true);
			
			ragdoll.addConnection(leftTendrilT, leftTendril, true, true);
			ragdoll.addConnection(rightTendrilT, rightTendril, true, true);
			ragdoll.addConnection(bodyH, body, true, true);
		} else if (entity instanceof EnderDragon) {
			// ender dragon model is null because ender dragon has no living entity renderer and thus
			// we have no access to it's model
			for (int i = 0; i < 5; i++) {
				// do neck
				ragdoll.addConnection(i * 2 + 1, i * 2, true);
				
				if (i != 0) ragdoll.addConnection((i - 1) * 2, i * 2);
			}
			
			int offset = 10;

			int head0 = 0 + offset;
			int head1 = 1 + offset;
			int head2 = 2 + offset;
			int head3 = 3 + offset;
			int head4 = 4 + offset;
			int head5 = 5 + offset;
			int jaw = 6 + offset;
			
			ragdoll.addConnection(jaw, head0, true);
			ragdoll.addConnection(head1, head0, true);
			ragdoll.addConnection(head2, head0, true);
			ragdoll.addConnection(head3, head0, true);
			ragdoll.addConnection(head4, head0, true);
			ragdoll.addConnection(head5, head0, true);
			
			ragdoll.addConnection(head0, 8);

			int body0 = 7 + offset;
			int body1 = 8 + offset;
			int body2 = 9 + offset;
			int body3 = 10 + offset;
			
			int leftWing = 11 + offset;
			int leftWingTexture = 12 + offset;
			int leftWingTip = 13 + offset;
			int leftWingTipTexture = 14 + offset;
			int leftFrontLeg = 15 + offset;
			int leftFrontLegTip = 16 + offset;
			int leftFrontFoot = 17 + offset;
			int leftHindLeg = 18 + offset;
			int leftHindLegTip = 19 + offset;
			int leftHindFoot = 20 + offset;
			
			int rightWing = 21 + offset;
			int rightWingTexture = 22 + offset;
			int rightWingTip = 23 + offset;
			int rightWingTipTexture = 24 + offset;
			int rightFrontLeg = 25 + offset;
			int rightFrontLegTip = 26 + offset;
			int rightFrontFoot = 27 + offset;
			int rightHindLeg = 28 + offset;
			int rightHindLegTip = 29 + offset;
			int rightHindFoot = 30 + offset;
			
			ragdoll.addConnection(0, body0);
			
			ragdoll.addConnection(body1, body0, true);
			ragdoll.addConnection(body2, body0, true);
			ragdoll.addConnection(body3, body0, true);
			
			ragdoll.addConnection(rightWing, body0);
			ragdoll.addConnection(leftWing, body0);
			
			ragdoll.addConnection(rightWingTip, rightWing);
			ragdoll.addConnection(leftWingTip, leftWing);
			
			ragdoll.addConnection(rightWingTexture, rightWing, true);
			ragdoll.addConnection(leftWingTexture, leftWing, true);
			
			ragdoll.addConnection(rightWingTipTexture, rightWingTip, true);
			ragdoll.addConnection(leftWingTipTexture, leftWingTip, true);
			
			ragdoll.addConnection(rightFrontLeg, body0);
			ragdoll.addConnection(rightHindLeg, body0);
			ragdoll.addConnection(leftFrontLeg, body0);
			ragdoll.addConnection(leftHindLeg, body0);
			
			ragdoll.addConnection(rightFrontLegTip, rightFrontLeg);
			ragdoll.addConnection(rightHindLegTip, rightHindLeg);
			ragdoll.addConnection(leftFrontLegTip, leftFrontLeg);
			ragdoll.addConnection(leftHindLegTip, leftHindLeg);
			
			ragdoll.addConnection(rightFrontFoot, rightFrontLegTip);
			ragdoll.addConnection(rightHindFoot, rightHindLegTip);
			ragdoll.addConnection(leftFrontFoot, leftFrontLegTip);
			ragdoll.addConnection(leftHindFoot, leftHindLegTip);
			
			offset = 41;

			for (int i = 0; i < 12; i++) {
				// do tail
				ragdoll.addConnection(i * 2 + 1 + offset, i * 2 + offset, true);
				
				if (i != 0) ragdoll.addConnection((i - 1) * 2 + offset, i * 2 + offset);
			}
			
			ragdoll.addConnection(offset, body0);
		}
	}

	@Override
	public void filterCuboidsFromEntities(List<PhysicsEntity> blockifiedEntity, Entity entity, EntityModel model) {
		// when ragdolls are enabled you don't want to filter overlays
		// like the dots on horses
		boolean ragdollsEnabled = RagdollMapper.areRagdollsEnabled(entity);
		
		if (model instanceof IronGolemModel) {
			while (blockifiedEntity.size() > 8) {
				blockifiedEntity.remove(blockifiedEntity.size() - 1);
			}
		} else if (model instanceof SpiderModel) {
			while (blockifiedEntity.size() > 11) {
				blockifiedEntity.remove(blockifiedEntity.size() - 1);
			}
		} else if (model instanceof StriderModel) {
			while (blockifiedEntity.size() > 9) {
				blockifiedEntity.remove(blockifiedEntity.size() - 1);
			}
		} else if (model instanceof WitherBossModel) {
			while (blockifiedEntity.size() > 9) {
				blockifiedEntity.remove(blockifiedEntity.size() - 1);
			}
		} else if (model instanceof SheepModel) {
			while (blockifiedEntity.size() > 6) {
				blockifiedEntity.remove(blockifiedEntity.size() - 1);
			}
		} else if (entity instanceof EnderDragon) {
			// 31 for all the model parts and the neck and tail get rendered additionally (17 * 2)
			while (blockifiedEntity.size() > 31 + 17 * 2) {
				blockifiedEntity.remove(blockifiedEntity.size() - 1);
			}
		} else if (model instanceof TropicalFishModelB || model instanceof TropicalFishModelA || model instanceof SkeletonModel || model instanceof HorseModel ||
				model instanceof LlamaModel || model instanceof DrownedModel || model instanceof IllagerModel || model instanceof VillagerModel || 
				model instanceof EndermanModel) {
			int count = RagdollMapper.countModelParts(entity, model);
			
			if (!ragdollsEnabled) {
				while (blockifiedEntity.size() > count) {
					blockifiedEntity.remove(blockifiedEntity.size() - 1);
				}
			}
		} else if (model instanceof PhantomModel || model instanceof ZombieModel || model instanceof PiglinModel) {
			int count = RagdollMapper.countModelParts(entity, model);
			
			while (blockifiedEntity.size() > count) {
				blockifiedEntity.remove(blockifiedEntity.size() - 1);
			}
		}
		
		// remove unnecessary features
		Iterator<PhysicsEntity> it = blockifiedEntity.iterator();
		
		while (it.hasNext()) {
			PhysicsEntity physicsEntity = it.next();
			
			if (physicsEntity.feature instanceof HumanoidArmorLayer || physicsEntity.feature instanceof CustomHeadLayer || 
					physicsEntity.feature instanceof ElytraLayer || physicsEntity.feature instanceof ItemInHandLayer || 
					physicsEntity.feature instanceof ArrowLayer || physicsEntity.feature instanceof Deadmau5EarsLayer || 
					physicsEntity.feature instanceof CapeLayer || physicsEntity.feature instanceof SpinAttackEffectLayer || 
					physicsEntity.feature instanceof ParrotOnShoulderLayer || physicsEntity.feature instanceof BeeStingerLayer) {
				if (!(model instanceof SkeletonModel && physicsEntity.feature instanceof ItemInHandLayer)) {
					it.remove();
				}
			}
		}
	}

}
