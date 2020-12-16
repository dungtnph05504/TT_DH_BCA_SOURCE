package com.nec.asia.nic.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang.StringUtils;


/**
 * Image utility class that uses imageIO. For Jpeg2000 support, requires JAI
 * imageIO api for server and JAI imageIO jre for client.
 * 
 * @author Alvin Chua
 * 
 * 02 Jul 2011 Mahesh: Added method for BMP to RAW conversion 
 * 07 AUG 2012 Mahesh: Modified to use unsigned byte to calculate width and height. 
 */
public class ImageUtil {
	
	/** The Constant FORMAT_BITMAP. */
	public static final String FORMAT_BITMAP = "BMP";
	
	/** The Constant FORMAT_GIF. */
	public static final String FORMAT_GIF = "GIF";
	
	/** The Constant FORMAT_JPEG. */
	public static final String FORMAT_JPEG = "JPG";
	
	/** The Constant FORMAT_JPEG2000. */
	public static final String FORMAT_JPEG2000 = "JPEG2000";
	
	/** The Constant FORMAT_PNG. */
	public static final String FORMAT_PNG = "PNG";
	
	/** The Constant FORMAT_TIFF. */
	public static final String FORMAT_TIFF = "TIF";

	/** The Constant FORMAT_RAW. */
	public static final String FORMAT_RAW = "RAW";
	
