package core.ships;

public enum EnemyShipType {
	STAR_CRUISER,
	STAR_SHOOTER,
	STAR_FIGHTER;
	
	public String getClassName() {
		switch (this) {
		case STAR_CRUISER: return StarCruiserShip.class.getSimpleName();
		case STAR_SHOOTER: return StarShooterShip.class.getSimpleName();
		default: return StarFighterShip.class.getSimpleName();
		}
	}
}
