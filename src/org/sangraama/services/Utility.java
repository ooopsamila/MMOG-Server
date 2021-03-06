package org.sangraama.services;

public class Utility {

	// Convert game world x coordinate to a client pixel x coordinate
	public static float toPixelPosX(float posX, float gameWorldWidth) {
		return Constants.canvasWidth * posX / gameWorldWidth;
	}

	// Convert client pixel x coordinate to a game world x coordinate
	public static float toPosX(float posX, float gameWorldWidth) {
		return (posX * gameWorldWidth * 1.0f) / Constants.canvasWidth;
	}

	// Convert game world y coordinate to a client pixel y coordinate
	public static float toPixelPosY(float posY, float gameWorldHeight) {
		return Constants.canvasHeight - (1.0f * Constants.canvasHeight) * posY
				/ gameWorldHeight;
	}

	// Convert client pixel y coordinate to a game world y coordinate
	public static float toPosY(float posY, float gameWorldHeight) {
		return gameWorldHeight
				- ((posY * gameWorldHeight * 1.0f) / Constants.canvasHeight);
	}

	// Convert game world width to pixel width
	public float toPixelWidth(float width, float gameWorldWidth) {
		return Constants.canvasWidth * width / gameWorldWidth;
	}

	// Convert game world height to pixel height
	public float toPixelHeight(float height, float gameWorldHeight) {
		return Constants.canvasHeight * height / gameWorldHeight;
	}

}
