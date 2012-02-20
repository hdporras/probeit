package probeIt.util;

import java.io.File;

public class OWLFilesFilter extends javax.swing.filechooser.FileFilter
{

	public OWLFilesFilter()
	{
	}

	public boolean accept(File f)
	{
		if (f.isDirectory())
		{
			return true;
		}

		String fname = f.getName();
		String ext = null;
		int idx = fname.lastIndexOf('.');
		if (idx > 0 && idx < fname.length() - 1)
		{
			ext = fname.substring(idx + 1).toLowerCase();
		}
		if (ext != null)
		{
			if (ext.equals("owl"))
			{
				return true;
			} else
			{
				return false;
			}
		}
		return false;
	}

	public String getDescription()
	{
		return "owl files";
	}

}