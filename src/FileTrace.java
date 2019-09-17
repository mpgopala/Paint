import java.io.*;
import java.util.*;
import java.text.*;

public class FileTrace
{
	private String 		strFileName;
	private int		nLevel;
	RandomAccessFile	rafTraceFile;

	public FileTrace()
	{
		strFileName = null;
		nLevel = 0;
		rafTraceFile = null;
	}

	public FileTrace(String fileName, int level)
	{
		strFileName = fileName;
		nLevel = level;
		try
		{
			rafTraceFile = new RandomAccessFile(strFileName, "rw");
			rafTraceFile.setLength(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void write(String strTrace)
	{
		write(strTrace, 0);
	}

	public void write(Exception e)
	{
		write(e.toString());
	}

	public void write(String strTrace, int level)
	{
		if(level >= nLevel)
		{
			synchronized(this)
			{
				strTrace = FormatTrace(strTrace);
				try
				{
					rafTraceFile.seek(rafTraceFile.length());
					rafTraceFile.writeBytes(strTrace);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return;
	}

	private String FormatTrace(String strTrace)
	{
		String strRet = "[" + Thread.currentThread().getName() + "]";
		strRet += FormatDate();
		strRet += " " + strTrace + "\r\n";
		System.out.println(strRet);
		return strRet;
	}



	private String FormatDate()
	{
		Calendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		String strRet = "[" + date.getDate();
		strRet += "-" + (date.getMonth() + 1);
		strRet += "-" + (date.getYear() + 1900);

		strRet += " " + date.getHours();
		strRet += ":" + date.getMinutes();
		strRet += ":" + date.getSeconds();

		strRet += "]";
		return strRet;
	}

	public void close()
	{
		try
		{
			rafTraceFile.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

/*	public static void main(String args[])
	{
		FileTrace fTrace = new FileTrace("c:\\ab.txt", 0);

		fTrace.write("Hello world", 0);
		fTrace.close();
	}
*/
}