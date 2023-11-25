package models;
/**
 * This class represents a holder for points. 
 * It allows for managing and tracking total points for a camp committee member.
 */
public class PointsHolder {
	/**
	 * The current number of points held.
	 */
	private int points = 0;
	// Getters
	/**
	 * Gets the current number of points.
	 * 
	 * @return the current points
	 */
	public int getPt() {
		return this.points;
	}
	// Setters
	/**
	 * Sets the number of points to a specified value.
	 * 
	 * @param n the new number of points
	 */
	public void setPt(int n) {
		this.points = n;
	}
	// Point management methods
	/**
	 * Increments the number of points by one.
	 */
	public void incPt() {
		this.points++;
	}
	/**
	 * Decrements the number of points by one.
	 */
	public void decPt() {
		this.points--;
	}


}
