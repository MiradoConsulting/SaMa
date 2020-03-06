/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;


/**
 * SpinBot - a sample robot by Mathew Nelson.
 * <p/>
 * Moves in a circle, firing hard when an enemy is detected.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class SaMa extends AdvancedRobot {
	static final double PI = 3.1415;

	/**
	 * SpinBot's run method - Circle
	 */
	public void run() {
		// Set colors
		setBodyColor(Color.pink);
		setGunColor(Color.pink);
		setRadarColor(Color.pink);
		setScanColor(Color.pink);
		double height = getBattleFieldHeight();
		double width = getBattleFieldWidth();
		
		double ourHeight = getHeight();
		double ourWidth = getWidth();
		boolean turnRight = true;
		double maxVelocity = 50;		

		// Loop forever
		while (true) {
			maxVelocity = 500;
			if(getX() > width - width/10)
			{
				if(0 < getHeading() && getHeading() < 90) {
					maxVelocity = 5;
					turnRight = false;
				} else if (getHeading() < 180) {
					maxVelocity = 5;
					turnRight = true;
				}
			} else if (getX() < width/10) {
				if(180 < getHeading() && getHeading() < 270) {
					maxVelocity = 5;
					turnRight = false;
				} else if (getHeading() > 270) {
					maxVelocity = 5;
					turnRight = true;
				}
			} else if (getY() < height/8) {
				if(getHeading() > 90 && getHeading() < 180) {
					maxVelocity = 5; 
					turnRight = false;
				} else if(getHeading() < 270 && getHeading() > 180) {
					maxVelocity = 5; 
					turnRight = true;
				}
			} else if (getY() > height - height/8) {
				if(0 < getHeading() && getHeading() < 90) {
					maxVelocity = 5; 
					turnRight = true;
				} else if(getHeading() > 270) {
					maxVelocity = 5; 
					turnRight = false;
				}
			} 
			
			
			

			if(turnRight) {
				setTurnRight(300);
			} else {
				setTurnLeft(300);
			}
			
			setMaxVelocity(maxVelocity);
			setAhead(300);
			execute();
		}
	}

	/**
	 * onScannedRobot: Fire hard!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double enemyBearing = e.getBearingRadians();
		double ourTurret = getGunHeadingRadians();
		double ourDirection = getHeadingRadians();
		double moveTurret = enemyBearing - ourDirection - ourTurret; 
		
		//if (moveTurret < -1 * PI) {
		//	setTurnGunRightRadians(PI*2 + moveTurret);
		//} else if(moveTurret < PI) { */
		setTurnGunRightRadians(moveTurret);
		//} else {
		//	setTurnGunLeftRadians(PI*2 - moveTurret);
		//}
		
		if(e.getDistance() < 150) {
			fire(5 * 150 / e.getDistance());
		} 
	}

	/**
	 * onHitRobot:  If it's our fault, we'll stop turning and moving,
	 * so we need to turn again to keep spinning.
	 */
	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			fire(3);
		}
		if (e.isMyFault()) {
			turnRight(10);
		}
	}
}
