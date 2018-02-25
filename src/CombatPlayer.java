
public class CombatPlayer extends Object{
	int hp;
	int juice;
	int strength;
	int dexterity;
	int intelligence;
	int luck;
	CombatPlayer(int hp, int juice, int strength, int dexterity, int intelligence, int luck)
	{
		this.hp = hp;
		this.juice = juice;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.luck = luck;
	}
	void update()
	{
		if(hp < 0)
		{
			
		}
	}
}
