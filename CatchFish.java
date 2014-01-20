package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.Player;
import org.tribot.api.rs3.types.EGWPosition;
import org.tribot.api.rs3.EGW;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.rs3.util.ThreadSettings.MODEL_CLICKING_METHOD;
import org.tribot.api.General;
import org.tribot.api.rs3.ScreenModels;
import org.tribot.api.rs3.types.ScreenModel;


public class CatchFish{
	
	public static void catchFish()  {
		ScreenModel[] fish = ScreenModels.findNearest(RS3LRCFishing.fishingSpot);  
		ScreenModel fish1 = RandomizedClicking.getClosestModel(RS3LRCFishing.fishingSpot);
		EGWPosition PlayerPOS = EGW.getPosition();
			
		if (fish.length > 0)	{	
			General.println("Found Fishing Spot");
			if(fish[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){		
				General.println("The fishing spot is clickable");
				Mouse.setSpeed(RS3LRCFishing.mouseSpeed);
				if(RandomizedClicking.clickScreenModel(fish1, "Bait", -2, 6, -3, 9)){
					General.println("click successful");
				}
				while(Player.getAnimation() > 0 || Player.isMoving()) { General.sleep(1000, 3000); }

			}
			RandomizedCameraMovements.randomCameraRotation(); General.sleep(1500, 2000);
			
		}else if(PlayerPOS.distance(RS3LRCFishing.Fishing_Pos) < 10) { WalkingMethods.walkToFishSpot2(); }
		else if(PlayerPOS.distance(RS3LRCFishing.Fishing_Pos2) < 10) { WalkingMethods.walkToFishSpot1(); }
	}
}