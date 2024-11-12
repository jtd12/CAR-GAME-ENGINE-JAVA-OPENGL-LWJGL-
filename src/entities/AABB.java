package entities;

public class AABB {
	    private float centerX, centerY, centerZ; // Centre de la boîte
	    private float width, height, depth;      // Dimensions de la boîte

	    public AABB(float centerX, float centerY, float centerZ, float width, float height, float depth) {
	        this.centerX = centerX;
	        this.centerY = centerY;
	        this.centerZ = centerZ;
	        this.width = width;
	        this.height = height;
	        this.depth = depth;
	    }

	    public float getMinX() {
	        return centerX - width / 2;
	    }

	    public float getMaxX() {
	        return centerX + width / 2;
	    }

	    public float getMinY() {
	        return centerY - height / 2;
	    }

	    public float getMaxY() {
	        return centerY + height / 2;
	    }

	    public float getMinZ() {
	        return centerZ - depth / 2;
	    }

	    public float getMaxZ() {
	        return centerZ + depth / 2;
	    }
	}

