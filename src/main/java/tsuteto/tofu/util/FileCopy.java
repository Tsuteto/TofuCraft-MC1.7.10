package tsuteto.tofu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileCopy
{
    private Object source;
    private File fileDest;

    public FileCopy(File fileOrg, File fileDest)
    {
        this.source = fileOrg;
        this.fileDest = fileDest;
    }

    public FileCopy(URL fileOrg, File fileDest)
    {
        this.source = fileOrg;
        this.fileDest = fileDest;
    }
    
    public boolean copy()
    {
        if (source instanceof File)
        {
            return this.copyFileToFile((File)source, fileDest); 
        }
        else if (source instanceof URL)
        {
            return this.copyResourceToFile((URL)source, fileDest);
        }
        return false;
    }

    private boolean copyResourceToFile(URL src, File dest)
    {
        InputStream is = null;
        OutputStream os = null;
        try
        {
            is = src.openStream();
            os = new FileOutputStream(dest);

            int size;
            byte[] buf = new byte[1024];
            while ((size = is.read(buf)) >= 0)
            {
                os.write(buf, 0, size);
            }
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {}
            }
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {}
            }
        }
    }

    private boolean copyFileToFile(File org, File dest)
    {
        FileInputStream is = null;
        FileOutputStream os = null;
        try
        {
            is = new FileInputStream(org);
            os = new FileOutputStream(dest);

            FileChannel chInput = null, chOutput = null;
            chInput = is.getChannel();
            chOutput = os.getChannel();

            chInput.transferTo(0, chInput.size(), chOutput);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {}
            }
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {}
            }
        }
    }

    public boolean verify()
    {
        if (source != null && fileDest != null && fileDest.exists())
        {
            String d1 = null;
            if (source instanceof File)
            {
                d1 = this.getMessageDigest((File)source);
            }
            else if (source instanceof URL)
            {
                d1 = this.getMessageDigest((URL)source);
            }
            String d2 = this.getMessageDigest(fileDest);
            return d1.equals(d2);
        }
        else
        {
            return false;
        }
    }

    private String getMessageDigest(URL file)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }

        InputStream in = null;
        try
        {
            in = file.openStream();
            byte[] dat = new byte[1024];
            int len;
            while ((len = in.read(dat)) >= 0)
            {
                md.update(dat, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
                try
                {
                    in.close();
                }
                catch (IOException e)
                {}
        }
        return this.digestToString(md);
    }
    
    private String getMessageDigest(File file)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }

        FileInputStream in = null;
        try
        {
            in = new FileInputStream(file);
            byte[] dat = new byte[1024];
            int len;
            while ((len = in.read(dat)) >= 0)
            {
                md.update(dat, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
                try
                {
                    in.close();
                }
                catch (IOException e)
                {}
        }

        return this.digestToString(md);
    }
    
    private String digestToString(MessageDigest md)
    {
        byte[] digestBytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digestBytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}