package scripts;

import java.awt.Color;
import java.awt.Point;
import java.awt.*;
import java.text.DecimalFormat;

import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api.rs3.*;
import org.tribot.api.rs3.types.*;
import org.tribot.api.input.*;
import org.tribot.api.rs3.Skills;
import org.tribot.api.rs3.Skills.*;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.rs3.util.ThreadSettings.MODEL_CLICKING_METHOD;
import org.tribot.script.EnumScript;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.api.Timing;
import org.tribot.api.util.Restarter;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;


@ScriptManifest(authors = { "dcthugs93" }, category = "Fishing", name = "Catches Fish in LRC")
public class RS3LRCFishing extends EnumScript<RS3LRCFishing.STATE> implements Painting
{

	public static Font font = AddFont.createFont();
	private final Color color = new Color(0,0,0);
	private static EGWPosition PlayerPOS = EGW.getPosition();
	public static final EGWPosition Bank_Pos = new EGWPosition (3655, 5114, 0),
									Fishing_Pos = new EGWPosition (3643, 5079, 0),
									Fishing_Pos2 = new EGWPosition (3626, 5085, 0),
									Fal_Pos = new EGWPosition (2967, 3403, 0),
									Fal_Bank_Pos = new EGWPosition (2945, 3370, 0),
									DM_Pos = new EGWPosition (3016, 3447, 0),
									Rope_Pos = new EGWPosition (3015, 9831, 0);
	public static final long[] 		banker = {509507916L, 3167445700L},
									fishingSpot = {2648051121L, 329245827L, 3554051133L}; 
	public static final long 		staircase = 3791790582L,
									rope = 3955626228L;	
	public static final int[] 		foodToEat = {1137865, 1037736, 1037579, 2380661, 1111183, 1094282},
									rawFish = {1041375, 1137424},
									loginScreen = {21234, 46069},
									keepItems = {1137865, 1037736, 1037579, 2380661, 1111183, 1094282, 1736402};
	public static final int 		fishingBait = 1736402,
									eatAtPercentage = General.random(40, 70),
									inventoryFull = 24078;
	public static int 				mouseSpeed = General.random(65, 155),
									healthPercentage = Math.round(Game.getHitpoints() *100/Game.getHitpointsMax());
	private static final long 		startTime = System.currentTimeMillis();
	private static 					String Status = "";
	
	
	enum STATE
	{
		CHECK, BANK, CHECK_FOR_FOOD, WALK_TO_FISH, CATCH_FISH, WALK_TO_BANK, WALK_TO_SAFE_SPOT, EAT, TELEPORT, WALK_TO_FAL_BANK, BANK_IN_FAL, WALK_TO_DM, CLICK_STAIRCASE, WALK_TO_ROPE, CLICK_ROPE
	}
	
		@Override
	public STATE getInitialState()
	{
		ThreadSettings.get().setScreenModelClickingMethod(MODEL_CLICKING_METHOD.CENTRE);
		AntiBan.startAntiBan();		
		Mouse.setSpeed(General.random(165, 175));


		return STATE.CHECK;
	}

