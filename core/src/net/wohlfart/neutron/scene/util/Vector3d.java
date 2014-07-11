package net.wohlfart.neutron.scene.util;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Vector3d {

	public double x;
	public double y;
	public double z;


	public Vector3d() {
		this(0,0,0);
	}

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3d add(Vector3 vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}

	public Vector3d rot(Quaternion q) {
        double xx, yy, zz;
        // @formatter:off
        xx = q.w * q.w * x + 2 * q.y * q.w * z
                - 2 * q.z * q.w * y + q.x * q.x * x
                + 2 * q.y * q.x * y + 2 * q.z * q.x * z
                - q.z * q.z * x - q.y * q.y * x;

        yy = 2 * q.x * q.y * x + q.y * q.y * y
                + 2 * q.z * q.y * z + 2 * q.w * q.z * x
                - q.z * q.z * y + q.w * q.w * y
                - 2 * q.x * q.w * z - q.x * q.x * y;

        zz = 2 * q.x * q.z * x + 2 * q.y * q.z * y
                + q.z * q.z * z - 2 * q.w * q.y * x
                - q.y * q.y * z + 2 * q.w * q.x * y
                - q.x * q.x * z + q.w * q.w * z;
        // @formatter:on
        this.x = xx;
        this.y = yy;
        this.z = zz;
        return this;
	}

}
