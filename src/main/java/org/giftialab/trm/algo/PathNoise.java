package org.giftialab.trm.algo;

import java.util.Random;

import javax.vecmath.Vector2f;

import org.giftialab.trm.algo.hash.Hashs;

public class PathNoise {

	private Hashs hash;

	public PathNoise(long seed) {
		this.hash = new Hashs(new Random(seed));
	}
	
	public void pathNoise(float x, float z, float bias, Vector2f out) {
		int baseX = (int) x, baseZ = (int) z;
		if (x < 0) --baseX;
		if (z < 0) --baseZ;
		
		float fx = x - (float) baseX, fz = z - (float) baseZ;
		float afx = fx - 1.0F, afz = fz - 1.0F;
		
		float s1 = (float) Math.sqrt(fx * fx + fz * fz);
		float w1 = noise(baseX, baseZ, bias) / s1;
		float vx1 = w1 * fx, vz1 = w1 * fz;
		
		float s2 = (float) Math.sqrt(afx * afx + fz * fz);
		float w2 = noise(baseX + 1, baseZ, bias) / s2;
		float vx2 = w2 * afx, vz2 = w2 * fz;
		
		float s3 = (float) Math.sqrt(fx * fx + afz * afz);
		float w3 = noise(baseX, baseZ + 1, bias) / s3;
		float vx3 = w3 * fx, vz3 = w3 * afz;
		
		float s4 = (float) Math.sqrt(afx * afx + afz * afz);
		float w4 = noise(baseX + 1, baseZ + 1, bias) / s4;
		float vx4 = w4 * afx, vz4 = w4 * afz;
		
		out.x = (vx2 + vx4) * fx - (vx1 + vx3) * afx;
		out.y = (vz3 + vz4) * fz - (vz1 + vz2) * afz;
	}
	
	private float noise(int x, int z, float bias) {
		double d = (double)hash.hash(x, z) / (double)Integer.MAX_VALUE;
		return (float) d + bias;
	}
	
}
