package GameState;

import java.util.ArrayList;

import GameState.Skill.Targets;

/**
 * @author Zhuo, Liam Howe
 * Class to hold a list of all skills; healing, damage and buffs.
 */
public class SkillsList {
	
	public	ArrayList<HealingSkill> HealingSkillList = new ArrayList<HealingSkill>();
	public	ArrayList<DamageSkill> DamageSkillList = new ArrayList<DamageSkill>();
	public	ArrayList<BuffSkill> BuffingSkillList = new ArrayList<BuffSkill>();

	/**
	 * Creates the skills, and adds them to the list of skills.
	 */
	public SkillsList()
	{
		//DamageSkill dSkill3 = new DamageSkill(		skillName, 				MPCost, skillID,		 target, 	power, accuracy, 			type)
				DamageSkill attack = new DamageSkill ("Attack", 				 0, 		0, 		Targets.SINGLE, 	70, 	95, 	DamageSkill.ElementalType.VOID,
						"<html>You attack...with or how is left to your imagination<br>"
						+ " Power: 40"+"<br>"
						+ " MP Cost: 0"+"<br>"
						+ " Accuracy 95</html>");
				DamageSkill dskill1 = new DamageSkill("Flame Strike",   		10, 		1, 		Targets.SINGLE, 	90, 	90, 	DamageSkill.ElementalType.FIRE,
						"<html>Attacks a single target, inflicting light fire damage upon them<br>"
						+ " Power: 60"+"<br>"
						+ " MP Cost: 10"+"<br>"
						+ " Accuracy 90</html>");
				DamageSkill dskill2 = new DamageSkill("Water Sprout",			10, 		2, 		Targets.SINGLE, 	90, 	90, 	DamageSkill.ElementalType.WATER,
						"<html>Attacks a single target, inflicting  light water damage upon them<br>"
						+ " Power: 60"+"<br>"
						+ " MP Cost: 10"+"<br>"
						+ " Accuracy 90</html>");
				DamageSkill dskill3 = new DamageSkill("Landslide", 				10, 		3, 		Targets.SINGLE,   	90, 	90, 	DamageSkill.ElementalType.EARTH,
						"<html>Attacks a single target, inflicting  light earth damage upon them<br>"
						+ " Power: 60"+"<br>"
						+ " MP Cost: 10"+"<br>"
						+ " Accuracy 90</html>");
				DamageSkill dskill4 = new DamageSkill("Cyclone Hammer",   		10, 		4, 		Targets.SINGLE, 	90, 	90, 	DamageSkill.ElementalType.AIR,
						"<html>Attacks a single target, inflicting  light wind damage upon them<br>"
						+ " Power: 60"+"<br>"
						+ " MP Cost: 10"+"<br>"
						+ " Accuracy 90</html>");
				DamageSkill dskill5 = new DamageSkill("Shadow Slash",			10, 		5, 		Targets.SINGLE, 	90, 	90, 	DamageSkill.ElementalType.VOID,
						"<html>Attacks a single target, inflicting  light void damage upon them<br>"
						+ " Power: 60"+"<br>"
						+ " MP Cost: 10"+"<br>"
						+ " Accuracy 90</html>");
				DamageSkill dskill6 = new DamageSkill("Inferno",   				15, 		6, 		Targets.TEAM, 		80, 	85, 	DamageSkill.ElementalType.FIRE,
						"<html>Attacks a team, inflicting them with light fire damage<br>"
						+ " Power: 55"+"<br>"
						+ " MP Cost: 15"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill7 = new DamageSkill("Whirlpool",				15, 		7, 		Targets.TEAM, 		80, 	85, 	DamageSkill.ElementalType.WATER,
						"<html>Attacks a team, inflicting them with light water damage<br>"
						+ " Power: 55"+"<br>"
						+ " MP Cost: 15"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill8 = new DamageSkill("Tremor", 				15, 		8, 		Targets.TEAM,  		80, 	85, 	DamageSkill.ElementalType.EARTH,
						"<html>Attacks a team, inflicting them with light earth damage<br>"
						+ " Power: 55"+"<br>"
						+ " MP Cost: 15"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill9 = new DamageSkill("Hurricane",   			15, 		9, 		Targets.TEAM, 		80, 	85, 	DamageSkill.ElementalType.AIR,
						"<html>Attacks a team, inflicting them with light wind damage<br>"
						+ " Power: 55"+"<br>"
						+ " MP Cost: 15"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill10 = new DamageSkill("Ruin",					15, 		10, 	Targets.TEAM, 		80, 	85, 	DamageSkill.ElementalType.VOID,
						"<html>Attacks a team, inflicting them with light void damage<br>"
						+ " Power: 55"+"<br>"
						+ " MP Cost: 15"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill11 = new DamageSkill("Kill it with Fire",   	20, 		11, 	Targets.SINGLE, 	160, 	85, 	DamageSkill.ElementalType.FIRE,
						"<html>Attacks a single target inflicting heavy fire damage upon them<br>"
						+ " Power: 140"+"<br>"
						+ " MP Cost: 20"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill12 = new DamageSkill("Downpour",				20, 		12, 	Targets.SINGLE, 	160, 	85, 	DamageSkill.ElementalType.WATER,
						"<html>Attacks a single target inflicting heavy water damage upon them<br>"
						+ " Power: 140"+"<br>"
						+ " MP Cost: 20"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill13 = new DamageSkill("Sinkhole",				20, 		13, 	Targets.SINGLE,   	160, 	85, 	DamageSkill.ElementalType.EARTH,
						"<html>Attacks a single target inflicting heavy earth damage upon them<br>"
						+ " Power: 140"+"<br>"
						+ " MP Cost: 20"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill14 = new DamageSkill("Sky Rapture",   		20, 		14, 	Targets.SINGLE, 	160, 	85, 	DamageSkill.ElementalType.AIR,
						"<html>Attacks a single target inflicting heavy wind damage upon them<br>"
						+ " Power: 140"+"<br>"
						+ " MP Cost: 20"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill15 = new DamageSkill("Soul Rend",				20, 		15, 	Targets.SINGLE, 	160, 	85, 	DamageSkill.ElementalType.VOID,
						"<html>Attacks a single target inflicting heavy void damage upon them<br>"
						+ " Power: 140"+"<br>"
						+ " MP Cost: 20"+"<br>"
						+ " Accuracy 85</html>");
				DamageSkill dskill16 = new DamageSkill("Burninate",   			35, 		16, 	Targets.TEAM, 	140, 	80, 	DamageSkill.ElementalType.FIRE,
						"<html>Attacks a team, inflicting medium fire damage.<br>"
						+ " Power: 120"+"<br>"
						+ " MP Cost: 35"+"<br>"
						+ " Accuracy 80</html>");
				DamageSkill dskill17= new DamageSkill("Tidal Wave",				35, 		17, 	Targets.TEAM, 	140, 	80, 	DamageSkill.ElementalType.WATER,
						"<html>Attacks a team, inflicting medium water damage.<br>"
						+ " Power: 120"+"<br>"
						+ " MP Cost: 35"+"<br>"
						+ " Accuracy 80</html>");
				DamageSkill dskill18 = new DamageSkill("Weight of the World", 	35, 		18, 	Targets.TEAM,   140, 	80, 	DamageSkill.ElementalType.EARTH,
						"<html>Attacks a team, inflicting medium earth damage.<br>"
						+ " Power: 120"+"<br>"
						+ " MP Cost: 35"+"<br>"
						+ " Accuracy 80</html>");
				DamageSkill dskill19= new DamageSkill("A Bit Breezy",  			35, 		19, 	Targets.TEAM, 	140, 	80, 	DamageSkill.ElementalType.AIR,
						"<html>Attacks a team, inflicting medium air damage.<br>"
						+ " Power: 120"+"<br>"
						+ " MP Cost: 35"+"<br>"
						+ " Accuracy 80</html>");
				DamageSkill dskill20 = new DamageSkill("Get Rekt",				35, 		20, 	Targets.TEAM, 	140, 	80, 	DamageSkill.ElementalType.VOID,
						"<html>Attacks a team, inflicting medium void damage.<br>"
						+ " Power: 120"+"<br>"
						+ " MP Cost: 35"+"<br>"
						+ " Accuracy 80</html>");
				DamageSkill dskill21 = new DamageSkill("The Red Hot Conga",		80, 		21, 	Targets.TEAM, 	500, 	95, 	DamageSkill.ElementalType.FIRE,
						"<html>All aboard the pain train...inflicts severe fire damage upon a team<br>"
						+ " Power: 400"+"<br>"
						+ " MP Cost: 80"+"<br>"
						+ " Accuracy 95</html>");
				
				//HealingSkill hskill4 = new HealingSkill(skillName, 					MPCost, skillID, healingTarget, 			healingPower)
				HealingSkill hskill0 = new HealingSkill("Lesser Healing Spring", 		30,	 		0, 		Targets.SINGLE, 		30,
						"<html>Slightly restores a target's HP.<br>"
						+ " Healing Power: 30"+"<br>"
						+ " MP Cost: 30"+"</html>");
				HealingSkill hskill1 = new HealingSkill("Greater Healing Spring", 		40, 		1, 		Targets.SINGLE, 		80,
						"<html>Moderately restores a target's HP.<br>"
						+ " Healing Power: 80"+"<br>"
						+ " MP Cost: 40"+"</html>");
				HealingSkill hskill2 = new HealingSkill("Sanctuary", 					55, 		2, 		Targets.SINGLE, 		9000,
						"<html>Significantly restores a target's HP.<br>"
						+ " Healing Power: 9000"+"<br>"
						+ " MP Cost: 55"+"</html>");
				HealingSkill hskill3 = new HealingSkill("Lesser Healing Torrent", 		40,	 		3, 		Targets.TEAM, 			30,
						"<html>Slightly restores a team's HP.<br>"
						+ " Healing Power: 30"+"<br>"
						+ " MP Cost: 40"+"</html>");
				HealingSkill hskill4 = new HealingSkill("Greater Healing Torrent", 		60, 		4, 		Targets.TEAM, 			80,
						"<html>Moderately restores a team's HP.<br>"
						+ " Healing Power: 80"+"<br>"
						+ " MP Cost: 60"+"</html>");
				HealingSkill hskill5 = new HealingSkill("Salvation", 					80, 		5, 		Targets.TEAM, 			9000,
						"<html>Significantly restores a team's HP.<br>"
						+ " Healing Power: 9000"+"<br>"
						+ " MP Cost: 80"+"</html>");
				
				//BuffSkill bskill4 = new BuffSkill(skillName, 			MPCost, 	skillID, buffTarget, 		, statAffected, StatUp )
				BuffSkill bskill0 = new BuffSkill("Heat Riser",			10, 		0, 		Targets.SINGLE, 		0		,	true	,
						"<html>Raises a target's power<br>"
						+ " MP Cost: 10"+"</html>");
				BuffSkill bskill1 = new BuffSkill("Poise Tank",			10, 		1, 		Targets.SINGLE, 		1		,	true	,
						"<html>Raises a target's defense<br>"
						+ " MP Cost: 10"+"</html>");
				BuffSkill bskill2 = new BuffSkill("Cardio", 			10, 		2, 		Targets.SINGLE, 		2, 			true, 
						"<html>Raises a target's speed<br>"
						+ " MP Cost: 10"+"</html>");
				BuffSkill bskill3 = new BuffSkill("Glorious Charge",	20, 		3, 		Targets.TEAM, 			0		,	true	,
						"<html>Raises a team's attack<br>"
						+ " MP Cost: 20"+"</html>");
				BuffSkill bskill4 = new BuffSkill("Final Bastion",		20, 		4, 		Targets.TEAM, 			1		,	true	,
						"<html>Raises a team's defense<br>"
						+ " MP Cost: 20"+"</html>");
				BuffSkill bskill5 = new BuffSkill("Squats", 			20,			5, 		Targets.TEAM, 			2, 			true, 
						"<html>Raises a team's speed<br>"
						+ " MP Cost: 20"+"</html>");
				BuffSkill bskill6 = new BuffSkill("Cripple",			10, 		6, 		Targets.SINGLE, 		0		,	false	,
						"<html>Decreases a target's power<br>"
						+ " MP Cost: 10"+"</html>");
				BuffSkill bskill7 = new BuffSkill("Guard Breaker",		20, 		7, 		Targets.SINGLE, 		1		,	false	,
						"<html>Decreases a target's defense<br>"
						+ " MP Cost: 20"+"</html>");
				BuffSkill bskill8 = new BuffSkill("Cramp", 				10, 		8, 		Targets.SINGLE, 		2, 			false, 
						"<html>Decrease a target's speed<br>"
						+ " MP Cost: 10"+"</html>");
				BuffSkill bskill9 = new BuffSkill("Fallen Sanctum",  	10, 		9, 		Targets.TEAM, 			1,	 		false	,
						"<html>Decreases a team's power<br>"
						+ " MP Cost: 10"+"</html>");
				BuffSkill bskill10 = new BuffSkill("Shattered Will", 	10, 		10, 	Targets.TEAM,  			0, 			false	,
						"<html>Decreases a team's defense<br>"
						+ " MP Cost: 10"+"</html>");
				BuffSkill bskill11 = new BuffSkill("Leg Day",		 	20, 		11,		Targets.TEAM, 			2, 			false, 
						"<html>Decrease a team's speed<br>"
						+ " MP Cost: 20"+"</html>");
				BuffSkill bskill12 = new BuffSkill("Dispel",			30, 		12, 	Targets.SINGLE,  		3, 			false	,
						"<html>Nullify stat penalties and boosts of a target.<br>"
						+ " MP Cost: 30"+"</html>");
				BuffSkill bskill13 = new BuffSkill("Final Destination",  45, 		13, 	Targets.TEAM,  			3, 			false	,
						"<html>Nullify stat penalties and boosts of a team.<br>"
						+ " MP Cost: 45"+"</html>");
				
				//Attack
				
				DamageSkillList.add(attack);
				DamageSkillList.add(dskill1);
				DamageSkillList.add(dskill2);
				DamageSkillList.add(dskill3);
				DamageSkillList.add(dskill4);
				DamageSkillList.add(dskill5);
				DamageSkillList.add(dskill6);
				DamageSkillList.add(dskill7);
				DamageSkillList.add(dskill8);
				DamageSkillList.add(dskill9);
				DamageSkillList.add(dskill10);
				DamageSkillList.add(dskill11);
				DamageSkillList.add(dskill12);
				DamageSkillList.add(dskill13);
				DamageSkillList.add(dskill14);
				DamageSkillList.add(dskill15);
				DamageSkillList.add(dskill16);
				DamageSkillList.add(dskill17);
				DamageSkillList.add(dskill18);
				DamageSkillList.add(dskill19);
				DamageSkillList.add(dskill20);
				DamageSkillList.add(dskill21);
				
				HealingSkillList.add(hskill0);
				HealingSkillList.add(hskill1);
				HealingSkillList.add(hskill2);
				HealingSkillList.add(hskill3);
				HealingSkillList.add(hskill4);
				HealingSkillList.add(hskill5);
				
				BuffingSkillList.add(bskill0);
				BuffingSkillList.add(bskill1);
				BuffingSkillList.add(bskill2);
				BuffingSkillList.add(bskill3);
				BuffingSkillList.add(bskill4);
				BuffingSkillList.add(bskill5);
				BuffingSkillList.add(bskill6);
				BuffingSkillList.add(bskill7);
				BuffingSkillList.add(bskill8);
				BuffingSkillList.add(bskill9);
				BuffingSkillList.add(bskill10);
				BuffingSkillList.add(bskill11);
				BuffingSkillList.add(bskill12);
				BuffingSkillList.add(bskill13);
				
	}
	
	
	/**
	 * Returns the list of damage skills	
	 * @return The list of damage skills
	 */
	public ArrayList<DamageSkill> getDSkill ()
	{
		return this.DamageSkillList;
	}
	
	/**
	 * Returns the list of buff skills
	 * @return The list of buff skills
	 */
	public ArrayList<BuffSkill> getBSkill ()
	{
		return this.BuffingSkillList;
	}
	
	/**
	 * Returns the list of healing skills
	 * @return The list of healing skills
	 */
	public ArrayList<HealingSkill> getHSkill ()
	{
		return this.HealingSkillList;
	}
	
}

