package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class collision {

	public boolean isCollideDroite(Vector3f playerPos, Vector3f playerScale, Vector3f entitiesPos,Vector3f entitiesScale) {

	    if ( 
	        ( playerPos.x-20 <= entitiesScale.x + entitiesPos.x && playerScale.x + playerPos.x >= entitiesPos.x-entitiesScale.x ) &&
	       // (playerPos.y <=   entitiesPos.y+entitiesScale.y ) && 
	        ( playerPos.z-20 <= (-2.5f*entitiesScale.z) + entitiesPos.z && playerScale.z + playerPos.z >= (-3.5f*entitiesScale.z)+entitiesPos.z-entitiesScale.z)  
	       ) 
	    {
	        System.out.println("Player is colliding!!!");
	        return true;
	        //p.increasePosition(0,0,4.5f*invert);
	        
	    }
	    return false;
	 
	}

}