	static
	{
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	/**
	 * Return a list of unique supported read formats (e.g png, jpeg, gif, jpg)
	 * 
	 * @return String
	 */
	public static String getAllSupportedReadFormats() {
		return getUniqueList(ImageIO.getReaderFormatNames());
	}

	/**
	 * Return a list of unique supported write formats (e.g png, jpeg, jpg)
	 * 
	 * @return String
	 */
	public static String getAllSupportedWriteFormats() {
		return getUniqueList(ImageIO.getWriterFormatNames());
	}

	/**
	 * Converts all strings to uppercase and returns a string containing only
	 * unique values.
	 *
	 * @param strings String[]
	 * @return String
	 */
	private static String getUniqueList(String[] strings) {
		Set<String> formats = new HashSet<String>();
		for (int i = 0; i < strings.length; i++) {
			formats.add(strings[i].toUpperCase());
		}

		return StringUtils.join(formats.iterator(), ",");
	}

	/**
	 * Convert image to another format.
	 *
	 * @param infile the infile
	 * @param outfile the outfile
	 * @param newFormat String
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void convertImage(File infile, File outfile, String newFormat)
			throws UnsupportedOperationException, IOException {
		if (!StringUtils.contains(getAllSupportedWriteFormats(), newFormat)) {
			throw new UnsupportedOperationException(
					"Image format not supported by JAI: " + newFormat);
		}
		ImageIO.setUseCache(false);
		BufferedImage bufferedImage = ImageIO.read(infile);
		ImageIO.write(bufferedImage, newFormat, outfile);
	}

	/**
	 * Convert image.
	 *
	 * @param inBytes the in bytes
	 * @param outputfile the outputfile
	 * @param newFormat the new format
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void convertImage(byte[] inBytes, File outputfile,
			String newFormat) throws UnsupportedOperationException, IOException {
		if (!StringUtils.contains(getAllSupportedWriteFormats(), newFormat)) {
			throw new UnsupportedOperationException(
					"Image format not supported by JAI: " + newFormat);
		}
		ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
		ImageIO.setUseCache(false);
		BufferedImage bufferedImage = ImageIO.read(in);
		ImageIO.write(bufferedImage, newFormat, outputfile);
		in.close();
	}

	/**
	 * Convert image to another format.
	 *
	 * @param inBytes the in bytes
	 * @param newFormat String
	 * @return the byte[]
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] convertImage(byte[] inBytes, String newFormat)
			throws UnsupportedOperationException, IOException {
		if (!StringUtils.contains(getAllSupportedWriteFormats(), newFormat)) {
			throw new UnsupportedOperationException(
					"Image format not supported by JAI: " + newFormat);
		}
		ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
		ImageIO.setUseCache(false);
		BufferedImage bufferedImage = ImageIO.read(in);
		if (bufferedImage==null){
			throw new IOException("Image buffer is empty.");
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, newFormat, out);
		byte[] outBytes=out.toByteArray();
		out.close();
		in.close();
		return outBytes;
	}

	/**
	 * Resizes image proportionally by height.
	 *
	 * @param image File
	 * @param newImage File
	 * @param height int
	 * @param newFormat String
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void resizeImageByHeight(File image, File newImage,
			int height, String newFormat) throws UnsupportedOperationException,
			IOException {
		BufferedImage bufferedImage = ImageIO.read(image);
		double resizeRatio = (double) height
				/ (double) bufferedImage.getHeight();
		resizeImage(bufferedImage, newImage, resizeRatio, newFormat);
	}

	/**
	 * Resizes image porportionally by width.
	 *
	 * @param image File
	 * @param newImage File
	 * @param width int
	 * @param newFormat String
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void resizeImageByWidth(File image, File newImage, int width,
			String newFormat) throws UnsupportedOperationException, IOException {
		BufferedImage bufferedImage = ImageIO.read(image);
		double resizeRatio = (double) width / (double) bufferedImage.getWidth();
		resizeImage(bufferedImage, newImage, resizeRatio, newFormat);
	}

	/**
	 * Resizes image while maintaining the aspect ratio.
	 *
	 * @param image BufferedImage
	 * @param newImage File
	 * @param resizeRatio double
	 * @param newFormat String
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void resizeImage(BufferedImage image, File newImage,
			double resizeRatio, String newFormat)
			throws UnsupportedOperationException, IOException {
		if (!StringUtils.contains(getAllSupportedWriteFormats(), newFormat)) {
			throw new UnsupportedOperationException(
					"Image format not supported by JAI: " + newFormat);
		}

		if (resizeRatio != 1) {
			AffineTransformOp op = new AffineTransformOp(AffineTransform
					.getScaleInstance(resizeRatio, resizeRatio), null);
			BufferedImage bufferedImage = op.filter(image, null);
			ImageIO.write(bufferedImage, newFormat, newImage);
		} else {
			ImageIO.write(image, newFormat, newImage);
		}
	}
	
	/**
	 * Resizes image porportionally by width.
	 *
	 * @param image bytes
	 * @param width int
	 * @param newFormat String
	 * @return the byte[]
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] resizeImageByWidth(byte[] image, int width,
			String newFormat) throws UnsupportedOperationException, IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(image);
		ImageIO.setUseCache(false);
		BufferedImage bufferedImage = ImageIO.read(in);
		double resizeRatio = (double) width / (double) bufferedImage.getWidth();
		return resizeImageByByte(bufferedImage, resizeRatio, newFormat);
	}
	
	public static byte[] resizeImageByWidthHeight(byte[] imageBytes, int width, int height, float quality) 
			throws IOException {
		InputStream in = new ByteArrayInputStream(imageBytes);
		BufferedImage bufferImageInput = ImageIO.read(in);
		if (bufferImageInput==null){
			throw new IOException("Image buffer is empty.");
		}
		int type = bufferImageInput.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferImageInput.getType();
		BufferedImage bufferImageOutput = resizeImage(bufferImageInput, type, width, height);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageOutputStream ios = ImageIO.createImageOutputStream(output);
		// creating JPG
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = (ImageWriter) iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(quality);
		writer.setOutput(ios);
		IIOImage image = new IIOImage(bufferImageOutput, null, null);
		writer.write(null, image, iwp);
		writer.dispose();
		
		//ByteArrayInputStream bai = new ByteArrayInputStream(output.toByteArray());
		//RenderedImage out = ImageIO.read(bai);
		int size = output.toByteArray().length;

		//System.out.println(""+ size);
		return output.toByteArray();
	}
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
	
	/**
	 * Resizes image while maintaining the aspect ratio.
	 *
	 * @param image BufferedImage
	 * @param resizeRatio double
	 * @param newFormat String
	 * @return the byte[]
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static byte[] resizeImageByByte(BufferedImage image,
			double resizeRatio, String newFormat)
			throws UnsupportedOperationException, IOException {
		if (!StringUtils.contains(getAllSupportedWriteFormats(), newFormat)) {
			throw new UnsupportedOperationException(
					"Image format not supported by JAI: " + newFormat);
		}

		if (resizeRatio != 1) {
			AffineTransformOp op = new AffineTransformOp(AffineTransform
					.getScaleInstance(resizeRatio, resizeRatio), null);
			BufferedImage bufferedImage = op.filter(image, null);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, newFormat, out);
			byte[] outBytes=out.toByteArray();
			out.close();
			return outBytes;
		}
		throw new UnsupportedOperationException(
				"Image Conversion is Null ");
	}
	
	/**
	 * Convert bmp to gray scale raw.
	 *
	 * @param bmpByteArray the bmp byte array
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static byte[] convertBMPToGrayScaleRAW(byte bmpByteArray[]) throws Exception {
		if(bmpByteArray[0]!='B' || bmpByteArray[1]!= 'M'){
			throw new Exception("Not a valid BMP format");
		}
		else if(bmpByteArray[28]!=8 || bmpByteArray[29] !=0) {
			throw new Exception("Color depth ["+((bmpByteArray[29]<<8)+bmpByteArray[28])+"] is not supported");
		}
		
		//We support only uncompressed BMP
		if(bmpByteArray[30]!=0 || bmpByteArray[31]!=0 || bmpByteArray[32]!=0 || bmpByteArray[33]!=0) {
			throw new Exception("This in not a valid uncompressed BMP");
		}

		//int height = ((bmpByteArray[25] << 24) +  (bmpByteArray[24] << 16) + (bmpByteArray[23] << 8)+ bmpByteArray[22]);
		int height = littleEndianToInt(bmpByteArray[22], bmpByteArray[23], bmpByteArray[24], bmpByteArray[25]);
		if(height<0) {
			throw new Exception("Unsupported height specified in BMP header ["+height+"]");
		}
		
		//int width = ((bmpByteArray[21] << 24) +  (bmpByteArray[20] << 16) + (bmpByteArray[19] << 8)+ bmpByteArray[18]);
		int width = littleEndianToInt(bmpByteArray[18], bmpByteArray[19], bmpByteArray[20], bmpByteArray[21]);
		byte rawByteArray[] = new byte[width * height];
		byte colorPallet[] = new byte[256];
		//fill color pallet
		for(int i=0;i<256;i++) {
			colorPallet[i]= bmpByteArray[54+i*4];
		}
		
		int i=0;
		//int offsetPos = (bmpByteArray[13] << 24) +  (bmpByteArray[12] << 16) + (bmpByteArray[11] << 8)+ bmpByteArray[10];
		int offsetPos = littleEndianToInt(bmpByteArray[10], bmpByteArray[11], bmpByteArray[12], bmpByteArray[13]);
		int multiplier = (width%4==0?width/4:(width/4+1))*4;
		//System.out.println("multiplier: "+multiplier);
		for(int y = height-1; y >= 0; y--){
		    int offset =offsetPos + ( y * multiplier);
		    for(int x = 0; x < width; x++){
		    	//System.out.println("offset+x: "+(offset+x));
		    	//System.out.println("ColorPalletIndex: "+(bmpByteArray[offset+x] & 0xFF));
		    	rawByteArray[i] = colorPallet[(int)(bmpByteArray[offset+x] & 0xFF)];
		        i++;
		    }
		}
		return rawByteArray;
	}
	
	private static int littleEndianToInt(byte val1, byte val2, byte val3, byte val4) {
		return (((int) val4 & 0xFF) << 24) + (((int) val3 & 0xFF) << 16) + (((int) val2 & 0xFF) << 8) + (((int) val1 & 0xFF) << 0);
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
        int[] maskArr = new int[]{0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, 0x80, 0x100, 0x200};
        for(int i = 0; i <= 1023; i++) {
            StringBuilder sb = new StringBuilder(Integer.toBinaryString(i)).append(":").append("\t");
            for(int j = 1; j <= 10; j++) {
                sb.append(j).append("=").append(isAmputated(j, i, maskArr)).append("\t");
            }
            System.out.println(sb.toString());
        }
    }

    private static boolean isAmputated(int position, int ampMask, int[] maskArr) {
        return ((ampMask & maskArr[position - 1]) != 0);
    }

	public BufferedImage getBufferedImage(byte[] data) throws IOException {
		InputStream in = new ByteArrayInputStream(data);
		return ImageIO.read(in);
	}

	public byte[] getByteArray(BufferedImage bufferedImage, String format) {

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, format, baos);
			baos.flush();
			byte[] imageInByteArray = baos.toByteArray();
			baos.close();
			return imageInByteArray;
		} catch (Exception e) {
			return null;
		}
	}

	public byte[] resizeImage(BufferedImage image, double resizeRatio, String newFormat)
			throws UnsupportedOperationException, IOException {
		if (!StringUtils.contains(getAllSupportedWriteFormats(), newFormat)) {
			throw new UnsupportedOperationException("Image format not supported by JAI: " + newFormat);
		}

		if (resizeRatio != 1) {
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(resizeRatio, resizeRatio),
					null);
			BufferedImage bufferedImage = op.filter(image, null);
			return this.getByteArray(bufferedImage, newFormat);
		} else {
			return this.getByteArray(image, newFormat);
		}
	}
}
