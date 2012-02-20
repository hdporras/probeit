package probeIt.util;

import java.io.*;
import java.util.*;

public class FileUtils
{
	public static String getWorkspaceDir()
	{return System.getenv("PROBEIT_HOME") + File.separator + "workspace";}
	
	public static String getProbeitDir()
	{return System.getenv("PROBEIT_HOME");}
	
	public static String writeTextFile(String fileContents, String dirName,
			String fileName)
	{
		File dirFile = new File(dirName);
		dirFile.mkdirs();
		String filePath = dirFile + File.separator + fileName;

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
			out.write(fileContents);
			out.close();
			return filePath;
		} catch (Exception e)
		{
			e.printStackTrace();
			return e.toString();
		}
	}

	public static boolean exists(String filePath)
	{
		File file = new File(filePath);
		if (file.exists())
			return true;
		return false;
	}

	public static FileOutputStream getLoggingStream()
	{
		try
		{
			return new FileOutputStream(getWorkspaceDir() + "/log.txt");
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String writeBinaryFile(byte[] fileContents, String dirName,
			String fileName)
	{
		File dirFile = new File(dirName);
		dirFile.mkdirs();
		String filePath = dirFile + File.separator + fileName;

		try
		{
			FileOutputStream out = new FileOutputStream(filePath);
			out.write(fileContents);
			out.close();
			return filePath;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return e.toString();
		}
	}

	public static byte[] readBinaryFile(String fileName)
	{
		try
		{
			FileInputStream in = new FileInputStream(fileName);
			byte[] fileContents = new byte[fileSize(fileName)];
			in.read(fileContents);
			in.close();
			return fileContents;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String readTextFile(String fileName)
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line, fileContents;

			fileContents = null;

			while ((line = in.readLine()) != null)
			{
				if (fileContents == null)
					fileContents = line + "\n";
				else
					fileContents = fileContents + line + "\n";
			}
			in.close();
			return fileContents;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String getRandomString()
	{
		// get the current time in milliseconds to use as data source temp file
		// name
		long miliseconds = (new Date()).getTime();
		String localFileName = new String("" + miliseconds);
		return localFileName;
	}

	public static void deleteFile(String fileName)
	{
		File file = new File(fileName);
		file.delete();
	}

	public static void cleanWorkspace()
	{
		File file = new File(getWorkspaceDir());
		String[] wsFiles = file.list();
		if (wsFiles != null)
		{
			for (String aFile : wsFiles)
			{
				deleteFile(FileUtils.makeFullPath(getWorkspaceDir(), aFile));
			}
		}
	}

	public static String makePathWellFormedURI(String path)
	{
		try
		{
			String uri = path.replaceAll("\"", "");
			return uri.replaceAll(" ", "%20");
			/*
			 * uri = URLEncoder.encode(uri, "UTF-8"); uri =
			 * uri.replaceAll("%3A", ":"); uri = uri.replaceAll("%2F", "/");
			 */
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String makeFullPath(String dir, String fileName)
	{
		return dir + File.separator + fileName;
	}

	public static int fileSize(String fileName)
	{
		File file = new File(fileName);
		return (int) file.length();
	}
	
	    
	  public static String getNameFromFilename(String fileName) {
	    StringTokenizer tokens = new StringTokenizer(fileName, ".");
	    String output = tokens.nextToken();
	    return output;
	  }

	  public static String getNameFromPath(String fileName) {
	    String name = new File(fileName).getName();
	    return name;
	  }

	  public static String getDirFromPath(String fileName) {
	    String path = new File(fileName).getParent();
	    return path;
	  }
}