	@Override
	public STATE handleState(STATE t)
	{
		switch (t)
		{
			case CHECK:
			ScreenModel[] fish = ScreenModels.findNearest(fishingSpot);  

			try {
				if (font == null) {
					font = AddFont.createFont();
                }
           } catch (Exception e) {
				e.printStackTrace();
           }
			if(loggedOut()){ 	
				Restarter.restartClient(); 
			}
			if(Backpack.find(foodToEat).length >= 1 && healthPercentage < eatAtPercentage){
					Status = "Eating Food";
					return STATE.EAT;
			}
			if(Backpack.find(fishingBait).length <= 0) { General.println("Out of bait stopping script"); super.stopScript();
			}
			if(Backpack.find(foodToEat).length <= 0) { Status = "Out of Food, Going To Get More"; return STATE.TELEPORT;
			}
			if(inCombat()){Status = "Walking to Safe Spot"; return STATE.WALK_TO_SAFE_SPOT;
			}
			if(Backpack.find(foodToEat).length >= 1 && healthPercentage > eatAtPercentage && PlayerPOS.distance(Fishing_Pos) > 10 && !inventoryIsFull()){
				Status = "Walking to Fishing Spot";
				return STATE.WALK_TO_FISH;
			}
			if(PlayerPOS.distance(Fishing_Pos) < 10 && fish.length > 0 && !inventoryIsFull()) {
				Status = "Catching Fish";
				return STATE.CATCH_FISH;
			}
			if(inventoryIsFull()){
				if(PlayerPOS.distance(Bank_Pos) < 6){
					Status = "Depositing Raw Fish";
					return STATE.BANK;
				}
				if(PlayerPOS.distance(Bank_Pos) > 15){
					Status = "Walking To Bank";
					return STATE.WALK_TO_BANK;
				}
			}
					
					

			
			case BANK:
				while(inventoryIsFull() || Backpack.find(rawFish).length > 0){
					Methods.bank();
			} 
			return STATE.WALK_TO_FISH;						
			
			case CATCH_FISH:
			if(Backpack.find(foodToEat).length > 0){
				if(healthPercentage > eatAtPercentage && !inventoryIsFull()){
					CatchFish.catchFish();
				}
				
			}

			return STATE.CHECK;
									
			case WALK_TO_BANK:
			EGWPosition PlayerPOS2 = EGW.getPosition();
			while(Backpack.find(rawFish).length > 0 && PlayerPOS2.distance(Bank_Pos) < 6){
				WalkingMethods.walkToBank();
			}
			return STATE.BANK;
			
			case TELEPORT:
			EGWPosition PlayerPOS3 = EGW.getPosition();
			if(!LodestoneNetwork.isOpen()){
				if(LodestoneNetwork.open()){
					if(PlayerPOS3.distance(Fal_Pos) > 6){
						LodestoneNetwork.teleport(LodestoneNetwork.LOCATIONS.FALADOR);
						Timing.waitCondition(new Condition() {
							public boolean active() {
								return PlayerPOS.distance(Fal_Pos) < 6;
							}
						}, General.random(2000, 4000));
						while(Player.getAnimation() > 0)
							General.sleep(1000, 2000);
					}
				}
			}
			if(PlayerPOS3.distance(Fal_Pos) < 6){
				return STATE.WALK_TO_FAL_BANK;
			}
			return STATE.TELEPORT;
			
			case WALK_TO_FAL_BANK:
			EGWPosition PlayerPOS4 = EGW.getPosition();
			if(PlayerPOS4.distance(Fal_Bank_Pos) > 6){
				WalkingMethods.walkToFalBank();
			}
			if(PlayerPOS4.distance(Fal_Bank_Pos) <= 6){
				return STATE.BANK_IN_FAL;
			}
			return STATE.WALK_TO_FAL_BANK;
			
			case BANK_IN_FAL:
			while(Backpack.find(foodToEat).length <= 0){
				Methods.bankInFal();
			} 
			return STATE.WALK_TO_DM;
			
			case WALK_TO_DM:
			EGWPosition PlayerPOS5 = EGW.getPosition();
			if(PlayerPOS5.distance(DM_Pos) > 6){
				WalkingMethods.walkToDM();
			}
			if(PlayerPOS5.distance(DM_Pos) <= 6){
				return STATE.CLICK_STAIRCASE;
			}
			return STATE.WALK_TO_DM;
			
			case CLICK_STAIRCASE:
			EGWPosition PlayerPOS6 = EGW.getPosition();
			if(PlayerPOS6.distance(DM_Pos) < 6){
				Methods.clickOnStaircase();
			}
			if(PlayerPOS6.distance(DM_Pos) > 6){
				return STATE.WALK_TO_ROPE;
			}
			return STATE.CLICK_STAIRCASE;
			
			case WALK_TO_ROPE:
			EGWPosition PlayerPOS7 = EGW.getPosition();
			if(PlayerPOS7.distance(Rope_Pos) > 4){
				WalkingMethods.walkToRope();
			}
			if(PlayerPOS7.distance(Rope_Pos) < 4){
				return STATE.CLICK_ROPE;
			}
			return STATE.WALK_TO_ROPE;
			
			case CLICK_ROPE:
			EGWPosition PlayerPOS8 = EGW.getPosition();
			if(PlayerPOS8.distance(Bank_Pos) > 10){
				Methods.clickOnRope();
			}
			if(PlayerPOS8.distance(Bank_Pos) < 10){
				return STATE.WALK_TO_FISH;
			}
			return STATE.CLICK_ROPE;
			
			case WALK_TO_SAFE_SPOT:
			EGWPosition PlayerPOS1 = EGW.getPosition();
				if(PlayerPOS1.distance(Fishing_Pos) < 10){
					WalkingMethods.walkToFishSpot2();
				} else { WalkingMethods.walkToFishSpot1(); }
				while(Player.isMoving())
					General.sleep(1000, 2000);
			return STATE.CHECK;
			
			case WALK_TO_FISH:
			WalkingMethods.walkToFishSpot1();
			return STATE.CHECK;
			
			case EAT:
			while(healthPercentage < eatAtPercentage){
				Methods.eatFood();
			}
			break;
			

		}
		return STATE.CHECK;
	}

	/* This is currently not being used.
	public static boolean textureOnScreen(){
		Texture[] XPTracker = Textures.find(XP);
		return XPTracker.length > 0;
	}*/
	
	/*Backpack.isFull() is broken so I use this*/
	public static boolean inventoryIsFull(){
		Texture[] inventory = Textures.find(inventoryFull);
		return inventory.length > 0;
	}
	
	private boolean inCombat(){ 
		return Combat.getTargetRings().length > 0; 
	}
	
	public static boolean loggedOut(){
		Texture[] loggedOut = Textures.find(loginScreen);
		return loggedOut.length > 0;
	}
	
	    @Override
    public void onPaint(Graphics g) {
		
        long timeRan = System.currentTimeMillis() - startTime;
		
		
		DecimalFormat df = new DecimalFormat("#,###");
	
        g.setFont(font);
        g.setColor(color);
        g.drawString("Time Ran: " + Timing.msToString(timeRan), 10, 30);
		g.drawString("Status: " + Status + "  ", 10, 60);
		g.drawString("This is in Beta Stage, Report Bugs", 10, 90);
		g.drawString("dcthugs93 RS3 LRC Fishing", 10, 120);

    }	

	
}
		
	