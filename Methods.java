package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.*;
import org.tribot.api.rs3.types.*;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.rs3.util.ThreadSettings.MODEL_CLICKING_METHOD;
import org.tribot.api.General;
import org.tribot.api.DynamicClicking;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;


public class Methods{

	/* These methods may not look great but the fail safe would be in the main class within the states.*/
	private static EGWPosition PlayerPOS = EGW.getPosition();
	private static final int[] warningSign = {54603, 43039, 15054};
	private static Texture[] warningSignTexture = Textures.find(warningSign);
	
	public static void eatFood(){
		if(Backpack.find(RS3LRCFishing.foodToEat).length >= 0){
			Backpack.find(RS3LRCFishing.foodToEat)[0].click("Eat");
		}General.println("food not found");
	}
	
		public static void clickOnStaircase()  {			
			ScreenModel[] stairs = ScreenModels.findNearest(RS3LRCFishing.staircase); 
			ScreenModel stairs1 = RandomizedClicking.getClosestModel(RS3LRCFishing.staircase);
			if (stairs.length > 0)	{	
				if(stairs[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
					Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
					General.sleep(500, 700);
					if(RandomizedClicking.clickScreenModel(stairs1, "Climb-down", -2, 2, -3, 3)){
						Timing.waitCondition(new Condition() {
							public boolean active() {
								return PlayerPOS.distance(RS3LRCFishing.DM_Pos) > 10;
							}
						}, General.random(1000, 1500));
					} 
				}
			RandomizedCameraMovements.randomCameraRotation(); General.sleep(1000, 1500); 
		}
	}
	public static void clickOnRope()  {
			ScreenModel[] rope = ScreenModels.findNearest(RS3LRCFishing.rope); 
			ScreenModel rope1 = RandomizedClicking.getClosestModel(RS3LRCFishing.rope);
			if (rope.length > 0)	{	
				if(rope[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
					Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
					General.sleep(500, 700);
					if(RandomizedClicking.clickScreenModel(rope1, "Climb", -1, 2, -1, 3)){
						Timing.waitCondition(new Condition() {
							public boolean active() {
								return warningSignTexture.length > 0;
							}
						}, General.random(500, 1000));
						Mouse.click(General.random(400, 500), General.random(260, 270), 1);
						Timing.waitCondition(new Condition() {
							public boolean active() {
								return PlayerPOS.distance(RS3LRCFishing.Bank_Pos) < 10;
							}
						}, General.random(500, 1000));
				}RandomizedCameraMovements.randomCameraRotation(); General.sleep(1000, 1500);
			
			}RandomizedCameraMovements.randomCameraRotation(); General.sleep(1000, 1500);
		}
	}
	
	public static void bank() {
		ScreenModel[] banker = ScreenModels.findNearest(RS3LRCFishing.banker); 
		ScreenModel banker1 = RandomizedClicking.getClosestModel(RS3LRCFishing.banker);
		if (banker.length > 0)	{	
			if(banker[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
				Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
				General.sleep(500, 700);
				if(RandomizedClicking.clickScreenModel(banker1, "Deposit", -4, 8, -4, 6)){
					Timing.waitCondition(new Condition() {
						public boolean active() {
							return Banking.isBankScreenOpen();
						}
					}, General.random(500, 1000));
				}
			}RandomizedCameraMovements.randomCameraRotation(); General.sleep(1500, 2000);	
			if(Banking.isBankScreenOpen()){
				Banking.depositAllExcept(RS3LRCFishing.keepItems);
				General.sleep(2000, 3000);
				Banking.close();
			}
		}
		Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
        WebWalking.walkTo(RS3LRCFishing.Bank_Pos);
        while (Player.isMoving())
			General.sleep(800, 1000);		
    }
	
	public static void bankInFal() {
		ScreenModel[] banker = ScreenModels.findNearest(RS3LRCFishing.banker); 
		ScreenModel banker1 = RandomizedClicking.getClosestModel(RS3LRCFishing.banker);
		if (banker.length > 0)	{	
			if(banker[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
				Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
				General.sleep(500, 700);
				if(RandomizedClicking.clickScreenModel(banker1, "Bank", -4, 8, -4, 6)){
					Timing.waitCondition(new Condition() {
						public boolean active() {
							return Banking.isBankScreenOpen();
						}
					}, General.random(500, 1000));
				}
			} RandomizedCameraMovements.randomCameraRotation(); General.sleep(1500, 2000); 
				if(Banking.isBankScreenOpen()){
					while(Backpack.find(RS3LRCFishing.foodToEat).length <= 0) {
						Banking.depositAllExcept(RS3LRCFishing.keepItems);
						Timing.waitCondition(new Condition() {
							public boolean active() {
								return Backpack.find(RS3LRCFishing.rawFish).length > 0;
							}
						}, General.random(500, 1000));
						Banking.withdraw(5, RS3LRCFishing.foodToEat);
						Timing.waitCondition(new Condition() {
							public boolean active() {
								return Backpack.find(RS3LRCFishing.foodToEat).length >= 5;
							}
						}, General.random(500, 1000));
					}
					Banking.close();
				}
		}
		Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
        WebWalking.walkTo(RS3LRCFishing.Fal_Bank_Pos);
        while (Player.isMoving())
			General.sleep(800, 1000);		
    }
}