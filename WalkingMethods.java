package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.*;
import org.tribot.api.rs3.types.*;
import org.tribot.api.General;

public class WalkingMethods{

	
	public static boolean walkToFishSpot1()  {
		EGWPosition PlayerPOS = EGW.getPosition();

		if(PlayerPOS.distance(RS3LRCFishing.Fishing_Pos) > 3){
			if(EGW.walkPath(EGW.randomizePath(EGW.generateStraightPath(RS3LRCFishing.Fishing_Pos), General.random(-1, 1), General.random(-1, 1)))){
				General.println("Walking to fishing spot.");
				return true;
			}
             while (Player.isMoving())
                 General.sleep(800, 1000);
		} return false;
	}
	
	public static boolean walkToFishSpot2()  {
	EGWPosition PlayerPOS = EGW.getPosition();

		if(PlayerPOS.distance(RS3LRCFishing.Fishing_Pos2) > 3){
			if(EGW.walkPath(EGW.randomizePath(EGW.generateStraightPath(RS3LRCFishing.Fishing_Pos2), General.random(-1, 1), General.random(-1, 1)))){
				General.println("Walking to fishing spot.");
				return true;
			}
             while (Player.isMoving())
                 General.sleep(800, 1000);
		}return false;
	}
	
	public static boolean walkToFalBank()  {
	EGWPosition PlayerPOS = EGW.getPosition();

		if(PlayerPOS.distance(RS3LRCFishing.Fal_Bank_Pos) > 3){
			if(EGW.walkPath(EGW.randomizePath(EGW.generateStraightPath(RS3LRCFishing.Fal_Bank_Pos), General.random(-1, 1), General.random(-1, 1)))){
				General.println("Walking to Falador Bank");
				return true;
			}
             while (Player.isMoving())
                 General.sleep(800, 1000);
		}return false;
	}
	
	public static void walkToDM()  {
	EGWPosition PlayerPOS = EGW.getPosition();

		if(PlayerPOS.distance(RS3LRCFishing.DM_Pos) > 3){
			WebWalking.walkTo(RS3LRCFishing.DM_Pos);
			General.println("Walking to D-Mines");
             while (Player.isMoving())
                 General.sleep(800, 1000);
		}
	}
	
	public static boolean walkToRope()  {
	EGWPosition PlayerPOS = EGW.getPosition();

		if(PlayerPOS.distance(RS3LRCFishing.Rope_Pos) > 4){
			if(EGW.walkPath(EGW.randomizePath(EGW.generateStraightPath(RS3LRCFishing.Rope_Pos), General.random(-1, 1), General.random(-1, 1)))){
				General.println("Walking to The Rope");
				return true;
			}
             while (Player.isMoving())
                 General.sleep(800, 1000);
		}return false;
	}
			
	
	public static boolean walkToBank()  {
		EGWPosition PlayerPOS = EGW.getPosition();
	
		if(PlayerPOS.distance(RS3LRCFishing.Bank_Pos) > 4){
			if(EGW.walkPath(EGW.randomizePath(EGW.generateStraightPath(RS3LRCFishing.Bank_Pos), General.random(-1, 1), General.random(-1, 1)))){
				General.println("Walking to fishing spot.");
				return true;
			}
             while (Player.isMoving())
                 General.sleep(800, 1000);
		} return false;
	}
}