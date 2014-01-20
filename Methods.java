package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.*;
import org.tribot.api.rs3.types.*;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.rs3.util.ThreadSettings.MODEL_CLICKING_METHOD;
import org.tribot.api.General;


public class Methods{

	/* These methods may not look great but the fail safe would be in the main class within the states.*/
	
	public static void eatFood(){
		if(Backpack.find(RS3LRCFishing.foodToEat).length >= 0){
		Backpack.find(RS3LRCFishing.foodToEat)[0].click("Eat");
		} else { General.println("food not found"); }
	}
	
		public static void clickOnStaircase()  {
			ScreenModel[] stairs = ScreenModels.findNearest(RS3LRCFishing.staircase); 
			if (stairs.length > 0)	{	
				if(stairs[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
					Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
					General.sleep(500, 700);
					stairs[0].click("Climb-down");
					General.sleep(8000, 10000);
			} else { RandomizedCameraMovements.randomCameraRotation(); General.sleep(1000, 1500); }	
			
		}
	}
		public static void clickOnRope()  {
			ScreenModel[] rope = ScreenModels.findNearest(RS3LRCFishing.rope); 
			if (rope.length > 0)	{	
				if(rope[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
					Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
					General.sleep(500, 700);
					rope[0].click("Climb");
					General.sleep(5000, 8000);
					Mouse.click(General.random(400, 500), General.random(260, 270), 1);
					General.sleep(8000, 10000);
			} else { RandomizedCameraMovements.randomCameraRotation(); General.sleep(1000, 1500); }	
			
		}
	}
	
		public static void bank() {
		ScreenModel[] banker = ScreenModels.findNearest(RS3LRCFishing.banker); 

			if (banker.length > 0)	{	
				if(banker[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
					Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
					General.sleep(500, 700);
					banker[0].click("Deposit");
					General.sleep(500, 700);
				} else { RandomizedCameraMovements.randomCameraRotation(); General.sleep(1500, 2000); }	
					if(Banking.isBankScreenOpen()){
						Banking.depositAllExcept(RS3LRCFishing.keepItems);
						General.sleep(2000, 3000);
						Banking.close();
					 }
			}else{
			    Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
                WebWalking.walkTo(RS3LRCFishing.Bank_Pos);
                while (Player.isMoving())
                    General.sleep(800, 1000);
            }
		
    }
		public static void bankInFal() {
		ScreenModel[] banker = ScreenModels.findNearest(RS3LRCFishing.banker); 

			if (banker.length > 0)	{	
				if(banker[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
					Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
					General.sleep(500, 700);
					banker[0].click("Bank");
					General.sleep(4000, 5000);
				} else { RandomizedCameraMovements.randomCameraRotation(); General.sleep(1500, 2000); }	
					if(Banking.isBankScreenOpen()){
						while(Backpack.find(RS3LRCFishing.foodToEat).length <= 0) {
							Banking.depositAllExcept(RS3LRCFishing.keepItems);
							General.sleep(2000, 3000);
							Banking.withdraw(5, RS3LRCFishing.foodToEat);
							General.sleep(2000, 3000);
						}
						Banking.close();
					 }
			}else{
			    Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
                WebWalking.walkTo(RS3LRCFishing.Fal_Bank_Pos);
                while (Player.isMoving())
                    General.sleep(800, 1000);
            }
		
    }
}