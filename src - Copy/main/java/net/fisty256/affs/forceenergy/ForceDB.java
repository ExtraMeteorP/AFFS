package net.fisty256.affs.forceenergy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import scala.tools.nsc.interactive.tests.core.SourcesCollector;

public class ForceDB {
	
	private static int[] energy = new int[1024];
	
	public static void init()
	{
		for (int i = 0; i < energy.length; i++)
		{
			energy[i] = -1;
		}
	}
	
	public static void setSource(int i, int value)
	{
		energy[i] = value;
	}
	
	public static void destroySource(int i)
	{
		energy[i] = -1;
	}
	
	public static boolean checkSource(int i)
	{
		return energy[i] > -1;
	}
	
	public static int getSource(int i)
	{
		return energy[i];
	}
	
	public static int addRandomSource(int value)
	{
		Random random = new Random();
		int r = random.nextInt(energy.length);
		while (!checkSource(r))
		{
			r = random.nextInt(energy.length);
		}
		setSource(r, value);
		return r;
	}
}