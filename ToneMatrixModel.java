/**
 * This Class is used to store and modify the Array information needed by the ToneMatrix.
 */
public class ToneMatrixModel {
	private int[][] array;

	public ToneMatrixModel() {
		array = new int[16][16];
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				array[x][y] = 0;
			}
		}
	}

	public boolean getOn(int x, int y) {
		return (array[x][y] != 0);
	}

	public int getColor(int x, int y) {
		return array[x][y];
	}

	public void setColor(int x, int y, int color) {
		array[x][y] = color;
	}
}
