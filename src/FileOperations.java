import java.io.*;

public class FileOperations
{

	public FileOperations()
	{
	}

	public static final byte[] ReadFile(String s)
	{
		try
		{
			File file = new File(s);
			int i = (int) file.length();
			byte abyte0[] = new byte[i];
			DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
			datainputstream.readFully(abyte0, 0, i);
			datainputstream.close();
			TotalRead++;
			return abyte0;
		}
		catch (Exception exception)
		{
		}
		return null;
	}

	public static int TotalRead = 0;
}